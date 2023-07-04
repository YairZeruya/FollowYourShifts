package com.example.followyourshifts.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.followyourshifts.Interfaces.CalendarCallBack;
import com.example.followyourshifts.Interfaces.DataCallBack;
import com.example.followyourshifts.Logic.DataManager;
import com.example.followyourshifts.Objects.Shift;
import com.example.followyourshifts.Objects.Workplace;
import com.example.followyourshifts.Utilities.CalendarUtils;
import com.example.followyourshifts.Fragments.CalendarFragment;
import com.example.followyourshifts.Fragments.ShiftFragment;
import com.example.followyourshifts.R;

import com.example.followyourshifts.Utilities.SignalGenerator;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CalendarCallBack {
    private CalendarFragment calendarFragment;
    private ShiftFragment shiftFragment;
    private MaterialButton main_BTN_viewOptions;
    private LinearLayout toolbarOptions;
    private TextView main_LBL_message;
    private MaterialButton main_BTN_logout;
    private boolean optionsIsOpen = false;
    private boolean optionsVisible = false;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataManager = new DataManager();
        setContentView(R.layout.activity_main);
        dataManager.getWorkplacesFromFirestore(new DataManager.WorkplaceListener() {
            @Override
            public void onWorkplacesRetrieved(ArrayList<Workplace> workplaces) {
                dataManager.setWorkplaceArrayList(workplaces);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        dataManager.getShiftsFromFirestore(new DataManager.ShiftListener() {
            @Override
            public void onShiftsRetrieved(ArrayList<Shift> shifts) {
                dataManager.setShiftsArrayList(shifts);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        //dataManager.assignShiftsToWorkplaces();
        initFragments();
        findViews();
        initViews();
        beginTransactions();
        setOnClickListeners();
        calendarFragment.setCalendarCallBack(this);
    }

    private void initViews() {
        Intent intent = getIntent();
        main_LBL_message.setText("Welcome Back " + intent.getStringExtra("username"));
        SignalGenerator.getInstance().toast( "Click on a date to see its shifts, days in green indicate shifts.", Toast.LENGTH_LONG);
//        main_BTN_update.setOnClickListener(v -> {
//            changeTitle(main_ET_text.getText().toString());
//        });
//        main_BTN_save.setOnClickListener(v -> {
//            saveVehicleToDB();
//        });
//        main_BTN_updatePrice.setOnClickListener(v -> {
//            updatePrice();
//        });
//        main_BTN_load.setOnClickListener(v -> {
//            loadVehicleFromDB();
//        });
    }

    private void setOnClickListeners() {
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
        MaterialButton main_BTN_logout = findViewById(R.id.main_BTN_logout);
        main_BTN_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == requestCode && resultCode == Activity.RESULT_OK) {
//            dataManager.updateDatabaseOnAppFinish();
//        }
//    }

    private void logoutUser() {
        // Clear any user-related data or preferences
        // For example, you can clear the user session or user preferences


        // Navigate back to the login screen
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        // Finish the current activity
        finish();
    }

    private void handleOptionClick(View option) {
        int id = option.getId();

        if (id == R.id.add_shift_BTN) {
            openAddShiftActivity();
        } else if (id == R.id.add_workplace_BTN) {
            openAddWorkplaceActivity();

        } else if (id == R.id.remove_shift_BTN) {
            openRemoveShiftActivity();

        } else if (id == R.id.remove_workplace_BTN) {
            openRemoveWorkplaceActivity();

        } else if (id == R.id.main_BTN_viewIncome) {
            openChooseIncomeActivity();
        }
    }

    private void openRemoveShiftActivity() {
        if(DataManager.getShifts().size() > 0) {
            Intent intent = new Intent(this, RemoveShiftActivity.class);
            startActivity(intent);
        }
        else{
            SignalGenerator.getInstance().toast("Add shift before you want to remove one.",Toast.LENGTH_SHORT);
        }
    }

    private void openAddWorkplaceActivity() {
        Intent intent = new Intent(this, AddWorkplaceActivity.class);
        startActivity(intent);
    }

    private void openAddShiftActivity() {
        Intent intent = new Intent(this, AddShiftActivity.class);
        startActivity(intent);
    }

    private void openRemoveWorkplaceActivity() {
        if(DataManager.getWorkPlaces().size() > 0) {
            Intent intent = new Intent(this, RemoveWorkplaceActivity.class);
            startActivity(intent);
        }
        else{
            SignalGenerator.getInstance().toast("Add workplace before you want to remove one.",Toast.LENGTH_SHORT);
        }
    }
    private void openChooseIncomeActivity() {
        //if(DataManager.getWorkPlace().size() > 0) {
            Intent intent = new Intent(this, ChooseIncomeActivity.class);
            startActivity(intent);
        //}
        //else{
          //  SignalGenerator.getInstance().toast("Add workplace before you want to see your income.",Toast.LENGTH_SHORT);
        //}
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



    private void findViews() {
        main_BTN_viewOptions = findViewById(R.id.main_BTN_viewOptions);
        toolbarOptions = findViewById(R.id.main_TOOLBAR_options);
        main_LBL_message = findViewById(R.id.main_LBL_message);
        main_BTN_logout = findViewById(R.id.main_BTN_logout);
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
