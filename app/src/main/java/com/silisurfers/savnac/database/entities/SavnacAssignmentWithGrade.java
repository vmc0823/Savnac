package com.silisurfers.savnac.database.entities;

import androidx.room.Embedded;
import androidx.room.Ignore;

public class SavnacAssignmentWithGrade {
    @Embedded
    public SavnacGradeEntry grade;

    @Embedded(prefix = "course_")
    public SavnacCourse course;

    @Embedded(prefix = "course_assignment")
    public SavnacAssignment assignment;

    public SavnacAssignmentWithGrade(){

    }

    @Ignore
    public SavnacAssignmentWithGrade(String assignmentName, int assignmentPoints, int grade){
        this.assignment = new SavnacAssignment();
        this.grade = new SavnacGradeEntry();
        this.assignment.setAssignmentName(assignmentName);
        this.assignment.setPoints(assignmentPoints);
        this.grade.setGrade(grade);
    }
}
