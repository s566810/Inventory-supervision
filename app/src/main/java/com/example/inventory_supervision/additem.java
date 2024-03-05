package com.example.inventory_supervision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class additem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
    }

    public void additem(View v){
        Button additemtodatabase = findViewById(R.id.additembuttontodatabase);
        Button scanbutton = findViewById(R.id.buttonscan);
        EditText itemname = findViewById(R.id.edititemname);
        EditText itemcategory = findViewById(R.id.editcategory);
        EditText itemprice = findViewById(R.id.editprice);

        String itemnameValue = itemname.getText().toString().trim();
        String itemcategoryValue = itemcategory.getText().toString().trim();
        String itempriceValue = itemprice.getText().toString().trim();

        if (TextUtils.isEmpty(itemnameValue)) {
            itemname.setError("Item name is required");
            return;
        }

        if (TextUtils.isEmpty(itemcategoryValue)) {
            itemcategory.setError("Item category is required");
            return;
        }

        if (TextUtils.isEmpty(itempriceValue)) {
            itemprice.setError("Item price is required");
            return;
        }

        // Assuming all fields are filled correctly, you can add the item to the database here
        // If the item is added successfully, move to the dashboard activity
        // For demonstration purposes, a toast message is shown here
        Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(additem.this, Dashboard.class));
    }

}