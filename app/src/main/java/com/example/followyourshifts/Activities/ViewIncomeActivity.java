package com.example.followyourshifts.Activities;

import static com.example.followyourshifts.DataManager.KEY_WORKPLACE_NAME;
import static com.example.followyourshifts.DataManager.getShifts;
import static com.example.followyourshifts.DataManager.getShiftsByMonthAndWorkplace;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.followyourshifts.DataManager;
import com.example.followyourshifts.Objects.Shift;
import com.example.followyourshifts.R;
import com.example.followyourshifts.Objects.Workplace;
import com.example.followyourshifts.SignalGenerator;


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
        for (Workplace workplace: DataManager.getWorkPlace()) {
            if(workplace.getName().equals(previousIntent.getStringExtra(DataManager.KEY_WORKPLACE_NAME))) {
                this.workplace = workplace;
//            if(workplace.getName().equals("Holmes Place"))
//                workplace.addShift(DataManager.getShifts().get(0));
//            else
//                workplace.addShift(DataManager.getShifts().get(1));

                if (workplace.getShifts().size() > 0) {
                    // here move for all the workplaces and their shifts
                    for (Shift shift : workplace.getShifts()) {
                        Month month = shift.getDate().getMonth();
                        displayShiftsInfoByMonthAndWorkplace(month, workplace, shifts_details);
                    }
                } else {
                    displayShiftsInfoByMonthAndWorkplace(selectedDate.getMonth(),workplace,shifts_details);
                }
            }

        }
        //workplace = new Workplace(previousIntent.getStringExtra(DataManager.KEY_WORKPLACE_NAME),previousIntent.getDoubleExtra(DataManager.KEY_SALARY_PER_HOUR,DEFAULT_VALUE));
        //workplace.setName(previousIntent.getStringExtra(DataManager.KEY_WORKPLACE_NAME));
        //workplace.setSalaryPerHour(previousIntent.getDoubleExtra(DataManager.KEY_SALARY_PER_HOUR,DEFAULT_VALUE));
        //here is the problem;
        //ArrayList<Shift> shiftsByMonthAndWorkplace = getShiftsByMonthAndWorkplace(workplace.getShifts().get(0).getDate().getMonth(),workplace);



//        workplace.setHours100(previousIntent.getDoubleExtra(DataManager.KEY_HOURS_100,DEFAULT_VALUE));
//        workplace.setHours125(previousIntent.getDoubleExtra(DataManager.KEY_HOURS_125, DEFAULT_VALUE));
//        workplace.setHours150(previousIntent.getDoubleExtra(DataManager.KEY_HOURS_150, DEFAULT_VALUE));
//        workplace.setTotalSalary(previousIntent.getDoubleExtra(DataManager.KEY_TOTAL_SALARY,DEFAULT_VALUE));
//        shifts_details.setText(workplace.toString());
        onClicklisteners();
    }

    public void displayShiftsInfoByMonthAndWorkplace(Month month, Workplace workplace, TextView textView) {
        ArrayList<Shift> shifts = getShiftsByMonthAndWorkplace(month, workplace);
        if (month == selectedDate.getMonth()) {
            // Calculate total income, total hours worked, extra hours with 1.25 multiplier, extra hours with 1.5 multiplier, and number of shifts
            double totalIncome = 0;
            double totalHoursWorked = 0;
            double extraHours1_25 = 0;
            double extraHours1_5 = 0;
            int numShifts = shifts.size();

            for (Shift shift : shifts) {
                totalIncome += shift.calculateIncome();
                totalHoursWorked += shift.calculateDuration();
                extraHours1_25 += shift.getExtraHours1_25();
                extraHours1_5 += shift.getExtraHours1_5();
            }

            // Prepare the text to display
            String infoText = "Total Income: " + totalIncome + "\n"
                    + "Total Hours Worked: " + totalHoursWorked + "\n"
                    + "Extra Hours (1.25x): " + extraHours1_25 + "\n"
                    + "Extra Hours (1.5x): " + extraHours1_5 + "\n"
                    + "Number of Shifts: " + numShifts;

            // Set the text in the TextView
            textView.setText(infoText);
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
            displayShiftsInfoByMonthAndWorkplace(selectedDate.getMonth(),workplace,shifts_details);
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
            displayShiftsInfoByMonthAndWorkplace(selectedDate.getMonth(),workplace,shifts_details);
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
        shifts_details = findViewById(R.id.VI_shifts_details);
    }
}
