package com.example.followyourshifts.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.followyourshifts.DataManager;
import com.example.followyourshifts.R;
import com.example.followyourshifts.Objects.Workplace;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ViewIncomeActivity extends AppCompatActivity {
    private static final int DEFAULT_VALUE = 0;
    private TextView monthYearText;
    private Button nextMonthButton;
    private Button previousMonthButton;
    private LocalDate selectedDate;
    private TextView shifts_details;
    private Workplace workplace;

//    private WorkplaceCallBack workplaceCallBack = new WorkplaceCallBack() {
//        @Override
//        public void workplaceClicked(String workplaceName, double salaryPerHour, int hours100, int hours125, int hours150, double totalSalary) {
//            //openViewIncomeActivity(workplaceName,salaryPerHour,hours100,hours125, hours150, totalSalary);
//            SignalGenerator.getInstance().toast("yesh!",1);
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_income_board);
        selectedDate = LocalDate.now();
        //chooseIncomeActivity.setWorkplaceCallBack(workplaceCallBack);
        findviews();
        setMonthView();
        Intent previousIntent = getIntent();
        workplace = new Workplace(previousIntent.getStringExtra(DataManager.KEY_WORKPLACE_NAME),previousIntent.getDoubleExtra(DataManager.KEY_SALARY_PER_HOUR,DEFAULT_VALUE));
//        workplace.setWorkplaceName(previousIntent.getStringExtra(DataManager.KEY_WORKPLACE_NAME));
//        workplace.setSalaryPerHour(previousIntent.getDoubleExtra(DataManager.KEY_SALARY_PER_HOUR,DEFAULT_VALUE));
        workplace.setHours100(previousIntent.getDoubleExtra(DataManager.KEY_HOURS_100,DEFAULT_VALUE));
        workplace.setHours125(previousIntent.getDoubleExtra(DataManager.KEY_HOURS_125, DEFAULT_VALUE));
        workplace.setHours150(previousIntent.getDoubleExtra(DataManager.KEY_HOURS_150, DEFAULT_VALUE));
        workplace.setTotalSalary(previousIntent.getDoubleExtra(DataManager.KEY_TOTAL_SALARY,DEFAULT_VALUE));
        shifts_details.setText(workplace.toString());
        onClicklisteners();
    }

    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction()
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(selectedDate));
    }

    public void nextMonthAction()
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }


    private void onClicklisteners() {
        nextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMonthAction();
            }
        });
        previousMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousMonthAction();
            }
        });
    }

    private void findviews() {
        monthYearText = findViewById(R.id.VI_monthYearTV);
        nextMonthButton =findViewById(R.id.VI_next_month_button);
        previousMonthButton = findViewById(R.id.VI_previous_month_button);
        shifts_details = findViewById(R.id.VI_shifts_details);
    }
}
