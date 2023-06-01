package com.example.followyourshifts.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.followyourshifts.Adapters.WorkplaceAdapter;
import com.example.followyourshifts.Logic.DataManager;
import com.example.followyourshifts.R;
import com.example.followyourshifts.Utilities.SignalGenerator;


public class ChooseIncomeActivity extends AppCompatActivity implements WorkplaceAdapter.WorkplaceCallBack{
    private RecyclerView workplace_list;

    @Override
    public void workplaceClicked(String workplaceName, int position) {
        openViewIncomeActivity(workplaceName);
        SignalGenerator.getInstance().toast("Your " + workplaceName + " Income", Toast.LENGTH_LONG);
        SignalGenerator.getInstance().playSound(R.raw.view_income_sound);
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
    }

    private void initViews() {
        WorkplaceAdapter workplaceAdapter = new WorkplaceAdapter(DataManager.getWorkPlace());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        workplace_list.setAdapter(workplaceAdapter);
        workplace_list.setLayoutManager(linearLayoutManager);
        workplaceAdapter.setWorkplaceCallBack(this);
    }


    private void findViews() {
        workplace_list = findViewById(R.id.workplace_list);
    }

}
