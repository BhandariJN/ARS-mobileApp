package com.jn.airlinemobileapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jn.airlinemobileapp.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private EditText emailField, passwordField;
    private Spinner roleSpinner;
    private Button loginButton;
    private TextView registerLink;
    private Validation validation = new Validation();
    SharedPreferences userPreferences;
    SharedPreferences airportPreferences;
    SharedPreferences airlinePreferences;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.login_layout);

        userPreferences = getSharedPreferences("user", MODE_PRIVATE);
        airportPreferences = getSharedPreferences("airport", MODE_PRIVATE);
        airlinePreferences = getSharedPreferences("airline", MODE_PRIVATE);


        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        roleSpinner = findViewById(R.id.role_spinner);
        loginButton = findViewById(R.id.login_button);
        registerLink = findViewById(R.id.register_link);


        // Set up spinner for role selection
        String[] roles = {"USER", "ADMIN"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, roles);
        roleSpinner.setAdapter(adapter);


        // Handle login button click
        loginButton.setOnClickListener(view -> {

            if (validation.inputValidation(emailField)
                    && validation.inputValidation(passwordField)
                    && validation.emailValidation(emailField)
                    && validation.passwordValidation(passwordField)
            ) {
                loginUser(emailField.getText().toString(), passwordField.getText().toString(), roleSpinner.getSelectedItem().toString());
            } else {
                Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
            }


        });

        registerLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser(String email, String password, String role) {

        try (DatabaseHelper databaseHelper = new DatabaseHelper(this);) {

            User user = databaseHelper.getUserByEmail(email);
            Log.d("user_log", "User info" + user.toString());
            if (user == null) {
                Toast.makeText(this, "Username not found", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!user.getPassword().equals(password)) {
                Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!user.getRole().equals(role)) {
                Toast.makeText(this, "Incorrect role", Toast.LENGTH_SHORT).show();
                return;
            }
            switch (role) {
                case "AIRPORT_ADMIN":
                    SharedPreferences.Editor editor = airportPreferences.edit();
                    editor.putInt("id", user.getId());
                    editor.apply();
                    startActivity(new Intent(LoginActivity.this, AirportHomeActivity.class));
                    break;

                case "AIRLINE_ADMIN":
                    SharedPreferences.Editor editor2 = airlinePreferences.edit();
                    editor2.putInt("id", user.getId());
                    editor2.apply();
                    startActivity(new Intent(LoginActivity.this, AirlineHomeActivity.class));
                    break;
                default:
                    SharedPreferences.Editor editor3 = userPreferences.edit();
                    editor3.putInt("id", user.getId());
                    editor3.apply();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    break;
            }


        } catch (
                Exception e) {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
            Log.e("Error", Objects.requireNonNull(e.getMessage()));
        }


    }


}
