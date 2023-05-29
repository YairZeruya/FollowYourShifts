package com.example.followyourshifts.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.followyourshifts.DataManager;
import com.example.followyourshifts.Objects.Shift;
import com.example.followyourshifts.Objects.Workplace;
import com.example.followyourshifts.R;
import com.example.followyourshifts.SignalGenerator;

import java.util.ArrayList;

public class RemoveShiftActivity extends AppCompatActivity {

    private Spinner shiftSpinner;
    private Button removeButton;

    private ArrayList<Shift> shifts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_shift_layout);

        shiftSpinner = findViewById(R.id.shiftSpinner);
        removeButton = findViewById(R.id.removeButton);

        // Get the list of shifts from your getShifts() function
        shifts = DataManager.getShifts();

        // Check if the shifts list is null or empty
        if (shifts != null && !shifts.isEmpty()) {
            // Create an ArrayAdapter to populate the shiftSpinner with the list of shifts
            ArrayAdapter<Shift> shiftAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, shifts);
            shiftAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            shiftSpinner.setAdapter(shiftAdapter);

            // Set the item selection listener for the spinner
            shiftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // Nothing to do here
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Nothing to do here
                }
            });
        } else {
            // Handle the case when the shifts list is null or empty
            // You can display an error message or take appropriate action
            Toast.makeText(this, "No shifts found.", Toast.LENGTH_SHORT).show();
        }

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the selected shift from the spinner
                Shift selectedShift = (Shift) shiftSpinner.getSelectedItem();

                if (selectedShift != null) {
                    // Remove the selected shift from the DataManager
                    DataManager.removeShift(selectedShift);

                    // Remove the shift from the corresponding workplace
                    Workplace workplace = selectedShift.getWorkplace();
                    if (workplace != null) {
                        workplace.removeShift(selectedShift);
                    }

                    // Display a message or perform other actions
                    SignalGenerator.getInstance().toast("Shift removed successfully!", Toast.LENGTH_SHORT);
                    finish();
                } else {
                    // Display an error message or take appropriate action
                    SignalGenerator.getInstance().toast("No shift selected.", Toast.LENGTH_SHORT);
                }
            }
        });

    }
}
