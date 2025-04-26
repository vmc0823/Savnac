package com.silisurfers.savnac;

import android.app.Activity;
import android.content.Context;
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
import com.silisurfers.savnac.database.SavnacRepository;
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
import java.util.UUID;

public class GradesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SavnacRepository repo;

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
        try{
            repo = SavnacRepository.getInstance(getApplicationContext());
            recyclerView = findViewById(R.id.grades_recycler_view);
            SavnacUser user = repo.getCurrentUser().getValue();
            int courseId = getIntent().getIntExtra("courseId", 0);
            if(user != null && courseId != 0){

                repo.getUserGradesByCourseId(user.getId(),courseId).observe(this, grades -> {
                    adapter.setItems(grades);
                    adapter.notifyDataSetChanged();
                });
            }
        }catch(Exception e){

           e.printStackTrace();
            System.out.println("failed to generate dummy data");
        }



        adapter = new GradesActivityRecyclerAdapter(new ArrayList<>());
//
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        MaterialToolbar toolbar = findViewById(R.id.grades_activity_toolbar);
        toolbar.setNavigationOnClickListener((v) -> {
            Intent intent = CoursesActivity.coursesIntentFactory(getApplicationContext());
            startActivity(intent);
        });

    }

    // added by Brandon (25 April 2025)
    static Intent gradesIntentFactory(Context context) {
        return new Intent(context, GradesActivity.class);
    }
}