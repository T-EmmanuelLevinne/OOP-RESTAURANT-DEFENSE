package order;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import menus.*;

public class Order {
    private ArrayList<MenuItem> items;

    public void saveReceipt(String customerName) {
        String fileName = customerName.replaceAll("[^a-zA-Z0-9 ]", "").trim() + "_receipt.txt";

        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
            pw.println("----- RECEIPT -----");

            for (MenuItem item : items) {
                pw.println(item.getName() + " - ₱" + String.format("%.2f", item.getPrice()));
            }

            pw.println("--------------------");
            pw.println("Total (with 12% VAT): ₱" + String.format("%.2f", calculateTotal()));
            pw.println("Customer: " + customerName);

            System.out.println("\nReceipt saved as: " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving receipt: " + e.getMessage());
        }
    }

    public Order() {
        items = new ArrayList<>();
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public double calculateTotal() {
        double subtotal = 0;
        for (MenuItem item : items) subtotal += item.getPrice();
        return subtotal * 1.12; // 12% VAT
    }

    public void displayReceipt(String customerName) {
        System.out.println("\n--- Receipt ---");
        for (MenuItem item : items) item.displayItem();
        System.out.println("Total (with 12% VAT): ₱" + String.format("%.2f", calculateTotal()));
        System.out.println("Name of the customer: " + customerName);
    }

   
}
