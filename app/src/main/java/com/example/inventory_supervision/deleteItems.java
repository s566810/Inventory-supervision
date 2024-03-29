package com.example.inventory_supervision;

import static com.example.inventory_supervision.ItemManager.saveItemList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

public class deleteItems extends AppCompatActivity {
    public static TextView codeView;
    EditText productNameToDelete;
    Button scancode, deletebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_items);
        codeView = findViewById(R.id.displaycode);
        scancode = findViewById(R.id.buttonscandelete);
        productNameToDelete = findViewById(R.id.productNameToDelete);
        deletebtn= findViewById(R.id.deleteItemToTheDatabasebtn);
        scancode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanCodeActivitydel.class));
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletefrmdatabase();
            }
        });

    }

    public void deletefrmdatabase()
    {
        String delbarcodevalue = codeView.getText().toString();
        EditText productName = findViewById(R.id.productNameToDelete);
        String product = productName.getText().toString();
        List<String> itemList = ItemManager.getItemList(this);
        for (int i = 0; i < itemList.size(); i++) {
            String jsonItem = itemList.get(i);
            Items item = new Gson().fromJson(jsonItem, Items.class);

            // Check if the product name or barcode matches
            if (item.getItemname().equals(product) || item.getItembarcode().equals(delbarcodevalue)) {
                // Remove the item from the list
                itemList.remove(i);
                saveItemList(this,itemList);
                Toast.makeText(deleteItems.this, "Item deleted", Toast.LENGTH_SHORT).show();
                // Exit the loop after the first match is found and deleted
                break;
            }
        }

        Log.d("Item List After Deletion", itemList.toString());


        startActivity(new Intent(deleteItems.this, Dashboard.class));
    }
}
