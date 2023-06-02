package com.example.followyourshifts.Activities;

import static com.example.followyourshifts.Logic.DataManager.VIBRATE_TIME;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.followyourshifts.Logic.DataManager;
import com.example.followyourshifts.Objects.Shift;
import com.example.followyourshifts.Objects.Workplace;
import com.example.followyourshifts.R;
import com.example.followyourshifts.Utilities.SignalGenerator;

import java.util.ArrayList;

public class AddWorkplaceActivity extends AppCompatActivity {

    private EditText name_edit_text;
    private EditText salary_edit_text;
    private Button add_workplace_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_workplace_layout);
        findViews();
        setupAddButtonListener();
    }

    private void findViews() {
        name_edit_text = findViewById(R.id.name_edit_text);
        salary_edit_text = findViewById(R.id.salary_edit_text);
        add_workplace_button = findViewById(R.id.add_workplace_button);
    }

    private void setupAddButtonListener() {
        add_workplace_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = name_edit_text.getText().toString();
                String salaryInput = salary_edit_text.getText().toString();

                if (TextUtils.isEmpty(salaryInput)) {
                    SignalGenerator.getInstance().toast("Please enter the salary per hour", Toast.LENGTH_SHORT);
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    SignalGenerator.getInstance().toast( "Please enter name", Toast.LENGTH_SHORT);
                    return;
                }

                double salaryPerHour;
                try {
                    salaryPerHour = Double.parseDouble(salaryInput);
                } catch (NumberFormatException e) {
                    SignalGenerator.getInstance().toast("Invalid salary per hour", Toast.LENGTH_SHORT);
                    return;
                }

                // Check if a workplace with the same name already exists
                boolean duplicateFound = false;
                for (Workplace workplace : DataManager.getWorkPlace()) {
                    if (workplace.getName().equals(name)) {
                        duplicateFound = true;
                        break;
                    }
                }

                if (duplicateFound) {
                    SignalGenerator.getInstance().toast("A workplace with the same name already exists", Toast.LENGTH_SHORT);
                    return;
                }

                Workplace newWorkplace = new Workplace(name, salaryPerHour);
                DataManager.getWorkPlace().add(newWorkplace);
                DataManager.addWorkplaceToDB(newWorkplace);
                //DataManager.getWorkPlace().add(newWorkplace);
                SignalGenerator.getInstance().toast("Workplace: " + newWorkplace.getName() + " added successfully!",Toast.LENGTH_SHORT);
                SignalGenerator.getInstance().vibrate(VIBRATE_TIME);
                finish();
            }
        });
    }

}
