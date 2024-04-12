package com.example.inventory_supervision;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

public class ScanAdapter extends RecyclerView.Adapter<ScanAdapter.ViewHolder> {

    private List<String> itemList;
    private OnItemClickListener listener;

    public ScanAdapter(List<String> itemList) {
        this.itemList = itemList;
    }

    public ScanAdapter(List<String> itemList, OnItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    public void clearItems() {
        itemList.clear();
        notifyDataSetChanged();
    }

    public void addItems(List<String> newItems) {
        itemList.addAll(newItems);
        notifyDataSetChanged();
    }

    public void setItems(List<String> itemList) {
        this.itemList.clear(); // Clear the existing itemList
        this.itemList.addAll(itemList); // Add all items from the new list
        notifyDataSetChanged(); // Notify adapter of data change
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String jsonItem = itemList.get(position);
        Items item = new Gson().fromJson(jsonItem, Items.class);
        Log.d("item", String.valueOf(item));
       // ItemManager.addItem(String.valueOf(item));

        holder.itemBarcode.setText(item.getItembarcode());
        holder.itemName.setText(item.getItemname());
        holder.itemPrice.setText(item.getItemprice());
        holder.itemCategory.setText(item.getItemcategory());
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemBarcode = itemView.findViewById(R.id.Sname);
            itemName = itemView.findViewById(R.id.cartitemname);
            itemPrice = itemView.findViewById(R.id.cartitemprice);
            itemCategory = itemView.findViewById(R.id.cartitemcategory);
            // Set OnClickListener here if needed
        }
    }
}
