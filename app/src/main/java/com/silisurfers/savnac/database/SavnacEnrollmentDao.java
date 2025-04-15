package com.silisurfers.savnac.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.silisurfers.savnac.database.entities.SavnacEnrollment;
import java.util.List;

/***
 * Author: Wootark Kim
 * Date creation: 14 April 2024
 * Purpose of file: a JAVA class acting as an Entity file for Enrollment
 */

@Dao
public interface SavnacEnrollmentDao {

    @Insert                                     // This means "once a SavnacEnrollment object has been created (which represents a row in the
    void insert(SavnacEnrollment enrollment);   // table), take that object and add it to the enrollmentOptions table. (see doc note below
                                                // "Constructor" on SavnacEnrollment.java for greater detail).

    @Query("SELECT * FROM enrollmentOptions")                   // This line specifically means "select all rows from the enrollmentOptions table."
    LiveData<List<SavnacEnrollment>> getAllEnrollmentOptions(); // This line does 2 things:
                                                                // 1. Let's users observe the list of all rows/options (in this case - all course options).
                                                                // 2. automatically updates the UI when the data changes.
}
