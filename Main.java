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
                        customer.browseMenu(scanner, menu); // Customer handles its own browsing
                    }
                    case 2 -> {
                        if (admin.login(scanner)) {
                            admin.displayInfo();     // Display admin info using inherited method
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
