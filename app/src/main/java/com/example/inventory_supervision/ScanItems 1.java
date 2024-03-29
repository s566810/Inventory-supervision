package com.example.inventory_supervision;

import static com.example.inventory_supervision.ItemManager.saveItemList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ScanItems extends AppCompatActivity {

    RecyclerView recyclerView;
    ScanAdapter adapter;
    List<String> itemList;

    static EditText searchField;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_items);

        searchField = findViewById(R.id.searchfield);
        searchButton = findViewById(R.id.searchbtnn);
        recyclerView = findViewById(R.id.recyclerViews);

        // Dummy item list (replace this with your actual item list)
        itemList = ItemManager.getItemList(this);

        adapter = new ScanAdapter(itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchItem(searchField.getText().toString());
            }
        });
    }

    private void searchItem(String searchQuery) {
        // Clear the current adapter data
        adapter.clearItems();

        // List to hold matching items
        List<String> matchedItems = new ArrayList<>();

        // Loop through the itemList to find matching items
        List<String> itemList = ItemManager.getItemList(this);
        for (int i = 0; i < itemList.size(); i++) {
            String jsonItem = itemList.get(i);
            Items item = new Gson().fromJson(jsonItem, Items.class);

            // Check if the product name or barcode matches
            if (item.getItemname().equals(searchQuery)) {
                // Remove the item from the list
                matchedItems.add(jsonItem);


                // Exit the loop after the first match is found and deleted
                break;
            }
        }

        // Add the matching items to the adapter
        adapter.addItems(matchedItems);
    }

}
