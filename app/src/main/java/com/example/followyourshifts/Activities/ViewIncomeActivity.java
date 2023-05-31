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


import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ViewIncomeActivity extends AppCompatActivity {
    private static final int DEFAULT_VALUE = 0;
    private TextView monthYearText;
    private Button nextMonthButton;
    private Button previousMonthButton;
    private LocalDate selectedDate;
    private TextView shifts_details;
    private Workplace workplace;
    private TextView work_place_name_textView;

    private TextView salary_text_view;
    private TextView hours_days_text_view;
    private TextView hours_worked_text_view;
    private TextView extra_hours_125_text_view;
    private TextView extra_hours_150_text_view;

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
        findviews();
        setMonthView();
        handleIntentData();
//        Intent previousIntent = getIntent();
//        for (Workplace workplace: DataManager.getWorkPlace()) {
//            if(workplace.getName().equals(previousIntent.getStringExtra(DataManager.KEY_WORKPLACE_NAME))) {
//                this.workplace = workplace;
//                if (workplace.getShifts().size() > 0) {
//                    // here move for all the workplaces and their shifts
//                    for (Shift shift : workplace.getShifts()) {
//                        Month month = shift.getDate().getMonth();
//                        displayShiftsInfoByMonthAndWorkplace(month, workplace, shifts_details);
//                    }
//                } else {
//                    displayShiftsInfoByMonthAndWorkplace(selectedDate.getMonth(),workplace,shifts_details);
//                }
//            }
//        }
        onClicklisteners();
    }
    public void handleIntentData(){
        Intent previousIntent = getIntent();
        for (Workplace workplace: DataManager.getWorkPlace()) {
            if(workplace.getName().equals(previousIntent.getStringExtra(DataManager.KEY_WORKPLACE_NAME))) {
                this.workplace = workplace;
                if (workplace.getShifts().size() > 0) {
                    // here move for all the workplaces and their shifts
                    for (Shift shift : workplace.getShifts()) {
                        Month month = shift.getDate().getMonth();
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
        ArrayList<Shift> shifts = getShiftsByMonthAndWorkplace(month, workplace);
        if (month == selectedDate.getMonth()) {
            // Calculate total income, total hours worked, extra hours with 1.25 multiplier, extra hours with 1.5 multiplier, and number of shifts
            String name = "";
            double totalIncome = 0;
            double totalHoursWorked = 0;
            double extraHours1_25 = 0;
            double extraHours1_5 = 0;
            int numShifts = shifts.size();
            if(numShifts > 0) {
                name = shifts.get(0).getWorkplace().getName();
            }

            for (Shift shift : shifts) {
                totalIncome += shift.calculateIncome();
                totalHoursWorked += shift.calculateDuration();
                extraHours1_25 += shift.getExtraHours1_25();
                extraHours1_5 += shift.getExtraHours1_5();
            }
            work_place_name_textView.setText("Workplace Name: " + name);
            salary_text_view.setText("Total Income: " + totalIncome + "$");
            hours_days_text_view.setText(totalHoursWorked + " Hours Total ," + numShifts + " Days");
            hours_worked_text_view.setText("Hours (1.0x): " + (totalHoursWorked- extraHours1_5- extraHours1_25) + " Hours");
            extra_hours_125_text_view.setText("Extra Hours (1.25x): " + extraHours1_25);
            extra_hours_150_text_view.setText("Extra Hours (1.50x): " + extraHours1_5);
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
        monthYearText.setText(monthYearFromDate(selectedDate));
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
        salary_text_view = findViewById(R.id.salary_text_view);
        hours_days_text_view = findViewById(R.id.hours_days_text_view);
        hours_worked_text_view= findViewById(R.id.hours_worked_text_view);
        extra_hours_125_text_view = findViewById(R.id.extra_hours_125_text_view);
        extra_hours_150_text_view =findViewById(R.id.extra_hours_150_text_view);
        work_place_name_textView = findViewById(R.id.work_place_name_textView);
        hours_worked_text_view = findViewById(R.id.hours_worked_text_view);
    }
}
