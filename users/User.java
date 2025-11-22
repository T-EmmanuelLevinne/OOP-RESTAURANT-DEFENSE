// ---------- User base Classes and its sublcass are customer and admin----------

package users;

public class User {
    protected String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void displayInfo() {
        System.out.println("User: " + name);
    }
}

