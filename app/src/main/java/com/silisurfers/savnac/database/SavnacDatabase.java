package com.silisurfers.savnac.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.silisurfers.savnac.database.entities.SavnacGradeEntry;
import com.silisurfers.savnac.database.typeConverters.DateConverter;

@Database(entities = {SavnacGradeEntry.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class SavnacDatabase extends RoomDatabase {

    //TODO: set fields, synchronize dao
}
