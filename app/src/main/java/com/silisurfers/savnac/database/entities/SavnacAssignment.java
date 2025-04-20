package com.silisurfers.savnac.database.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName="assignments",
    foreignKeys = {
            @ForeignKey(
                    entity = SavnacCourse.class,
                    parentColumns = "id", childColumns = "course_id",
                    onDelete = CASCADE)
    }
)
public class SavnacAssignment {
    @PrimaryKey(autoGenerate = true)
    private int id;


    @ColumnInfo(name="course_id")
    private int courseId;

    private String assignmentName;

    private int points;

    @Ignore
    public SavnacAssignment(String assignmentName, int courseId, int points){
        this.assignmentName = assignmentName;
        this.courseId = courseId;
        this.points = points;
    }

    public SavnacAssignment(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String name) {
        this.assignmentName = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
