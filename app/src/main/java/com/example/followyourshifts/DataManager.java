package com.example.followyourshifts;

import java.util.ArrayList;

public class DataManager {
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
        shifts.add(new Shift("Holme Place",50,5,0,0));
        shifts.add(new Shift("kantri",55,10,0,0));
        return shifts;
    }
}
