package com.silisurfers.savnac.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.silisurfers.savnac.database.entities.SavnacAssignment;

import java.util.List;

@Dao
public interface SavnacAssignmentDao {
    @Insert
    void insert(SavnacAssignment assignment);


    @Query("SELECT * FROM assignments")
    LiveData<List<SavnacAssignment>> getAllAssignments();

    /// TODO: update this to get all assignments from a specific course

    /// TODO: delete assignment

    /// TODO: update assignment
}
