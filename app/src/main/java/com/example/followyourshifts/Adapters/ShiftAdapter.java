package com.example.followyourshifts.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.followyourshifts.R;
import com.example.followyourshifts.Objects.Shift;

import java.util.ArrayList;


public class ShiftAdapter extends RecyclerView.Adapter<ShiftAdapter.ShiftsViewHolder>{

    private ArrayList<Shift> shifts;
    public ShiftAdapter(ArrayList<Shift> shifts){
        this.shifts = shifts;
    }

    @NonNull
    @Override
    public ShiftsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shift_item, parent, false);
        ShiftsViewHolder shiftsViewHolder = new ShiftsViewHolder(view);
        return shiftsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShiftAdapter.ShiftsViewHolder holder, int position) {
        Shift shift = getItem(position);
        holder.shift_LBL_workplace.setText(shift.getWorkplaceName());
        holder.shift_LBL_hours.setText(shift.getStartTime() + "-" + shift.getEndTime() + "");
    }


    @Override
    public int getItemCount() {
        return this.shifts == null ? 0 : this.shifts.size();
    }

    public Shift getItem(int position) {
        return this.shifts.get(position);
    }

    public class ShiftsViewHolder extends RecyclerView.ViewHolder {
        private TextView shift_LBL_workplace;
        private TextView shift_LBL_hours;

        public ShiftsViewHolder(@NonNull View itemView) {
            super(itemView);
            shift_LBL_workplace = itemView.findViewById(R.id.shift_LBL_workplace);
            shift_LBL_hours = itemView.findViewById(R.id.shift_LBL_hours);
        }
    }
}
