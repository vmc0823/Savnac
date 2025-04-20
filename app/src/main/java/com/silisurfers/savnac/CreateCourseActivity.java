package com.silisurfers.savnac;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.silisurfers.savnac.database.SavnacRepository;
import com.silisurfers.savnac.database.entities.SavnacCourse;
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
        currentUser = repo.getCurrentUser().getValue();

        if(currentUser == null || !"teacher".equals(currentUser.getRole())) { //block non-teachers
            finish();
            return;
        }

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
            String course = editTextCourseName.getText().toString().trim();
            if (TextUtils.isEmpty(course) || admitted.isEmpty()) {
            //TODO: show error to user
            return;
        }
        for (String studentName : admitted) {
            //Enrollment entity here instead
            repo.insertUser(new SavnacUser(studentName,
                    "",
                    "student"));
            }
        finish();
        });
    }
}
