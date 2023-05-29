package com.example.followyourshifts.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.followyourshifts.R;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>
{
    private final ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;
    private int selectedItemPosition;

    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener, int selectedItemPosition)
    {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
        this.selectedItemPosition = selectedItemPosition;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calender_item, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        holder.dayOfMonth.setText(daysOfMonth.get(position));
        if (position == selectedItemPosition) {
            holder.itemView.setBackgroundResource(R.drawable.dollars_svgrepo_com);
        } else {
            holder.itemView.setBackgroundResource(0);
        }
    }

    @Override
    public int getItemCount()
    {
        return daysOfMonth.size();
    }
    public interface  OnItemListener
    {
        void onItemClick(int position, String dayText);
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public final TextView dayOfMonth;
        private final CalendarAdapter.OnItemListener onItemListener;
        public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener)
        {
            super(itemView);
            dayOfMonth = itemView.findViewById(R.id.cellDayText);
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
        }
    }

}
