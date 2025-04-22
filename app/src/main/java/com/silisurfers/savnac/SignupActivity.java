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
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.silisurfers.savnac.database.SavnacRepository;
import com.silisurfers.savnac.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {
    // Private data -------------------------------------------------------------------------------
    private ActivitySignupBinding binding;

    private SavnacRepository repository;

    // Class behaviors ----------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = SavnacRepository.getInstance(getApplicationContext());

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupUser();
            }
        });
    }

    private void setupUser() {
        // TODO: implement setupUser
    }

    static Intent signupIntentFactory(Context context) {
        return new Intent(context, SignupActivity.class);
    }
}