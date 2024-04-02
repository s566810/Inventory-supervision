package com.example.inventory_supervision;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_Department= "Department";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void Registeruser(View v) {
        EditText Email = findViewById(R.id.emailRegister);
        EditText Password = findViewById(R.id.passwordRegister);
        EditText Cpassword = findViewById(R.id.confirmPassword);
        EditText editTextName = findViewById(R.id.departmentName);
        Button Register = findViewById(R.id.button_register);

        String email = Email.getText().toString();
        String password = Password.getText().toString().trim();
        String cpassword = Cpassword.getText().toString().trim();
        String dept = editTextName.getText().toString().trim();

        if (email.isEmpty()) {
            Email.setError("Email is required");
            return;
        }
        if (password.isEmpty()) {
            Password.setError("Password is required");
            return;
        }
        if (!password.equals(cpassword)) {
            Cpassword.setError("Passwords do not match");
            return;
        }

        // Save user details using SharedPreferences
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_Department, dept);
        editor.apply();

        startActivity(new Intent(RegisterActivity.this, Dashboard.class));
    }
}
