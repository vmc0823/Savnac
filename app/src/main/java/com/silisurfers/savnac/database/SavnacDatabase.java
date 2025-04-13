package com.silisurfers.savnac.database;

import android.content.Context;


import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.silisurfers.savnac.database.entities.SavnacGradeEntry;
import com.silisurfers.savnac.database.typeConverters.DateConverter;

@Database(entities = {SavnacGradeEntry.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class SavnacDatabase extends RoomDatabase {

    //TODO: set fields, synchronize dao



    private static volatile SavnacGradeEntry INSTANCE;

//    static SavnacDatabase getDatabase(final Context context) {
//        if (INSTANCE == null) {
//            synchronized (SavnacDatabase.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                                    SavnacDatabase.class, "Savnac")
//                            .fallbackToDestructiveMigration()
//                            .addCallback(addDefaultValues)
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }

}


