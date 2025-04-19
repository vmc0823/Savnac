package com.silisurfers.savnac.database;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.silisurfers.savnac.database.entities.SavnacAssignment;
import com.silisurfers.savnac.database.entities.SavnacCourse;
import com.silisurfers.savnac.database.entities.SavnacEnrollment;
import com.silisurfers.savnac.database.entities.SavnacGradeEntry;
import com.silisurfers.savnac.database.entities.SavnacUser;
import com.silisurfers.savnac.database.typeConverters.DateConverter;

//@author: vw

@Database(entities = {
        SavnacGradeEntry.class,
        SavnacCourse.class,
        SavnacAssignment.class,
        SavnacEnrollment.class,
        SavnacUser.class},
        version = 1,
        exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class SavnacDatabase extends RoomDatabase {

    public abstract SavnacGradeEntryDao savnacGradeEntryDao();
    public abstract SavnacCourseDao savnacCourseDao();
    public abstract SavnacAssignmentDao savnacAssignmentDao();
    public abstract SavnacEnrollmentDao savnacEnrollmentDao();
    public abstract SavnacUserDao savnacUserDao();

    //singleton
    private static volatile SavnacDatabase INSTANCE;

    //getInstance in the repository class
    public static SavnacDatabase getInstance(Context context) {
        if (INSTANCE == null) { //avoids unnecessary sync
            synchronized (SavnacDatabase.class) {
                if (INSTANCE == null) { //checks that one thread creates the db instance
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    SavnacDatabase.class, "savnac.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }






}


