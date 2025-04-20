package com.silisurfers.savnac.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.silisurfers.savnac.database.entities.SavnacCourse;

import java.util.List;

@Dao
public interface SavnacCourseDao {

    @Insert
    void insert(SavnacCourse course);

    @Query("SELECT * FROM courses")
    LiveData<List<SavnacCourse>> getAllCourses();

    @Query("SELECT * FROM courses WHERE teacher_id = :teacherId") //added by vw
    LiveData<List<SavnacCourse>> getCoursesByTeacher(int teacherId);

    @Query("DELETE FROM courses where id = :courseId") //added by vw
    void deleteById(int courseId);
}
