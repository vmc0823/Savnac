/*
 * Author: Brandon Evans
 * File: LoginActivity.java
 * Date: 4/15/2025
 * Description: This class houses the login page functionality for Savnac.
 */

package com.silisurfers.savnac;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.silisurfers.savnac.database.SavnacDatabase;
import com.silisurfers.savnac.database.SavnacUserDao;

public class LoginActivity extends AppCompatActivity {
    // Private data -------------------------------------------------------------------------------
    private SavnacUserDao userDAO;

    private static SavnacDatabase repository;

    // Class behaviors ----------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: Finish onCreate method
    }

    private void verifyUser() {
        // TODO: Implement verifyUser method
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}