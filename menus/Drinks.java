// ---------- Drinks subclass for menuItem ----------

package menus;

public class Drinks extends MenuItem {
    public Drinks(String name, double price, String description) {
        super(name, price, description, "Drinks");
    }

    @Override
    public void displayItem() {
        System.out.println("[Drinks] " + getName() + " - ₱" + getPrice() + " - " + getDescription());
    }
}

