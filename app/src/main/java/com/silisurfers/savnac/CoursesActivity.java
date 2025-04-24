package com.silisurfers.savnac;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
    private Button createNewCourseButton;
    private Button joinCourseButton;



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

//        currentUser = repo.getCurrentUser().getValue();
        recyclerView = findViewById(R.id.courses_recycler_view);
        createNewCourseButton = findViewById(R.id.create_a_course_button);
        joinCourseButton = findViewById(R.id.join_a_course_button);

        ///  just some dummy data for now
//        courses = new ArrayList<>();
//        courses.add(new SavnacCourse("CST 300", 1));
//        courses.add(new SavnacCourse("CST 338", 1));
//        courses.add(new SavnacCourse("CST 363", 1));

//        adapter = new CoursesActivityRecyclerAdapter(courses);

        // added by Tom (19 April 2025)
        adapter = new CoursesActivityRecyclerAdapter(new ArrayList<>(), course -> {
            // JAVA forces this 2nd parameter to exist but it doesn't do anything
            // this is because the changes ive modified for the CoursesActivityRecyclerAdapter was meant for my
            // joinOrLeaveCoursesTeacherPerspectiveActivity activity page (not this coursesActivity... yet)
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //button navigation
        createNewCourseButton.setOnClickListener(v ->
                startActivity(new Intent(this, CreateCourseActivity.class)));
        joinCourseButton.setOnClickListener(v ->
                startActivity(new Intent(this, joinOrLeaveCoursesTeacherPerspectiveActivity.class)));

        //LiveData sync
        repo = SavnacRepository.getInstance(getApplicationContext());

        repo.getCurrentUser().observe(this, user -> {
            boolean isTeacher = (user != null && "teacher".equalsIgnoreCase(user.getRole().trim())); //teachers see both create new course and join a course,
            // plus only their own courses and students see only join a course, plus every course in the system

            // only teachers can create
            createNewCourseButton.setVisibility(isTeacher ? View.VISIBLE : View.GONE);

            // everyone sees join
            joinCourseButton.setVisibility(user != null ? View.VISIBLE : View.GONE);

            //load course list
            if (user != null) {
                if (isTeacher) {
                    repo.getCourseByTeacher(user.getId())
                            .observe(this, coursesList -> adapter.updateData(coursesList));
                } else {
                    // Students see all courses
                    repo.getAllCourses()
                            .observe(this, coursesList -> adapter.updateData(coursesList));

                    //this option makes it so join a course button disappears for students
//            if (user != null && "teacher".equals(user.getRole())) {
//                createNewCourseButton.setVisibility(View.VISIBLE);
//                joinCourseButton.setVisibility(View.VISIBLE);
//                repo.getCourseByTeacher(user.getId())
//                        .observe(this, coursesList -> {
//                            adapter.updateData(coursesList);
//                        });
//            } else { //student: show all courses
//                createNewCourseButton.setVisibility(View.GONE);
//                joinCourseButton.setVisibility(View.GONE);
//                repo.getAllCourses().observe(this, coursesList ->
//                        adapter.updateData(coursesList));
                }
            }
        });
    }
}