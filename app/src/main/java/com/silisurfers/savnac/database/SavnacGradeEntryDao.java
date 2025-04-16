package com.silisurfers.savnac.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.silisurfers.savnac.database.entities.SavnacGradeEntry;

import java.util.List;

@Dao
public interface SavnacGradeEntryDao {

    @Insert
    void insert(SavnacGradeEntry gradeEntry);

    @Query("SELECT * FROM grade_entries ORDER BY entry_date DESC")
    LiveData<List<SavnacGradeEntry>> getAllGradeEntries();

    @Query("SELECT * FROM grade_entries WHERE student_id = :stid ORDER BY entry_date DESC")
    LiveData<List<SavnacGradeEntry>> getForStudent(int stid);

    @Query("SELECT * FROM grade_entries WHERE assignment_id = :asid")
    LiveData<List<SavnacGradeEntry>> getForAssignment(int asid);
}
