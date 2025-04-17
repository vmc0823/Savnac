package com.silisurfers.savnac.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


//@author: vw
@Entity(tableName = "courses")
public class SavnacCourse {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "course_name")
    private String courseName;

    @ColumnInfo(name = "teacher_id")
    private int teacherId;

    public SavnacCourse(String courseName, int teacherId) {
        this.courseName = courseName;
        this.teacherId = teacherId;
    }

    //autogenerate getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;

    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
