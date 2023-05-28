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
    static Workplace holmes_place = new Workplace("Holmes Place", 65);
    static Workplace kantri = new Workplace("kantri", 60);
    public static ArrayList<Shift> getShifts() {

//        ArrayList records = new ArrayList();
//        int numOfRecords = MySPv.getInstance().getInt("Num Of Records", DEFAULT_VALUE);
//        for (int i = 0; i < numOfRecords; i++) {
//            String s = MySPv.getInstance().getString("Rank: " + (i + 1), "");
//            if (s != "") {
//                Record record = new Gson().fromJson(s, Record.class);
//                records.add(record);
//            }
//        }
        ArrayList shifts = new ArrayList();
        LocalDate date1 = LocalDate.of(2023, 6, 1);
        LocalDate date2 = LocalDate.of(2023, 5, 10);

        Shift shift1 = new Shift(date1, LocalTime.of(9, 0), LocalTime.of(17, 0), holmes_place);
        Shift shift2 = new Shift(date2, LocalTime.of(8, 0), LocalTime.of(16, 0),kantri);

        shifts.add(shift1);
        shifts.add(shift2);
        return shifts;
    }
    public static ArrayList<Workplace> getWorkPlace() {

//        ArrayList records = new ArrayList();
//        int numOfRecords = MySPv.getInstance().getInt("Num Of Records", DEFAULT_VALUE);
//        for (int i = 0; i < numOfRecords; i++) {
//            String s = MySPv.getInstance().getString("Rank: " + (i + 1), "");
//            if (s != "") {
//                Record record = new Gson().fromJson(s, Record.class);
//                records.add(record);
//            }
//        }
        ArrayList workplaces = new ArrayList();
        holmes_place.addShift(getShifts().get(0));
        workplaces.add(holmes_place);
        kantri.addShift(getShifts().get(1));
        workplaces.add(kantri);
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
}