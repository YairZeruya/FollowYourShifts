package com.example.followyourshifts.Activities;

import static com.example.followyourshifts.Logic.DataManager.getShiftsByMonthAndWorkplace;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.followyourshifts.Logic.DataManager;
import com.example.followyourshifts.Objects.Shift;
import com.example.followyourshifts.R;
import com.example.followyourshifts.Objects.Workplace;


import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ViewIncomeActivity extends AppCompatActivity {
    private TextView VI_month_and_year;
    private Button VI_next_month_button;
    private Button VI_previous_month_button;
    private LocalDate selectedDate;
    private Workplace workplace;
    private TextView work_place_name_textView;
    private TextView salary_text_view;
    private TextView hours_days_text_view;
    private TextView hours_worked_text_view;
    private TextView extra_hours_125_text_view;
    private TextView extra_hours_150_text_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_income_board);
        selectedDate = LocalDate.now();
        DataManager.assignShiftsToWorkplaces();
        findViews();
        setMonthView();
        handleIntentData();
        onClicklisteners();
    }
    public void handleIntentData(){
        Intent previousIntent = getIntent();
        for (Workplace workplace: DataManager.getWorkPlaces()) {
            if(workplace.getName().equals(previousIntent.getStringExtra(DataManager.KEY_WORKPLACE_NAME))) {
                this.workplace = workplace;
                if (workplace.getShifts().size() > 0) {
                    for (Shift shift : workplace.getShifts()) {
                        Month month = LocalDate.parse(shift.getDate()).getMonth();
                        displayShiftsInfoByMonthAndWorkplace(month, workplace, work_place_name_textView,salary_text_view, hours_days_text_view,hours_worked_text_view
                                ,extra_hours_125_text_view, extra_hours_150_text_view);
                    }
                } else {
                    displayShiftsInfoByMonthAndWorkplace(selectedDate.getMonth(),workplace, work_place_name_textView,salary_text_view, hours_days_text_view,hours_worked_text_view
                            ,extra_hours_125_text_view, extra_hours_150_text_view);
                }
            }
        }
    }



    public void displayShiftsInfoByMonthAndWorkplace(
            Month month, Workplace workplace, TextView work_place_name_textView,
            TextView salary_text_view,TextView hours_days_text_view,
            TextView hours_worked_text_view,TextView extra_hours_125_text_view,TextView extra_hours_150_text_view) {

        ArrayList<Shift> shifts = getShiftsByMonthAndWorkplace(month,workplace);
        if (month == selectedDate.getMonth()) {
            String name = "";
            if(workplace !=null) {
                name = workplace.getName();
            }
            double totalIncome = 0;
            double totalHoursWorked = 0;
            double extraHours1_25 = 0;
            double extraHours1_5 = 0;
            int numShifts = shifts.size();
            if(numShifts > 0) {
                name = shifts.get(0).getWorkplaceName();
            }

            for (Shift shift : shifts) {
                totalIncome += shift.getIncome();
                totalHoursWorked += shift.calculateDuration();
                extraHours1_25 += shift.getExtraHours1_25();
                extraHours1_5 += shift.getExtraHours1_5();
            }
            // Create a DecimalFormat object with the desired format
            DecimalFormat decimalFormat = new DecimalFormat("#.##");

            work_place_name_textView.setText("Workplace Name: " + name);
            salary_text_view.setText("Total Income: " + decimalFormat.format(totalIncome) + "$");
            hours_days_text_view.setText(decimalFormat.format(totalHoursWorked) + " Hours Total ," + numShifts + " Days");
            hours_worked_text_view.setText("Hours (1.0x): " + decimalFormat.format(totalHoursWorked- extraHours1_5- extraHours1_25) + " Hours");
            extra_hours_125_text_view.setText("Extra Hours (1.25x): " + decimalFormat.format(extraHours1_25));
            extra_hours_150_text_view.setText("Extra Hours (1.50x): " + decimalFormat.format(extraHours1_5));
        }
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
        if(workplace != null){
            displayShiftsInfoByMonthAndWorkplace(selectedDate.getMonth(),workplace, work_place_name_textView,salary_text_view, hours_days_text_view,hours_worked_text_view
                    ,extra_hours_125_text_view, extra_hours_150_text_view);
        }
    }

    private void setMonthView() {
        VI_month_and_year.setText(monthYearFromDate(selectedDate));
    }

    public void nextMonthAction()
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
        if(workplace != null){
            displayShiftsInfoByMonthAndWorkplace(selectedDate.getMonth(),workplace, work_place_name_textView,salary_text_view, hours_days_text_view,hours_worked_text_view
                    ,extra_hours_125_text_view, extra_hours_150_text_view);
        }
    }


    private void onClicklisteners() {
        VI_next_month_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMonthAction();
            }
        });
        VI_previous_month_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousMonthAction();
            }
        });
    }


    private void findViews() {
        VI_month_and_year = findViewById(R.id.VI_month_and_year);
        VI_next_month_button =findViewById(R.id.VI_next_month_button);
        VI_previous_month_button = findViewById(R.id.VI_previous_month_button);
        salary_text_view = findViewById(R.id.salary_text_view);
        hours_days_text_view = findViewById(R.id.hours_days_text_view);
        hours_worked_text_view= findViewById(R.id.hours_worked_text_view);
        extra_hours_125_text_view = findViewById(R.id.extra_hours_125_text_view);
        extra_hours_150_text_view =findViewById(R.id.extra_hours_150_text_view);
        work_place_name_textView = findViewById(R.id.work_place_name_textView);
        hours_worked_text_view = findViewById(R.id.hours_worked_text_view);
    }
}