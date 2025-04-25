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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.silisurfers.savnac.database.SavnacRepository;
import com.silisurfers.savnac.database.entities.SavnacUser;
import com.silisurfers.savnac.databinding.ActivityLoginBinding;

import java.util.Objects;

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
                verifyUser();
            }
        });

        binding.goToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignupActivity();
            }
        });
    }

    private void verifyUser() {
        String username = binding.usernameLoginEditText.getText().toString();
        String password = binding.passwordLoginEditText.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(this, "Username may not be blank.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Password may not be blank.", Toast.LENGTH_SHORT).show();
            return;
        }

        LiveData<SavnacUser> userObserver = repository.getUserByUsername(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                if (password.equals(user.getPassword())) {
                    // Set current user to the matched user
                    repository.setCurrentUser(userObserver);

                    // Inform user they have logged in. This feature doubles as a debugging tool for checking that the setCurrentUser call was successful.
                    Toast.makeText(this, String.format("Logged in as %s", Objects.requireNonNull(repository.getCurrentUser().getValue()).getUsername()), Toast.LENGTH_SHORT).show();

                    // Load course homepage if username and password match
                    Intent intent = new Intent(this, CoursesActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Invalid password.", Toast.LENGTH_SHORT).show();
                    binding.passwordLoginEditText.setSelection(0);
                }
            } else {
                Toast.makeText(this, String.format("%s is not a valid username.", username), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToSignupActivity() {
        // Switch to login page if user already has an account.
        Intent intent = SignupActivity.signupIntentFactory(getApplicationContext());
        startActivity(intent);
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}