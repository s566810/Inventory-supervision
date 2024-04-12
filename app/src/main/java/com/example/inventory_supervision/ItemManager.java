package com.example.inventory_supervision;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    private static List<String> itemList = new ArrayList<>();

    // Method to add an item to the list
    public static void addItem(String item) {
        itemList.add(item);
    }

    // Method to remove an item from the list
    public static void removeItem(String item) {
        itemList.remove(item);
    }

    // Method to retrieve the item list
    public static List<String> getItemList() {
        return itemList;
    }

    public static String department;

    public static void saveDepartment(String dept) {
        department = dept;
    }

    public static String getDepartment() {
        return department;
    }

    public static void clearDepartment() {
        department = null;
    }

    // Method to update the item in the list
    public static void updateItem(String oldItem, String newItem) {
        int index = itemList.indexOf(oldItem);
        if (index != -1) {
            itemList.set(index, newItem);
        }
    }

    // Method to save items
    public static void saveItemList(List<String> items) {
        itemList.clear();
        itemList.addAll(items);
    }

    // Method to print all items
    public static void printAllItems() {
        if (!itemList.isEmpty()) {
            for (String item : itemList) {
                System.out.println(item);
            }
        } else {
            System.out.println("Item list is empty.");
        }
    }
}
