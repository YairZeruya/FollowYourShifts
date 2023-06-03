package com.example.followyourshifts.Activities;

import androidx.appcompat.app.AppCompatActivity;

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
    private boolean optionsIsOpen = false;
    private boolean optionsVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();
//        LocalDate selectedDate = LocalDate.of(2023, 6, 1);
//        LocalTime startTime = LocalTime.of(9, 0);
//        LocalTime endTime = LocalTime.of(17, 0);
//        LocalDate selectedDate2 = LocalDate.of(2023, 6, 2);
//        LocalTime startTime2 = LocalTime.of(8, 0);
//        LocalTime endTime2 = LocalTime.of(14, 0);
        Workplace selectedWorkplace = new Workplace("Office A", 45);
        Workplace selectedWorkplace2 = new Workplace("Office B", 46);

//        ArrayList<Workplace> workplaceArrayList = new ArrayList<>();
//        workplaceArrayList.add(selectedWorkplace);
//        workplaceArrayList.add(selectedWorkplace2);
//        DataManager.addWorkplaceToDB(selectedWorkplace);
//        DataManager.addWorkplaceToDB(selectedWorkplace2);
//        Shift shift = new Shift(selectedDate.toString(), startTime.toString(), endTime.toString(), selectedWorkplace);
//        DataManager.addShift(shift);
//        Shift shift2 = new Shift(selectedDate2.toString(), startTime2.toString(), endTime2.toString(), selectedWorkplace2);
//        DataManager.addShift(shift2);
//
//        DataManager.getAllShifts();
//        DataManager.getAllWorkPlaces();
//        if(DataManager.getWorkPlace().size() == 0){
//            DataManager.setWorkplaceArrayList(workplaceArrayList);
//            DataManager.assignShiftsToWorkplaces(DataManager.getShifts(),workplaceArrayList);
//        }
//        else {
//            DataManager.assignShiftsToWorkplaces(DataManager.getShifts(), DataManager.getWorkPlace());
//        }
        DataManager dataManager = new DataManager();
        //dataManager.addWorkplaceToDB(selectedWorkplace);
        //dataManager.addWorkplaceToDB(selectedWorkplace2);
        dataManager.getAllShifts2(new DataManager.DataStatusShifts() {
            @Override
            public void DataIsLoaded(ArrayList<Shift> shifts, ArrayList<Workplace> workplaces) {
                for(Shift shift: shifts){
                    SignalGenerator.getInstance().toast(shift.toString(),Toast.LENGTH_SHORT);
                }

                dataManager.setShiftsArrayList(shifts);
                dataManager.setWorkplaceArrayList(workplaces);
//                dataManager.assignShiftsToWorkplaces(DataManager.getShifts(), DataManager.getWorkPlace());
            }
        });
//        dataManager.getAllWorkPlaces2(new DataManager.DataStatusWorkplaces() {
//            @Override
//            public void DataIsLoaded(ArrayList<Workplace> workplaces, ArrayList<String> keys) {
//                for (Workplace workplace: workplaces){
//                    SignalGenerator.getInstance().toast(workplace.getName(),Toast.LENGTH_SHORT);
//                }
//                dataManager.setWorkplaceArrayList(workplaces);
//            }
//        });
//        dataManager.assignShiftsToWorkplaces(DataManager.getShifts(), DataManager.getWorkPlace());
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
        if(DataManager.getWorkPlace().size() > 0) {
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
