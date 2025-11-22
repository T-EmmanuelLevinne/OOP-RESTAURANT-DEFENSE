// ---------- Dessert subclass for menuItem ----------

package menus;

public class Dessert extends MenuItem {
    public Dessert(String name, double price, String description) {
        super(name, price, description, "Dessert");
    }

    @Override
    public void displayItem() {
        System.out.println("[Dessert] " + getName() + " - ₱" + getPrice() + " - " + getDescription());
    }
}

