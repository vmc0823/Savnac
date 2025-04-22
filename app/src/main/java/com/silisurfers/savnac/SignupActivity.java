/*
 * Author: Brandon Evans
 * File: SignupActivity.java
 * Date: 4/15/2025
 * Description: This class houses the login page functionality for Savnac.
 */

package com.silisurfers.savnac;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.silisurfers.savnac.database.SavnacRepository;
import com.silisurfers.savnac.databinding.ActivityLoginBinding;

public class SignupActivity extends AppCompatActivity {
    // Private data -------------------------------------------------------------------------------
    private ActivityLoginBinding binding;

    private SavnacRepository repository;

    // Class behaviors ----------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: implement onCreate
    }

    private void setupUser() {
        // TODO: implement setupUser
    }

    static Intent signupIntentFactory(Context context) {
        return new Intent(context, SignupActivity.class);
    }
}