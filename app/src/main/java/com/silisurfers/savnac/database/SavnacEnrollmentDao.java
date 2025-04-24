package com.silisurfers.savnac.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.silisurfers.savnac.database.entities.SavnacEnrollment;
import java.util.List;

/***
 * Author: Wootark (Tom) Kim
 * Date creation: 14 April 2024
 * Purpose of file: a JAVA class acting as an Entity file for Enrollment.
 *                  (which means this DAO tells Room how to insert a row into the enrollmentOptions table)
 */

@Dao
public interface SavnacEnrollmentDao {

    // [Explanation of the @Insert annotation]==============================================================================================================
    // @Insert is an instruction for Room.
    // Reminder: each instance of SavnacEnrollment object is technically one row.
    // The @Insert annotation tells Room how to insert that row into the enrollmentOptions table
    @Insert                                     // This means "once a SavnacEnrollment object has been created (which represents a row in the
    void insert(SavnacEnrollment enrollment);   // table), take that object and add it to the enrollmentOptions table. (see doc note below
                                                // "Constructor" on SavnacEnrollment.java for greater detail).

    // [Explanation of the @Query annotation]===============================================================================================================
    // @Query is an instruction for Room.
    // This instruction tells Room to select all rows from the enrollmentOptions table.
    @Query("SELECT * FROM enrollments")// This line specifically means "select all rows from the enrollmentOptions table."

    // [Explanation of the return type]=====================================================================================================================
    // The return type being LiveData<List<SavnacEnrollment>>, this tells Room to
    // 1. continuously observe the enrollmentOptions table for changes.
    // 2. Automatically update the LiveData list whenever the table data changes.
    LiveData<List<SavnacEnrollment>> getAllEnrollmentOptions(); // Thus, this line does 2 things:
                                                                // 1. Let's users observe the list of all rows/options (in this case - all course options).
                                                                // 2. automatically updates the UI when the data changes.

    @Query("SELECT * FROM enrollments WHERE id = :id") //added by vw, this query matches repo class (see repo class)
    LiveData<SavnacEnrollment> getById(int id);

    @Query("SELECT * FROM enrollments WHERE course_id = :courseId")
    LiveData<List<SavnacEnrollment>> getEnrollmentsByCourse(int courseId);

    @Query("SELECT * FROM enrollments WHERE student_id = :studentId")
    LiveData<List<SavnacEnrollment>> getEnrollmentsByStudent(int studentId);
}
