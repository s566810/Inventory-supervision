package com.example.inventory_supervision;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserDetailsAdapter extends RecyclerView.Adapter<UserDetailsAdapter.UserViewHolder> {

    private List<String> userEmailList;

    public UserDetailsAdapter(List<String> userEmailList) {
        this.userEmailList = userEmailList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_details_layout, parent, false);
        return new UserViewHolder(view);
    }

    @Override

    public void onBindViewHolder(@NonNull final UserViewHolder holder, int position) {
        final String email = userEmailList.get(position);
        holder.textViewEmail.setText(email);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the FirebaseUser
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    // Get the reference to the database node for UserDetails
                    DatabaseReference userDetailsRef = FirebaseDatabase.getInstance().getReference("Users")
                            .child(getEmailPrefix(currentUser.getEmail()))
                            .child("UserDetails");

                    // Remove the user from the database
                    userDetailsRef.child(getEmailPrefix(email)).removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Remove the user from the RecyclerView list after success
                                    int position = holder.getAdapterPosition();
                                    if (position != RecyclerView.NO_POSITION) {
                                        userEmailList.remove(position);
                                        notifyItemRemoved(position);
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle the failure
                                    Log.e("UserDetailsAdapter", "Failed to delete user: " + e.getMessage());
                                }
                            });
                }
            }
        });
    }

    // Method to encode the email to create a valid database path
    private String encodeEmail(String email) {
        return email.replace(".", ",");
    }

    private String getEmailPrefix(String email) {
        int index = email.indexOf('@');
        if (index != -1) {
            return email.substring(0, index);
        }
        return email; // Return full email if '@' symbol not found
    }
    @Override
    public int getItemCount() {
        return userEmailList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textViewEmail;
        Button deleteButton;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEmail = itemView.findViewById(R.id.StoreName);
            deleteButton = itemView.findViewById(R.id.DeleteUser);
        }
    }
}
