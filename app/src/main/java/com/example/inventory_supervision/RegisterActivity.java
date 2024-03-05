package com.example.inventory_supervision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
    public void Registeruser( View v){
        EditText Email =  findViewById(R.id.emailRegister);
        EditText Password = findViewById(R.id.passwordRegister);
        EditText Cpassword = findViewById(R.id.confirmPassword);
        Button Register = findViewById(R.id.button_register);


        String email = Email.getText().toString();
        String password = Password.getText().toString().trim();
        String cpassword = Cpassword.getText().toString().trim();
        if (email.isEmpty()) {
            Email.setError("Email is required");

            return;
        }
        if (password.isEmpty()) {
            Password.setError("Password is required");
            return;
        }


        startActivity(new Intent(RegisterActivity.this, Dashboard.class));

    }
}