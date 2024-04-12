package com.example.inventory_supervision;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
    }

    public void Registeruser(View v) {
        EditText Email = findViewById(R.id.emailRegister);
        EditText Password = findViewById(R.id.passwordRegister);
        EditText Cpassword = findViewById(R.id.confirmPassword);
        EditText editTextName = findViewById(R.id.departmentName);
        EditText editTextStoreName = findViewById(R.id.storeName);

        final String email = Email.getText().toString().trim();
        final String password = Password.getText().toString().trim();
        String cpassword = Cpassword.getText().toString().trim();
        final String dept = editTextName.getText().toString().trim();
        final String storeName = editTextStoreName.getText().toString().trim();

        if (email.isEmpty()) {
            Email.setError("Email is required");
            return;
        }
        if (password.isEmpty()) {
            Password.setError("Password is required");
            return;
        }
        if (!password.equals(cpassword)) {
            Cpassword.setError("Passwords do not match");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registration successful
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            if (currentUser != null) {
                                String emailPrefix = getEmailPrefix(email); // Get the email prefix before '@'
                                // Save user details to Firebase
                                final User user = new User(dept, email); // Assuming User constructor takes (dept, email) parameters

                                DatabaseReference userRef;
                                if ("admin".equalsIgnoreCase(storeName)) {
                                    // Create a new user without adding to any existing user's details
                                    userRef = FirebaseDatabase.getInstance().getReference("Users").child(emailPrefix);
                                    userRef.child("UserDetails").child(emailPrefix).setValue(user)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(RegisterActivity.this, "Registration Success", Toast.LENGTH_LONG).show();
                                                        // Redirect to login class after user creation
                                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                        finish(); // Close the current activity
                                                    } else {
                                                        Toast.makeText(RegisterActivity.this, "Failed to save user details", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });



                                } else {
                                    // Add user to the UserDetails list of the specified user
                                    userRef = FirebaseDatabase.getInstance().getReference("Users").child(storeName).child("UserDetails");
                                    userRef.child(emailPrefix).setValue(user)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(RegisterActivity.this, "Registration Success", Toast.LENGTH_LONG).show();
                                                        // Redirect to login class after user creation
                                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                        finish(); // Close the current activity
                                                    } else {
                                                        Toast.makeText(RegisterActivity.this, "Failed to save user details", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "Current user is null", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Registration failed
                            Toast.makeText(RegisterActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    // Helper method to get the email prefix before '@'
    private String getEmailPrefix(String email) {
        int index = email.indexOf('@');
        if (index != -1) {
            return email.substring(0, index);
        }
        return email; // Return full email if '@' symbol not found
    }
}
