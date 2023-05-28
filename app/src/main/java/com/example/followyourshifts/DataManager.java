package com.example.followyourshifts;

import com.example.followyourshifts.Objects.Shift;
import com.example.followyourshifts.Objects.Workplace;

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
        shifts.add(new Shift(holmes_place, holmes_place.getSalaryPerHour(),0,0,0));
        shifts.add(new Shift(kantri,kantri.getSalaryPerHour(),0,0,0));
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
        workplaces.add(holmes_place);
        workplaces.add(kantri);
        return workplaces;
    }
}