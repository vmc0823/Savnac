package com.silisurfers.savnac;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.silisurfers.savnac.database.entities.SavnacCourse;
import com.silisurfers.savnac.viewHolder.CoursesActivityRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class joinOrLeaveCoursesTeacherPerspectiveActivity extends AppCompatActivity {

    private RecyclerView recyclerView; // UI element that will display the list. recyclerView was chosen as I will need a scrollable list of courses
    private CoursesActivityRecyclerAdapter adapter; // this tells RecyclerView what to show

    private List<SavnacCourse> courses; // I need this line to create a dummy data => will create a list of course objects (e.g. "CST 300" etc.)


    // loading the activity screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This line is what connects my JAVA code to my xml file/layout. It basically means:
        // "use this activity_main.xml layout file to build this screen's UI"
        setContentView(R.layout.join_or_leave_courses_teacher_perspective_page);

        recyclerView = findViewById(R.id.AvailableCourses);


        // dummy data
        courses = new ArrayList<>();
        courses.add(new SavnacCourse("CST 300", 1));
        courses.add(new SavnacCourse("CST 338", 1));
        courses.add(new SavnacCourse("CST 363", 1));
        courses.add(new SavnacCourse("CST 101", 1));
        courses.add(new SavnacCourse("CST 102", 1));
        courses.add(new SavnacCourse("CST 103", 1));

        adapter = new CoursesActivityRecyclerAdapter(courses);  // creating an adapter and giving it the list of course data

        recyclerView.setLayoutManager(new LinearLayoutManager(this));   // this means the list will scroll vertically like a classic list
        recyclerView.setAdapter(adapter);   // tells the RecyclerView how to display each item by using the adapter

    }

}
