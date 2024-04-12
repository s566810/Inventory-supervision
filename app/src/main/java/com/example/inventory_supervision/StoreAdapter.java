package com.example.inventory_supervision;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.UserViewHolder> {

    private List<String> userObjectList;

    public StoreAdapter(List<String> userObjectList) {
        this.userObjectList = userObjectList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.storelayout, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        String userObject = userObjectList.get(position);
        holder.textViewUserObject.setText(userObject);
    }

    @Override
    public int getItemCount() {
        return userObjectList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUserObject;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserObject = itemView.findViewById(R.id.Sname);
        }
    }
}
