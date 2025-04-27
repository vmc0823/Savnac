package com.silisurfers.savnac;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.silisurfers.savnac.database.SavnacAssignmentDao;
import com.silisurfers.savnac.database.SavnacCourseDao;
import com.silisurfers.savnac.database.SavnacDatabase;
import com.silisurfers.savnac.database.SavnacUserDao;
import com.silisurfers.savnac.database.entities.SavnacAssignment;
import com.silisurfers.savnac.database.entities.SavnacCourse;
import com.silisurfers.savnac.database.entities.SavnacUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SavnacAssignmentDaoTest {

    private SavnacDatabase SavnacDatabase;
    private SavnacCourseDao courseDao;
    private SavnacAssignmentDao SavnacAssignmentDao;
    private SavnacUserDao userDao;

    private int dummyTeacherId;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        SavnacDatabase = Room.inMemoryDatabaseBuilder(context, SavnacDatabase.class).allowMainThreadQueries().build();
        SavnacAssignmentDao = SavnacDatabase.savnacAssignmentDao();

        courseDao = SavnacDatabase.savnacCourseDao();
        userDao   = SavnacDatabase.savnacUserDao();

        // insert a dummy teacher so foreign key constraint passes
        SavnacUser teacher = new SavnacUser("teacher1", "pass", "teacher");
        teacher.setUsername("teacher1");
        teacher.setPassword("pass");
        teacher.setRole("teacher");
        long tid = userDao.insert(teacher);
        dummyTeacherId = (int) tid;
    }

    @After
    public void closeDb() {
        SavnacDatabase.close();
    }


    @Test
    public void insertAssignment() {
        // Create a test course
        SavnacCourse course = new SavnacCourse("American History", dummyTeacherId);

        // insert that test course into the database
        long courseId = courseDao.insert(course);

        // Creating an assignment under the courseId
        SavnacAssignment assignment = new SavnacAssignment("Civil War Essay", (int) courseId, 100);

        // inserting assignment into database
        long id = SavnacAssignmentDao.insert(assignment);

        // Retrieve the assignment from the database
        SavnacAssignment fromDb = SavnacAssignmentDao.getAssignmentByIdSync((int) id);

        assertNotNull(fromDb);
        assertEquals("Civil War Essay", fromDb.getAssignmentName());
    }

    @Test
    public void updateAssignment() {
        // Create a test course
        SavnacCourse course = new SavnacCourse("Class Name", dummyTeacherId);

        // insert that test course into the database
        long courseId = courseDao.insert(course);

        SavnacAssignment assignment = new SavnacAssignment("Outdated Name", (int) courseId, 100);

        long id = SavnacAssignmentDao.insert(assignment);

        assignment.setId((int) id);
        assignment.setAssignmentName("Updated Name");   // Shows we're updating the name
        assignment.setPoints(95);                       // Shows points update
        SavnacAssignmentDao.update(assignment);

        SavnacAssignment fromDb = SavnacAssignmentDao.getAssignmentByIdSync((int) id);
        assertEquals("Updated Name", fromDb.getAssignmentName());
        assertEquals(95, fromDb.getPoints());
    }

    @Test
    public void deleteAssignment() {

        // Create a test course
        SavnacCourse course = new SavnacCourse("Class Name", dummyTeacherId);

        // insert that test course into the database
        long courseId = courseDao.insert(course);

        // creating a test assignment
        SavnacAssignment assignment = new SavnacAssignment("To Delete", (int) courseId, 50);

        // inserting test assignment
        long id = SavnacAssignmentDao.insert(assignment);

        assignment.setId((int) id);
        SavnacAssignmentDao.delete(assignment);

        SavnacAssignment fromDb = SavnacAssignmentDao.getAssignmentByIdSync((int) id);
        assertNull(fromDb); // asserting that the assignment has indeed been deleted
    }
}



