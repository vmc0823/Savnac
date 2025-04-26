package com.silisurfers.savnac;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.silisurfers.savnac.database.SavnacCourseDao;
import com.silisurfers.savnac.database.SavnacDatabase;
import com.silisurfers.savnac.database.SavnacEnrollmentDao;
import com.silisurfers.savnac.database.SavnacUserDao;
import com.silisurfers.savnac.database.entities.SavnacCourse;
import com.silisurfers.savnac.database.entities.SavnacEnrollment;
import com.silisurfers.savnac.database.entities.SavnacUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class SavnacEnrollmentDaoTest {
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private SavnacDatabase db;
    private SavnacEnrollmentDao enrollmentDao;
    private SavnacUserDao userDao;
    private SavnacCourseDao courseDao;
    private int studentId;
    private int courseId;
    private int teacherId;

    @Before
    public void setUp() {
        db = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                SavnacDatabase.class
        ).allowMainThreadQueries().build();

        enrollmentDao = db.savnacEnrollmentDao();
        userDao       = db.savnacUserDao();
        courseDao     = db.savnacCourseDao();

        // insert a student
        studentId = (int) userDao.insert(
                new SavnacUser("student1","pw","student")
        );

        // insert a teacher + course
        teacherId = (int) userDao.insert(
                new SavnacUser("teacher1","pw","teacher")
        );
        courseId = (int) courseDao.insert(
                new SavnacCourse("Test Course", teacherId)
        );
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void testInsertEnrollment() {
        enrollmentDao.insert(new SavnacEnrollment(studentId, courseId));
        List<SavnacEnrollment> all = enrollmentDao.getAllEnrollmentOptionsSync();
        assertEquals(1, all.size());
        assertEquals(studentId, all.get(0).getStudentId());
        assertEquals(courseId,  all.get(0).getCourseId());
    }

    @Test public void testUpdateEnrollment() {
        SavnacEnrollment e = new SavnacEnrollment(studentId, courseId);
        enrollmentDao.insert(e);
        e = enrollmentDao.getAllEnrollmentOptionsSync().get(0);

        // change to a second dummy course (insert that first)
        int otherCourse = (int) courseDao.insert(
                new SavnacCourse("Other", teacherId)
        );
        e.setCourseId(otherCourse);
        enrollmentDao.update(e);

        SavnacEnrollment updated = enrollmentDao.getByIdSync(e.getId());
        assertEquals(otherCourse, updated.getCourseId());
    }

    @Test public void testDeleteEnrollment() {
        SavnacEnrollment e = new SavnacEnrollment(studentId, courseId);
        enrollmentDao.insert(e);
        List<SavnacEnrollment> before = enrollmentDao.getAllEnrollmentOptionsSync();
        assertEquals(1, before.size());

        enrollmentDao.delete(before.get(0));
        List<SavnacEnrollment> after = enrollmentDao.getAllEnrollmentOptionsSync();
        assertTrue(after.isEmpty());
    }
}
