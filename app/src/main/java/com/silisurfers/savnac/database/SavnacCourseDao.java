package com.silisurfers.savnac.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RewriteQueriesToDropUnusedColumns;
import androidx.room.Update;

import com.silisurfers.savnac.database.entities.SavnacCourse;

import java.util.List;

@Dao
public interface SavnacCourseDao {

    @Insert
    long insert(SavnacCourse course);

    @Update
    void update(SavnacCourse course);

    @Delete
    void delete(SavnacCourse course);

    @Query("SELECT * FROM courses") //read
    LiveData<List<SavnacCourse>> getAllCourses();

    @Query("SELECT * FROM courses WHERE teacher_id = :teacherId") //read
    LiveData<List<SavnacCourse>> getCoursesByTeacher(int teacherId);

    @Query("DELETE FROM courses where id = :courseId") //delete
    void deleteById(int courseId);

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM courses WHERE id = :courseId") //read
    LiveData<SavnacCourse> getCourseById(int courseId);

    @Query("SELECT * FROM courses WHERE id = :courseId") //read
    SavnacCourse getCourseByIdSync(int courseId);
}
