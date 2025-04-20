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
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.silisurfers.savnac.database.SavnacDatabase;
import com.silisurfers.savnac.database.SavnacRepository;
import com.silisurfers.savnac.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    // Private data -------------------------------------------------------------------------------
    private ActivityLoginBinding binding;

    private SavnacRepository repository;

    // Class behaviors ----------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = SavnacRepository.getInstance(getApplicationContext());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // IMPLEMENT VERIFYUSER
            }
        });
    }

    private void verifyUser() {
        // TODO: Implement verifyUser method
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}