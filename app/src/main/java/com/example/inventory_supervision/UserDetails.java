package com.example.inventory_supervision;

import android.os.Bundle;

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

import java.util.ArrayList;
import java.util.List;

public class UserDetails extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserDetailsAdapter adapter;
    private List<String> userEmailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        recyclerView = findViewById(R.id.recyclerViews);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userEmailList = new ArrayList<>();
        adapter = new UserDetailsAdapter(userEmailList);
        recyclerView.setAdapter(adapter);

        // Retrieve user details from Firebase and populate the RecyclerView
        populateRecyclerView();
    }

    private void populateRecyclerView() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users")
                    .child(getEmailPrefix(currentUser.getEmail())).child("UserDetails");

            usersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        userEmailList.clear();
                        for (DataSnapshot userDetailsSnapshot : dataSnapshot.getChildren()) {
                            String email = userDetailsSnapshot.child("email").getValue(String.class);
                            String deptName = userDetailsSnapshot.child("deptname").getValue(String.class);
                            if ("user".equals(deptName) && email != null) {
                                userEmailList.add(email);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle error
                }
            });
        }
    }


    private String getEmailPrefix(String email) {
        int index = email.indexOf('@');
        if (index != -1) {
            return email.substring(0, index);
        }
        return email; // Return full email if '@' symbol not found
    }
}
