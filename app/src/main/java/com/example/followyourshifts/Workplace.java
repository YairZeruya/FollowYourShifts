package com.example.followyourshifts;

import java.util.ArrayList;

public class Workplace {
    private String workplaceName;
    private double salaryPerHour;
    private double hours100;
    private double hours125;
    private double hours150;
    private double totalSalary;
    //maybe a list of dates.

    public Workplace(String workplaceName, double salaryPerHour) {
        this.workplaceName = workplaceName;
        this.salaryPerHour = salaryPerHour;
    }

    public String getWorkplaceName() {
        return workplaceName;
    }

    public void setWorkplaceName(String workplaceName) {
        this.workplaceName = workplaceName;
    }

    public double getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setSalaryPerHour(double salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    public double getHours100() {
        return hours100;
    }

    public void setHours100(double hours100) {
        this.hours100 = hours100;
    }

    public double getHours125() {
        return hours125;
    }

    public void setHours125(double hours125) {
        this.hours125 = hours125;
    }

    public double getHours150() {
        return hours150;
    }

    public void setHours150(double hours150) {
        this.hours150 = hours150;
    }

    public double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(double totalSalary) {
        this.totalSalary = totalSalary;
    }

    @Override
    public String toString() {
        return "Workplace{" +
                "workplaceName='" + workplaceName + '\'' +
                ", salaryPerHour=" + salaryPerHour +
                ", hours100=" + hours100 +
                ", hours125=" + hours125 +
                ", hours150=" + hours150 +
                ", totalSalary=" + totalSalary +
                '}';
    }
}
