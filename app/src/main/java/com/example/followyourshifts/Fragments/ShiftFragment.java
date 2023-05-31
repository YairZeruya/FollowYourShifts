package com.example.followyourshifts.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.followyourshifts.Adapters.ShiftAdapter;
import com.example.followyourshifts.Interfaces.CalendarCallBack;
import com.example.followyourshifts.Logic.DataManager;
import com.example.followyourshifts.Objects.Shift;
import com.example.followyourshifts.R;
import com.example.followyourshifts.Utilities.SignalGenerator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ShiftFragment extends Fragment implements CalendarCallBack {
    private RecyclerView main_LST_shifts;
    private ShiftAdapter shiftAdapter;
    private ArrayList<Shift> displayedShifts;
    private TextView start_message;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shifts_fragment, container, false);
        findViews(view);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        displayedShifts = new ArrayList<>();
        shiftAdapter = new ShiftAdapter(displayedShifts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_shifts.setAdapter(shiftAdapter);
        main_LST_shifts.setLayoutManager(linearLayoutManager);
    }

    private void findViews(View view) {
        main_LST_shifts = view.findViewById(R.id.main_LST_shifts);
        start_message = view.findViewById(R.id.start_message);
    }

    @Override
    public void onItemClick(int position, String dayText) {

    }

    @Override
    public void onDateSelected(LocalDate selectedDate) {
        // Filter shifts based on the selected date
        displayedShifts.clear();
        for (Shift shift : DataManager.getShifts()) {
            if (shift.getDate().isEqual(selectedDate)) {
                displayedShifts.add(shift);
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd / MMMM / yyyy");
        String newMessage = selectedDate.format(formatter);
        start_message.setText(newMessage + " Shifts: ");
        // Update the RecyclerView with the filtered shifts
        shiftAdapter.notifyDataSetChanged();
        // Show a toast if no shifts are available for the selected date
        if (displayedShifts.isEmpty()) {
            SignalGenerator.getInstance().toast("No shifts available for selected date", Toast.LENGTH_SHORT);
        }
    }

//    public void setRecordCallBack(RecordCallBack recordCallBack) {
//        this.recordCallBack = recordCallBack;
//    }



}
