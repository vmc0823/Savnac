package com.silisurfers.savnac.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RewriteQueriesToDropUnusedColumns;
import androidx.room.Update;

import com.silisurfers.savnac.database.entities.SavnacCourse;
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

    @Update
    void update(SavnacEnrollment enrollment);

    @Delete
    void delete(SavnacEnrollment enrollment);
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

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM enrollments WHERE id = :id")
    LiveData<SavnacEnrollment> getById(int id);

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM enrollments WHERE course_id = :courseId")
    LiveData<List<SavnacEnrollment>> getEnrollmentsByCourse(int courseId);

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM enrollments WHERE student_id = :studentId")
    LiveData<List<SavnacEnrollment>> getEnrollmentsByStudent(int studentId);

    @Query("SELECT * FROM enrollments WHERE student_id = :studentId")
    LiveData<List<SavnacEnrollment>> getEnrollmentsForStudent(int studentId);

    @Query(
            "SELECT c.* FROM courses AS c " +
                    "JOIN enrollments AS e ON c.id = e.course_id " +
                    "WHERE e.student_id = :studentId"
    )
    LiveData<List<SavnacCourse>> getCoursesForStudent(int studentId);

    @Query("SELECT * FROM enrollments")
    List<SavnacEnrollment> getAllEnrollmentOptionsSync(); //for testing

    @Query("SELECT * FROM enrollments WHERE id = :id") //for testing
    SavnacEnrollment getByIdSync(int id);

}
