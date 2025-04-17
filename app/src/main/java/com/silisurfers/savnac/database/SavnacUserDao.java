package com.silisurfers.savnac.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.silisurfers.savnac.database.entities.SavnacUser;

import java.util.List;

//@author: vw
@Dao
public interface SavnacUserDao {
    @Insert void insert(SavnacUser user);

    @Query("SELECT * FROM users WHERE id = :id")
    LiveData<SavnacUser> getById(int id);

    @Query("SELECT * FROM users WHERE username = :usn AND password = :pwd LIMIT 1")
    LiveData<SavnacUser> login(String usn, String pwd);

    @Query("SELECT * from users")
    LiveData<List<SavnacUser>> getAll();
}
