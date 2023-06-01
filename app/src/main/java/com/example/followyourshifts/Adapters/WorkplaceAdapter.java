package com.example.followyourshifts.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.followyourshifts.R;
import com.example.followyourshifts.Objects.Workplace;

import java.util.ArrayList;

public class WorkplaceAdapter extends RecyclerView.Adapter<WorkplaceAdapter.WorkplaceViewHolder> {

    private ArrayList<Workplace> workplaces;
    private  WorkplaceCallBack workplaceCallBack;

    public WorkplaceAdapter(ArrayList<Workplace> workplaces){
        this.workplaces = workplaces;
    }

    public void setWorkplaceCallBack(WorkplaceCallBack workplaceCallBack) {
        this.workplaceCallBack = workplaceCallBack;
    }

    public interface WorkplaceCallBack {
        void workplaceClicked(String workplaceName, int position);
    }

    @NonNull
    @Override
    public WorkplaceAdapter.WorkplaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workplace_item, parent, false);
        WorkplaceAdapter.WorkplaceViewHolder workplaceViewHolder = new WorkplaceAdapter.WorkplaceViewHolder(view);
        return workplaceViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkplaceAdapter.WorkplaceViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Workplace workplace = getItem(position);
        holder.shift_LBL_workplace.setText(workplace.getName());
        holder.shift_LBL_salaryPerHour.setText(workplace.getSalaryPerHour() + " per hour");
        String workplaceName =workplace.getName();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(workplaceCallBack != null){
                    workplaceCallBack.workplaceClicked
                            (workplaceName,position);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return this.workplaces == null ? 0 : this.workplaces.size();
    }

    public Workplace getItem(int position) {
        return this.workplaces.get(position);
    }


    public class WorkplaceViewHolder extends RecyclerView.ViewHolder {
        public TextView shift_LBL_salaryPerHour;
        private TextView shift_LBL_workplace;

        public WorkplaceViewHolder(@NonNull View itemView) {
            super(itemView);
            shift_LBL_workplace = itemView.findViewById(R.id.shift_LBL_workplace);
            shift_LBL_salaryPerHour = itemView.findViewById(R.id.shift_LBL_salaryPerHour);
        }

    }
}
