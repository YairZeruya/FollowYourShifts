package com.example.followyourshifts.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.followyourshifts.R;
import com.example.followyourshifts.Shift;
import com.example.followyourshifts.Workplace;

import java.text.BreakIterator;
import java.util.ArrayList;

public class WorkplaceAdapter extends RecyclerView.Adapter<WorkplaceAdapter.WorkplaceViewHolder> {



    private ArrayList<Workplace> workplaces;
    private  WorkplaceCallBack workplaceCallBack;
//    private RecordCallBack recordCallBack;
//
//    public void setRecordCallBack(RecordCallBack recordCallBack) {
//        this.recordCallBack = recordCallBack;
//    }

    //    public RecordAdapter(ArrayList<Record> records, RecordCallBack recordCallBack) {
//        this.records = records;
//        this.recordCallBack = recordCallBack;
//    }
    public WorkplaceAdapter(ArrayList<Workplace> workplaces){
        this.workplaces = workplaces;
    }

    public void setWorkplaceCallBack(WorkplaceCallBack workplaceCallBack) {
        this.workplaceCallBack = workplaceCallBack;
    }

    //should be like this
//    @NonNull
//    @Override
//    public ShiftFragment.ShiftsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shift_item, parent, false);
//        ShiftsViewHolder shiftsViewHolder = new ShiftsViewHolder(view);
//        return shiftsViewHolder;
//    }

    //*****but i try this
    @NonNull
    @Override
    public WorkplaceAdapter.WorkplaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workplace_item, parent, false);
        WorkplaceAdapter.WorkplaceViewHolder workplaceViewHolder = new WorkplaceAdapter.WorkplaceViewHolder(view);
        return workplaceViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkplaceAdapter.WorkplaceViewHolder holder, int position) {
        Workplace workplace = getItem(position);
        holder.shift_LBL_workplace.setText(workplace.getWorkplaceName());
        holder.shift_LBL_salaryPerHour.setText(workplace.getSalaryPerHour() + " per hour");
        String workplaceName =workplace.getWorkplaceName();
        double salaryPerHour = workplace.getSalaryPerHour();
        double hours100 = workplace.getHours100();
        double hours125 = workplace.getHours125();
        double hours150 = workplace.getHours150();
        double totalSalary = workplace.getTotalSalary();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(workplaceCallBack != null){
                    workplaceCallBack.workplaceClicked
                            (workplaceName,salaryPerHour,hours100,hours125,hours150,totalSalary,position);
                }
            }
        });
                //holder.record_LBL_score.setText(shift.getScore());
                //holder.record_layout.setOnClickListener(v -> recordClicked(record.getLatitude(), record.getLongitude()));
    }

//    private void workplaceClicked(String workplaceName, double salaryPerHour, int hours100, int hours125, int hours150, double totalSalary) {
//        if(workplaceCallBack != null){
//            workplaceCallBack.workplaceClicked(workplaceName,salaryPerHour,hours100,hours125,hours150,totalSalary);
//        }
//    }


//    private void recordClicked(double latitude, double longitude) {
//        if (recordCallBack != null) {
//            recordCallBack.recordClicked(latitude, longitude);
//        }
//    }

    @Override
    public int getItemCount() {
        return this.workplaces == null ? 0 : this.workplaces.size();
    }

    public Workplace getItem(int position) {
        return this.workplaces.get(position);
    }
    public interface WorkplaceCallBack {
        void workplaceClicked(String workplaceName, double salaryPerHour,double hours100,double hours125,double hours150,double totalSalary, int position);
    }

    public class WorkplaceViewHolder extends RecyclerView.ViewHolder {
        public TextView shift_LBL_salaryPerHour;
        private TextView shift_LBL_workplace;

        //private LinearLayout record_layout;


        public WorkplaceViewHolder(@NonNull View itemView) {
            super(itemView);
            shift_LBL_workplace = itemView.findViewById(R.id.shift_LBL_workplace);
            shift_LBL_salaryPerHour = itemView.findViewById(R.id.shift_LBL_salaryPerHour);
//            String workplaceName = getItem(getAdapterPosition()).getWorkplaceName();
//            double salaryPerHour = getItem(getAdapterPosition()).getSalaryPerHour();
//            int hours100 = getItem(getAdapterPosition()).getHours100();
//            int hours125 = getItem(getAdapterPosition()).getHours125();
//            int hours150 = getItem(getAdapterPosition()).getHours150();
//            double totalSalary = getItem(getAdapterPosition()).getTotalSalary();
//            itemView.setOnClickListener(v -> {
//                if(workplaceCallBack != null){
//                    workplaceCallBack.workplaceClicked(workplaceName,salaryPerHour,hours100,hours125,hours150,totalSalary);
//                }
//            });
//            record_LBL_score = itemView.findViewById(R.id.score_textview);
//            record_layout = itemView.findViewById(R.id.record_layout);
        }

    }
}
