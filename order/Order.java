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

    public void startOrdering(Scanner scanner, ArrayList<MenuItem> menu, String customerName) {
        boolean ordering = true;

        while (ordering) {
            System.out.println("\n--- Order Menu ---");
            System.out.println("1. Appetizers");
            System.out.println("2. Main Courses");
            System.out.println("3. Desserts");
            System.out.println("4. Drinks");
            System.out.println("5. Finish Ordering");
            System.out.print("Choose an option: ");

            int categoryChoice;
            try {
                categoryChoice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (categoryChoice) {
                case 1 -> orderFromCategory(scanner, menu, "Appetizer");
                case 2 -> orderFromCategory(scanner, menu, "Main Course");
                case 3 -> orderFromCategory(scanner, menu,  "Dessert");
                case 4 -> orderFromCategory(scanner, menu, "Drinks");
                case 5 -> ordering = false;
                default -> System.out.println("Invalid choice. Try again.");
            }
        }

        displayReceipt(customerName);
        saveReceipt(customerName);
        System.out.println("Salamat po! Thank you for your order!");

    }

    private void orderFromCategory(Scanner scanner, ArrayList<MenuItem> menu, String category) {
        ArrayList<MenuItem> categoryItems = new ArrayList<>();
        for (MenuItem item : menu) if (item.getCategory().equals(category)) categoryItems.add(item);

        if (categoryItems.isEmpty()) {
            System.out.println("No items available in this category.");
            return;
        }

        boolean selecting = true;
        while (selecting) {
            System.out.println("\n--- " + category + " ---");
            for (int i = 0; i < categoryItems.size(); i++) {
                System.out.print((i + 1) + ". ");
                categoryItems.get(i).displayItem();
            }

            // Display current order
            if (!items.isEmpty()) {
                System.out.println("\nCurrent Order:");
                for (int i = 0; i < items.size(); i++) {
                    System.out.print((i + 1) + ". ");
                    items.get(i).displayItem();
                }
            }

            System.out.print("\nEnter item number to add to order, 'r' to remove an item, or 0/back to go back: ");
            String input = scanner.nextLine().trim().toLowerCase();

            // Remove item
            if (input.equals("r")) {
                removeItemInCategory(scanner);
                continue; // back to selecting loop
            }

            // Go back
            if (input.equals("0") || input.equals("back") || input.equals("b")) {
                selecting = false;
                continue;
            }

            // Add item
            try {
                int choice = Integer.parseInt(input);
                if (choice > 0 && choice <= categoryItems.size()) {
                    addItem(categoryItems.get(choice - 1));
                    System.out.println("\n✓ Added: " + categoryItems.get(choice - 1).getName());
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and " + categoryItems.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a valid number, 'r' to remove, or 0/back to go back.");
            }
        }
    }

    private void removeItemInCategory(Scanner scanner) {
        if (items.isEmpty()) {
            System.out.println("No items in your order to remove.");
            return;
        }

        System.out.println("\n--- Remove Item ---");
        for (int i = 0; i < items.size(); i++) {
            System.out.print((i + 1) + ". ");
            items.get(i).displayItem();
        }

        System.out.print("Enter item number to remove (0 to cancel): ");
        String input = scanner.nextLine().trim();

        if (input.equals("0")) {
            System.out.println("Removal cancelled.");
            return;
        }

        try {
            int choice = Integer.parseInt(input);
            if (choice > 0 && choice <= items.size()) {
                MenuItem removed = items.remove(choice - 1);
                System.out.println("✓ Removed: " + removed.getName());
            } else {
                System.out.println("Invalid item number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }
}
