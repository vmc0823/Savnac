package com.silisurfers.savnac.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.silisurfers.savnac.database.entities.SavnacGradeEntry;
import com.silisurfers.savnac.database.entities.SavnacGradeEntryWithCourse;

import java.util.List;

//@author: vw

@Dao
public interface SavnacGradeEntryDao {

    @Insert
    void insert(SavnacGradeEntry gradeEntry);

    @Update
    void update(SavnacGradeEntry gradeEntry); //rows updated

    @Delete
    void delete(SavnacGradeEntry gradeEntry); //rows deleted

    @Query("SELECT * FROM grade_entries ORDER BY entry_date DESC")
    LiveData<List<SavnacGradeEntry>> getAllGradeEntries();

    @Query("SELECT * FROM grade_entries WHERE student_id = :stid ORDER BY entry_date DESC")
    LiveData<List<SavnacGradeEntry>> getForStudent(int stid);

    @Query("SELECT * FROM grade_entries WHERE assignment_id = :asid")
    LiveData<List<SavnacGradeEntry>> getForAssignment(int asid);

    @Query(
        "SELECT "+
            "g.*,\n"+
            "a.*\n" +
        "FROM grade_entries g\n"+
        "INNER JOIN assignments a ON g.assignment_id = a.id\n"+
        "WHERE g.student_id = :userId AND a.course_id = :courseId\n"
    )
    LiveData<List<SavnacGradeEntryWithCourse>> getUserGradesByCourseId(int userId, int courseId);
}
