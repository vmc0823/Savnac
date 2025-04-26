package com.silisurfers.savnac;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.silisurfers.savnac.database.SavnacRepository;
import com.silisurfers.savnac.database.entities.SavnacCourse;
import com.silisurfers.savnac.database.entities.SavnacEnrollment;
import com.silisurfers.savnac.database.entities.SavnacUser;
import com.silisurfers.savnac.viewHolder.StudentListAdapter;

import java.util.ArrayList;
import java.util.List;

//@author: vw

//this class is an extension from course activity when teachers click on Create New Course
public class CreateCourseActivity extends AppCompatActivity {

    private SavnacRepository repo;
    private SavnacUser currentUser;
    private RecyclerView recyclerViewStudents;
    private StudentListAdapter adapter;
    private final List<String> admitted = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initializing repo and curreent user
        repo = SavnacRepository.getInstance(getApplicationContext());
        repo.getCurrentUser().observe(this, user -> {
            if (user == null || !"teacher".equalsIgnoreCase(user.getRole().trim())) { //block non-teachers
                finish();
            } else {
                currentUser = user;
                initializeUI();
            }
        });
    }

    private void initializeUI() {
        //inflate layout
        setContentView(R.layout.activity_create_course);
        //bind views
        EditText editTextCourseName = findViewById(R.id.editTextCourseName);
        EditText editTextStudentName = findViewById(R.id.editTextStudentName);
        Button buttonAddStudent = findViewById(R.id.buttonAddStudent);
        Button buttonConfirmCourse = findViewById(R.id.buttonConfirmCourse);
        recyclerViewStudents = findViewById(R.id.recyclerViewStudents);
        //layout manager
        recyclerViewStudents.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentListAdapter(admitted);
        recyclerViewStudents.setAdapter(adapter);

        //add student button
        buttonAddStudent.setOnClickListener(v -> {
            String name = editTextStudentName.getText().toString().trim();
            if (!TextUtils.isEmpty(name)) {
                adapter.addStudent(name);
                editTextStudentName.getText().clear();
            }
        });

        //press enter to confirm course button
        buttonConfirmCourse.setOnClickListener(v -> {
            String courseName = editTextCourseName.getText().toString().trim();
            if (TextUtils.isEmpty(courseName) || admitted.isEmpty()) {
                Toast.makeText(this, "Course name/students required", Toast.LENGTH_SHORT).show();
                return;
            }

            //insert course and get the newCourseId directly
            SavnacCourse course = new SavnacCourse(courseName, currentUser.getId());
            long newCourseId = repo.insertCourse(course); //casted it so if multiple courses are added quickly,
            // the last item in the list might not be the newly created course


            //enroll students using newCourseId
            for (String studentName : admitted) {
                repo.getUserByUsername(studentName).observe(this, student -> {
                    if (student != null) {
                        SavnacEnrollment enrollment = new SavnacEnrollment(student.getId(), (int) newCourseId);
                        repo.insertEnrollment(enrollment);
                    }
                });
            }
            finish();
        });
        MaterialToolbar toolbar = findViewById(R.id.courses_activity_toolbar);
        toolbar.setNavigationOnClickListener((v) -> {
            Intent intent = CoursesActivity.coursesIntentFactory(getApplicationContext());
            startActivity(intent);
        });
    }

    // added by Brandon (25 April 2025)
    static Intent createCourseIntentFactory(Context context) {
        return new Intent(context, CreateCourseActivity.class);
    }
}