package com.silisurfers.savnac;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class join_or_leave_courses_teacher_perspective_activity extends AppCompatActivity {

    // loading the activity screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This line is what connects my JAVA code to my xml file/layout. It basically means:
        // "use this activity_main.xml layout file to build this screen's UI"
        setContentView(R.layout.join_or_leave_courses_teacher_perspective_page);




    }

}
