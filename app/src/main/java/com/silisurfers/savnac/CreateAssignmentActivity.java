package com.silisurfers.savnac;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.silisurfers.savnac.database.SavnacRepository;
import com.silisurfers.savnac.database.entities.SavnacAssignment;
import com.silisurfers.savnac.database.entities.SavnacUser;

public class CreateAssignmentActivity extends AppCompatActivity {
    public static final String EXTRA_COURSE_ID = "courseId";

    private EditText editTextAssignmentName, editTextMaxPoints;
    private Button buttonConfirmAssignment;
    private SavnacRepository repo;
    private SavnacUser currentUser;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        repo = SavnacRepository.getInstance(getApplicationContext());
        currentUser = repo.getCurrentUser().getValue();

        //blocking students from editing
        if(currentUser == null
            || !"teacher".equalsIgnoreCase(currentUser.getRole().trim())) {

            // added by Tom (4/25/2025)
            // for debugging purpose. To check if user is null or what kind of user. Check Logcat and enter "Checkpoint"
            Log.d("Checkpoint", "If this message shows up then that means User is null or not a teacher. User is: " + currentUser);
            Log.d("Checkpoint", "If User shows up then User role is: " + currentUser.getRole());

            finish();
            return;
        }

        setContentView(R.layout.activity_create_assignment);
        editTextAssignmentName = findViewById(R.id.editTextAssignmentName);
        editTextMaxPoints = findViewById(R.id.editTextMaxPoints);
        buttonConfirmAssignment = findViewById(R.id.buttonConfirmAssignment);

        courseId = getIntent().getIntExtra(EXTRA_COURSE_ID, -1);

        // this means: "hey courseId shouldn't be negative so something is clearly wrong. Instead of crashing the app, just close the screen and go back"
        if(courseId < 0) {

            Log.d("checkpoint", "If this message shows up, then it means courseId is < 0");

            finish();
            return;
        }

        buttonConfirmAssignment.setOnClickListener(v -> {
            String name = editTextAssignmentName.getText().toString().trim();
            String points = editTextMaxPoints.getText().toString().trim();
            //validate input
            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(points)) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int maxPoints = Integer.parseInt(points);
//            try {
//                maxPoints = Integer.parseInt(points);
//            } catch (NumberFormatException e) {
//                //TODO: show invalid-number error to user
//                return;
//            }

            //entity
            SavnacAssignment assignment = new SavnacAssignment(name, courseId, maxPoints);

            repo.insertAssignment(assignment);
            finish();
        });
    }
}
