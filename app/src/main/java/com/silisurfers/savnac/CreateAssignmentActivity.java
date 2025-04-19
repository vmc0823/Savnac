package com.silisurfers.savnac;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

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
    private long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repo = SavnacRepository.getInstance(getApplicationContext());
        currentUser = repo.getCurrentUserSync();

        //blocking students from editing
        if(currentUser == null
            || !"teacher".equals(currentUser.getRole())) {
            finish();
            return;
        }

        setContentView(R.layout.activity_create_assignment);
        editTextAssignmentName = findViewById(R.id.editTextAssignmentName);
        editTextMaxPoints = findViewById(R.id.editTextMaxPoints);
        buttonConfirmAssignment = findViewById(R.id.buttonConfirmAssignment);

        courseId = getIntent().getLongExtra(EXTRA_COURSE_ID, -1);
        if(courseId < 0) {
            finish();
            return;
        }

        buttonConfirmAssignment.setOnClickListener(v -> {
            String name = editTextAssignmentName.getText().toString().trim();
            String points = editTextMaxPoints.getText().toString().trim();
            //validate input
            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(points)) {
                //TODO: user feedback maybe a toast?
                return;
            }

            int maxPoints;
            try {
                maxPoints = Integer.parseInt(points);
            } catch (NumberFormatException e) {
                //TODO: show invalid-number error to user
                return;
            }

            //entity
            SavnacAssignment asgmnt = new SavnacAssignment();
            asgmnt.setCourseId(courseId);
            asgmnt.setName(name);
            asgmnt.setPoints(maxPoints);

            repo.insertAssignment(asgmnt);
            finish();
        });
    }
}
