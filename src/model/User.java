package model;

/**
 * Base abstract class representing a system user.
 * Demonstrates OOP Concepts:
 * - Encapsulation: Private attributes with getters/setters.
 * - Abstraction: Abstract method displayProfile() enforced on subclasses.
 */
public abstract class User {
    private int userId;
    private String name;
    private String username;
    private String password;

    public User(int userId, String name, String username, String password) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    // Getters and Setters (Encapsulation)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Polymorphic abstract method implemented differently by Admin and Student.
     */
    public abstract void displayProfile();
}
