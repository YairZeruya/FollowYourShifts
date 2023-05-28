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

        datePicker = findViewById(R.id.datePicker);
        submitButton = findViewById(R.id.submitButton);
        startTimeButton = findViewById(R.id.startTimeButton);
        endTimeButton = findViewById(R.id.endTimeButton);
        workplaceSpinner = findViewById(R.id.workplaceSpinner);

        // Get the list of workplaces from your getWorkplaces() function
        workplaces = DataManager.getWorkPlace();

        // Check if the workplaces list is null or empty
        if (workplaces != null && !workplaces.isEmpty()) {
            // Create an ArrayAdapter to populate the workplaceSpinner with the list of workplaces
            ArrayAdapter<Workplace> workplaceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, workplaces);
            workplaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            workplaceSpinner.setAdapter(workplaceAdapter);
        } else {
            // Handle the case when the workplaces list is null or empty
            // You can display an error message or take appropriate action
            Toast.makeText(this, "No workplaces found.", Toast.LENGTH_SHORT).show();
        }


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
                // Retrieve the selected date from the DatePicker
                int year = datePicker.getYear();
                int month = datePicker.getMonth() + 1;  // Month starts from 0
                int day = datePicker.getDayOfMonth();

                // Create a LocalDate object from the selected date
                LocalDate selectedDate = LocalDate.of(year, month, day);

                // Get the selected workplace from the spinner
                Workplace selectedWorkplace = (Workplace) workplaceSpinner.getSelectedItem();

                // Create a Shift object with the selected date, start time, end time, and workplace
                Shift shift = new Shift(selectedDate, startTime, endTime, selectedWorkplace);

                // Use the shift object as needed
                // You can access shift.getStartTime(), shift.getEndTime(), shift.getWorkplace(), etc.

                // Display a message or perform other actions
                SignalGenerator.getInstance().toast("Shift added successfully!", Toast.LENGTH_SHORT);
            }
        });
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
