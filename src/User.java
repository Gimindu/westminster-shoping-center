import java.io.*;
import java.util.ArrayList;

// Class to represent a user
public class User implements Serializable {
     public String username;
     public String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


// Getters and Setters
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

    // Method to check if a username and password match
    public boolean authenticate(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }

    // Method to check if a username is already taken
    public static boolean isUsernameTaken(String newUsername, ArrayList<User> userList) {
        for (User user : userList) {
            if (user.getUsername().equals(newUsername)) {
                return true; // Username already taken
            }
        }
        return false; // Username available
    }

    // Method to find a user by username
    public static User findUser(String enteredUsername, ArrayList<User> userList) {
        for (User user : userList) {
            if (user.getUsername().equals(enteredUsername)) {
                return user; // User found
            }
        }
        return null; // User not found
    }
}