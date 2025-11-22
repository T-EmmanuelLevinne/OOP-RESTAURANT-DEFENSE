// ---------- Abstract MenuItem BaseClass for Maincourse,Dessert,drinks,Appetizer ----------

package menus;

public abstract class MenuItem {
    private String name;
    private double price;
    private String description;
    private String category;

    public MenuItem(String name, double price, String description, String category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    // Getters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(String category) { this.category = category; }

    public void displayItem() {
        System.out.println(name + " - $" + price + "\nDescription: " + description + "\nCategory: " + category);
    }
}

