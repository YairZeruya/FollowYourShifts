package com.example.followyourshifts.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.followyourshifts.Fragments.CalendarFragment;
import com.example.followyourshifts.Fragments.ShiftFragment;
import com.example.followyourshifts.R;
import com.google.android.material.button.MaterialButton;


public class MainActivity extends AppCompatActivity  {
    private CalendarFragment calendarFragment;
    private ShiftFragment shiftFragment;
    private MaterialButton main_BTN_viewIncome;
    private MaterialButton main_BTN_add_shift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();
        findviews();
        beginTransactions();
        onClicklisteners();
    }

    private void onClicklisteners() {
        main_BTN_viewIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChooseIncomeActivity();

            }
        });
        main_BTN_add_shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add shifts
            }
        });
    }
    private void openChooseIncomeActivity() {
        Intent intent = new Intent(this, ChooseIncomeActivity.class);
        startActivity(intent);
    }

    private void findviews() {
        main_BTN_viewIncome = findViewById(R.id.main_BTN_viewIncome);
        main_BTN_add_shift = findViewById(R.id.main_BTN_add_shift);
    }
//    private RecordCallBack recordCallBack = new RecordCallBack() {
//        @Override
//        public void recordClicked(double latitude, double longitude) {
//            mapFragment.zoomOnRecord(latitude, longitude);
//        }
//    };


    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_shifts, shiftFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_calendar, calendarFragment).commit();
    }

    private void initFragments() {
        shiftFragment = new ShiftFragment();
        calendarFragment = new CalendarFragment();
    }


}