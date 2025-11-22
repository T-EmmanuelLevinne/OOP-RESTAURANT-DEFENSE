package display;

import java.util.*;
import java.io.*;
import menus.MenuItem;
import menus.Appetizer;
import menus.MainCourse;
import menus.Dessert;
import menus.Drinks;

// ---------- MenuDisplay Class ----------

public class MenuDisplay {
    private static final String MENU_FILE = "menu.csv";

    // Load menu from CSV file
    public static ArrayList<MenuItem> loadMenu() {
        ArrayList<MenuItem> menu = new ArrayList<>();
        File file = new File(MENU_FILE);
        if (!file.exists()) return menu; // empty menu if file doesn't exist

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 4);
                if (parts.length < 4) continue;

                String category = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                String description = parts[3];

                switch (category) {
                    case "Appetizer" -> menu.add(new Appetizer(name, price, description));
                    case "Main Course" -> menu.add(new MainCourse(name, price, description));
                    case "Dessert" -> menu.add(new Dessert(name, price, description));
                    case "Drinks" -> menu.add(new Drinks(name, price, description));
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading menu file: " + e.getMessage());
        }
        return menu;
    }

    // Save menu to CSV file
    public static void saveMenu(ArrayList<MenuItem> menu) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(MENU_FILE))) {
            for (MenuItem item : menu) {
                pw.println(item.getCategory() + "," + item.getName() + "," + item.getPrice() + "," + item.getDescription());
            }
        } catch (IOException e) {
            System.out.println("Error saving menu: " + e.getMessage());
        }
    }

    // Display full menu organized by category
    public static void displayFullMenu(ArrayList<MenuItem> menu) {
        System.out.println("\n--- Full Menu ---");
        if (menu.isEmpty()) {
            System.out.println("Menu is empty.");
            return;
        }

        // Define categories in order
        String[] categories = {"Appetizer", "Main Course", "Dessert", "Drinks"};
        
        // Display each category
        for (String category : categories) {
            ArrayList<MenuItem> catItems = getItemsByCategory(menu, category);
            if (!catItems.isEmpty()) {
                System.out.println("\n--- " + category + " ---");
                for (int i = 0; i < catItems.size(); i++) {
                    MenuItem item = catItems.get(i);
                    System.out.println((i + 1) + ". " + item.getName() +
                            " - ₱" + item.getPrice() + " - " + item.getDescription());
                }
            }
        }
    }

    // Display menu by category with numbering
    public static void displayByCategory(ArrayList<MenuItem> menu, String category) {
        System.out.println("\n--- " + category + " Menu ---");
        ArrayList<MenuItem> catItems = getItemsByCategory(menu, category);
        if (catItems.isEmpty()) {
            System.out.println("No items in this category.");
            return;
        }

        for (int i = 0; i < catItems.size(); i++) {
            MenuItem item = catItems.get(i);
            System.out.println("[" + item.getCategory() + "] " + (i + 1) + ". " + item.getName() +
                    " - ₱" + item.getPrice() + " - " + item.getDescription());
        }
    }

    // Helper: get list of items by category
    public static ArrayList<MenuItem> getItemsByCategory(ArrayList<MenuItem> menu, String category) {
        ArrayList<MenuItem> catItems = new ArrayList<>();
        for (MenuItem item : menu) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                catItems.add(item);
            }
        }
        return catItems;
    }
}

