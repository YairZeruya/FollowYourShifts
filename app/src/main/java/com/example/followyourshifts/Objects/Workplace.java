package com.example.followyourshifts.Objects;

import java.util.ArrayList;

public class Workplace {
    private String name;
    private double salaryPerHour;
    private ArrayList<Shift> shifts;

    public Workplace() {
    }

    public Workplace(String name, double salaryPerHour) {
        this.name = name;
        this.salaryPerHour = salaryPerHour;
        shifts = new ArrayList<>();
    }

    public void addShift(Shift shift) {
        shifts.add(shift);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalaryPerHour(double salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    public void setShifts(ArrayList<Shift> shifts) {
        this.shifts = shifts;
    }

    public double getSalaryPerHour() {
        return salaryPerHour;
    }


    public ArrayList<Shift> getShifts() {
        return shifts;
    }

    public Workplace getWorkplaceByName(String name) {
        if (name.equals(this.name)) {
            return this;
        }
        return null;
    }


//    @Override
//    public String toString() {
//        return name  + ", salary Per Hour-" + salaryPerHour;
//    }
@Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(name).append(", salary Per Hour-").append(salaryPerHour);
    return sb.toString();
}

//    public static Workplace fromString(String workplaceString) {
//        String[] parts = workplaceString.split("\n");
//
//        String workplaceInfo = parts[0];
//        String[] workplaceInfoParts = workplaceInfo.split(", salary Per Hour-");
//        String name = workplaceInfoParts[0];
//        double salaryPerHour = Double.parseDouble(workplaceInfoParts[1]);
//
//        Workplace workplace = new Workplace(name, salaryPerHour);
//
//        for (int i = 1; i < parts.length; i++) {
//            Shift shift = Shift.fromString(parts[i]);
//            workplace.addShift(shift);
//        }
//
//        return workplace;
//    }



    public void removeShift(Shift selectedShift) {
        for (int i = 0; i< shifts.size();i++){
            if(shifts.get(i).equals(selectedShift)){
                shifts.remove(i);
            }
        }
    }
}
