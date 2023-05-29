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

import java.util.ArrayList;

public class RemoveWorkplaceActivity extends AppCompatActivity {

    private Spinner workplaceSpinner;
    private Button removeButton;

    private ArrayList<Workplace> workplaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_workplace_layout);

        initializeViews();
        setupWorkplaceSpinner();
        setupRemoveButton();
    }

    private void initializeViews() {
        workplaceSpinner = findViewById(R.id.workplaceSpinner);
        removeButton = findViewById(R.id.removeButton);
    }

    private void setupWorkplaceSpinner() {
        workplaces = DataManager.getWorkPlace();

        if (workplaces != null && !workplaces.isEmpty()) {
            ArrayAdapter<Workplace> workplaceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, workplaces);
            workplaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            workplaceSpinner.setAdapter(workplaceAdapter);
        } else {
            Toast.makeText(this, "No workplaces found.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupRemoveButton() {
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the selected workplace from the spinner
                Workplace selectedWorkplace = (Workplace) workplaceSpinner.getSelectedItem();
                if(selectedWorkplace != null) {

                    // Remove the selected workplace from the DataManager
                    DataManager.removeWorkplace(selectedWorkplace);

                    // Remove shifts associated with the removed workplace
                    removeShiftsForWorkplace(selectedWorkplace);

                    // Display a message or perform other actions
                    Toast.makeText(RemoveWorkplaceActivity.this, "Workplace removed successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                // Display an error message or take appropriate action
                Toast.makeText(RemoveWorkplaceActivity.this, "No workplace selected.", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }

// Function to remove shifts associated with a workplace
        private void removeShiftsForWorkplace(Workplace removedWorkplace) {
            ArrayList<Shift> shifts = DataManager.getShifts(); // Assuming you have a method to retrieve shifts

            // Iterate over the shifts and remove the ones associated with the removed workplace
            for (int i = shifts.size() - 1; i >= 0; i--) {
                Shift shift = shifts.get(i);
                if (shift.getWorkplace().equals(removedWorkplace)) {
                    shifts.remove(i);
                }
            }
        }

    }


