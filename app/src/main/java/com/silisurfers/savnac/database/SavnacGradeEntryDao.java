package com.silisurfers.savnac.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RewriteQueriesToDropUnusedColumns;
import androidx.room.Transaction;
import androidx.room.Update;

import com.silisurfers.savnac.database.entities.SavnacGradeEntry;
import com.silisurfers.savnac.database.entities.SavnacAssignmentWithGrade;

import java.util.List;

//@author: vw

@Dao
public interface SavnacGradeEntryDao {

    @Insert
    long insert(SavnacGradeEntry gradeEntry);

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

    @Query("SELECT * FROM grade_entries WHERE id = :id")
    SavnacGradeEntry getGradeEntryByIdSync(int id);

    @RewriteQueriesToDropUnusedColumns
    @Transaction
    @Query("SELECT  grade_entries.*," +
            "assignments.id as course_assignment_id, "+
            "assignments.course_id as course_assignment_course_id, "+
            "assignments.assignment_name as course_assignment_assignment_name, " +
            "assignments.points as course_assignment_points "+
            "FROM grade_entries " +
            "INNER JOIN assignments ON grade_entries.assignment_id = assignments.id " +
            "WHERE grade_entries.student_id = :userId AND assignments.course_id = :courseId"
//            michael: this query was throwing errors indicating necessary assignment fields were not included
//    @Query("SELECT assignments.assignment_name, assignments.points, grade_entries.grade " +
//            "FROM assignments " +
//            "INNER JOIN grade_entries ON assignments.id = grade_entries.assignment_id " +
//            "WHERE grade_entries.student_id = :userId AND assignments.course_id = :courseId"
    )
    LiveData<List<SavnacAssignmentWithGrade>> getUserGradesByCourseId(int userId, int courseId);
}
