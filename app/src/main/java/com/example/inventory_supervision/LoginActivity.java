package com.example.inventory_supervision;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    private String getEmailPrefix(String email) {
        int index = email.indexOf('@');
        if (index != -1) {
            return email.substring(0, index);
        }
        return email; // Return full email if '@' symbol not found
    }

    public void loginuser(View v) {
        FirebaseAuth auth;
        EditText emailEditText = findViewById(R.id.EmailLogin);
        EditText passwordEditText = findViewById(R.id.password);
        EditText storeEditText = findViewById(R.id.storeloginname);
        auth = FirebaseAuth.getInstance();

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString().trim();

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Fetch user department from Firebase and redirect accordingly
                    FirebaseDatabase.getInstance().getReference("Users").child(storeEditText.getText().toString().trim())
                            .child("UserDetails").child(getEmailPrefix(email)).child("deptname")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String department = dataSnapshot.getValue(String.class);
                                    Log.d("Department", "Login" + department);
                                    if (department != null) {
                                        if (department.equals("admin")) {
                                            Intent intent = new Intent(LoginActivity.this, admindashboard.class);
                                            intent.putExtra("USER_EMAIL", email);
                                            startActivity(intent);

                                        } else if (department.equals("user")) {
                                            Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                                            intent.putExtra("USER_EMAIL", email);
                                            startActivity(intent);


                                        } else {
                                            Toast.makeText(LoginActivity.this, "Invalid department", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Department not found", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.e("LoginActivity", "Error fetching user department: " + databaseError.getMessage());
                                }
                            });
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
