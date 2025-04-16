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

@Database(entities = {SavnacGradeEntry.class, SavnacCourse.class, SavnacAssignment.class, SavnacEnrollment.class, SavnacUser.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class SavnacDatabase extends RoomDatabase {

    public abstract SavnacGradeEntryDao gradeEntryDao();
    public abstract SavnacCourseDao courseDao();
    public abstract SavnacAssignmentDao assignmentDao();
    public abstract SavnacEnrollmentDao enrollmentDao();
    public abstract SavnacUserDao UserDao();

    private static volatile SavnacDatabase INSTANCE;

    public static SavnacDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SavnacDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    SavnacDatabase.class, "Savnac")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }






}


