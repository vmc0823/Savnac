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

/***
 * Author: Wootark (Tom) Kim
 */

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
    private SavnacUser currentUser;


    // loading the activity screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repo = SavnacRepository.getInstance(getApplicationContext());

        repo.getCurrentUser().observe(this, user -> {
            Log.d("JoinActivity", "Loaded role: [" + user.getRole() + "]");
            if (user == null || !"teacher".equalsIgnoreCase(user.getRole().trim())) {
                finish();
                return;
            }

        currentTeacherId = user.getId();

        setContentView(R.layout.join_or_leave_courses_teacher_perspective_page);
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

            //adapters with click-handlers that update Room
        currentlyEnrolledCourseAdapter = new CoursesActivityRecyclerAdapter(
                new ArrayList<>(),
                course -> {
                course.setTeacherId(0);
                repo.updateCourse(course);
                }
        );

        availableCoursesAdapter = new CoursesActivityRecyclerAdapter(
                new ArrayList<>(),
                course -> {
                    course.setTeacherId(currentTeacherId);
                    repo.updateCourse(course);
                }
        );

        //wiring them to recyclerViews
        currentlyEnrolledCourseRecyclerView.setLayoutManager(new LinearLayoutManager(joinOrLeaveCoursesTeacherPerspectiveActivity.this));    // this means the list will scroll vertically like a classic list
        currentlyEnrolledCourseRecyclerView.setAdapter(currentlyEnrolledCourseAdapter);

        availableCoursesRecyclerView.setLayoutManager(new LinearLayoutManager(joinOrLeaveCoursesTeacherPerspectiveActivity.this));    // this means the list will scroll vertically like a classic list
        availableCoursesRecyclerView.setAdapter(availableCoursesAdapter);                       // tells the RecyclerView how to display each item by using the adapter



//        currentlyEnrolledCourseAdapter = new CoursesActivityRecyclerAdapter(currentlyEnrolledCourses, course -> {
//            // when clicked, remove from current and move to available
//            currentlyEnrolledCourseAdapter.removeItem(course);
//            availableCoursesAdapter.addItem(course);
//        });


            //observe Room lists and feed into adapters
            repo.getCourseByTeacher(currentTeacherId)
                    .observe(joinOrLeaveCoursesTeacherPerspectiveActivity.this, taughtCourses -> {
                        currentlyEnrolledCourseAdapter.updateData(taughtCourses);
                    });

            //"available = all other courses
            repo.getAllCourses()
                    .observe(joinOrLeaveCoursesTeacherPerspectiveActivity.this, allCourses -> {
                        List<SavnacCourse> available = new ArrayList<>(allCourses);
                        available.removeIf(c -> c.getTeacherId() == currentTeacherId);
                        availableCoursesAdapter.updateData(available);
                    });
        });

    }
}
