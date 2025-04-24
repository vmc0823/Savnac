package com.silisurfers.savnac.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.silisurfers.savnac.database.entities.SavnacAssignment;

import java.util.List;

@Dao
public interface SavnacAssignmentDao {
    @Insert
    long insert(SavnacAssignment assignment);

    @Update
    void update(SavnacAssignment assignment);

    @Delete
    void delete(SavnacAssignment assignment);

    @Query("SELECT * FROM assignments")
    LiveData<List<SavnacAssignment>> getAllAssignments();

    @Query("SELECT * FROM assignments WHERE id = :assignmentId")
    LiveData<SavnacAssignment> getAssignmentById(int assignmentId);

    @Query("SELECT * FROM assignments WHERE id = :assignmentId")
    SavnacAssignment getAssignmentByIdSync(int assignmentId);

    @Query("DELETE FROM assignments WHERE id = :assignmentId")
    void deleteById(int assignmentId);

    @Query("DELETE FROM assignments WHERE course_id = :courseId")
    void deleteByCourseId(int courseId);
}
