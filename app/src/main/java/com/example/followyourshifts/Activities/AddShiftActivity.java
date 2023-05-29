package com.example.followyourshifts.Activities;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.followyourshifts.DataManager;
import com.example.followyourshifts.Objects.Shift;
import com.example.followyourshifts.Objects.Workplace;
import com.example.followyourshifts.R;
import com.example.followyourshifts.SignalGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class AddShiftActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Button submitButton;
    private Button startTimeButton;
    private Button endTimeButton;
    private Spinner workplaceSpinner;

    private LocalTime startTime;
    private LocalTime endTime;

    private ArrayList<Workplace> workplaces; // List of available workplaces

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_shift_layout);

        findViews();
        populateWorkplaceSpinner();
        onClickListeners();
    }

    private void findViews() {
        datePicker = findViewById(R.id.datePicker);
        submitButton = findViewById(R.id.submitButton);
        startTimeButton = findViewById(R.id.startTimeButton);
        endTimeButton = findViewById(R.id.endTimeButton);
        workplaceSpinner = findViewById(R.id.workplaceSpinner);
    }

    private void populateWorkplaceSpinner() {
        workplaces = DataManager.getWorkPlace();

        if (workplaces != null && !workplaces.isEmpty()) {
            ArrayAdapter<Workplace> workplaceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, workplaces);
            workplaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            workplaceSpinner.setAdapter(workplaceAdapter);
        } else {
            Toast.makeText(this, "No workplaces found.", Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickListeners() {
        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(true);
            }
        });

        endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(false);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleShiftSubmission();
            }
        });
    }

    private void handleShiftSubmission() {
        int year = datePicker.getYear();
        int month = datePicker.getMonth() + 1;
        int day = datePicker.getDayOfMonth();
        LocalDate selectedDate = LocalDate.of(year, month, day);

        Workplace selectedWorkplace = (Workplace) workplaceSpinner.getSelectedItem();

        if (startTime == null || endTime == null) {
            SignalGenerator.getInstance().toast("Please select start and end times", Toast.LENGTH_SHORT);
            return;
        }


        if (DataManager.getWorkPlace().isEmpty()) {
            SignalGenerator.getInstance().toast("No workplaces found. Please add a workplace before adding a shift.", Toast.LENGTH_SHORT);
            return;
        }

        if (selectedWorkplace == null) {
            SignalGenerator.getInstance().toast("Please select a workplace", Toast.LENGTH_SHORT);
            return;
        }

        if (startTime.isBefore(endTime)) {
            Shift shift = new Shift(selectedDate, startTime, endTime, selectedWorkplace);
            DataManager.getShifts().add(shift);

            for (Workplace workplace : DataManager.getWorkPlace()) {
                if (workplace.getName().equals(selectedWorkplace.getName())) {
                    workplace.addShift(shift);
                }
            }

            SignalGenerator.getInstance().toast("Shift added successfully!", Toast.LENGTH_SHORT);
            finish();
        } else {
            SignalGenerator.getInstance().toast("Start time must be before end time", Toast.LENGTH_SHORT);
        }
    }






    private void showTimePickerDialog(final boolean isStartTime) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        LocalTime selectedTime = LocalTime.of(hourOfDay, minute);
                        if (isStartTime) {
                            startTime = selectedTime;
                            startTimeButton.setText(selectedTime.toString());
                        } else {
                            endTime = selectedTime;
                            endTimeButton.setText(selectedTime.toString());
                        }
                    }
                },
                0, 0, true);

        timePickerDialog.show();
    }

}
