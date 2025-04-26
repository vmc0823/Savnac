/*
 * Author: Brandon Evans
 * File: IntentFactoryTest.java
 * Date: 4/26/2025
 * Description: This file tests the functionality of Savnac's Intent Factory methods. (20pts) These also act as my unit tests for the project.
 */

package com.silisurfers.savnac;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.*;

import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

@RunWith(AndroidJUnit4.class)
public class IntentFactoryTest {
    @Test
    public void testLoginIntentFactory() {
        // Set up a LoginActivity intent factory and put an extra value
        Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
        intent.putExtra("extraTest", "extraValue");

        // Check that the intent is not null and that the extra value was properly set
        assertNotNull(intent);
        assertTrue("hasExtra==false", intent.hasExtra("extraTest"));
        assertEquals(Objects.requireNonNull(intent.getExtras()).getString("extraTest"), "extraValue");
    }

    @Test
    public void testSignupIntentFactory() {
        // Set up a SignupActivity intent factory and put an extra value
        Intent intent = SignupActivity.signupIntentFactory(getApplicationContext());
        intent.putExtra("extraTest", "extraValue");

        // Check that the intent is not null and that the extra value was properly set
        assertNotNull(intent);
        assertTrue("hasExtra==false", intent.hasExtra("extraTest"));
        assertEquals(Objects.requireNonNull(intent.getExtras()).getString("extraTest"), "extraValue");
    }

    @Test
    public void testCoursesIntentFactory() {
        // Set up a CoursesActivity intent factory and put an extra value
        Intent intent = CoursesActivity.coursesIntentFactory(getApplicationContext());
        intent.putExtra("extraTest", "extraValue");

        // Check that the intent is not null and that the extra value was properly set
        assertNotNull(intent);
        assertTrue("hasExtra==false", intent.hasExtra("extraTest"));
        assertEquals(Objects.requireNonNull(intent.getExtras()).getString("extraTest"), "extraValue");
    }

    @Test
    public void testCreateCourseIntentFactory() {
        // Set up a CreateCourseActivity intent factory and put an extra value
        Intent intent = CreateCourseActivity.createCourseIntentFactory(getApplicationContext());
        intent.putExtra("extraTest", "extraValue");

        // Check that the intent is not null and that the extra value was properly set
        assertNotNull(intent);
        assertTrue("hasExtra==false", intent.hasExtra("extraTest"));
        assertEquals(Objects.requireNonNull(intent.getExtras()).getString("extraTest"), "extraValue");
    }

    @Test
    public void testCreateAssignmentIntentFactory() {
        // Set up a CreateAssignmentActivity intent factory and put an extra value
        Intent intent = CreateAssignmentActivity.createAssignmentIntentFactory(getApplicationContext());
        intent.putExtra("extraTest", "extraValue");

        // Check that the intent is not null and that the extra value was properly set
        assertNotNull(intent);
        assertTrue("hasExtra==false", intent.hasExtra("extraTest"));
        assertEquals(Objects.requireNonNull(intent.getExtras()).getString("extraTest"), "extraValue");
    }

    @Test
    public void testGradesIntentFactory() {
        // Set up a GradesActivity intent factory and put an extra value
        Intent intent = GradesActivity.gradesIntentFactory(getApplicationContext());
        intent.putExtra("extraTest", "extraValue");

        // Check that the intent is not null and that the extra value was properly set
        assertNotNull(intent);
        assertTrue("hasExtra==false", intent.hasExtra("extraTest"));
        assertEquals(Objects.requireNonNull(intent.getExtras()).getString("extraTest"), "extraValue");
    }

    @Test
    public void testJoinOrLeaveCoursesTeacherPerspectiveIntentFactory() {
        // Set up a joinOrLeaveCoursesTeacherPerspectiveActivity intent factory and put an extra value
        Intent intent = joinOrLeaveCoursesTeacherPerspectiveActivity.joinOrLeaveCoursesTeacherIntentFactory(getApplicationContext());
        intent.putExtra("extraTest", "extraValue");

        // Check that the intent is not null and that the extra value was properly set
        assertNotNull(intent);
        assertTrue("hasExtra==false", intent.hasExtra("extraTest"));
        assertEquals(Objects.requireNonNull(intent.getExtras()).getString("extraTest"), "extraValue");
    }

    @Test
    public void testShowListOfActiveAssignmentsIntentFactory() {
        // Set up a ShowListOfActiveAssignments intent factory and put an extra value
        Intent intent = ShowListOfActiveAssignmentsActivity.showListOfActiveAssignmentsIntentFactory(getApplicationContext());
        intent.putExtra("extraTest", "extraValue");

        // Check that the intent is not null and that the extra value was properly set
        assertNotNull(intent);
        assertTrue("hasExtra==false", intent.hasExtra("extraTest"));
        assertEquals(Objects.requireNonNull(intent.getExtras()).getString("extraTest"), "extraValue");
    }
}