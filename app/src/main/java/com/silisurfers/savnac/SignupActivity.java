/*
 * Author: Brandon Evans
 * File: SignupActivity.java
 * Date: 4/22/2025
 * Description: This class houses the signup page functionality for Savnac.
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

import java.util.Objects;

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

        binding.goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginActivity();
            }
        });
    }

    private void setupUser() {
        String username = binding.usernameSignupEditText.getText().toString();
        String password = binding.passwordSignupEditText.getText().toString();
        String role;

        // If the teacher checkbox is checked then the account's role is Teacher instead of Student.
        if (binding.isTeacherCheckBox.isChecked()) {
            role = "Teacher";
        } else {
            role = "Student";
        }

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
                Toast.makeText(this, String.format("An account with the username %s already exists.", username), Toast.LENGTH_SHORT).show();
            } else {
                // This call should create the new user with the given signup details.
                SavnacUser newUser = new SavnacUser(username, password, role);
                repository.insertUser(newUser);

                // Inform user they signed up before taking them to the login page.
                Toast.makeText(this, String.format("Signed up as %s", username), Toast.LENGTH_SHORT).show();

                // Switch over to login page after signing the user up.
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);

                // Remove this observer since an account was created.
                userObserver.removeObservers(this);
            }
        });
    }

    private void goToLoginActivity() {
        // Switch to login page if user already has an account.
        Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
        startActivity(intent);
    }

    static Intent signupIntentFactory(Context context) {
        return new Intent(context, SignupActivity.class);
    }
}