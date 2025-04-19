package com.silisurfers.savnac.database.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

//@author: vw

//linking student+assignment
@Entity(
        tableName = "grade_entries",
        foreignKeys={
            @ForeignKey(
                entity= SavnacUser.class,
                parentColumns = "id",
                childColumns = "student_id",
                onDelete = CASCADE
            ),
            @ForeignKey(
                entity= SavnacAssignment.class,
                parentColumns = "id",
                childColumns = "assignment_id",
                onDelete = CASCADE
            )
        },
        indices = {
                @Index("student_id"),
                @Index("assignment_id")
        }
)

public class SavnacGradeEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "assignment_id")
    private int assignmentId;

    @ColumnInfo(name = "student_id")
    private int studentId;

    @ColumnInfo(name = "grade")
    private String grade;

    @ColumnInfo(name = "entry_date")
    private LocalDateTime entryDate;

    public SavnacGradeEntry(int studentId, int assignmentId, String grade, LocalDateTime entryDate) {
        this.studentId = studentId;
        this.assignmentId = assignmentId;
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

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }
}
