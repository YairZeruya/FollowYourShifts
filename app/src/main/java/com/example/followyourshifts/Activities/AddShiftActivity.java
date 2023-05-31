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

import com.example.followyourshifts.Logic.DataManager;
import com.example.followyourshifts.Objects.Shift;
import com.example.followyourshifts.Objects.Workplace;
import com.example.followyourshifts.R;
import com.example.followyourshifts.Utilities.SignalGenerator;

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
            SignalGenerator.getInstance().toast( "No workplaces found.", Toast.LENGTH_SHORT);
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
        // Retrieve the selected date from the DatePicker
        int year = datePicker.getYear();
        int month = datePicker.getMonth() + 1;
        int day = datePicker.getDayOfMonth();
        LocalDate selectedDate = LocalDate.of(year, month, day);

        // Retrieve the selected workplace from the spinner
        Workplace selectedWorkplace = (Workplace) workplaceSpinner.getSelectedItem();

        // Check if both start time and end time are selected
        if (startTime == null || endTime == null) {
            SignalGenerator.getInstance().toast("Please select start and end times", Toast.LENGTH_SHORT);
            return;
        }

        // Check if any workplaces are available
        if (DataManager.getWorkPlace().isEmpty()) {
            SignalGenerator.getInstance().toast("No workplaces found. Please add a workplace before adding a shift.", Toast.LENGTH_SHORT);
            return;
        }

        // Check if a workplace is selected
        if (selectedWorkplace == null) {
            SignalGenerator.getInstance().toast("Please select a workplace", Toast.LENGTH_SHORT);
            return;
        }

        // Check if the start time is before the end time
        if (startTime.isBefore(endTime)) {
            // Check for overlapping shifts
            if (hasOverlappingShifts(selectedDate, startTime, endTime)) {
                SignalGenerator.getInstance().toast("Shift overlaps with existing shifts", Toast.LENGTH_SHORT);
            } else {
                // Create and add the new shift
                Shift shift = new Shift(selectedDate, startTime, endTime, selectedWorkplace);
                DataManager.getShifts().add(shift);
                selectedWorkplace.addShift(shift);
                SignalGenerator.getInstance().toast("Shift added successfully!", Toast.LENGTH_SHORT);
                SignalGenerator.getInstance().toast( "Click on a date to see its shifts, days in white indicate shifts.", Toast.LENGTH_LONG);
                finish();
            }
        } else {
            SignalGenerator.getInstance().toast("Start time must be before end time", Toast.LENGTH_SHORT);
        }
    }

    private boolean hasOverlappingShifts(LocalDate date, LocalTime startTime, LocalTime endTime) {
        for (Workplace workplace : DataManager.getWorkPlace()) {
            for (Shift shift : workplace.getShifts()) {
                if (shift.getDate().isEqual(date)) {
                    // Check if the new shift's start time is between the existing shift's start and end times
                    if (startTime.isAfter(shift.getStartTime()) && startTime.isBefore(shift.getEndTime())) {
                        return true;
                    }
                    // Check if the new shift's end time is between the existing shift's start and end times
                    if (endTime.isAfter(shift.getStartTime()) && endTime.isBefore(shift.getEndTime())) {
                        return true;
                    }
                    // Check if the new shift's start and end times fully encompass the existing shift's start and end times
                    if (startTime.isBefore(shift.getStartTime()) && endTime.isAfter(shift.getEndTime())) {
                        return true;
                    }
                    // Check if the new shift's start time is equal to the existing shift's start time, and the end time is after the existing shift's end time
                    if (startTime.equals(shift.getStartTime()) && endTime.isAfter(shift.getEndTime())) {
                        return true;
                    }
                    // Check if the new shift's start time is before the existing shift's start time, and the end time is equal to the existing shift's end time
                    if (startTime.isBefore(shift.getStartTime()) && endTime.equals(shift.getEndTime())) {
                        return true;
                    }
                }
            }
        }
        return false;
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
