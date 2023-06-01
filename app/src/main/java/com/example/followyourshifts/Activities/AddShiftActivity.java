package com.example.followyourshifts.Activities;

import static com.example.followyourshifts.Logic.DataManager.VIBRATE_TIME;

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

    private DatePicker date_picker;
    private Button add_shift_button;
    private Button start_time_button;
    private Button end_time_button;
    private Spinner workplace_spinner;
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
        date_picker = findViewById(R.id.date_picker);
        add_shift_button = findViewById(R.id.add_shift_button);
        start_time_button = findViewById(R.id.start_time_button);
        end_time_button = findViewById(R.id.end_time_button);
        workplace_spinner = findViewById(R.id.workplace_spinner);
    }

    private void populateWorkplaceSpinner() {
        workplaces = DataManager.getWorkPlace();

        if (workplaces != null && !workplaces.isEmpty()) {
            ArrayAdapter<Workplace> workplaceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, workplaces);
            workplaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            workplace_spinner.setAdapter(workplaceAdapter);
        } else {
            SignalGenerator.getInstance().toast( "No workplaces found.", Toast.LENGTH_SHORT);
        }
    }

    private void onClickListeners() {
        start_time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(true);
            }
        });

        end_time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(false);
            }
        });

        add_shift_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleShiftSubmission();
            }
        });
    }

    private void handleShiftSubmission() {

        int year = date_picker.getYear();
        int month = date_picker.getMonth() + 1;
        int day = date_picker.getDayOfMonth();
        LocalDate selectedDate = LocalDate.of(year, month, day);
        Workplace selectedWorkplace = (Workplace) workplace_spinner.getSelectedItem();

        if (startTime == null || endTime == null) {
            SignalGenerator.getInstance().toast("Please select start and end times", Toast.LENGTH_SHORT);
            return;
        }

        // Check if any workplaces are available
        if (DataManager.getWorkPlace().isEmpty()) {
            SignalGenerator.getInstance().toast("No workplaces found. Please add a workplace before adding a shift.", Toast.LENGTH_SHORT);
            return;
        }

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
                SignalGenerator.getInstance().vibrate(VIBRATE_TIME);
                SignalGenerator.getInstance().playSound(R.raw.money_sound);
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
                    //Check overlapping options:
                    if (startTime.isAfter(shift.getStartTime()) && startTime.isBefore(shift.getEndTime())) {
                        return true;
                    }
                    if (endTime.isAfter(shift.getStartTime()) && endTime.isBefore(shift.getEndTime())) {
                        return true;
                    }
                    if (startTime.isBefore(shift.getStartTime()) && endTime.isAfter(shift.getEndTime())) {
                        return true;
                    }
                    if (startTime.equals(shift.getStartTime()) && endTime.isAfter(shift.getEndTime())) {
                        return true;
                    }
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
                this, R.style.CustomTimePickerDialog,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        LocalTime selectedTime = LocalTime.of(hourOfDay, minute);
                        if (isStartTime) {
                            startTime = selectedTime;
                            start_time_button.setText(selectedTime.toString());
                        } else {
                            endTime = selectedTime;
                            end_time_button.setText(selectedTime.toString());
                        }
                    }
                },
                0, 0, true);

        timePickerDialog.show();
    }


}
