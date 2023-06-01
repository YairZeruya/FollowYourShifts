package com.example.followyourshifts.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class RemoveShiftActivity extends AppCompatActivity {

    private Spinner shiftSpinner;
    private Button removeButton;
    private ArrayList<Shift> shifts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_shift_layout);
        findViews();
        setupShiftSpinner();
        setupRemoveButton();
    }

    private void findViews() {
        shiftSpinner = findViewById(R.id.shift_spinner);
        removeButton = findViewById(R.id.remove_button);
    }

    private void setupShiftSpinner() {
        shifts = DataManager.getShifts();

        if (shifts != null && !shifts.isEmpty()) {
            ArrayAdapter<Shift> shiftAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, shifts);
            shiftAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            shiftSpinner.setAdapter(shiftAdapter);
        } else {
            Toast.makeText(this, "No shifts found.", Toast.LENGTH_SHORT).show();
        }

        shiftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}});
    }

    private void setupRemoveButton() {
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSelectedShift();
            }
        });
    }

    private void removeSelectedShift() {
        Shift selectedShift = (Shift) shiftSpinner.getSelectedItem();

        if (selectedShift != null) {
            DataManager.removeShift(selectedShift);
            Workplace workplace = selectedShift.getWorkplace();
            if (workplace != null) {
                workplace.removeShift(selectedShift);
            }

            SignalGenerator.getInstance().toast("Shift removed successfully!", Toast.LENGTH_SHORT);
            finish();
        } else {
            SignalGenerator.getInstance().toast("No shift selected.", Toast.LENGTH_SHORT);
        }
    }
}
