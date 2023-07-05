package com.example.followyourshifts.Logic;

import android.widget.Toast;


import com.example.followyourshifts.Activities.MainActivity;
import com.example.followyourshifts.Objects.Shift;
import com.example.followyourshifts.Objects.Workplace;
import com.example.followyourshifts.Utilities.SignalGenerator;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


public class DataManager {

    public static final String KEY_WORKPLACE_NAME = "KEY_WORKPLACE_NAME";
    public static final int VIBRATE_TIME = 1000;
    public static ArrayList<Workplace> workplaces = new ArrayList();
    public static ArrayList<Shift> shifts = new ArrayList();
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static FirebaseAuth auth = FirebaseAuth.getInstance();
    public static CollectionReference shiftsCollection;
    public static CollectionReference workplacesCollection;
    private static String userId;

    public static ArrayList<Shift> getShifts() {
        return shifts;
    }


    public static void init() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            userId = MainActivity.getUserID();
            // Initialize the Firestore collection references with the correct user ID
            shiftsCollection = db.collection("Users").document(userId).collection("Shifts");
            workplacesCollection = db.collection("Users").document(userId).collection("Workplaces");
        } else {
            SignalGenerator.getInstance().toast("User not found", Toast.LENGTH_SHORT);
        }
    }


    public static void addShift(Shift shift, String shiftId) {
        // Set the desired ID for the shift object
        shift.setId(shiftId);
        addShiftFireStore(shift, shiftId).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                SignalGenerator.getInstance().toast("Shift added with ID: " + shiftId, Toast.LENGTH_SHORT);
            } else {
                Exception e = task.getException();
                SignalGenerator.getInstance().toast("Failed to write shift to database",Toast.LENGTH_SHORT);
            }
        });
    }



    public static void addWorkplace(Workplace workplace, String workplaceId) {
        // Set the desired ID for the workplace object
        workplace.setId(workplaceId);
        addWorkplaceFireStore(workplace, workplaceId).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                SignalGenerator.getInstance().toast("Workplace added with ID: " + workplaceId, Toast.LENGTH_SHORT);
            } else {
                Exception e = task.getException();
                SignalGenerator.getInstance().toast("Failed to write workplace to database",Toast.LENGTH_SHORT);
            }
        });
    }
    public static void getShiftsFromFirestore(ShiftListener listener) {
            shiftsCollection.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Shifts retrieved successfully
                    ArrayList<Shift> shifts = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Shift shift = document.toObject(Shift.class);
                        shifts.add(shift);
                    }
                    listener.onShiftsRetrieved(shifts);
                } else {
                    Exception e = task.getException();
                    listener.onError(e);
                }
            });
        }



    public static void getWorkplacesFromFirestore(WorkplaceListener listener) {
        workplacesCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Workplaces retrieved successfully
                ArrayList<Workplace> workplaces = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Workplace workplace = document.toObject(Workplace.class);
                    workplaces.add(workplace);
                }
                listener.onWorkplacesRetrieved(workplaces);
            } else {
                Exception e = task.getException();
                listener.onError(e);
            }
        });
    }

    public interface ShiftListener {
        void onShiftsRetrieved(ArrayList<Shift> shifts);
        void onError(Exception e);
    }

    public interface WorkplaceListener {
        void onWorkplacesRetrieved(ArrayList<Workplace> workplaces);
        void onError(Exception e);
    }

    public static Task<Void> addShiftFireStore(Shift shift, String shiftId) {
        // Get the UID of the current user
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            return null;
        }
        String uid = currentUser.getUid();
        // Create a new document in the "shifts" subcollection of the user's document with the specified ID
        DocumentReference shiftDocRef = db.collection("Users").document(uid).collection("Shifts").document(shiftId);
        return shiftDocRef.set(shift);
    }
    public static Task<Void> addWorkplaceFireStore(Workplace workplace, String workplaceId) {
        // Get the UID of the current user
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            return null;
        }
        String uid = currentUser.getUid();
        // Create a new document in the "workplaces" subcollection of the user's document with the specified ID
        DocumentReference workplaceDocRef = db.collection("Users").document(uid)
                .collection("Workplaces").document(workplaceId);

        // Set the document data
        return workplaceDocRef.set(workplace);
    }

    public static void assignShiftsToWorkplaces() {
        for (Shift shift : shifts) {
            for (Workplace workplace : workplaces) {
                if (workplace.getName().equals(shift.getWorkplaceName()) && workplace.getSalaryPerHour() == shift.getWorkplaceSalaryPerHour()) {
                    workplace.getShifts().add(shift);
                    break;
                }
            }
        }
    }


    public static void removeWorkplace(Workplace selectedWorkplace) {
        workplaces.remove(selectedWorkplace);
        // Remove the workplace from Firestore
        workplacesCollection.document(selectedWorkplace.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    SignalGenerator.getInstance().toast("Workplace deleted successfully", Toast.LENGTH_SHORT);
                })
                .addOnFailureListener(e -> {
                    SignalGenerator.getInstance().toast("Failed to delete workplace", Toast.LENGTH_SHORT);
                });
    }

    public static void removeShift(Shift selectedShift) {
        shifts.remove(selectedShift);
        // Remove the shift from Firestore
        shiftsCollection.document(selectedShift.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    SignalGenerator.getInstance().toast("Shift deleted successfully", Toast.LENGTH_SHORT);
                })
                .addOnFailureListener(e -> {
                    SignalGenerator.getInstance().toast("Failed to delete shift", Toast.LENGTH_SHORT);
                });
    }


    public static ArrayList<Workplace> getWorkPlaces() {
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

        for (Shift shift : shifts) {
            Month shiftMonth = LocalDate.parse(shift.getDate()).getMonth();
            if (shiftMonth.equals(month) && shift.getWorkplaceName().equals(workplace.getName())) {
                shiftsByMonthAndWorkplace.add(shift);
            }
        }

        return shiftsByMonthAndWorkplace;
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


    public static Workplace getWorkplaceByName(String workplaceName) {
        for (Workplace workplace: workplaces){
            if(workplaceName.equals(workplace.getName())){
                return workplace;
            }
        }
        return null;
    }

}