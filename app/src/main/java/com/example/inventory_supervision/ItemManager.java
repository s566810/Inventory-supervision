package com.example.inventory_supervision;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    private static final String ITEM_LIST_PREF = "item_list_pref";
    private static final String ITEM_LIST_KEY = "item_list_key";

    // Method to save itemList to SharedPreferences
    public static void saveItemList(Context context, List<String> itemList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ITEM_LIST_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonItemList = gson.toJson(itemList);
        editor.putString(ITEM_LIST_KEY, jsonItemList);
        editor.apply();
    }

    // Method to retrieve itemList from SharedPreferences
    public static List<String> getItemList(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ITEM_LIST_PREF, Context.MODE_PRIVATE);
        String jsonItemList = sharedPreferences.getString(ITEM_LIST_KEY, null);
        List<String> itemList;
        if (!TextUtils.isEmpty(jsonItemList)) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<String>>() {}.getType();
            itemList = gson.fromJson(jsonItemList, type);
        } else {
            itemList = new ArrayList<>();
        }
        return itemList;
    }

    // Method to print all items
    public static void printAllItems(Context context) {
        List<String> itemList = getItemList(context);
        if (itemList != null) {
            for (String jsonItem : itemList) {
                System.out.println(jsonItem);
            }
        } else {
            System.out.println("Item list is empty.");
        }
    }
}
