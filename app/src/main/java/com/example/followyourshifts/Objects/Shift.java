package com.example.followyourshifts.Objects;

import com.example.followyourshifts.Objects.Workplace;

public class Shift {
    private Workplace workplace;
    private double salaryPerHour;
    private int hours100;
    private int hours125;
    private int hours150;

    public Shift(Workplace workplace, double salaryPerHour, int hours100, int hours125, int hours150) {
        this.workplace = workplace;
        this.salaryPerHour = salaryPerHour;
        this.hours100 = hours100;
        this.hours125 = hours125;
        this.hours150 = hours150;
    }

    public Workplace getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

    public double getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setSalaryPerHour(double salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    public int getHours100() {
        return hours100;
    }

    public void setHours100(int hours100) {
        this.hours100 = hours100;
    }

    public int getHours125() {
        return hours125;
    }

    public void setHours125(int hours125) {
        this.hours125 = hours125;
    }

    public int getHours150() {
        return hours150;
    }

    public void setHours150(int hours150) {
        this.hours150 = hours150;
    }
}
