package com.example.inventory_supervision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity {

    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_DEPARTMENT = "Department";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Retrieve user's department from SharedPreferences
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String userDepartment = preferences.getString(KEY_DEPARTMENT, "");

        // Load different layouts based on the user's department
        if (userDepartment.equals("admin")) {
            setContentView(R.layout.admindashboard);
        } else {
            setContentView(R.layout.activity_dashboard);
        }
    }

    public void addproduct(View v) {
        Intent i = new Intent(this, additem.class);
        startActivity(i);
    }

    public void updateproduct(View v) {
        Intent i = new Intent(this, additem.class);
        startActivity(i);
    }

    public void Cart(View v) {
        Intent i = new Intent(this, CartActivity.class);
        startActivity(i);
    }

    public void deleteproduct(View v) {
        Intent i = new Intent(this, deleteItems.class);
        startActivity(i);
    }

    public void viewInventory(View v) {
        Intent i = new Intent(this, viewInventoryActivity.class);
        startActivity(i);
    }

    public void searchInventory(View v) {
        Intent i = new Intent(this, ScanItems.class);
        startActivity(i);
    }
}
