// ---------- User subclass or Customer subclass  ----------

package users;

import order.Order;

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
}

