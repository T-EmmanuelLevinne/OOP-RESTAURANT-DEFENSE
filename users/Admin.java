package users;

import java.util.ArrayList;
import java.util.Scanner;
import menus.MenuItem;
import menus.Appetizer;
import menus.MainCourse;
import menus.Dessert;
import menus.Drinks;
import display.MenuDisplay;

public class Admin extends User {
    private final String username = "admin";
    private final String password = "1234";

    public Admin() {
        super("Admin"); // Default name for admin user
    }

    @Override
    public void displayInfo() {
        System.out.println("Current user: " + getName());
    }

    // Admin login
    public boolean login(Scanner input) {
        System.out.print("Enter admin username: ");
        String userInput = input.nextLine();
        System.out.print("Enter admin password: ");
        String passInput = input.nextLine();

        if (userInput.equals(username) && passInput.equals(password)) {
            System.out.println("Login successful! Welcome, " + getName() + "!");
            return true;
        } else {
            System.out.println("Invalid credentials.");
            return false;
        }
    }

    // Modify menu by category
    public void modifyMenuByCategory(ArrayList<MenuItem> menu, Scanner input) {
        boolean editing = true;

        while (editing) {
            System.out.println("\n--- Menu Categories ---");
            System.out.println("1. Appetizer");
            System.out.println("2. Main Course");
            System.out.println("3. Dessert");
            System.out.println("4. Drinks");
            System.out.println("5. View Full Menu");
            System.out.println("6. Exit Admin Menu");
            System.out.print("Select category to manage: ");

            int catChoice = -1;
            if (input.hasNextInt()) {
                catChoice = input.nextInt();
                input.nextLine();
            } else {
                input.nextLine();
                System.out.println("Invalid input.");
                continue;
            }

            if (catChoice == 5) {
                MenuDisplay.displayFullMenu(menu);
                continue; // Go back to main admin menu
            }

            if (catChoice == 6) {
                editing = false;
                continue; // Exit admin menu
            }

            String category = switch (catChoice) {
                case 1 -> "Appetizer";
                case 2 -> "Main Course";
                case 3 -> "Dessert";
                case 4 -> "Drinks";
                default -> {
                    System.out.println("Invalid choice.");
                    yield null;
                }
            };

            if (category == null) {
                continue;
            }

            boolean categoryMenu = true;
            while (categoryMenu) {
                System.out.println("\n--- Managing " + category + " ---");
                MenuDisplay.displayByCategory(menu, category);
                System.out.println("1. Add Item");
                System.out.println("2. Edit Item");
                System.out.println("3. Remove Item");
                System.out.println("4. Back to Categories");
                System.out.print("Choose an option: ");

                int option = -1;
                if (input.hasNextInt()) {
                    option = input.nextInt();
                    input.nextLine();
                } else {
                    input.nextLine();
                    System.out.println("Invalid input.");
                    continue;
                }

                switch (option) {
                    case 1 -> {
                        // Add new item
                        System.out.print("Enter name: ");
                        String name = input.nextLine();
                        boolean itemExists = menu.stream().anyMatch(item -> item.getName().equals(name));
                        if (itemExists) {
                            System.out.println("Name already exists.");
                            break;
                        }
                        else System.out.print("Enter price: ");
                        double price = -1;
                        if (input.hasNextDouble()) {
                            price = input.nextDouble();
                            input.nextLine();
                        } else {
                            input.nextLine();
                            System.out.println("It must be a number");
                            continue;
                        }
                        System.out.print("Enter description: ");
                        String desc = input.nextLine();

                        MenuItem newItem = switch (category) {
                            case "Appetizer" -> new Appetizer(name, price, desc);
                            case "Main Course" -> new MainCourse(name, price, desc);
                            case "Dessert" -> new Dessert(name, price, desc);
                            case "Drinks" -> new Drinks(name, price, desc);
                            default -> null;
                        };

                        if (newItem != null) {
                            menu.add(newItem);
                            System.out.println(name + " added to " + category);
                        }
                    }
                    case 2 -> {
                        // Edit existing item
                        ArrayList<MenuItem> catItems = MenuDisplay.getItemsByCategory(menu, category);
                        if (catItems.isEmpty()) {
                            System.out.println("No items to edit.");
                            break;
                        }

                        System.out.print("Enter item number to edit: ");
                        int itemNum = input.nextInt();
                        input.nextLine(); // clear buffer

                        if (itemNum < 1 || itemNum > catItems.size()) {
                            System.out.println("Invalid item number.");
                            break;
                        }

                        MenuItem item = catItems.get(itemNum - 1);

                        // ----- NAME -----
                        System.out.print("New name (Enter 'next' to continue) (" + item.getName() + "): ");
                        String newName = input.nextLine();

                        if (!newName.equalsIgnoreCase("next") && !newName.isEmpty()) {
                            item.setName(newName);
                        }

                        // ----- PRICE -----
                        System.out.print("New price (Enter 'next' to continue) (" + item.getPrice() + "): ");
                        String priceInput = input.nextLine();

                        if (!priceInput.equalsIgnoreCase("next") && !priceInput.isEmpty()) {
                            try {
                                item.setPrice(Double.parseDouble(priceInput));
                            } catch (NumberFormatException e) {
                                System.out.println("It must be a number");
                            }
                        }

                        // ----- DESCRIPTION -----
                        System.out.print("New description (Enter 'next' to continue) (" + item.getDescription() + "): ");
                        String newDesc = input.nextLine();

                        if (!newDesc.equalsIgnoreCase("next") && !newDesc.isEmpty()) {
                            item.setDescription(newDesc);
                        }

                        System.out.println("Item updated!");
                    }
                    case 3 -> {
                        // Remove item
                        ArrayList<MenuItem> catItems = MenuDisplay.getItemsByCategory(menu, category);
                        if (catItems.isEmpty()) {
                            System.out.println("No items to remove.");
                            break;
                        }

                        System.out.print("Enter item number to remove: ");
                        int itemNum = input.nextInt();
                        input.nextLine();

                        if (itemNum < 1 || itemNum > catItems.size()) {
                            System.out.println("Invalid item number.");
                            break;
                        }

                        MenuItem removed = catItems.get(itemNum - 1);
                        menu.remove(removed);
                        System.out.println(removed.getName() + " removed from " + category);
                    }
                    case 4 -> categoryMenu = false;
                    default -> System.out.println("Invalid choice.");
                }
            }
        }
    }
}

