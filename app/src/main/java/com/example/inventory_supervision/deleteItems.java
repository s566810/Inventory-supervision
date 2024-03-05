package com.example.inventory_supervision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

public class deleteItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_items);
    }

    public void deletefrmdatabase(View v)
    {

            Toast.makeText(deleteItems.this,"Item deleted",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(deleteItems.this, Dashboard.class));
    }
}