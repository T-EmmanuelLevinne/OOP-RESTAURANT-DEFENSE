import java.util.*;
import menus.MenuItem;
import users.Admin;
import users.Customer;
import display.MenuDisplay;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Load menu from storage or initialize empty
        ArrayList<MenuItem> menu = MenuDisplay.loadMenu(); // MenuDisplay handles CSV read/write

        Admin admin = new Admin();

        boolean running = true;

        while (running) {
            System.out.println("\nLogin as?");
            System.out.println("1. Customer");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Select: ");

            if (scanner.hasNextInt()) {
                int role = scanner.nextInt();
                scanner.nextLine();

                switch (role) {
                    case 1 -> {
                        System.out.print("Enter your name: ");
                        String cname = scanner.nextLine();
                        Customer customer = new Customer(cname);
                        customer.displayInfo(); // Use inherited method following OOP principles

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
                                        customer.getOrder().startOrdering(scanner, menu, customer.getName());
                                    }
                                    default -> System.out.println("Invalid option. Please try again.");
                                }
                            } else {
                                scanner.nextLine();
                                System.out.println("Invalid input. Please enter a number (1-6).");
                            }
                        }
                    }
                    case 2 -> {
                        if (admin.login(scanner)) {
                            admin.displayInfo(); // Display admin info using inherited method
                            admin.modifyMenuByCategory(menu, scanner);
                            MenuDisplay.saveMenu(menu); // Save changes immediately
                        }
                    }
                    case 3 -> {
                        System.out.println("Exiting system... Goodbye!");
                        running = false;
                        MenuDisplay.saveMenu(menu); // Save before exiting
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } else {
                scanner.nextLine();
                System.out.println("Invalid input. Please enter a number (1-3).");
            }
        }

        scanner.close();
    }
}
