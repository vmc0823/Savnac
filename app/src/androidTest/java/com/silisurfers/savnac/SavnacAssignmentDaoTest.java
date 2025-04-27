package com.silisurfers.savnac;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.silisurfers.savnac.database.SavnacAssignmentDao;
import com.silisurfers.savnac.database.SavnacDatabase;
import com.silisurfers.savnac.database.entities.SavnacAssignment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SavnacAssignmentDaoTest {

    private SavnacDatabase SavnacDatabase;
    private SavnacAssignmentDao SavnacAssignmentDao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        SavnacDatabase = Room.inMemoryDatabaseBuilder(context, SavnacDatabase.class).allowMainThreadQueries().build();
        SavnacAssignmentDao = SavnacDatabase.savnacAssignmentDao();
    }

    @After
    public void closeDb() {
        SavnacDatabase.close();
    }

    @Test
    public void insertAssignment() {
        SavnacAssignment assignment = new SavnacAssignment("Civil War Essay", 1, 100);
        long id = SavnacAssignmentDao.insert(assignment);

        SavnacAssignment fromDb = SavnacAssignmentDao.getAssignmentByIdSync((int) id);
        assertNotNull(fromDb);
        assertEquals("Civil War Essay", fromDb.getAssignmentName());
    }

    @Test
    public void updateAssignment() {
        SavnacAssignment assignment = new SavnacAssignment("Outdated Name", 1, 100);
        long id = SavnacAssignmentDao.insert(assignment);

        assignment.setId((int) id);
        assignment.setAssignmentName("Updated Name");   // Shows we're updating the name
        assignment.setCourseId(2);                      // Shows we're updating course ID as well (not necessary)
        assignment.setPoints(95);                       // Shows points update
        SavnacAssignmentDao.update(assignment);

        SavnacAssignment fromDb = SavnacAssignmentDao.getAssignmentByIdSync((int) id);
        assertEquals("Updated Name", fromDb.getAssignmentName());
        assertEquals(2, fromDb.getCourseId());
        assertEquals(95, fromDb.getPoints());
    }

    @Test
    public void deleteAssignment() {
        SavnacAssignment assignment = new SavnacAssignment("To Delete", 1, 50);
        long id = SavnacAssignmentDao.insert(assignment);

        assignment.setId((int) id);
        SavnacAssignmentDao.delete(assignment);

        SavnacAssignment fromDb = SavnacAssignmentDao.getAssignmentByIdSync((int) id);
        assertNull(fromDb); // asserting that the assignment has indeed been deleted
    }
}



