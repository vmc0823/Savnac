package com.silisurfers.savnac.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class SavnacCourse {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "class_name")
    private String className;

    @ColumnInfo(name = "teacher_id")
    private int teacherId;

    public SavnacCourse(String className, int teacherId) {
        this.className = className;
        this.teacherId = teacherId;
    }

    //autogenerate getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
