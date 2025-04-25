package com.silisurfers.savnac;


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

        repo = SavnacRepository.getInstance(getApplicationContext());
        listOfAssignmentDisplayRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListOfAvailableAssignmentsAdapter(assignmentList, null);
        listOfAssignmentDisplayRecyclerView.setAdapter(adapter);



        setContentView(R.layout.show_list_of_active_assignments);

        // Retrieve the number associated with "courseId." If no valid number exist, default to -1 which will serve as an "error flag"
        courseId = getIntent().getIntExtra("courseId", -1);

        // for debugging purpose
        Log.d("Checkpoint", "courseId passed: " + courseId);

        // associating the java field "listOfAssignmentDisplayRecyclerView" to the xml view id "list_of_assignment_display_recyclerView"
        // found in the "show_list_of_active_assignments.xml" file
        listOfAssignmentDisplayRecyclerView = findViewById(R.id.list_of_assignment_display_recyclerView);

        // associating the java field "createNewAssignmentButton" with the xml view id "create_new_assignment_button" found in the
        // "show_list_of_active_assignments.xml" file
        createNewAssignmentButton = findViewById(R.id.create_new_assignment_button);

        // assigning what happens when the createNewAssignmentButton is clicked on.
        createNewAssignmentButton.setOnClickListener(v ->{

            // for debugging purpose: to make sure this button being clicked is actually being registered
            Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ShowListOfActiveAssignmentsActivity.this, CreateAssignmentActivity.class);
            intent.putExtra(CreateAssignmentActivity.EXTRA_COURSE_ID, courseId);
            startActivity(intent);
        });
    }
}
