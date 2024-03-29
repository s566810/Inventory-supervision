package com.example.inventory_supervision;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_DEPARTMENT = "Department";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void Loginuser(View v) {
        EditText Email = findViewById(R.id.emaillogin);
        EditText Password = findViewById(R.id.password);

        String email = Email.getText().toString();
        String password = Password.getText().toString().trim();

        // Retrieve saved user details using SharedPreferences
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String savedEmail = preferences.getString(KEY_EMAIL, "");
        String savedPassword = preferences.getString(KEY_PASSWORD, "");
        String savedDepartment = preferences.getString(KEY_DEPARTMENT, "");

        if (email.equals(savedEmail) && password.equals(savedPassword)) {
            if (savedDepartment.equals("user")) {
                startActivity(new Intent(LoginActivity.this, Dashboard.class));
            } else if (savedDepartment.equals("admin")) {
                startActivity(new Intent(LoginActivity.this, Dashboard.class));
            } else {
                // Handle other departments or scenarios
                Toast.makeText(this, "Unknown department", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }
}
