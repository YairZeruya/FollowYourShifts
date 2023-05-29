package com.example.followyourshifts.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.Toast;

import com.example.followyourshifts.CalendarCallBack;
import com.example.followyourshifts.CalendarUtils;
import com.example.followyourshifts.Fragments.CalendarFragment;
import com.example.followyourshifts.Fragments.ShiftFragment;
import com.example.followyourshifts.R;

import com.example.followyourshifts.SignalGenerator;
import com.google.android.material.button.MaterialButton;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity implements CalendarCallBack {
    private CalendarFragment calendarFragment;
    private ShiftFragment shiftFragment;
    private MaterialButton main_BTN_viewIncome;
    private MaterialButton main_BTN_viewOptions;
    private LinearLayout toolbarOptions;
    private boolean optionsIsOpen = false;
    private boolean optionsVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();
        findViews();
        beginTransactions();
        setOnClickListeners();
        // Set the CalendarCallBack for the CalendarFragment
        calendarFragment.setCalendarCallBack(this);
        // Set the current date as the selected date
        //calendarFragment.setSelectedDate(LocalDate.now());
    }

    private void setOnClickListeners() {
//        main_BTN_viewIncome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openChooseIncomeActivity();
//            }
//        });
        main_BTN_viewOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!optionsIsOpen) {
                    toggleOptionsVisibility();
                    optionsIsOpen = true;
                    main_BTN_viewOptions.setText("Close options");
                }
                else{
                    toggleOptionsVisibility();
                    main_BTN_viewOptions.setText("View Options");
                    optionsIsOpen = false;
                }
            }
        });
        for (int i = 0; i < toolbarOptions.getChildCount(); i++) {
            View option = toolbarOptions.getChildAt(i);
            option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleOptionClick(option);
                }
            });
        }
    }

    private void handleOptionClick(View option) {
        int id = option.getId();

        if (id == R.id.add_shift_BTN) {
            Intent intent = new Intent(this, AddShiftActivity.class);
            startActivity(intent);
        } else if (id == R.id.add_workplace_BTN) {
            Intent intent = new Intent(this, AddWorkplaceActivity.class);
            startActivity(intent);
        } else if (id == R.id.remove_shift_BTN) {
            Intent intent = new Intent(this, RemoveShiftActivity.class);
            startActivity(intent);
            // Handle option 3 click
        } else if (id == R.id.remove_workplace_BTN) {
            Intent intent = new Intent(this, RemoveWorkplaceActivity.class);
            startActivity(intent);
            // Handle option 4 click
        } else if(id == R.id.main_BTN_viewIncome){
            openChooseIncomeActivity();
            // Handle other options if needed
        }
    }

    private void toggleOptionsVisibility() {
        if (optionsVisible) {
            toolbarOptions.setVisibility(View.GONE);
            optionsVisible = false;
        } else {
            toolbarOptions.setVisibility(View.VISIBLE);
            optionsVisible = true;
        }
    }


    private void openChooseIncomeActivity() {
        Intent intent = new Intent(this, ChooseIncomeActivity.class);
        startActivity(intent);
    }

    private void findViews() {
        main_BTN_viewIncome = findViewById(R.id.main_BTN_viewIncome);
        main_BTN_viewOptions = findViewById(R.id.main_BTN_viewOptions);
        toolbarOptions = findViewById(R.id.main_TOOLBAR_options);
    }

    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_shifts, shiftFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_calendar, calendarFragment).commit();
    }

    private void initFragments() {
        shiftFragment = new ShiftFragment();
        calendarFragment = new CalendarFragment();
    }


    @Override
    public void onItemClick(int position, String dayText) {
        if (!dayText.equals("")) {
            // Update the selected date and highlight the selected day
            LocalDate selectedDate = CalendarUtils.selectedDate.withDayOfMonth(Integer.parseInt(dayText));
            calendarFragment.setSelectedDate(selectedDate);
        }
    }

    @Override
    public void onDateSelected(LocalDate selectedDate) {
        // Pass the selected date to the ShiftFragment
        if (shiftFragment != null) {
            shiftFragment.onDateSelected(selectedDate);
        }
    }
}
