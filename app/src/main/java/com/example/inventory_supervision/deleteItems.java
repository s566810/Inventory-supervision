package com.example.inventory_supervision;

import androidx.annotation.NonNull;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;


// Your existing imports

public class deleteItems extends AppCompatActivity {
    public static EditText codeView;
    EditText productNameToDelete;
    Button scancode, deletebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_items);
        codeView = findViewById(R.id.displaycode);
        scancode = findViewById(R.id.buttonscandelete);
        productNameToDelete = findViewById(R.id.productNameToDelete);
        deletebtn = findViewById(R.id.deleteItemToTheDatabasebtn);
        scancode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanCodeActivitydel.class));
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFromDatabase();
            }
        });

    }

    public void deleteFromDatabase() {
        String delBarcodeValue = codeView.getText().toString();
        String productName = productNameToDelete.getText().toString();

        if (!TextUtils.isEmpty(delBarcodeValue)) {
            deleteItemByBarcode(delBarcodeValue);
        } else if (!TextUtils.isEmpty(productName)) {
            deleteItemByProductName(productName);
        } else {
            Toast.makeText(deleteItems.this, "No barcode or product name provided", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteItemByBarcode(String barcode) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(deleteItems.this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                .child(getEmailPrefix(currentUser.getEmail())).child("Items").child(barcode);

        // Check if the item exists before trying to delete it
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Item exists, proceed with deletion
                    databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(deleteItems.this, "Item is Deleted", Toast.LENGTH_SHORT).show();
                            // Update UI or perform any other actions after deletion
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(deleteItems.this, "Failed to delete item: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Item does not exist
                    Toast.makeText(deleteItems.this, "Item not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(deleteItems.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void deleteItemByProductName(String productName) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(deleteItems.this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                .child(getEmailPrefix(currentUser.getEmail())).child("Items");

        databaseReference.orderByChild("itemname").equalTo(productName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean itemDeleted = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                    itemDeleted = true;
                }
                if (itemDeleted) {
                    Toast.makeText(deleteItems.this, "Item is Deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(deleteItems.this, "Item with product name '" + productName + "' not found", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(deleteItems.this, "Failed to delete item: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getEmailPrefix(String email) {
        int index = email.indexOf('@');
        if (index != -1) {
            return email.substring(0, index);
        }
        return email; // Return full email if '@' symbol not found
    }

}
