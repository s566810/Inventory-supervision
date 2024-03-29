package com.example.inventory_supervision;

import static com.example.inventory_supervision.ItemManager.saveItemList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventory_supervision.ScanCodeActivity;
import com.google.gson.Gson;

import java.util.List;

public class additem extends AppCompatActivity {
    private EditText itemname, itemcategory, itemprice;
    private EditText itembarcode;

    public static TextView resulttextview;
    Button scanbutton, additemtodatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        resulttextview = findViewById(R.id.barcodeview);
        additemtodatabase = findViewById(R.id.additembuttontodatabase);
        scanbutton = findViewById(R.id.buttonscan);
        itemname = findViewById(R.id.edititemname);
        itemcategory = findViewById(R.id.editcategory);
        itemprice = findViewById(R.id.editprice);
        itembarcode = findViewById(R.id.barcodeview);

        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ScanCodeActivity.class));
            }
        });

        additemtodatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });
    }

    private void addItem() {
        String itemnameValue = itemname.getText().toString();
        String itemcategoryValue = itemcategory.getText().toString();
        String itempriceValue = itemprice.getText().toString();
        String itembarcodeValue = itembarcode.getText().toString();

        if (!TextUtils.isEmpty(itemnameValue) && !TextUtils.isEmpty(itemcategoryValue) && !TextUtils.isEmpty(itempriceValue)) {
            List<String> itemList = ItemManager.getItemList(this);
            boolean found = false;
            for (int i = 0; i < itemList.size(); i++) {
                String jsonItem = itemList.get(i);
                Items item = new Gson().fromJson(jsonItem, Items.class);
                if (item.getItemname().equals(itemnameValue)) {
                    // Update existing item
                    item.setItemcategory(itemcategoryValue);
                    item.setItemprice(itempriceValue);
                    item.setItembarcode(itembarcodeValue);
                    itemList.set(i, new Gson().toJson(item));
                    found = true;
                    break;
                }
            }

            if (!found) {
                // Add new item
                Items item = new Items(itemnameValue, itemcategoryValue, itempriceValue, itembarcodeValue);
                itemList.add(new Gson().toJson(item));
            }

            saveItemList(this, itemList);
            Log.d("Item List After Addition", itemList.toString());
            Toast.makeText(additem.this, itemnameValue + (found ? " Updated" : " Added"), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(additem.this, "Please Fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }

}
