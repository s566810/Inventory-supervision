package com.example.inventory_supervision;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {

    private List<String> itemList;
    private OnItemClickListener listener;
    public InventoryAdapter(List<String> itemList) {
        this.itemList = itemList;

    }

    public InventoryAdapter(List<String> itemList, OnItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        return new ViewHolder(view);
    }

    public void clearItems() {
        itemList.clear();
        notifyDataSetChanged();
    }

    public void addItems(List<String> newItems) {
        itemList.addAll(newItems);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String jsonItem = itemList.get(position);
        Items item = new Gson().fromJson(jsonItem, Items.class);

        holder.itemBarcode.setText(item.getItembarcode());
        holder.itemName.setText(item.getItemname());
        holder.itemPrice.setText(item.getItemprice()); // Change to correct method name
        holder.itemCategory.setText(item.getItemcategory());

        holder.addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                holder.addButton.setText("Added");
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemBarcode, itemName, itemPrice, itemCategory;
        Button addButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemBarcode = itemView.findViewById(R.id.Sname);
            itemName = itemView.findViewById(R.id.cartitemname);
            itemPrice = itemView.findViewById(R.id.cartitemprice);
            itemCategory = itemView.findViewById(R.id.cartitemcategory);
            addButton = itemView.findViewById(R.id.AddButton);
        }
    }
}
