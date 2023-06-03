package com.example.followyourshifts.Objects;


import com.example.followyourshifts.Logic.DataManager;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class Shift {
        private String date;
        private String startTime;
        private String endTime;
        private double income;
        private double extraHours1_25;
        private double extraHours1_5;
        private Workplace workplace;
        private boolean isHoliday_isSaturday;

    public Shift() {
    }

    public Shift(String date, String startTime, String endTime, Workplace workplace) {
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
            tempIncome = salaryPerHour * duration;
        } else if (duration <= 11) {
            tempIncome = salaryPerHour * 9;
            extraHours1_25 = duration - 9;
            tempIncome = salaryPerHour * 1.25 * extraHours1_25;
        } else {
            tempIncome = salaryPerHour * 9;
            extraHours1_25 = 2;
            tempIncome += salaryPerHour * 1.25 * extraHours1_25;
            extraHours1_5 = duration - 11;
            tempIncome += salaryPerHour * 1.5 * extraHours1_5;
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

    public double calculateDuration() {
        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);
        Duration duration = Duration.between(start,end);
        long minutes = duration.toMinutes();
        double hours = minutes / 60.0;
        return hours;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
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

//    @Override
//    public String toString() {
//        return  date +
//                " ," + startTime +
//                "-" + endTime +
//                ", Workplace-" + workplace.getName() + " " + (workplace.getSalaryPerHour() + "");
//    }
@Override
public String toString() {
    String formattedDate = date.toString().replace("-", "");
    String formattedStartTime = startTime.toString().replace(":", "");
    String formattedEndTime = endTime.toString().replace(":", "");
    String formattedWorkplaceName = workplace.getName().replace(" ", "_");
    String formattedSalaryPerHour = Double.toString(workplace.getSalaryPerHour()).replace(".", "_");

    return formattedDate +
            "_" + formattedStartTime +
            "_" + formattedEndTime +
            "_" + formattedWorkplaceName +
            "_" + formattedSalaryPerHour;
}

}
