/*
 * Author: Brandon Evans
 * File: SavnacUserDaoTest.java
 * Date: 4/26/2025
 * Description: This file tests the functionality of SavnacUserDao. These also act as my unit tests for the project.
 */

package com.silisurfers.savnac;

import static org.junit.Assert.*;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.silisurfers.savnac.database.SavnacDatabase;
import com.silisurfers.savnac.database.SavnacUserDao;
import com.silisurfers.savnac.database.entities.SavnacUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SavnacUserDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private SavnacDatabase db;
    private SavnacUserDao userDao;

    @Before
    public void createDb() {
        db = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(),
                        SavnacDatabase.class
                )
                .allowMainThreadQueries()
                .build();

        userDao = db.savnacUserDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void testInsertUser() {
        // Test inserting a student account
        SavnacUser student = new SavnacUser("student", "studentpassword", "Student");
        long studentid = userDao.insert(student);

        SavnacUser sFromDb = userDao.getByIdSync((int) studentid);
        assertNotNull(sFromDb);
        assertEquals("student", sFromDb.getUsername());
        assertEquals("studentpassword", sFromDb.getPassword());
        assertEquals("Student", sFromDb.getRole());
        assertEquals((int) studentid, sFromDb.getId());

        // Test inserting a teacher account
        SavnacUser teacher = new SavnacUser("teacher", "teacherpassword", "Teacher");
        long teacherid = userDao.insert(teacher);

        SavnacUser tFromDb = userDao.getByIdSync((int) teacherid);
        assertNotNull(tFromDb);
        assertEquals("teacher", tFromDb.getUsername());
        assertEquals("teacherpassword", tFromDb.getPassword());
        assertEquals("Teacher", tFromDb.getRole());
        assertEquals((int) teacherid, tFromDb.getId());
    }

    @Test
    public void testUpdateUser() {
        // Insert a student account
        SavnacUser student = new SavnacUser("Old Name", "studentpassword", "Student");
        long studentid = userDao.insert(student);

        // Get the newly inserted account to update then update it with the new name
        SavnacUser toUpdate = userDao.getByIdSync((int) studentid);
        toUpdate.setUsername("New Name");
        userDao.update(toUpdate);

        // Get the newly updated account to check that it has the new name
        SavnacUser updated = userDao.getByIdSync((int) studentid);
        assertEquals("New Name", updated.getUsername());
    }

    @Test
    public void testDeleteUser() {
        // Insert a student account
        SavnacUser student = new SavnacUser("To Be Deleted", "studentpassword", "Student");
        long studentid = userDao.insert(student);

        // Verify that the account was inserted then delete the account
        SavnacUser inserted = userDao.getByIdSync((int) studentid);
        assertNotNull(inserted);
        userDao.delete(inserted);

        // Verify that the account was deleted
        SavnacUser afterDelete = userDao.getByIdSync((int) studentid);
        assertNull(afterDelete);
    }
}

