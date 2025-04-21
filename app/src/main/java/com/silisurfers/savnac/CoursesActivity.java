package com.silisurfers.savnac;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.silisurfers.savnac.database.SavnacRepository;
import com.silisurfers.savnac.database.entities.SavnacCourse;
import com.silisurfers.savnac.database.entities.SavnacUser;
import com.silisurfers.savnac.viewHolder.CoursesActivityRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CoursesActivity extends AppCompatActivity {
    ///  this is the container that holds all the links. I picked recycler view since it is a dynamic list and may need to scroll
    private RecyclerView recyclerView;
    private List<SavnacCourse> courses;
    private CoursesActivityRecyclerAdapter adapter;
    private SavnacRepository repo;
    private SavnacUser currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_courses);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        repo = SavnacRepository.getInstance(getApplication());
        currentUser = repo.getCurrentUser().getValue();
        recyclerView = findViewById(R.id.courses_recycler_view);

        ///  just some dummy data for now
        courses = new ArrayList<>();
        courses.add(new SavnacCourse("CST 300", 1));
        courses.add(new SavnacCourse("CST 338", 1));
        courses.add(new SavnacCourse("CST 363", 1));

//        adapter = new CoursesActivityRecyclerAdapter(courses);

        // added by Tom (19 April 2025)
        adapter = new CoursesActivityRecyclerAdapter(courses, course ->{
            // JAVA forces this 2nd parameter to exist but it doesn't do anything
            // this is because the changes ive modified for the CoursesActivityRecyclerAdapter was meant for my
            // joinOrLeaveCoursesTeacherPerspectiveActivity activity page (not this coursesActivity... yet)
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Button createNewCourseButton = findViewById(R.id.create_a_course_button);
        if(currentUser == null || !"teacher".equals(currentUser.getRole())) {
            createNewCourseButton.setVisibility(View.GONE);
        }


        createNewCourseButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreateCourseActivity.class);
            startActivity(intent);
        });


    }
}