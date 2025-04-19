package com.silisurfers.savnac;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.silisurfers.savnac.database.entities.SavnacCourse;
import com.silisurfers.savnac.viewHolder.CoursesActivityRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CoursesActivity extends AppCompatActivity {
    ///  this is the container that holds all the links. I picked recycler view since it is a dynamic list and may need to scroll
    private RecyclerView recyclerView;
    private List<SavnacCourse> courses;
    private CoursesActivityRecyclerAdapter adapter;

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

        recyclerView = findViewById(R.id.courses_recycler_view);

        ///  just some dummy data for now
        courses = new ArrayList<>();
        courses.add(new SavnacCourse("CST 300", 1));
        courses.add(new SavnacCourse("CST 338", 1));
        courses.add(new SavnacCourse("CST 363", 1));

        adapter = new CoursesActivityRecyclerAdapter(courses);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }
}