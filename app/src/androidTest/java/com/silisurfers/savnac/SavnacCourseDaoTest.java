package com.silisurfers.savnac;

import static org.junit.Assert.*;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.silisurfers.savnac.database.SavnacCourseDao;
import com.silisurfers.savnac.database.SavnacDatabase;
import com.silisurfers.savnac.database.SavnacUserDao;
import com.silisurfers.savnac.database.entities.SavnacCourse;
import com.silisurfers.savnac.database.entities.SavnacUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SavnacCourseDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private SavnacDatabase db;
    private SavnacCourseDao courseDao;
    private SavnacUserDao userDao;

    private int dummyTeacherId;

    @Before
    public void createDb() {
        db = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(),
                        SavnacDatabase.class
                )
                .allowMainThreadQueries()
                .build();

        courseDao = db.savnacCourseDao();
        userDao   = db.savnacUserDao();

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
        db.close();
    }

    @Test
    public void testInsertCourse() {
        SavnacCourse course = new SavnacCourse("CST 101", dummyTeacherId);
        long id = courseDao.insert(course);

        SavnacCourse fromDb = courseDao.getCourseByIdSync((int) id);
        assertNotNull(fromDb);
        assertEquals("CST 101", fromDb.getCourseName());
        assertEquals(Integer.valueOf(dummyTeacherId), fromDb.getTeacherId());
    }

    @Test
    public void testUpdateCourse() {
        SavnacCourse course = new SavnacCourse("Old Name", dummyTeacherId);
        long id = courseDao.insert(course);

        SavnacCourse toUpdate = courseDao.getCourseByIdSync((int) id);
        toUpdate.setCourseName("New Name");
        courseDao.update(toUpdate);

        SavnacCourse updated = courseDao.getCourseByIdSync((int) id);
        assertEquals("New Name", updated.getCourseName());
    }

    @Test
    public void testDeleteCourse() {
        SavnacCourse course = new SavnacCourse("To Be Deleted", dummyTeacherId);
        long id = courseDao.insert(course);

        SavnacCourse inserted = courseDao.getCourseByIdSync((int) id);
        assertNotNull(inserted);

        courseDao.delete(inserted);
        SavnacCourse afterDelete = courseDao.getCourseByIdSync((int) id);
        assertNull(afterDelete);
    }
}
