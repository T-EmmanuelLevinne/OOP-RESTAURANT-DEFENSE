// ---------- Appetizer sublcass of MenuItem ----------

package menus;

public class Appetizer extends MenuItem {
    public Appetizer(String name, double price, String description) {
        super(name, price, description, "Appetizer");
    }

    @Override
    public void displayItem() {
        System.out.println("[Appetizer] " + getName() + " - ₱" + getPrice() + " - " + getDescription());
    }
}

