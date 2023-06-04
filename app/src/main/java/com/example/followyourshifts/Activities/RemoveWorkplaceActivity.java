package com.example.followyourshifts.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.followyourshifts.Logic.DataManager;
import com.example.followyourshifts.Objects.Shift;
import com.example.followyourshifts.Objects.Workplace;
import com.example.followyourshifts.R;
import com.example.followyourshifts.Utilities.SignalGenerator;

import java.util.ArrayList;

public class RemoveWorkplaceActivity extends AppCompatActivity {

    private Spinner workplace_spinner;
    private Button remove_button;

    private ArrayList<Workplace> workplaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_workplace_layout);
        findViews();
        setupWorkplaceSpinner();
        setupRemoveButton();
    }

    private void findViews() {
        workplace_spinner = findViewById(R.id.workplace_spinner);
        remove_button = findViewById(R.id.remove_button);
    }

    private void setupWorkplaceSpinner() {
        workplaces = DataManager.getWorkPlaces();

        if (workplaces != null && !workplaces.isEmpty()) {
            ArrayAdapter<Workplace> workplaceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, workplaces);
            workplaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            workplace_spinner.setAdapter(workplaceAdapter);
        } else {
            SignalGenerator.getInstance().toast( "No workplaces found.", Toast.LENGTH_SHORT);
        }
    }

    private void setupRemoveButton() {
        remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Workplace selectedWorkplace = (Workplace) workplace_spinner.getSelectedItem();
                if(selectedWorkplace != null) {
                    DataManager.removeWorkplace(selectedWorkplace);
                    removeShiftsForWorkplace(selectedWorkplace);
                    SignalGenerator.getInstance().toast( "Workplace removed successfully!", Toast.LENGTH_SHORT);
                    finish();
                }
                else {
                SignalGenerator.getInstance().toast("No workplace selected.", Toast.LENGTH_SHORT);
            }
            }
        });
    }

        private void removeShiftsForWorkplace(Workplace removedWorkplace) {
            ArrayList<Shift> shifts = DataManager.getShifts();
            for (int i = shifts.size() - 1; i >= 0; i--) {
                Shift shift = shifts.get(i);
                if (shift.getWorkplaceName().equals(removedWorkplace.getName())) {
                    //shifts.remove(i);
                    DataManager.removeShift(shifts.get(i));
                }
            }
        }
    }


