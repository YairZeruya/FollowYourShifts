package com.example.followyourshifts.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.followyourshifts.CalendarUtils;
import com.example.followyourshifts.DataManager;
import com.example.followyourshifts.Objects.Shift;
import com.example.followyourshifts.R;

import java.time.YearMonth;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private final ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;
    private int selectedItemPosition = -1;

    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener, int selectedItemPosition) {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
        this.selectedItemPosition = selectedItemPosition;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calender_item, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        String dayString = daysOfMonth.get(position);
        if (!dayString.isEmpty()) {
            int day = Integer.parseInt(dayString);

            holder.dayOfMonth.setText(dayString);

            // Get the selected year and month from the CalendarFragment
            int currentYear = CalendarUtils.selectedDate.getYear();
            int currentMonth = CalendarUtils.selectedDate.getMonthValue();

            // Check if there are shifts for the current day and month
            boolean hasShifts = DataManager.hasShiftsForDayAndMonth(day, currentMonth, currentYear);

            if (hasShifts) {
                // If there are shifts, set the text color and background accordingly
                holder.dayOfMonth.setTextColor(holder.itemView.getResources().getColor(R.color.white));
                holder.itemView.setBackgroundResource(R.color.gray);
            } else {
                // If there are no shifts, reset the text color and background
                holder.dayOfMonth.setTextColor(holder.itemView.getResources().getColor(android.R.color.black));
                holder.itemView.setBackgroundResource(0);
            }

            // Highlight the selected item
            if (selectedItemPosition == position) {
                holder.itemView.setBackgroundResource(R.color.black);
            } else {
                holder.itemView.setBackgroundResource(0);
            }
        } else {
            // Handle the case when the day string is empty
            holder.dayOfMonth.setText("");
            holder.itemView.setBackgroundResource(0);
        }
    }



    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public interface OnItemListener {
        void onItemClick(int position, String dayText);
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView dayOfMonth;
        private final OnItemListener onItemListener;

        public CalendarViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            dayOfMonth = itemView.findViewById(R.id.cellDayText);
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            selectedItemPosition = getAdapterPosition(); // Update the selected item position
            notifyDataSetChanged(); // Notify the adapter that the data has changed
            onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
        }
    }
}
