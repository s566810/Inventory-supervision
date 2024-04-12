package com.example.inventory_supervision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class admindashboard extends AppCompatActivity {

    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_DEPARTMENT = "Department";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        final FirebaseUser users = firebaseAuth.getCurrentUser();

//        String finaluser = users.getEmail();
//        String[] resultemail = finaluser.split("@");
//
//        TextView name = findViewById(R.id.name);
//        name.setText("Hello,"+ resultemail[0]);

        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String userDepartment = preferences.getString(KEY_DEPARTMENT, "");
        //Log.d("Department","useDepartment"+userDepartment);

//        // Load different layouts based on the user's department
//        if (userDepartment.equals("admin")) {
//            setContentView(R.layout.admindashboard);
//        } else {
//            setContentView(R.layout.activity_dashboard);
//        }
        setContentView(R.layout.activity_admindashboard);
//        String department = ItemManager.getDepartment();
//        Log.d("Department", "useDepartment" + department);
//
//        // Load different layouts based on the user's department
//        if (department.equals("admin")) {
//            setContentView(R.layout.admindashboard);
//        } else {
//            setContentView(R.layout.activity_dashboard);
//        }


    }
//    protected void onResume() {
//        super.onResume();
//        Log.d("cleared", "10");
//        ItemManager.clearDepartment();
//        //clearSharedPreferences();
//    }

//    private void clearSharedPreferences() {
//        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.clear();
//        editor.apply();
//    }

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

    public void StoreNames(View v) {
        Intent i = new Intent(this, storeNames.class);
        startActivity(i);
    }


    public void searchInventory(View v) {
        Intent i = new Intent(this, ScanItems.class);
        startActivity(i);
    }

    public void userDetails(View v) {
        Intent i = new Intent(this, UserDetails.class);
        startActivity(i);
    }

    public void logout(View v) {
        // Clear any session data or preferences
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        // Navigate to the main activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Close the Dashboard activity to prevent going back to it with the back button
    }


}
