// ---------- User subclass or Customer subclass  ----------

package users;

import java.util.ArrayList;
import java.util.Scanner;
import menus.MenuItem;
import order.Order;
import display.MenuDisplay;

public class Customer extends User {
    private Order order;

    public Customer(String name) {
        super(name);
        this.order = new Order();
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public void displayInfo() {
        System.out.println("Welcome, " + getName() + "!");
    }

    // Customer browsing and menu interaction
    public void browseMenu(Scanner scanner, ArrayList<MenuItem> menu) {
        boolean browsing = true;
        while (browsing) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. View Appetizers");
            System.out.println("2. View Main Courses");
            System.out.println("3. View Desserts");
            System.out.println("4. View Drinks");
            System.out.println("5. View Full Menu");
            System.out.println("6. Start Ordering");
            System.out.print("Choose an option: ");

            if (scanner.hasNextInt()) {
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1 -> MenuDisplay.displayByCategory(menu, "Appetizer");
                    case 2 -> MenuDisplay.displayByCategory(menu, "Main Course");
                    case 3 -> MenuDisplay.displayByCategory(menu, "Dessert");
                    case 4 -> MenuDisplay.displayByCategory(menu, "Drinks");
                    case 5 -> MenuDisplay.displayFullMenu(menu);
                    case 6 -> {
                        browsing = false;
                        startOrdering(scanner, menu);
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } else {
                scanner.nextLine();
                System.out.println("Invalid input. Please enter a number (1-6).");
            }
        }
    }

    // Customer ordering process
    public void startOrdering(Scanner scanner, ArrayList<MenuItem> menu) {
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
                case 3 -> orderFromCategory(scanner, menu, "Dessert");
                case 4 -> orderFromCategory(scanner, menu, "Drinks");
                case 5 -> ordering = false;
                default -> System.out.println("Invalid choice. Try again.");
            }
        }

        order.displayReceipt(getName());
        order.saveReceipt(getName());
        System.out.println("Salamat po! Thank you for your order!");
    }

    private void orderFromCategory(Scanner scanner, ArrayList<MenuItem> menu, String category) {
        ArrayList<MenuItem> categoryItems = new ArrayList<>();
        for (MenuItem item : menu) {
            if (item.getCategory().equals(category)) {
                categoryItems.add(item);
            }
        }

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
            if (!order.isEmpty()) {
                System.out.println("\nCurrent Order:");
                order.displayItems();
            }

            System.out.print("\nEnter item number to add to order, 'r' to remove an item, or 0/back to go back: ");
            String input = scanner.nextLine().trim().toLowerCase();

            // Remove item
            if (input.equals("r")) {
                removeItemFromOrder(scanner);
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
                    order.addItem(categoryItems.get(choice - 1));
                    System.out.println("\n✓ Added: " + categoryItems.get(choice - 1).getName());
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and " + categoryItems.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a valid number, 'r' to remove, or 0/back to go back.");
            }
        }
    }

    private void removeItemFromOrder(Scanner scanner) {
        if (order.isEmpty()) {
            System.out.println("No items in your order to remove.");
            return;
        }

        System.out.println("\n--- Remove Item ---");
        order.displayItems();

        System.out.print("Enter item number to remove (0 to cancel): ");
        String input = scanner.nextLine().trim();

        if (input.equals("0")) {
            System.out.println("Removal cancelled.");
            return;
        }

        try {
            int choice = Integer.parseInt(input);
            if (choice > 0 && choice <= order.getItemCount()) {
                MenuItem removed = order.removeItem(choice - 1);
                if (removed != null) {
                    System.out.println("✓ Removed: " + removed.getName());
                }
            } else {
                System.out.println("Invalid item number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }
}

