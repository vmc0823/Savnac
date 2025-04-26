package com.silisurfers.savnac;

import static org.junit.Assert.*;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.silisurfers.savnac.database.SavnacAssignmentDao;
import com.silisurfers.savnac.database.SavnacCourseDao;
import com.silisurfers.savnac.database.SavnacDatabase;
import com.silisurfers.savnac.database.SavnacEnrollmentDao;
import com.silisurfers.savnac.database.SavnacGradeEntryDao;
import com.silisurfers.savnac.database.SavnacUserDao;
import com.silisurfers.savnac.database.entities.SavnacAssignment;
import com.silisurfers.savnac.database.entities.SavnacCourse;
import com.silisurfers.savnac.database.entities.SavnacEnrollment;
import com.silisurfers.savnac.database.entities.SavnacGradeEntry;
import com.silisurfers.savnac.database.entities.SavnacUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class SavnacGradeDaoTest {
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private SavnacDatabase db;
    private SavnacGradeEntryDao gradeEntryDao;
    private SavnacUserDao userDao;
    private SavnacCourseDao courseDao;

    private SavnacAssignmentDao assignmentDao;

    private int studentId;
    private int courseId;
    private int teacherId;

    private int assignmentId;

    @Before
    public void setUp() {
        db = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                SavnacDatabase.class
        ).allowMainThreadQueries().build();

        userDao       = db.savnacUserDao();
        courseDao     = db.savnacCourseDao();
        assignmentDao = db.savnacAssignmentDao();
        gradeEntryDao = db.savnacGradeEntryDao();

        // insert a student
        studentId = (int) userDao.insert(
                new SavnacUser("student1","pw","student")
        );

        // insert a teacher + course + assignment
        teacherId = (int) userDao.insert(
                new SavnacUser("teacher1","pw","teacher")
        );
        courseId = (int) courseDao.insert(
                new SavnacCourse("Test Course", teacherId)
        );

        assignmentId = (int) assignmentDao.insert(new SavnacAssignment("Test assignment", courseId, 200));
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void testInsertGrade() {
        int id = (int) gradeEntryDao.insert(new SavnacGradeEntry(studentId, assignmentId, 200, LocalDateTime.now()));
        SavnacGradeEntry dbEntry = gradeEntryDao.getGradeEntryByIdSync(id);
        assertEquals(studentId, dbEntry.getStudentId());
        assertEquals(assignmentId,  dbEntry.getAssignmentId());
        assertEquals(200, dbEntry.getGrade());
    }

    @Test public void testUpdateEnrollment() {
        SavnacGradeEntry e = new SavnacGradeEntry(studentId, assignmentId, 200, LocalDateTime.now());

        int id = (int) gradeEntryDao.insert(e);
        SavnacGradeEntry dbEntry = gradeEntryDao.getGradeEntryByIdSync(id);

        dbEntry.setGrade(200);

        gradeEntryDao.update(dbEntry);
        SavnacGradeEntry updatedEntry = gradeEntryDao.getGradeEntryByIdSync(id);
        assertEquals( 200, updatedEntry.getGrade());
    }

    @Test public void testDeleteEnrollment() {
        SavnacGradeEntry e = new SavnacGradeEntry(studentId, assignmentId, 200, LocalDateTime.now());

        int id = (int) gradeEntryDao.insert(e);
        SavnacGradeEntry dbEntry = gradeEntryDao.getGradeEntryByIdSync(id);

        assertNotNull(dbEntry);

        gradeEntryDao.delete(dbEntry);
        SavnacGradeEntry updatedEntry = gradeEntryDao.getGradeEntryByIdSync(id);
        assertNull( updatedEntry);
    }
}
