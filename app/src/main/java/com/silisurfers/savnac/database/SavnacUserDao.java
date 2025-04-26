package com.silisurfers.savnac.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.silisurfers.savnac.database.entities.SavnacUser;

import java.util.List;

//@author: vw
@Dao
public interface SavnacUserDao {
    @Insert long insert(SavnacUser user);

    @Update
    void update(SavnacUser user);

    @Delete
    void delete(SavnacUser user);

    @Query("SELECT * FROM users LIMIT 1")
    LiveData<SavnacUser> getFirstUserSync();

    @Query("SELECT * FROM users WHERE users.id = :id")
    LiveData<SavnacUser> getById(int id);

    @Query("SELECT * FROM users WHERE users.id = :id")
    SavnacUser getByIdSync(int id);

    @Query("SELECT * FROM users WHERE username = :usn AND password = :pwd LIMIT 1")
    LiveData<SavnacUser> login(String usn, String pwd);

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    LiveData<SavnacUser> getByUsername(String username);

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    SavnacUser getByUsernameSync(String username);

    @Query("SELECT * from users")
    LiveData<List<SavnacUser>> getAll();
}
