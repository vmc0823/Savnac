package com.silisurfers.savnac.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//@author: vw

@Entity(tableName = "users")
public class SavnacUser {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="username")
    private String username;

    @ColumnInfo(name="password")
    private String password;

    @ColumnInfo(name="role") //"teacher" or "student"
    private String role;

    public SavnacUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    //autogen sets-gets
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
