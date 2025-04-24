package com.silisurfers.savnac;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.silisurfers.savnac.database.SavnacRepository;
import com.silisurfers.savnac.database.entities.SavnacCourse;
import com.silisurfers.savnac.database.entities.SavnacUser;
import com.silisurfers.savnac.viewHolder.CoursesActivityRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class joinOrLeaveCoursesTeacherPerspectiveActivity extends AppCompatActivity {

    // chose recyclerView since I want a scrollable list of courses that can be clickable
    private RecyclerView availableCoursesRecyclerView;
    private RecyclerView currentlyEnrolledCourseRecyclerView;

    // this tells RecyclerView what to show
    private CoursesActivityRecyclerAdapter availableCoursesAdapter;
    private CoursesActivityRecyclerAdapter currentlyEnrolledCourseAdapter;

    // I need these lines to create a dummy data => will create a list of course objects (e.g. "CST 300" etc.)
    private List<SavnacCourse> availableCourses;
    private List<SavnacCourse> currentlyEnrolledCourses;
    private SavnacRepository repo;
    private int currentTeacherId;


    // loading the activity screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This line is what connects my JAVA code to my xml file/layout. It basically means:
        // "use this activity_main.xml layout file to build this screen's UI"
        setContentView(R.layout.join_or_leave_courses_teacher_perspective_page);

        repo = SavnacRepository.getInstance(getApplicationContext());

        availableCoursesRecyclerView = findViewById(R.id.AvailableCourses);
        currentlyEnrolledCourseRecyclerView = findViewById(R.id.Current_Course_RecyclerView);

// ==========================[EVERYTHING DOWN HERE IS FOR TESTING PURPOSE]=======================================================================================
        // dummy data
//        availableCourses = new ArrayList<>();
//        availableCourses.add(new SavnacCourse("Computer Science", 1));
//        availableCourses.add(new SavnacCourse("Biology", 1));
//        availableCourses.add(new SavnacCourse("Physics", 1));
//
//        currentlyEnrolledCourses = new ArrayList<>();
//        currentlyEnrolledCourses.add(new SavnacCourse("History", 1));
//
//
//        // creating an adapter and giving it the list of course data
//        availableCoursesAdapter = new CoursesActivityRecyclerAdapter(availableCourses, course -> {
//            // when clicked, remove from available and move to current
//            availableCoursesAdapter.removeItem(course);
//            Log.d("checkpoint", "checkpoint reached: successfully removed course from 'availableCourses'");
//            currentlyEnrolledCourseAdapter.addItem(course);
//            Log.d("checkpoint", "checkpoint reached: successfully added course to 'currentlyEnrolledCourse'");
//        });

        //-VW. initializing adapters with empty lists + click listeners
        availableCoursesAdapter = new CoursesActivityRecyclerAdapter(
                new ArrayList<>(),
                course -> {
                    course.setTeacherId(currentTeacherId);
                    repo.updateCourse(course);
                }
        );

        availableCoursesRecyclerView.setLayoutManager(new LinearLayoutManager(this));    // this means the list will scroll vertically like a classic list
        availableCoursesRecyclerView.setAdapter(availableCoursesAdapter);                       // tells the RecyclerView how to display each item by using the adapter

        currentlyEnrolledCourseAdapter = new CoursesActivityRecyclerAdapter(   //teacher joins this course -> sets teacherId and updates DB
                new ArrayList<>(),
                course -> {
                    course.setTeacherId(0);
                    repo.updateCourse(course);
                }
        );

//        currentlyEnrolledCourseAdapter = new CoursesActivityRecyclerAdapter(currentlyEnrolledCourses, course -> {
//            // when clicked, remove from current and move to available
//            currentlyEnrolledCourseAdapter.removeItem(course);
//            availableCoursesAdapter.addItem(course);
//        });

        currentlyEnrolledCourseRecyclerView.setLayoutManager(new LinearLayoutManager(this));    // this means the list will scroll vertically like a classic list
        currentlyEnrolledCourseRecyclerView.setAdapter(currentlyEnrolledCourseAdapter);                // tells the RecyclerView how to display each item by using the adapter

        //pulling from DB
        repo.getCurrentUser().observe(this, (Observer<SavnacUser>) user -> {
            if (user == null || !"teacher".equals(user.getRole())) {
                finish();
                return;
            }
            currentTeacherId = user.getId();

            //"enrolled" = courses teacher teaches
            repo.getCourseByTeacher(currentTeacherId)
                    .observe(this, taughtCourses -> {
                        currentlyEnrolledCourseAdapter.updateData(taughtCourses);
                    });

            //"available = all other courses
            repo.getAllCourses()
                    .observe(this, allCourses -> {
                        List<SavnacCourse> available = new ArrayList<>(allCourses);
                        available.removeIf(c -> c.getTeacherId() == currentTeacherId);
                        availableCoursesAdapter.updateData(available);
                    });
        });

    }
}
