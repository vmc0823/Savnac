package com.silisurfers.savnac.database.entities;

import androidx.room.Embedded;

public class SavnacGradeEntryWithCourse {
    @Embedded
    public SavnacGradeEntry grade;

    @Embedded(prefix = "course_")
    public SavnacCourse course;
}
