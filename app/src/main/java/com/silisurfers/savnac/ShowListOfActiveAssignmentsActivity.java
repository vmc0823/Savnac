package com.silisurfers.savnac;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.silisurfers.savnac.database.SavnacRepository;
import com.silisurfers.savnac.database.entities.SavnacAssignment;
import com.silisurfers.savnac.database.entities.SavnacUser;
import com.silisurfers.savnac.viewHolder.CoursesActivityRecyclerAdapter;
import com.silisurfers.savnac.viewHolder.ListOfAvailableAssignmentsAdapter;

import java.util.ArrayList;
import java.util.List;

/***
 * Author: Wootark (Tom) Kim
 * Date creation: 23 April 2024
 * Purpose of file: a JAVA activity class for the "show_list_of_active_assignments" xml file.
 */

public class ShowListOfActiveAssignmentsActivity extends AppCompatActivity {

    // Fields
    private RecyclerView listOfAssignmentDisplayRecyclerView;
    private Button createNewAssignmentButton;
    private SavnacRepository repo;
    private int courseId;


    private List<SavnacAssignment> assignmentList = new ArrayList<>();
    private ListOfAvailableAssignmentsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_list_of_active_assignments);

        // for debugging purpose
        SavnacUser user = SavnacRepository.getInstance(getApplicationContext()).getCurrentUser().getValue();
        if (user != null) {
            Log.d("Checkpoint", "Arrived in AssignmentsActivity, user role is: " + user.getRole());
        } else {
            Log.d("Checkpoint", "User is NULL in AssignmentsActivity.");
        }

        //for debugging purpose
        // Retrieve the number associated with "courseId." If no valid number exist, default to -1 which will serve as an "error flag"
        courseId = getIntent().getIntExtra("courseId", -1);
        Log.d("Checkpoint", "courseId passed: " + courseId);

        // HINT: for future reference, remember to initialize these fields to the id before calling the layout manager
        // associating the java field "listOfAssignmentDisplayRecyclerView" to the xml view id "list_of_assignment_display_recyclerView"
        // found in the "show_list_of_active_assignments.xml" file
        listOfAssignmentDisplayRecyclerView = findViewById(R.id.list_of_assignment_display_recyclerView);

        // associating the java field "createNewAssignmentButton" with the xml view id "create_new_assignment_button" found in the
        // "show_list_of_active_assignments.xml" file
        createNewAssignmentButton = findViewById(R.id.create_new_assignment_button);

        // setup the repository
        repo = SavnacRepository.getInstance(getApplicationContext());

        // setup recyclerview and adapter
        adapter = new ListOfAvailableAssignmentsAdapter(assignmentList, null);
        listOfAssignmentDisplayRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listOfAssignmentDisplayRecyclerView.setAdapter(adapter);

        // assigning what happens when the createNewAssignmentButton is clicked on.
        createNewAssignmentButton.setOnClickListener(v ->{

            // for debugging purpose: to make sure this button being clicked is actually being registered
            Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show();

            Intent intent = CreateAssignmentActivity.createAssignmentIntentFactory(getApplicationContext());
            intent.putExtra(CreateAssignmentActivity.EXTRA_COURSE_ID, courseId);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        repo.getAssignmentsByCourse(courseId).observe(this, assignments -> {
            assignmentList.clear();
            assignmentList.addAll(assignments);
            adapter.notifyDataSetChanged();
        });
    }

    // added by Brandon (25 April 2025)
    static Intent showListOfActiveAssignmentsIntentFactory(Context context) {
        return new Intent(context, ShowListOfActiveAssignmentsActivity.class);
    }
}
