package com.example.followyourshifts.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.followyourshifts.DataManager;
import com.example.followyourshifts.Objects.Workplace;
import com.example.followyourshifts.R;
import com.example.followyourshifts.SignalGenerator;

public class AddWorkplaceActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText salaryEditText;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_workplace_layout);

        findViews();
        setupAddButtonListener();
    }

    private void findViews() {
        nameEditText = findViewById(R.id.nameEditText);
        salaryEditText = findViewById(R.id.salaryEditText);
        addButton = findViewById(R.id.addButton);
    }

    private void setupAddButtonListener() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the input values
                String name = nameEditText.getText().toString();
                String salaryInput = salaryEditText.getText().toString();

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

                // Create a new Workplace object
                Workplace newWorkplace = new Workplace(name, salaryPerHour);
                DataManager.getWorkPlace().add(newWorkplace);

                // Perform any necessary actions with the new Workplace object
                // ...

                // Finish the current activity and return to the previous one
                finish();
            }
        });
    }

}
