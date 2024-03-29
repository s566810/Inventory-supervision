package com.example.inventory_supervision;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.gson.Gson;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<String> itemList;

    public CartAdapter(List<String> itemList) {
        this.itemList = itemList;
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

        holder.itemBarcode.setText(item.getItembarcode());
        holder.itemName.setText(item.getItemname());
        holder.itemPrice.setText(item.getItemprice());
        holder.itemCategory.setText(item.getItemcategory());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemBarcode, itemName, itemPrice, itemCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemBarcode = itemView.findViewById(R.id.cartitembarcode);
            itemName = itemView.findViewById(R.id.cartitemname);
            itemPrice = itemView.findViewById(R.id.cartitemprice);
            itemCategory = itemView.findViewById(R.id.cartitemcategory);
        }
    }
}
