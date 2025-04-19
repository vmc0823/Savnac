package com.silisurfers.savnac.viewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.silisurfers.savnac.R;
import com.silisurfers.savnac.database.entities.SavnacAssignment;
import com.silisurfers.savnac.database.entities.SavnacAssignmentWithGrade;
import com.silisurfers.savnac.database.entities.SavnacCourse;
import com.silisurfers.savnac.database.entities.SavnacGradeEntry;

import java.util.List;

public class GradesActivityRecyclerAdapter extends RecyclerView.Adapter<GradesActivityRecyclerAdapter.MyViewHolder> {
    private List<SavnacAssignmentWithGrade> itemList;

    public GradesActivityRecyclerAdapter(List<SavnacAssignmentWithGrade> itemList) {
        this.itemList = itemList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public Button textTitle;
        public TextView grade;

        public TextView assignmentPoints;

        public MyViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.assignment_name);
            assignmentPoints = itemView.findViewById(R.id.assignment_points);
            grade = itemView.findViewById(R.id.grade);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignment_grade_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SavnacAssignmentWithGrade item = itemList.get(position);
        holder.textTitle.setText(item.assignment.getAssignmentName());
        holder.grade.setText(String.valueOf(item.grade.getGrade()));
        holder.assignmentPoints.setText(String.valueOf(item.assignment.getPoints()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItem(SavnacAssignmentWithGrade item) {
        itemList.add(item);
        notifyItemInserted(itemList.size() - 1);
    }
}