package com.silisurfers.savnac;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.silisurfers.savnac.database.entities.SavnacAssignment;
import com.silisurfers.savnac.database.entities.SavnacAssignmentWithGrade;
import com.silisurfers.savnac.database.entities.SavnacCourse;
import com.silisurfers.savnac.database.entities.SavnacGradeEntry;
import com.silisurfers.savnac.database.entities.SavnacUser;
import com.silisurfers.savnac.viewHolder.CoursesActivityRecyclerAdapter;
import com.silisurfers.savnac.viewHolder.GradesActivityRecyclerAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GradesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<SavnacAssignmentWithGrade> grades;
    private GradesActivityRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grades);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.grades_recycler_view);
        SavnacUser dummyUser = new SavnacUser("dummyStudent", "dummyPassword", "student");
        SavnacUser dummyTeacher = new SavnacUser("dummyTeacher", "dummyPassword", "teacher");
        SavnacCourse dummyCourse = new SavnacCourse("dummyCourse", dummyTeacher.getId());
        SavnacAssignment dummyAssignment1 = new SavnacAssignment("dummyAssignment1", dummyCourse.getId(), 20);
        SavnacAssignment dummyAssignment2 = new SavnacAssignment("dummyAssignment2", dummyCourse.getId(), 20);
        SavnacAssignment dummyAssignment3 = new SavnacAssignment("dummyAssignment3", dummyCourse.getId(), 100);
        SavnacGradeEntry dummyAssignmentGrade1 = new SavnacGradeEntry(dummyUser.getId(),dummyAssignment1.getId(),20, LocalDateTime.now());
        SavnacGradeEntry dummyAssignmentGrade2 = new SavnacGradeEntry(dummyUser.getId(),dummyAssignment2.getId(),15, LocalDateTime.now());
        SavnacGradeEntry dummyAssignmentGrade3 = new SavnacGradeEntry(dummyUser.getId(),dummyAssignment2.getId(),95, LocalDateTime.now());
        SavnacAssignmentWithGrade dummyAssignmentWithGrade1 = new SavnacAssignmentWithGrade(dummyAssignmentGrade1,dummyAssignment1);
        SavnacAssignmentWithGrade dummyAssignmentWithGrade2 = new SavnacAssignmentWithGrade(dummyAssignmentGrade2,dummyAssignment2);
        SavnacAssignmentWithGrade dummyAssignmentWithGrade3 = new SavnacAssignmentWithGrade(dummyAssignmentGrade3,dummyAssignment3);


        ///  just some dummy data for now
        grades = new ArrayList<>();
        grades.add(dummyAssignmentWithGrade1);
        grades.add(dummyAssignmentWithGrade2);
        grades.add(dummyAssignmentWithGrade3);

        adapter = new GradesActivityRecyclerAdapter(grades);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        MaterialToolbar toolbar = findViewById(R.id.grades_activity_toolbar);
        toolbar.setNavigationOnClickListener((v) -> {
            Intent intent  = new Intent(this, CoursesActivity.class);
            startActivity(intent);
        });

    }


}