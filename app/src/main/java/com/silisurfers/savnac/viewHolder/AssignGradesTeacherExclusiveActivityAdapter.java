package com.silisurfers.savnac.viewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.silisurfers.savnac.AssignGradesTeacherExclusiveActivity;
import com.silisurfers.savnac.R;
import com.silisurfers.savnac.database.entities.SavnacUser;

import java.util.List;

public class AssignGradesTeacherExclusiveActivityAdapter extends RecyclerView.Adapter<AssignGradesTeacherExclusiveActivityAdapter.ViewHolder> {

    // fields
    private List<SavnacUser> students;
    private int assignmentId;

    public AssignGradesTeacherExclusiveActivityAdapter(List<SavnacUser> students, int assignmentId){
        this.students = students;
        this.assignmentId = assignmentId;
    }

    // [Serving as an internal ViewHolder class (instead of creating a separate file)]
    // =============================================================================================================================================
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentNameTextView;
        EditText gradeInput;

        public ViewHolder(View itemView) {
            super(itemView);
            studentNameTextView = itemView.findViewById(R.id.student_name_text_view);
            gradeInput = itemView.findViewById(R.id.grade_input_edit_text);
        }
    }
    //===============================================================================================================================================

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_grade_input_row, parent, false); // <-- create this XML layout
        return new ViewHolder(view);

        //TODO: make a student_grade_input_row xml file to put into the student_list_with_grade_input_recyclerView
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SavnacUser student = students.get(position);
        holder.studentNameTextView.setText(student.getUsername());

        //TODO: implement manual grade entry handling here later
    }

    @Override
    public int getItemCount(){
        return students.size();
    }
}
