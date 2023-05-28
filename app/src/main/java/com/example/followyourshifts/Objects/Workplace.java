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
    public Workplace getWorkplaceByName(String name){
        if(this.name.equals(name)){
            return this;
        }
        return null;
    }

    // Getters and setters for other properties


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

    @Override
    public String toString() {
        return "Workplace{" +
                "name='" + name + '\'' +
                ", salaryPerHour=" + salaryPerHour +
                '}';
    }
}
