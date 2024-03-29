// viewInventoryActivity.java
package com.example.inventory_supervision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class viewInventoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    InventoryAdapter adapter;
    List<String> itemList;
    List<String> selectedItemsList; // List to store selected items

    private TextView totalItems, totalSum;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inventory);

        totalItems = findViewById(R.id.totalnoitem);
        totalSum = findViewById(R.id.totalsum);
        recyclerView = findViewById(R.id.recyclerViews);
        submitButton = findViewById(R.id.submitButton); // Assuming you have a button with id submitButton

        // Retrieve item list from ItemManager
        itemList = ItemManager.getItemList(this);

        // Set total items count
        totalItems.setText(String.valueOf(itemList.size()));

        // Calculate total sum
        double sum = 0;
        for (String jsonItem : itemList) {
            Items item = new Gson().fromJson(jsonItem, Items.class);
            sum += Double.parseDouble(item.getItemprice());
        }
        totalSum.setText(String.valueOf(sum));

        // Initialize selected items list
        selectedItemsList = new ArrayList<>();

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InventoryAdapter(itemList, new InventoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String selectedItem = itemList.get(position);
                Log.d("seeeee",selectedItem.toString());
                // Add selected item to the list
                selectedItemsList.add(selectedItem);
                // Log the selected items list
                Log.d("SelectedItems", selectedItemsList.toString());
            }
        });
        recyclerView.setAdapter(adapter);

        // Set OnClickListener for the submit button
        submitButton.setOnClickListener(v -> {
            // Pass the selected items list to the CartActivity
            Intent intent = new Intent(viewInventoryActivity.this, CartActivity.class);
            intent.putStringArrayListExtra("selectedItemsList", (ArrayList<String>) selectedItemsList);
            startActivity(intent);
        });
    }
}
