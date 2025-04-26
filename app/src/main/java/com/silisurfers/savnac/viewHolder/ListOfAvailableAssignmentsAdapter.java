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
import com.silisurfers.savnac.database.entities.SavnacCourse;

import java.util.List;

/**
 * Author: Wootark (Tom) Kim
 * Date: (25 April 2025)
 */
public class ListOfAvailableAssignmentsAdapter extends RecyclerView.Adapter<ListOfAvailableAssignmentsAdapter.AssignmentViewHolder> {

    private List<SavnacAssignment> assignmentList;

    private CoursesActivityRecyclerAdapter.OnCourseClickListener listener;

    public ListOfAvailableAssignmentsAdapter(List<SavnacAssignment> assignmentList, CoursesActivityRecyclerAdapter.OnCourseClickListener listener){
        this.assignmentList = assignmentList;
        this.listener = listener;
    }

    // [Serving as an internal ViewHolder class (instead of creating a separate file)]
    // =============================================================================================================================================
    public static class AssignmentViewHolder extends RecyclerView.ViewHolder{
        Button assignmentNameButton;
        TextView assignmentPoints;

        public AssignmentViewHolder(View itemView){
            super(itemView);
            assignmentNameButton = itemView.findViewById(R.id.assignment_name);
            assignmentPoints = itemView.findViewById(R.id.assignment_points);
        }
    }
    // =============================================================================================================================================

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_grade_item, parent, false);
        return new AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position){
    SavnacAssignment assignment = assignmentList.get(position);
    holder.assignmentNameButton.setText(assignment.getAssignmentName());
    holder.assignmentPoints.setText(String.valueOf(assignment.getPoints()));
    }

    @Override
    public int getItemCount(){
        return assignmentList.size();
    }
}
