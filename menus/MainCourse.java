// ---------- Main Course subclass of MenuItem ----------

package menus;

public class MainCourse extends MenuItem {
    public MainCourse(String name, double price, String description) {
        super(name, price, description, "Main Course");
    }

    @Override
    public void displayItem() {
        System.out.println("[Main Course] " + getName() + " - ₱" + getPrice() + " - " + getDescription());
    }
}

