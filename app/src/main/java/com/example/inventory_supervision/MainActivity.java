package com.example.inventory_supervision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login (View view)
    {
        Intent i = new Intent(this,LoginActivity.class);

        startActivity(i);
    }
    public void register (View view)
    {
        Intent i = new Intent(this,RegisterActivity.class);

        startActivity(i);
    }
}