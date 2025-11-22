package order;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import menus.*;

public class Order {
    private ArrayList<MenuItem> items;

    public void saveReceipt(String customerName) {
        String fileName = customerName.replaceAll("[^a-zA-Z0-9 ]", "").trim() + "_receipt.txt";
        
        // Generate random transaction number
        Random random = new Random();
        int transactionNumber = 100000 + random.nextInt(900000); // 6-digit number (100000-999999)

        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
            pw.println("----- RECEIPT -----");
            pw.println("Transaction Number: " + transactionNumber);

            for (MenuItem item : items) {
                pw.println(item.getName() + " - ₱" + String.format("%.2f", item.getPrice()));
            }

            pw.println("--------------------");
            pw.println("Total (with 12% VAT): ₱" + String.format("%.2f", calculateTotal()));
            pw.println("Customer: " + customerName);

            System.out.println("\nReceipt saved as: " + fileName);
            System.out.println("Transaction Number: " + transactionNumber);
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
        // Generate random transaction number
        Random random = new Random();
        int transactionNumber = 100000 + random.nextInt(900000); // 6-digit number (100000-999999)
        
        System.out.println("\n--- Receipt ---");
        System.out.println("Transaction Number: " + transactionNumber);
        for (MenuItem item : items) item.displayItem();
        System.out.println("Total (with 12% VAT): ₱" + String.format("%.2f", calculateTotal()));
        System.out.println("Name of the customer: " + customerName);
    }

    // Get items list (for Customer to access)
    public ArrayList<MenuItem> getItems() {
        return items;
    }
}
