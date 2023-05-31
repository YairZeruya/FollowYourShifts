package com.example.followyourshifts.Logic;

import com.example.followyourshifts.Objects.Shift;
import com.example.followyourshifts.Objects.Workplace;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;

public class DataManager {
    public static final String KEY_WORKPLACE_NAME = "KEY_WORKPLACE_NAME";
    public static ArrayList<Workplace> workplaces = new ArrayList();
    public static ArrayList<Shift> shifts = new ArrayList();
    public static ArrayList<Shift> getShifts() {
        return shifts;
    }
    public static ArrayList<Workplace> getWorkPlace() {
        return workplaces;
    }

    public static ArrayList<Shift> getShiftsByMonthAndWorkplace(Month month, Workplace workplace) {
        ArrayList<Shift> shiftsByMonthAndWorkplace = new ArrayList<>();

        for (Shift shift : workplace.getShifts()) {
            if (shift.getDate().getMonth().equals(month)) {
                shiftsByMonthAndWorkplace.add(shift);
            }
        }

        return shiftsByMonthAndWorkplace;
    }

    public static void removeWorkplace(Workplace selectedWorkplace) {
        for (int i = 0; i<workplaces.size();i++) {
            if(workplaces.get(i).getName().equals(selectedWorkplace.getName())){
                workplaces.remove(i);
            }
        }
    }

    public static void removeShift(Shift selectedShift) {
        for(int i = 0; i<shifts.size();i++){
            if(selectedShift.equals(shifts.get(i))){
                shifts.remove(i);
            }
        }
    }
    public static boolean hasShiftsForDayAndMonth(int day, int month, int year) {
        // Iterate through the list of shifts and check if any shift matches the given day, month, and year
        for (Shift shift : shifts) {
            int shiftDay = shift.getDate().getDayOfMonth();
            int shiftMonth = shift.getDate().getMonthValue();
            int shiftYear = shift.getDate().getYear();
            if (shiftDay == day && shiftMonth == month && shiftYear == year) {
                return true; // Return true if a shift is found for the given day, month, and year
            }
        }
        return false; // Return false if no shift is found for the given day, month, and year
    }
}