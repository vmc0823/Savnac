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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.silisurfers.savnac.database.SavnacRepository;
import com.silisurfers.savnac.database.entities.SavnacUser;
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
        String username = binding.emailSignupEditText.getText().toString();
        String password = binding.passwordSignupEditText.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(this, "Username may not be blank.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Password may not be blank.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if a user with this username already exists in the database.
        LiveData<SavnacUser> userObserver = repository.getUserByUsername(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                Toast.makeText(this, String.format("A account with the username %s already exists!", username), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, String.format("%s is not a valid username.", username), Toast.LENGTH_SHORT).show();
            }
        });
    }

    static Intent signupIntentFactory(Context context) {
        return new Intent(context, SignupActivity.class);
    }
}