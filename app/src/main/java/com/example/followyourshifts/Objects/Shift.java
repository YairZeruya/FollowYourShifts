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
        double tempIncome = 0;

        if (duration <= 9) {
            tempIncome = salaryPerHour * duration; // Calculate base salary for hours up to 9
        } else if (duration <= 11) {
            tempIncome = salaryPerHour * 9; // Calculate base salary for the first 9 hours
            extraHours1_25 = duration - 9; // Calculate the number of hours with 1.25 multiplier
            tempIncome = salaryPerHour * 1.25 * extraHours1_25; // Add the extra hours with 1.25 multiplier
        } else {
            tempIncome = salaryPerHour * 9; // Calculate base salary for the first 9 hours
            extraHours1_25 = 2; // Number of hours with 1.25 multiplier (9-11)
            tempIncome += salaryPerHour * 1.25 * extraHours1_25; // Add the extra hours with 1.25 multiplier
            extraHours1_5 = duration - 11; // Calculate the number of hours with 1.5 multiplier (hours after 11)
            tempIncome += salaryPerHour * 1.5 * extraHours1_5; // Add the extra hours with 1.5 multiplier
        }

        income = tempIncome;

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
        return  date +
                " ," + startTime +
                "-" + endTime +
                ", Workplace-" + workplace.getName();
    }
}
