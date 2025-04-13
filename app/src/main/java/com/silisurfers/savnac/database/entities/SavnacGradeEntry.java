package com.silisurfers.savnac.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "grade_entries")
public class SavnacGradeEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "course_name")
    private String courseName;

    @ColumnInfo(name = "assignment_name")
    private String assignmentName;

    @ColumnInfo(name = "grade")
    private String grade;

    @ColumnInfo(name = "entry_date")
    private Date entryDate;

    public SavnacGradeEntry(String courseName, String assignmentName, String grade, Date entryDate) {
        this.courseName = courseName;
        this.assignmentName = assignmentName;
        this.grade = grade;
        this.entryDate = entryDate;
    }

    //auto-generate setters-getters
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

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
}
