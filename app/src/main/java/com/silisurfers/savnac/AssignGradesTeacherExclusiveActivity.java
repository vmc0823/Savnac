package com.silisurfers.savnac;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.silisurfers.savnac.database.SavnacRepository;
import com.silisurfers.savnac.viewHolder.AssignGradesTeacherExclusiveActivityAdapter;

public class AssignGradesTeacherExclusiveActivity extends AppCompatActivity {

    private int courseId;
    private int assignmentId;
    private RecyclerView studentListWithGradeInputRecyclerView;
    private AssignGradesTeacherExclusiveActivityAdapter adapter;
    private SavnacRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assign_grades_teacher_exclusive_page);

        // getting data from previous activity
        courseId = getIntent().getIntExtra("courseId", -1);
        assignmentId = getIntent().getIntExtra("assignmentId", -1);

        // for debugging purpose:
        Log.d("Checkpoint", "Received courseId: " + courseId + "\nassignmentId: " + assignmentId);

        repository = SavnacRepository.getInstance(getApplicationContext());

        studentListWithGradeInputRecyclerView = findViewById(R.id.student_list_with_grade_input_recyclerView);
        studentListWithGradeInputRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository.getStudentsInCourse(courseId).observe(this, students -> {
            adapter = new AssignGradesTeacherExclusiveActivityAdapter(students, assignmentId);
            studentListWithGradeInputRecyclerView.setAdapter(adapter);
        });
    }

    static Intent assignGradesIntentFactory(Context context, int courseId, int assignmentId) {
        Intent intent = new Intent(context, AssignGradesTeacherExclusiveActivity.class);
        intent.putExtra("courseId", courseId);
        intent.putExtra("assignmentId", assignmentId);
        return intent;
    }

}
