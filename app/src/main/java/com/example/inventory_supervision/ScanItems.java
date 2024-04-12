package com.example.inventory_supervision;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScanItems extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    DatabaseReference mdatabaseReference;
    RecyclerView recyclerView;
    ScanAdapter adapter;
    List<String> itemList;

    static EditText searchField;
    ImageButton IBtn;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_items);

        searchField = findViewById(R.id.searchfield);
        searchButton = findViewById(R.id.searchbtnn);
        recyclerView = findViewById(R.id.recyclerViews);
        firebaseAuth = FirebaseAuth.getInstance();
        IBtn =  findViewById(R.id.imageButtonsearch);
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        itemList = new ArrayList<>(); // Initialize itemList

        if (currentUser != null) {
            final String userEmail = currentUser.getEmail();
            final String emailPrefix = userEmail;

            // Reference to the "Users" node in Firebase
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");

            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        DataSnapshot userDetailsSnapshot = userSnapshot.child("UserDetails");
                        for (DataSnapshot userDetails : userDetailsSnapshot.getChildren()) {
                            // Get email value from UserDetails
                            String email = userDetails.child("email").getValue(String.class);
                            if (email != null && email.equals(userEmail)) {
                                // If email matches, fetch items from the corresponding user's node
                                String userId = userSnapshot.getKey();
                                DatabaseReference itemsRef = FirebaseDatabase.getInstance().getReference("Users")
                                        .child(userId).child("Items");
                                fetchItems(itemsRef);
                                return; // Exit loop once the email is found
                            }
                        }
                    }
                    // If no matching email is found
                    Toast.makeText(ScanItems.this, "User email not found", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("ScanItems", "Error fetching data from Firebase: " + databaseError.getMessage());
                    Toast.makeText(ScanItems.this, "Failed to fetch data from Firebase", Toast.LENGTH_SHORT).show();
                }
            });

            IBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(getApplicationContext(), ScanCodeActivitysearch.class));
                }
            });

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchItem(searchField.getText().toString());
                }
            });
        } else {
            // Handle the case when no user is logged in
            Toast.makeText(ScanItems.this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchItems(DatabaseReference itemsRef) {
        itemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear(); // Clear itemList before adding new items
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Map<String, String> itemData = (Map<String, String>) snapshot.getValue();
                    if (itemData != null) {
                        String jsonItem = new Gson().toJson(itemData);
                        itemList.add(jsonItem);
                    }
                }
                // Save itemList to ItemManager
                ItemManager.saveItemList(itemList);

                // Initialize or update the adapter
                if (adapter == null) {
                    adapter = new ScanAdapter(itemList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ScanItems.this));
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.setItems(itemList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ScanItems", "Error fetching data from Firebase: " + databaseError.getMessage());
                Toast.makeText(ScanItems.this, "Failed to fetch data from Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchItem(String searchQuery) {
        // Retrieve item list from ItemManager
        List<String> itemList = ItemManager.getItemList();

        Log.d("ItemManager", itemList.toString());

        if(searchQuery.isEmpty()){

            adapter.clearItems();

            adapter.addItems(ItemManager.getItemList());


        }else{

            adapter.clearItems();
            List<String> matchedItems = new ArrayList<>();

            // Loop through the itemList to find matching items
            for (String jsonItem : itemList) {
                Items item = new Gson().fromJson(jsonItem, Items.class);

                // Check if the search query is a number
                boolean isNumeric = searchQuery.matches("-?\\d+(\\.\\d+)?");

                if (isNumeric) {
                    // If the search query is a number, compare with the barcode
                    if (item.getItembarcode().equals(searchQuery)) {
                        // Add the matching item to the list
                        matchedItems.add(jsonItem);
                    }
                } else {
                    // If the search query is not a number, compare with the item name
                    if (item.getItemname().equals(searchQuery)) {
                        // Add the matching item to the list
                        matchedItems.add(jsonItem);
                    }
                }
            }


            // Add the matching items to the adapter
            adapter.addItems(matchedItems);
        }

        // Clear the current adapter data


        // List to hold matching items

    }
}
