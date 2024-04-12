package com.example.inventory_supervision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

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

        // Initialize selected items list
        selectedItemsList = new ArrayList<>();

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve logged-in user's email
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();

            // Retrieve all users' details to find the user's email
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean userFound = false;
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        DataSnapshot userDetails = userSnapshot.child("UserDetails");
                        for (DataSnapshot detailSnapshot : userDetails.getChildren()) {
                            String email = detailSnapshot.child("email").getValue(String.class);
                            if (email != null && email.equals(userEmail)) {
                                // User found, retrieve their items
                                userFound = true;
                                DatabaseReference itemsRef = userSnapshot.child("Items").getRef();
                                itemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot itemDataSnapshot) {
                                        itemList = new ArrayList<>();
                                        double sum = 0;
                                        for (DataSnapshot itemSnapshot : itemDataSnapshot.getChildren()) {
                                            Items item = itemSnapshot.getValue(Items.class);
                                            if (item != null) {
                                                String jsonItem = new Gson().toJson(item);
                                                itemList.add(jsonItem);
                                                sum += Double.parseDouble(item.getItemprice());
                                            }
                                        }
                                        // Set total items count
                                        totalItems.setText(String.valueOf(itemList.size()));

                                        // Set total sum
                                        totalSum.setText(String.valueOf(sum));

                                        // Set adapter
                                        adapter = new InventoryAdapter(itemList, position -> {
                                            String selectedItem = itemList.get(position);
                                            // Add selected item to the list
                                            selectedItemsList.add(selectedItem);
                                            // Log the selected items list
                                            Log.d("SelectedItems", selectedItemsList.toString());
                                        });
                                        recyclerView.setAdapter(adapter);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.e("viewInventoryActivity", "Error retrieving items: " + databaseError.getMessage());
                                    }
                                });
                                break; // No need to continue searching for user's email
                            }
                        }
                        if (userFound) {
                            break; // No need to continue searching for user's email
                        }
                    }
                    if (!userFound) {
                        Log.e("viewInventoryActivity", "User email not found: " + userEmail);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("viewInventoryActivity", "Error retrieving users: " + databaseError.getMessage());
                }
            });

            // Set OnClickListener for the submit button
            submitButton.setOnClickListener(v -> {
                // Pass the selected items list to the CartActivity
                Intent intent = new Intent(viewInventoryActivity.this, CartActivity.class);
                intent.putStringArrayListExtra("selectedItemsList", (ArrayList<String>) selectedItemsList);
                startActivity(intent);
            });
        }
    }
}
