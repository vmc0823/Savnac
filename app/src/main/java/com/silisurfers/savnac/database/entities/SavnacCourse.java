package com.silisurfers.savnac.database.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;


//@author: vw
@Entity(
       tableName = "courses",
       foreignKeys= {
               @ForeignKey(
                       entity = SavnacUser.class,
                       parentColumns = "id", childColumns = "teacher_id",
                       onDelete = CASCADE),

       },
       indices = {
        @Index("teacher_id") //speeds up joins on teacher_id for fast lookups
        }
)
public class SavnacCourse {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "course_name")
    private String courseName;

    @ColumnInfo(name = "teacher_id")
    private int teacherId;


    public SavnacCourse(){

    }

    @Ignore
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
