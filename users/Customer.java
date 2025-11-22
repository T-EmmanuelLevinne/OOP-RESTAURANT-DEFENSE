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
                        order.startOrdering(scanner, menu, getName());
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } else {
                scanner.nextLine();
                System.out.println("Invalid input. Please enter a number (1-6).");
            }
        }
    }
}

