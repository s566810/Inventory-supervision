package com.example.inventory_supervision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
    public void Loginuser( View v){
        EditText Email =  findViewById(R.id.emaillogin);
        EditText Password = findViewById(R.id.password);
        Button Login = findViewById(R.id.Login);

        TextView passwordreset = findViewById(R.id.forgotpassword);

        startActivity(new Intent(LoginActivity.this, Dashboard.class));

    }

}