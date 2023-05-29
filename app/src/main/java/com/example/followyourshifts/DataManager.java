package com.example.followyourshifts;

import com.example.followyourshifts.Objects.Shift;
import com.example.followyourshifts.Objects.Workplace;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;

public class DataManager {
    public static final String KEY_WORKPLACE_NAME = "KEY_WORKPLACE_NAME";
    public static final String KEY_SALARY_PER_HOUR = "KEY_SALARY_PER_HOUR";
    public static final String KEY_HOURS_100 = "KEY_HOURS_100";
    public static final String KEY_HOURS_125 = "KEY_HOURS_125";
    public static final String KEY_HOURS_150 = "KEY_HOURS_150";
    public static final String KEY_TOTAL_SALARY = "KEY_TOTAL_SALARY";
    public static ArrayList<Workplace> workplaces = new ArrayList();
    public static ArrayList<Shift> shifts = new ArrayList();
    private static boolean isWorkplaceInit = false;
    private static boolean isShiftInit = false;
    static Workplace holmes_place = new Workplace("Holmes Place", 65);
    static Workplace kantri = new Workplace("kantri", 60);
    public static ArrayList<Shift> getShifts() {
        if(!isShiftInit) {
            LocalDate date1 = LocalDate.of(2023, 6, 1);
            LocalDate date2 = LocalDate.of(2023, 5, 10);

//            Shift shift1 = new Shift(date1, LocalTime.of(9, 0), LocalTime.of(17, 0), holmes_place);
//            Shift shift2 = new Shift(date2, LocalTime.of(8, 0), LocalTime.of(16, 0), kantri);
//
//            shifts.add(shift1);
//            shifts.add(shift2);
        }
        return shifts;
    }
    public static ArrayList<Workplace> getWorkPlace() {

        if(!isWorkplaceInit) {
            //holmes_place.addShift(getShifts().get(0));
            //workplaces.add(holmes_place);
            //kantri.addShift(getShifts().get(1));
            //workplaces.add(kantri);
        }
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