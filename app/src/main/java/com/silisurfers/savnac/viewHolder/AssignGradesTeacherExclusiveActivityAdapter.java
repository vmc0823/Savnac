package com.silisurfers.savnac.viewHolder;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.silisurfers.savnac.AssignGradesTeacherExclusiveActivity;
import com.silisurfers.savnac.R;
import com.silisurfers.savnac.database.SavnacRepository;
import com.silisurfers.savnac.database.entities.SavnacGradeEntry;
import com.silisurfers.savnac.database.entities.SavnacUser;

import java.time.LocalDateTime;
import java.util.List;

public class AssignGradesTeacherExclusiveActivityAdapter extends RecyclerView.Adapter<AssignGradesTeacherExclusiveActivityAdapter.ViewHolder> {

    // fields
    private List<SavnacUser> students;
    private int assignmentId;
    private SavnacRepository repository;

    public AssignGradesTeacherExclusiveActivityAdapter(List<SavnacUser> students, int assignmentId, Context context){
        this.students = students;
        this.assignmentId = assignmentId;
        this.repository = SavnacRepository.getInstance(context);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_for_student_and_grade_input, parent, false); // <-- create this XML layout
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SavnacUser student = students.get(position);
        holder.studentNameTextView.setText(student.getUsername());

        holder.gradeInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {

                String input = holder.gradeInput.getText().toString().trim();
                if (!input.isEmpty()) {
                    try {
                        int grade = Integer.parseInt(input);
                        SavnacGradeEntry entry = new SavnacGradeEntry(assignmentId, student.getId(), grade, LocalDateTime.now());
                        repository.insertGradeEntry(entry);
                    } catch (NumberFormatException e) {
                        Log.e("GradeInput", "Invalid grade input: " + input);
                    }
                }
                return true; // consume event
            }
            return false;
        });
    }

    @Override
    public int getItemCount(){
        return students.size();
    }
}
