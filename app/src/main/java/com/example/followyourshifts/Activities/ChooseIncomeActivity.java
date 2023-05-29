package com.example.followyourshifts.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.followyourshifts.Adapters.WorkplaceAdapter;
import com.example.followyourshifts.DataManager;
import com.example.followyourshifts.R;
import com.example.followyourshifts.SignalGenerator;


public class ChooseIncomeActivity extends AppCompatActivity implements WorkplaceAdapter.WorkplaceCallBack{
    private TextView topTextView;
    private RecyclerView workplaceList;

    @Override
    public void workplaceClicked(String workplaceName, int position) {
        openViewIncomeActivity(workplaceName);
        SignalGenerator.getInstance().toast(workplaceName, Toast.LENGTH_LONG);
    }

    private void openViewIncomeActivity(String workplaceName) {
        Intent intent = new Intent(this, ViewIncomeActivity.class);
        intent.putExtra((DataManager.KEY_WORKPLACE_NAME), workplaceName);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_income_board);
        findViews();
        initViews();

        onClicklisteners();
    }

    private void initViews() {
        WorkplaceAdapter workplaceAdapter = new WorkplaceAdapter(DataManager.getWorkPlace());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        workplaceList.setAdapter(workplaceAdapter);
        workplaceList.setLayoutManager(linearLayoutManager);
        workplaceAdapter.setWorkplaceCallBack(this);
    }

    private void onClicklisteners() {
    }

    private void findViews() {
        topTextView = findViewById(R.id.topTextView);
        workplaceList = findViewById(R.id.workplace_list);
    }

}
