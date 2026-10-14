package model;

/**
 * Admin subclass inheriting from User.
 * Demonstrates:
 * - Inheritance: Extends base User class.
 * - Polymorphism: Overrides displayProfile() for administrative details.
 */
public class Admin extends User {
    private String department;

    public Admin(int userId, String name, String username, String password, String department) {
        super(userId, name, username, password);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public void displayProfile() {
        System.out.println("========================================");
        System.out.println("            ADMIN PROFILE               ");
        System.out.println("========================================");
        System.out.println("Admin ID   : " + getUserId());
        System.out.println("Name       : " + getName());
        System.out.println("Username   : " + getUsername());
        System.out.println("Department : " + department);
        System.out.println("========================================");
    }
}
