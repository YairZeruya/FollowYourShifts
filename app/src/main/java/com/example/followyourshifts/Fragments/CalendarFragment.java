package com.example.followyourshifts.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.followyourshifts.Adapters.CalendarAdapter;
import com.example.followyourshifts.Interfaces.CalendarCallBack;
import com.example.followyourshifts.Logic.DataManager;
import com.example.followyourshifts.Utilities.CalendarUtils;
import com.example.followyourshifts.R;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarFragment extends Fragment implements CalendarAdapter.OnItemListener  {

    private CalendarCallBack calendarCallBack;
    private TextView month_and_year;
    private RecyclerView calendar_recycler_view;
    private Button next_month_button;
    private Button previous_month_button;
    private int selectedItemPosition = -1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);
        findViews(view);

        onClickListeners(view);
        setMonthView(view);
        return view;
    }

    private void onClickListeners(View view) {
        next_month_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMonthAction(view);
            }
        });
        previous_month_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousMonthAction(view);
            }
        });
    }

    private void findViews(View view)
    {
        CalendarUtils.selectedDate = LocalDate.now();
        calendar_recycler_view = view.findViewById(R.id.calendar_recycler_view);
        month_and_year = view.findViewById(R.id.month_and_year);
        next_month_button = view.findViewById(R.id.next_month_button);
        previous_month_button = view.findViewById(R.id.previous_month_button);
    }

    private void setMonthView(View view)
    {
        month_and_year.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this,selectedItemPosition);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(), 7);
        calendar_recycler_view.setLayoutManager(layoutManager);
        calendar_recycler_view.setAdapter(calendarAdapter);

        // Set the selected item position in the adapter
        if (selectedItemPosition != -1) {
            calendar_recycler_view.scrollToPosition(selectedItemPosition);
        }
    }

    public void setSelectedDate(LocalDate selectedDate) {
        CalendarUtils.selectedDate = selectedDate;
        setMonthView(getView());
    }


    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = CalendarUtils.selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= CalendarUtils.DAYS_IN_MONTH; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView(view);
    }

    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView(view);
    }

    @Override
    public void onItemClick(int position, String dayText) {
        if (!dayText.equals("")) {
            // Update the selected date and highlight the selected day
            setSelectedDate(CalendarUtils.selectedDate.withDayOfMonth(Integer.parseInt(dayText)));
            if (calendarCallBack != null) {
                calendarCallBack.onDateSelected(CalendarUtils.selectedDate);
            }
        }
    }

    public void setCalendarCallBack(CalendarCallBack calendarCallBack) {
        this.calendarCallBack = calendarCallBack;
    }
}
