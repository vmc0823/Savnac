package com.silisurfers.savnac.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RewriteQueriesToDropUnusedColumns;
import androidx.room.Transaction;
import androidx.room.Update;

import com.silisurfers.savnac.database.entities.SavnacAssignment;
import com.silisurfers.savnac.database.entities.SavnacAssignmentWithGrade;
import com.silisurfers.savnac.database.entities.SavnacGradeEntry;

import java.util.List;

@Dao
public interface SavnacAssignmentWithGradeDao {
    @RewriteQueriesToDropUnusedColumns
    @Transaction
    @Query("SELECT * FROM assignments " +
            "INNER JOIN grade_entries ON assignments.id = grade_entries.assignment_id")
    LiveData<List<SavnacAssignmentWithGrade>> getAllGradesWithAssignments();
}
