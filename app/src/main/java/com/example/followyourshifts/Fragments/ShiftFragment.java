package com.example.followyourshifts.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.followyourshifts.Adapters.ShiftAdapter;
import com.example.followyourshifts.DataManager;
import com.example.followyourshifts.R;

public class ShiftFragment extends Fragment {
    private RecyclerView main_LST_shifts;
    //private RecordCallBack recordCallBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shifts_fragment, container, false);
        findViews(view);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        ShiftAdapter shiftAdapter = new ShiftAdapter(DataManager.getShifts());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_shifts.setAdapter(shiftAdapter);
        main_LST_shifts.setLayoutManager(linearLayoutManager);
    }
//    private void initViews(View view) {
//        RecordAdapter recordAdapter = new RecordAdapter(DataManager.getRecords(), recordCallBack);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
//        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//        main_LST_records.setAdapter(recordAdapter);
//        main_LST_records.setLayoutManager(linearLayoutManager);
//    }

    private void findViews(View view) {
        main_LST_shifts = view.findViewById(R.id.main_LST_shifts);
    }

//    public void setRecordCallBack(RecordCallBack recordCallBack) {
//        this.recordCallBack = recordCallBack;
//    }
}
