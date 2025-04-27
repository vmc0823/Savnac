package com.silisurfers.savnac;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.silisurfers.savnac.database.SavnacAssignmentWithGradeDao;
import com.silisurfers.savnac.database.SavnacDatabase;
import com.silisurfers.savnac.database.entities.SavnacAssignment;
import com.silisurfers.savnac.database.entities.SavnacAssignmentWithGrade;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Update: Testing for this dao not necessary as the relevant dao was not used
 */

//@RunWith(AndroidJUnit4.class)
//public class SavnacAssignmentWithGradeDaoTest {
//
//    private SavnacDatabase SavnacDatabase;
//    private SavnacAssignmentWithGradeDao SavnacAssignmentWithGradeDao;
//
//    @Before
//    public void createDb() {
//        Context context = ApplicationProvider.getApplicationContext();
//        SavnacDatabase = Room.inMemoryDatabaseBuilder(context, SavnacDatabase.class).allowMainThreadQueries().build();
//        SavnacAssignmentWithGradeDao = SavnacDatabase.savnacAssignmentWithGradeDao(); // adjust to your db setup
//    }
//
//    @After
//    public void closeDb() {
//        SavnacDatabase.close();
//    }
//
//    @Test
//    public void insertAssignmentTest() {
////        // creating new assignment object
////        SavnacAssignment assignment = new SavnacAssignment("Test Assignment", 1, 100);
////
////        // inserting assignment into the database
////        SavnacAssignmentWithGradeDao.insertAssignment(assignment);
//
//        SavnacAssignment assignment = new SavnacAssignment("Civil War Essay", 1, 100);
//        long id = SavnacAssignmentWithGradeDao.insert(assignment);
//
//        SavnacAssignment fromDb = SavnacAssignmentWithGradeDao.getAssignmentByIdSync((int) id);
//        assertNotNull(fromDb);
//        assertEquals("Civil War Essay", fromDb.getAssignmentName());
//
//        assertNotNull(result);
//    }
//}
