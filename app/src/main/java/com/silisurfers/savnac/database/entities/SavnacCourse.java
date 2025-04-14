package com.silisurfers.savnac.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class SavnacCourse {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String className;

    private int teacherId;

    public SavnacCourse(String className, int teacherId) {
        this.className = className;
        this.teacherId = teacherId;
    }

    //TODO: autogenerate getters and setters
}
