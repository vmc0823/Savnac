package com.silisurfers.savnac.database.entities;

import androidx.room.Embedded;


public class SavnacAssignmentWithGrade {
    @Embedded
    public SavnacGradeEntry grade;


    @Embedded(prefix = "course_assignment")
    public SavnacAssignment assignment;


    public SavnacAssignmentWithGrade(SavnacGradeEntry grade, SavnacAssignment assignment) {
        this.grade = grade;
        this.assignment = assignment;
    }


}
