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
import com.silisurfers.savnac.database.entities.SavnacAssignmentWithGrade;
import com.silisurfers.savnac.database.entities.SavnacCourse;
import com.silisurfers.savnac.viewHolder.CoursesActivityRecyclerAdapter;
import com.silisurfers.savnac.viewHolder.GradesActivityRecyclerAdapter;

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

        ///  just some dummy data for now
        grades = new ArrayList<>();
        grades.add(new SavnacAssignmentWithGrade("Assignment 1", 20, 20));
        grades.add(new SavnacAssignmentWithGrade("Assignment 2", 20, 15));
        grades.add(new SavnacAssignmentWithGrade("Assignment 3", 100, 95));

        adapter = new GradesActivityRecyclerAdapter(grades);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        MaterialToolbar toolbar = findViewById(R.id.grades_activity_toolbar);
        toolbar.setNavigationOnClickListener((v) -> {
            System.out.println("menu item click listener fired");
            Intent intent  = new Intent(this, CoursesActivity.class);
            startActivity(intent);
        });

    }


}