package com.example.followyourshifts.Objects;

import java.util.ArrayList;

public class Workplace {
    private String name;
    private double salaryPerHour;
    private ArrayList<Shift> shifts;

    public Workplace(String name, double salaryPerHour) {
        this.name = name;
        this.salaryPerHour = salaryPerHour;
        this.shifts = new ArrayList<>();
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

    public void setSalaryPerHour(int salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
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


    @Override
    public String toString() {
        return name  + ", salary Per Hour-" + salaryPerHour;
    }

    public void removeShift(Shift selectedShift) {
        for (int i = 0; i< shifts.size();i++){
            if(shifts.get(i).equals(selectedShift)){
                shifts.remove(i);
            }
        }
    }
}
