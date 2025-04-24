package com.silisurfers.savnac;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.silisurfers.savnac.database.SavnacRepository;
import com.silisurfers.savnac.viewHolder.CoursesActivityRecyclerAdapter;

/***
 * Author: Wootark (Tom) Kim
 * Date creation: 23 April 2024
 * Purpose of file: a JAVA activity class for the "show_list_of_active_assignments" xml file.
 */

public class ShowListOfActiveAssignmentsActivity extends AppCompatActivity {

    // Fields
    private RecyclerView listOfAssignmentDisplayRecyclerView;
    private Button createNewAssignmentButton;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_list_of_active_assignments);

        // Retrieve the number associated with "courseId." If no valid number exist, default to -1 which will serve as an "error flag"
        courseId = getIntent().getIntExtra("courseId", -1);

        // associating the java field "listOfAssignmentDisplayRecyclerView" to the xml view id "list_of_assignment_display_recyclerView"
        // found in the "show_list_of_active_assignments.xml" file
        listOfAssignmentDisplayRecyclerView = findViewById(R.id.list_of_assignment_display_recyclerView);

        // associating the java field "createNewAssignmentButton" with the xml view id "create_new_assignment_button" found in the
        // "show_list_of_active_assignments.xml" file
        createNewAssignmentButton = findViewById(R.id.create_new_assignment_button);

        // assigning what happens when the createNewAssignmentButton is clicked on.
        createNewAssignmentButton.setOnClickListener(v ->{
            Intent intent = new Intent(ShowListOfActiveAssignmentsActivity.this, CreateAssignmentActivity.class);
            intent.putExtra(CreateAssignmentActivity.EXTRA_COURSE_ID, courseId);
            startActivity(intent);
        });

    }


}
