package com.example.followyourshifts.Logic;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.followyourshifts.Objects.Shift;
import com.example.followyourshifts.Objects.Workplace;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class DataManager {
    public static final String KEY_WORKPLACE_NAME = "KEY_WORKPLACE_NAME";
    private static boolean callbackInvoked = false;
    public static final int VIBRATE_TIME = 1000;
    public static ArrayList<Workplace> workplaces = new ArrayList();
    public static ArrayList<Shift> shifts = new ArrayList();
    public static FirebaseDatabase database;
    public static DatabaseReference workplacesRef;
    public static DatabaseReference shiftsRef;

    public static ArrayList<Shift> getShifts() {
        return shifts;
    }

    public DataManager() {
        database = FirebaseDatabase.getInstance();
        workplacesRef = database.getReference("Workplaces");
        shiftsRef = database.getReference("Shifts");
    }
    //    public static void removeWorkplace(Workplace selectedWorkplace) {
//        workplaces.remove(selectedWorkplace);
//        DatabaseReference workplacesRef = database.getReference("Workplaces");
//        workplacesRef.child(selectedWorkplace.getName()).removeValue();
//    }



    public static ArrayList<Workplace> getWorkPlace() {
        return workplaces;
    }
    public static void setWorkplaceArrayList(ArrayList<Workplace> workplaceArrayList){
        workplaces = workplaceArrayList;
    }
    public static void setShiftsArrayList(ArrayList<Shift> shiftsArrayList){
        shifts = shiftsArrayList;
    }

    public static ArrayList<Shift> getShiftsByMonthAndWorkplace(Month month, Workplace workplace) {
        ArrayList<Shift> shiftsByMonthAndWorkplace = new ArrayList<>();
        for (Shift shift : workplace.getShifts()) {
            Month shiftMonth = LocalDate.parse(shift.getDate()).getMonth();
            if (shiftMonth.equals(month)) {
                shiftsByMonthAndWorkplace.add(shift);
            }
        }
        return shiftsByMonthAndWorkplace;
    }




    public static void removeWorkplace(Workplace selectedWorkplace) {

        for (int i = 0; i < workplaces.size(); i++) {
            if (workplaces.get(i).getName().equals(selectedWorkplace.getName())) {
                workplaces.remove(i);
                workplacesRef.child(selectedWorkplace.getName()).removeValue();
            }
        }
    }



    public static void removeShift(Shift selectedShift) {
        for (int i = 0; i < shifts.size(); i++) {
            if (selectedShift.toString().equals(shifts.get(i).toString())) {
                shifts.remove(i);
                shiftsRef.child(selectedShift.toString()).removeValue();
            }
        }
    }

    public interface CallBack_shift {
        public void onDataReceived(Shift shift);
    }

    public static boolean hasShiftsForDayAndMonth(int day, int month, int year) {
        for (Shift shift : shifts) {
            int shiftDay = LocalDate.parse(shift.getDate()).getDayOfMonth();
            int shiftMonth = LocalDate.parse(shift.getDate()).getMonthValue();
            int shiftYear = LocalDate.parse(shift.getDate()).getYear();
            if (shiftDay == day && shiftMonth == month && shiftYear == year) {
                return true;
            }
        }
        return false;
    }


    public static void addWorkplaceToDB(Workplace workplace) {
        workplacesRef.child(workplace.getName()).setValue(workplace);
    }


    public static void addShift(Shift shift) {
        shiftsRef.child(shift.toString()).setValue(shift);
    }

    public interface CallBack_ShiftArrayList {
        public void onDataReceived(ArrayList<Shift> shifts);
    }


    public static void getWorkplace(CallBack_workplace callBack_workplace){

    }
    public static void getAllShifts(){

        //DatabaseReference shiftsRef = database.getReference("Shifts");
        shiftsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ArrayList<Shift> shifts = new ArrayList<>();
                    for(DataSnapshot child: snapshot.getChildren()) {
                        try {
                            Shift shift = child.getValue(Shift.class);
                            shifts.add(shift);
                        } catch (Exception e) {

                        }
                    }
                    setShiftsArrayList(shifts);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public interface CallBack_workplaces {
        public void onDataReceived(ArrayList<Workplace> workplaces);
    }

    public interface DataStatusWorkplaces{
        void DataIsLoaded(ArrayList<Workplace> workplaces, ArrayList<String> keys);
    }


    public static void getAllWorkPlaces2(final DataStatusWorkplaces dataStatus) {
        //DatabaseReference workplacesRef = database.getReference("Workplaces");
        workplacesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                workplaces.clear();
                ArrayList<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode: snapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Workplace workplace = keyNode.getValue(Workplace.class);
                    workplaces.add(workplace);
                }
                dataStatus.DataIsLoaded(workplaces,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any cancellation events or errors
            }
        });
    }
    public interface DataStatusShifts{
        void DataIsLoaded(ArrayList<Shift> shifts, ArrayList<Workplace> workplaces);
    }
    public static void getAllShifts2(final DataStatusShifts dataStatusShifts){
        shiftsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shifts.clear();
                ArrayList<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode: snapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Shift shift = keyNode.getValue(Shift.class);
                    shifts.add(shift);
                }
                for (Shift shift: shifts){
                    Workplace temp = shift.getWorkplace();
                    Workplace workplace = new Workplace(temp.getName(),temp.getSalaryPerHour());
                    if(workplaces.size() == 0){
                        workplaces.add(workplace);
                    }
                    else {
                        for (Workplace workplace1 : workplaces) {
                            if (!(workplace.getName().equals(workplace1.getName()))) {
                                workplaces.add(workplace);
                            }
                        }
                    }
                }
                assignShiftsToWorkplaces(shifts, workplaces);
                dataStatusShifts.DataIsLoaded(shifts,workplaces);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any cancellation events or errors
            }
        });
    }




    public static void getAllWorkPlaces() {
        //DatabaseReference workplacesRef = database.getReference("Workplaces");
        workplacesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ArrayList<Workplace> workplaces = new ArrayList<>();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        try {
                            Workplace workplace = child.getValue(Workplace.class);
                            workplaces.add(workplace);
                        } catch (Exception e) {
                            // Handle any exceptions that occur during data retrieval
                        }
                    }
                    setWorkplaceArrayList(workplaces);
