package com.example.inventory_supervision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void addproduct( View v){

        Intent i = new Intent(this,additem.class);
        startActivity(i);

    }
    public void deleteproduct( View v){

        Intent i = new Intent(this,deleteItems.class);
        startActivity(i);

    }
    public void viewInventory( View v){

        Intent i = new Intent(this,viewInventoryActivity.class);
        startActivity(i);
    }
    public void searchInventory( View v){

        Intent i = new Intent(this,ScanItems.class);
        startActivity(i);

    }
}