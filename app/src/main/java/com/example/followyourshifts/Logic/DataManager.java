package com.example.followyourshifts.Logic;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.followyourshifts.Objects.Shift;
import com.example.followyourshifts.Objects.Workplace;
import com.example.followyourshifts.Utilities.SignalGenerator;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;


public class DataManager {

    public static final String KEY_WORKPLACE_NAME = "KEY_WORKPLACE_NAME";
    private static boolean callbackInvoked = false;
    public static final int VIBRATE_TIME = 1000;
    public static ArrayList<Workplace> workplaces = new ArrayList();
    public static ArrayList<Shift> shifts = new ArrayList();
    public static FirebaseDatabase database;
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static CollectionReference shiftsCollection = db.collection("Shifts");
    public static CollectionReference workplacesCollection = db.collection("Workplaces");
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


    public static void addShift(Shift shift, String shiftId) {
        // Set the desired ID for the shift object
        shift.setId(shiftId);
        // Add the shift to Firestore with the specified document ID
        addShiftFireStore(shift, shiftId).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Shift added successfully
                SignalGenerator.getInstance().toast("Shift added with ID: " + shiftId, Toast.LENGTH_SHORT);
                // Handle the shift ID or any other desired operation
            } else {
                // Error occurred while adding the shift
                Exception e = task.getException();
                // Handle the error
            }
        });
    }

    public static void addWorkplace(Workplace workplace, String workplaceId) {
        // Set the desired ID for the workplace object
        workplace.setId(workplaceId);

        // Add the workplace to Firestore with the specified document ID
        addWorkplaceFireStore(workplace, workplaceId).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Workplace added successfully
                SignalGenerator.getInstance().toast("Workplace added with ID: " + workplaceId, Toast.LENGTH_SHORT);
            } else {
                // Error occurred while adding the workplace
                Exception e = task.getException();
                // Handle the error
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
                // Error occurred while retrieving shifts
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
                // Error occurred while retrieving workplaces
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
        // Create a new document in the "shifts" collection with the specified ID
        return shiftsCollection.document(shiftId).set(shift);
    }

    public static Task<Void> addWorkplaceFireStore(Workplace workplace, String workplaceId) {
        // Create a new document in the "workplaces" collection with the specified ID
        return workplacesCollection.document(workplaceId).set(workplace);
    }

    public static void removeWorkplace(Workplace selectedWorkplace) {
        workplaces.remove(selectedWorkplace);

        // Remove the workplace from Firestore
        workplacesCollection.document(selectedWorkplace.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Workplace deleted successfully
                    SignalGenerator.getInstance().toast("Workplace deleted", Toast.LENGTH_SHORT);
                })
                .addOnFailureListener(e -> {
                    // Error occurred while deleting the workplace
                    SignalGenerator.getInstance().toast("Failed to delete workplace", Toast.LENGTH_SHORT);
                });
    }

    public static void removeShift(Shift selectedShift) {
        // Remove the shift from the ArrayList
      shifts.remove(selectedShift);

        // Remove the shift from Firestore
        shiftsCollection.document(selectedShift.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Shift deleted successfully
                    SignalGenerator.getInstance().toast("Shift deleted", Toast.LENGTH_SHORT);
                })
                .addOnFailureListener(e -> {
                    // Error occurred while deleting the shift
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
        for (Shift shift : workplace.getShifts()) {
            Month shiftMonth = LocalDate.parse(shift.getDate()).getMonth();
            if (shiftMonth.equals(month)) {
                shiftsByMonthAndWorkplace.add(shift);
            }
        }
        return shiftsByMonthAndWorkplace;
    }

    public static void updateDatabaseOnAppFinish() {
        // Get the Firestore instance

        // Create a new batch write operation
        WriteBatch batch = db.batch();

        // Update the shifts collection
        ArrayList<Shift> shifts = DataManager.getShifts();
        for (Shift shift : shifts) {
            DocumentReference shiftDocRef = shiftsCollection.document(shift.getId());
            batch.set(shiftDocRef, shift);
        }

        // Update the workplaces collection
        ArrayList<Workplace> workplaces = DataManager.getWorkPlaces();
        for (Workplace workplace : workplaces) {
            DocumentReference workplaceDocRef = workplacesCollection.document(workplace.getId());
            batch.set(workplaceDocRef, workplace);
        }

        // Commit the batched write operation
        batch.commit()
                .addOnSuccessListener(aVoid -> {
                    // Batch write operation successful
                    SignalGenerator.getInstance().toast("Database updated on app finish", Toast.LENGTH_SHORT);
                })
                .addOnFailureListener(e -> {
                    // Error occurred during batch write operation
                    SignalGenerator.getInstance().toast("Failed to update database on app finish", Toast.LENGTH_SHORT);
                });
    }


    public static void updateWorkplaceShiftsInFirestore(Workplace workplace) {
        // Get the Firestore document reference for the specific workplace
        DocumentReference workplaceRef = workplacesCollection.document(workplace.getId());

        // Update the shifts field of the workplace document with the updated shifts list
        workplaceRef.update("shifts", workplace.getShifts())
                .addOnSuccessListener(aVoid -> {
                    // Shifts updated successfully
                    SignalGenerator.getInstance().toast("Workplace shifts updated", Toast.LENGTH_SHORT);
                })
                .addOnFailureListener(e -> {
                    // Error occurred while updating shifts
                    SignalGenerator.getInstance().toast("Failed to update workplace shifts", Toast.LENGTH_SHORT);
                });
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