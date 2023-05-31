package com.example.followyourshifts.Interfaces;

import java.time.LocalDate;

public interface CalendarCallBack {
    void onItemClick(int position, String dayText);

    void onDateSelected(LocalDate selectedDate);
}
