package com.example.followyourshifts;

import java.time.LocalDate;

public interface CalendarCallBack {
    void onItemClick(int position, String dayText);

    void onDateSelected(LocalDate selectedDate);
}
