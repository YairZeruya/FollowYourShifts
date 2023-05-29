package com.example.followyourshifts.Objects;

import com.example.followyourshifts.Objects.Workplace;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Shift {
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;
        private double income;
        private double extraHours1_25;
        private double extraHours1_5;
        private Workplace workplace;

        public Shift(LocalDate date, LocalTime startTime, LocalTime endTime, Workplace workplace) {
            this.date = date;
            this.startTime = startTime;
            this.endTime = endTime;
            this.workplace = workplace;
            calculateIncome();
        }

    public double calculateIncome() {
        double duration = calculateDuration();
        double salaryPerHour = workplace.getSalaryPerHour();
        double baseSalary = salaryPerHour;

        double extraHours1_25 = 0; // Variable to store the number of hours worked with 1.25 multiplier
        double extraHours1_5 = 0;  // Variable to store the number of hours worked with 1.5 multiplier

        if (duration > 9) {
            extraHours1_25 = Math.min(duration - 9, 2); // Calculate the number of hours with 1.25 multiplier (up to 2 hours)
            baseSalary *= 1.25; // Apply 1.25 multiplier for hours from 9-11
        }

        if (duration > 11) {
            extraHours1_5 = duration - 11; // Calculate the number of hours with 1.5 multiplier (hours after 11)
            baseSalary *= 1.5; // Apply 1.5 multiplier for hours from 11 onwards
        }

        income = baseSalary * duration;

        // Store the extra hours worked with multipliers in the shift object
        this.extraHours1_25 = extraHours1_25;
        this.extraHours1_5 = extraHours1_5;

        return income;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shift shift = (Shift) o;
        return Double.compare(shift.income, income) == 0 &&
                Double.compare(shift.extraHours1_25, extraHours1_25) == 0 &&
                Double.compare(shift.extraHours1_5, extraHours1_5) == 0 &&
                Objects.equals(date, shift.date) &&
                Objects.equals(startTime, shift.startTime) &&
                Objects.equals(endTime, shift.endTime) &&
                Objects.equals(workplace, shift.workplace);
    }

    public double getExtraHours1_25() {
        return extraHours1_25;
    }

    public void setExtraHours1_25(double extraHours1_25) {
        this.extraHours1_25 = extraHours1_25;
    }

    public double getExtraHours1_5() {
        return extraHours1_5;
    }

    public void setExtraHours1_5(double extraHours1_5) {
        this.extraHours1_5 = extraHours1_5;
    }

//    public long calculateDuration() {
//        Duration duration = Duration.between(startTime, endTime);
//        return duration.toHours();
//    }
    public double calculateDuration() {
        Duration duration = Duration.between(startTime, endTime);
        long minutes = duration.toMinutes();
        double hours = minutes / 60.0;
        return hours;
    }


    // Getters for other properties

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public Workplace getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

    public double getIncome() {
            return income;
        }

    @Override
    public String toString() {
        return "Shift{" +
                "date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", workplace=" + workplace +
                '}';
    }
}
