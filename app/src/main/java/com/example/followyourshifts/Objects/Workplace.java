package com.example.followyourshifts.Objects;

import java.util.ArrayList;

public class Workplace {
    private String id; // Firestore document ID
    private String name;
    private double salaryPerHour;
    private ArrayList<Shift> shifts;


    public Workplace() {
    }


    public Workplace(String name, double salaryPerHour) {
        this.name = name;
        this.salaryPerHour = salaryPerHour;
        shifts = new ArrayList<>();
        this.id = name;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Workplace getWorkplaceByName(String name) {
        if (name.equals(this.name)) {
            return this;
        }
        return null;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(", salary Per Hour-").append(salaryPerHour);
        return sb.toString();
    }


    public void removeShift(Shift selectedShift) {
        for (int i = 0; i< shifts.size();i++){
            if(shifts.get(i).equals(selectedShift)){
                shifts.remove(i);
            }
        }
    }
}
