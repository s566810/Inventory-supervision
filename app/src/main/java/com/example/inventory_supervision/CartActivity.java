// CartActivity.java
package com.example.inventory_supervision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CartAdapter adapter;
    List<String> cartItemList;

    private TextView totalItems, totalSum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        totalItems = findViewById(R.id.totalnoitem);
        totalSum = findViewById(R.id.totalsum);
        recyclerView = findViewById(R.id.recyclerViews);

        if (totalItems == null || totalSum == null || recyclerView == null) {
            // Log an error or display a message indicating the missing views
            Log.e("CartActivity", "One or more views are null");
            return;
        }

        // Retrieve cart item list from intent extras
        cartItemList = getIntent().getStringArrayListExtra("selectedItemsList");

        if (cartItemList == null) {
            // Initialize the list if it's null to avoid NullPointerException
            cartItemList = new ArrayList<>();
        }

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartAdapter(cartItemList);
        recyclerView.setAdapter(adapter);

        // Update total items count and total sum
        updateTotalItemsAndSum();
    }


    private void updateTotalItemsAndSum() {
        // Calculate total items count
        int itemCount = cartItemList.size();
        totalItems.setText(String.valueOf(itemCount));

        // Calculate total sum
        double sum = 0;
        for (String jsonItem : cartItemList) {
            Items item = new Gson().fromJson(jsonItem, Items.class);
            sum += Double.parseDouble(item.getItemprice());
        }
        totalSum.setText(String.valueOf(sum));
    }
}
