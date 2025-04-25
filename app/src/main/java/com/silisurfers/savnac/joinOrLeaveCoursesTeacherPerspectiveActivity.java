package com.silisurfers.savnac;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.silisurfers.savnac.database.SavnacRepository;
import com.silisurfers.savnac.database.entities.SavnacCourse;
import com.silisurfers.savnac.database.entities.SavnacEnrollment;
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

    private SavnacRepository repo;
    //private SavnacUser currentUser;
    private boolean isTeacher;

    private int currentUserId;


    // loading the activity screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.join_or_leave_courses_teacher_perspective_page);

        MaterialToolbar toolbar = findViewById(R.id.courses_activity_toolbar);
        toolbar.setNavigationOnClickListener((v) -> {
            startActivity(new Intent(this, CoursesActivity.class));
        });

        availableCoursesRecyclerView = findViewById(R.id.AvailableCourses);
        currentlyEnrolledCourseRecyclerView = findViewById(R.id.Current_Course_RecyclerView);

        availableCoursesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        currentlyEnrolledCourseRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // attach empty adapters right away so layout has something
        availableCoursesAdapter = new CoursesActivityRecyclerAdapter(
                new ArrayList<>(),
                course -> { /* will be replaced below */ }
        );
        currentlyEnrolledCourseAdapter = new CoursesActivityRecyclerAdapter(
                new ArrayList<>(),
                course -> { /* will be replaced below */ }
        );
        availableCoursesRecyclerView.setAdapter(availableCoursesAdapter);
        currentlyEnrolledCourseRecyclerView.setAdapter(currentlyEnrolledCourseAdapter);

        // get your repo
        repo = SavnacRepository.getInstance(getApplicationContext());

        // only once we know who is logged in do we wire the click-handlers and LiveData sources
        repo.getCurrentUser().observe(this, user -> {
            if (user == null) {
                finish();
                return;
            }

            currentUserId = user.getId();
            isTeacher     = "teacher".equalsIgnoreCase(user.getRole().trim());

            if (isTeacher) {
                // TEACHER: assign / unassign courses
                availableCoursesAdapter = new CoursesActivityRecyclerAdapter(
                        new ArrayList<>(),
                        course -> {
                            // unassign moves it up
                            availableCoursesAdapter.removeItem(course);
                            currentlyEnrolledCourseAdapter.addItem(course);
                            course.setTeacherId(currentUserId);
                            repo.updateCourse(course);
                        }
                );
                currentlyEnrolledCourseAdapter = new CoursesActivityRecyclerAdapter(
                        new ArrayList<>(),
                        course -> {
                            // unassign (null teacher)
                            currentlyEnrolledCourseAdapter.removeItem(course);
                            availableCoursesAdapter.addItem(course);
                            course.setTeacherId(null);
                            repo.updateCourse(course);
                        }
                );

                // observe taught courses
                repo.getCourseByTeacher(currentUserId)
                        .observe(this, taught ->
                                currentlyEnrolledCourseAdapter.updateData(taught)
                        );
                // observe others
                repo.getAllCourses()
                        .observe(this, all -> {
                            List<SavnacCourse> avail = new ArrayList<>(all);
                            avail.removeIf(c -> c.getTeacherId() != null
                                    && c.getTeacherId().equals(currentUserId));
                            availableCoursesAdapter.updateData(avail);
                        });

            } else {
                // STUDENT: enroll / unenroll
                availableCoursesAdapter = new CoursesActivityRecyclerAdapter(
                        new ArrayList<>(),
                        course -> {
                            availableCoursesAdapter.removeItem(course);
                            currentlyEnrolledCourseAdapter.addItem(course);
                            repo.insertEnrollment(new SavnacEnrollment(currentUserId, course.getId()));
                        }
                );
                currentlyEnrolledCourseAdapter = new CoursesActivityRecyclerAdapter(
                        new ArrayList<>(),
                        course -> {
                            currentlyEnrolledCourseAdapter.removeItem(course);
                            availableCoursesAdapter.addItem(course);
                            repo.deleteEnrollment(new SavnacEnrollment(currentUserId, course.getId()));
                        }
                );

                // observe enrolled courses
                repo.getCoursesForStudent(currentUserId)
                        .observe(this, enrolled ->
                                currentlyEnrolledCourseAdapter.updateData(enrolled)
                        );
                // observe the rest
                repo.getAllCourses()
                        .observe(this, all -> {
                            repo.getEnrollmentsForStudent(currentUserId)
                                    .observe(this, enrolls -> {
                                        List<Integer> ids = new ArrayList<>();
                                        for (var e : enrolls) ids.add(e.getCourseId());
                                        List<SavnacCourse> avail = new ArrayList<>(all);
                                        avail.removeIf(c -> ids.contains(c.getId()));
                                        availableCoursesAdapter.updateData(avail);
                                    });
                        });
            }

            // re-attach the newly-created adapters
            availableCoursesRecyclerView.setAdapter(availableCoursesAdapter);
            currentlyEnrolledCourseRecyclerView.setAdapter(currentlyEnrolledCourseAdapter);
        });
    }
}