//                    // Invoke the callback function if it hasn't been invoked already
//                    if (callBack_workplaces != null) {
//                        callBack_workplaces.onDataReceived(workplaces);
//                    }
//                } else {
//
//                    if (callBack_workplaces != null) {
//                        callBack_workplaces.onDataReceived(null);
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any cancellation events or errors
            }
        });
    }
    public static void assignShiftsToWorkplaces(ArrayList<Shift> shifts, ArrayList<Workplace> workplaces) {
        for (Shift shift : shifts) {
            String workplaceName = shift.getWorkplace().getName();
            for (Workplace workplace : workplaces) {
                if (workplace.getName().equals(workplaceName)) {
                    workplace.addShift(shift);
                    break;
                }
            }
        }
    }



    //    public static void getAllWorkPlaces(CallBack_workplaces callBack_workplaces){
//        workplacesRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    ArrayList<Workplace> workplaces = new ArrayList<>();
//                    for(DataSnapshot child: snapshot.getChildren()){
//                        try{
//                            Workplace workplace = child.getValue(Workplace.class);
//                            workplaces.add(workplace);
//                        }catch (Exception e){
//
//                        }
//                        if(callBack_workplaces != null) {
//                            callBack_workplaces.onDataReceived(workplaces);
//                        }
//                    }
//                } else {
//                    if(callBack_workplaces != null) {
//                        callBack_workplaces.onDataReceived(null);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
    public static void getShiftsByWorkplace(String workplaceName, CallBack_ShiftArrayList callBack_shift) {
        shiftsRef.orderByChild("workplace/name").equalTo(workplaceName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ArrayList<Shift> shifts = new ArrayList<>();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        try {
                            Shift shift = child.getValue(Shift.class);
                            shifts.add(shift);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (callBack_shift != null) {
                        callBack_shift.onDataReceived(shifts);
                    }
                } else {
                    if (callBack_shift != null) {
                        callBack_shift.onDataReceived(null);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }


    public interface callBack_String {
        public  void onDataReceived(String name);
    }

    public interface CallBack_workplace {
        public  void onDataReceived(Workplace workplace);
    }



    public static void getWorkplaceByName(String workplaceName, CallBack_workplace callBack_workplace) {
        workplacesRef.child(workplaceName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Workplace workplace = dataSnapshot.getValue(Workplace.class);
                Log.d("pttt", "workplace is " + workplace.getName());
                if(callBack_workplace != null) {
                    callBack_workplace.onDataReceived(workplace);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
            }
        });
    }

}