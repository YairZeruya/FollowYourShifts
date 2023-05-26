package com.example.followyourshifts.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.followyourshifts.R;

public class ChooseIncomeActivity extends AppCompatActivity {
    private TextView topTextView;
    private RecyclerView workplaceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_income);
        findviews();
        onClicklisteners();
    }

    private void onClicklisteners() {
    }

    private void findviews() {
        topTextView = findViewById(R.id.topTextView);
        workplaceList = findViewById(R.id.workplace_list);
    }
}
