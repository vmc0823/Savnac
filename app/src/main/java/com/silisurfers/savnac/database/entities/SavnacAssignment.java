package com.silisurfers.savnac.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="assignments")
public class SavnacAssignment {
    @PrimaryKey(autoGenerate = true)
    private int id;


    /// TODO: establish foreign key relationship to courses table
    private long courseId;

    private String name;

    private int points;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
