import javax.swing.*;
import java.util.*;
import java.io.*;

public class Main {
    public static ArrayList<Product> productList = new ArrayList<>(50);
    public static ArrayList<User> userList = new ArrayList<>();


    public static void electronicProduct() {
        Scanner input = new Scanner(System.in);
        try {
            // Getting user input for Electronic product
            System.out.println("\nEnter Electronic Product details:");
            //maximum limit for product list
            if (productList.size() >= 50) {
                System.out.println("Cannot add more products. Maximum limit reached (50).");
                return;
            }
            // Getting user input for Electronic product
            System.out.println("\nEnter Electronic Product details:");

            System.out.print(" Product ID: ");// getting user input for product id
            String productId = input.next();

            System.out.print(" Product Name: ");// getting user input for product name
            String productName = input.next();

            System.out.print(" Available Items: ");// getting user input for available items
            int availableItems = input.nextInt();

            System.out.print(" Price: ");// getting user input for price
            double price = input.nextDouble();
            input.nextLine(); // Consume the newline character

            System.out.print(" Brand: ");// getting user input for brand
            String electronicBrand = input.nextLine();

            System.out.print(" Warranty Period: ");// getting user input for warranty period
            String electronicWarrantyPeriod = input.next();

            // Creating an instance of Electronic using user input
            Electronic electronicProduct = new Electronic(
                    productId,
                    productName,
                    availableItems,
                    price,
                    electronicBrand,
                    electronicWarrantyPeriod);

            productList.add(electronicProduct);
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please enter valid data types.");
            // error handling logic or prompt the user to retry
        } catch (Exception e) {
            System.out.println("Error: An unexpected error occurred.");
            // Log the exception or handle it accordingly
        }
    }

    public static void clothProduct() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.println("Enter Clothing Product details:");
            //maximum limit for product list
            if (productList.size() >= 50) {
                System.out.println("Cannot add more products. Maximum limit reached (50).");
                return;
            }

            // Getting user input for Clothing
            System.out.print(" product ID:");
            String productId = input.next();

            System.out.print(" product name:");// getting user input for product name
            String productName = input.next();

            System.out.print(" available items:");// getting user input for available items
            int availableItems = input.nextInt();

            System.out.print(" price:");// getting user input for price
            double price = input.nextDouble();

            System.out.print(" size:");// getting user input for size
            String size = input.next();

            input.nextLine(); // Consume the newline character

            System.out.print(" color:");// getting user input for color
            String color = input.nextLine();

            // Creating Clothing object using user input
            Clothing clothingProduct = new Clothing(productId, productName, availableItems, price, size, color);// creating
                                                                                                                // clothing
                                                                                                                // object

            productList.add(clothingProduct);
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please enter valid data types.");
            // error handling logic or prompt the user to retry
        } catch (Exception e) {
            System.out.println("Error: An unexpected error occurred.");
            // Log the exception or handle it accordingly
        }

    }

    public static void deleteProduct() {
        Scanner input = new Scanner(System.in);
        // Getting user input for the product ID to delete
        System.out.print("Enter the product ID to delete: ");
        String productIdToDelete = input.next();

        // Find and remove the product with the specified ID
        boolean removed = false;
        for (Product product : productList) {
            if (product.getProductId().equals(productIdToDelete)) {
                productList.remove(product);
                removed = true;
                break; // Exit the loop
            }
        }

        // Check if the product was found and removed
        if (removed) {
            System.out.println("Product with ID " + productIdToDelete + " removed successfully.");
        } else {
            System.out.println("Product with ID " + productIdToDelete + " not found in the list.");
        }

    }

    private static void saveToFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("output.dat"))) {
            outputStream.writeObject(productList);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Output file not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("output.dat"))) {
            productList = (ArrayList<Product>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Input file not found. No data loaded.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    // display product
    public static void displayProduct() {
        // Sorting the productList based on product ID

        productList.sort(new Comparator<>() {
            @Override
            public int compare(Product product1, Product product2) {
                return product1.getProductId().compareTo(product2.getProductId());
            }
        });

        // Displaying the sorted productList
        System.out.println("Sorted productList based on product ID:");
        for (Product product : productList) {
            System.out.println(product);
        }
    }
    public static void signup() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a new username:");
        String newUsername = input.next();

        // Check if the username is already taken
        if (User.isUsernameTaken(newUsername, userList)) {
            System.out.println("Username already taken. Please choose another one.");
            return;
        }

        System.out.println("Enter a password:");
        String newPassword = input.next();

        // Create a new user and add it to the user list
        User newUser = new User(newUsername, newPassword);
        userList.add(newUser);

        System.out.println("Signup successful! You can now login.");

        // Save the updated userList to a file
        saveUserListToFile();
    }

    private static void saveUserListToFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("userList.dat"))) {
            outputStream.writeObject(userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void login() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your username:");
        String enteredUsername = input.next();

        System.out.println("Enter your password:");
        String enteredPassword = input.next();

        // Load userList from file
        loadUserListFromFile();

        // Check if the entered credentials match any user in the list
        User foundUser = User.findUser(enteredUsername, userList);

        if (foundUser != null && foundUser.authenticate(enteredUsername, enteredPassword)) {
            System.out.println("Login successful! Welcome, " + foundUser.getUsername() + "!");
            System.out.println("\nLaunching GUI for User...");
            SwingUtilities.invokeLater(() -> {
                WestminsterShoppingManagerGui westminsterShoppingManagerGui = new WestminsterShoppingManagerGui();
                westminsterShoppingManagerGui.setVisible(true);
            });
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private static void loadUserListFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("userList.dat"))) {
            userList = (ArrayList<User>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Handle exceptions or just ignore if the file doesn't exist or cannot be read
        }
    }






    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        loadFromFile();//loading data from file
        boolean exit = false;

        while (!exit) {
            System.out.println("\nAre you a User or an Admin?");
            System.out.println("1. User");
            System.out.println("2. Admin");
            System.out.println("3. Exit");

            System.out.print("\nEnter your role: ");
            String role = input.nextLine();

            switch (role) {
                case "1": // User

//
//                    loadFromFile();
//                    System.out.println("\nLaunching GUI for User...");
//                    SwingUtilities.invokeLater(() -> {
//                        WestminsterShoppingManagerGui westminsterShoppingManagerGui = new WestminsterShoppingManagerGui();
//                        westminsterShoppingManagerGui.setVisible(true);
//                    });
//                    exit = true; // Exit after launching the GUI
//                    break;

                    boolean userExit = false;
                    while (!userExit) {
                        System.out.println("\n          USER MENU            \n");
                        System.out.println("1. Login");
                        System.out.println("2. Sign Up");
                        System.out.println("3. Launch GUI");
                        System.out.println("4. Exit User Menu");

                        System.out.print("\nEnter menu option: ");
                        String userMenuInput = input.nextLine();

                        switch (userMenuInput) {
                            case "1":
                                // Implement login logic here
                                System.out.println("Enter your username and password to login.");
                                login();
                                loadFromFile();
                                // Add code for login validation
                                break;
                            case "2":
                                // Implement signup logic here
                                System.out.println("Enter your details to sign up.");
                                signup();
                                // Add code for user registration
                                break;
                            case "3":
                                loadFromFile();
                                System.out.println("\nLaunching GUI for User...");
                                SwingUtilities.invokeLater(() -> {
                                    WestminsterShoppingManagerGui westminsterShoppingManagerGui = new WestminsterShoppingManagerGui();
                                    westminsterShoppingManagerGui.setVisible(true);
                                });
                                exit = true; // Exit after launching the GUI
                                break;
                            case "4":
                                userExit = true;
                                break;
                            default:
                                System.out.println("Invalid option, please try again.");
                                break;
                        }
                    }
                    break;

                case "2": // Admin
                    boolean adminExit = false;
                    while (!adminExit) {
                        System.out.println("\n          ADMIN MENU            \n");
                        System.out.println("1. Add a new product.");
                        System.out.println("2. Delete a product.");
                        System.out.println("3. Print the list of the products");
                        System.out.println("4. Save to a file");
                        System.out.println("5. Load from a file");
                        System.out.println("6. Exit Admin Menu");

                        System.out.print("\nEnter menu option: ");
                        String menuInput = input.nextLine();

                        switch (menuInput) {
                            case "1":
                                System.out.println("choose the product category");
                                System.out.println("1. Electronics");
                                System.out.println("2. Clothing");
                                String productInput = input.nextLine();
                                switch (productInput) {
                                    case "1" -> electronicProduct();

                                    case "2" -> clothProduct();

                                    default -> System.out.println("Invalid option, please try again.\n");

                                }
                                break;
                            case "2":
                                // Implement delete product logic
                                deleteProduct();
                                break;
                            case "3":
                                // Implement display product logic
                                displayProduct();
                                break;
                            case "4":
                                // Implement save to file logic
                                saveToFile();
                                break;
                            case "5":
                                // Implement load from file logic
                                loadFromFile();
                                break;
                            case "6":
                                // Exit
                                adminExit = true;
                                break;
                            default:
                                // Invalid option
                                System.out.println("Invalid option, please try again.");
                                break;
                        }
                    }
                    break;

                case "3": // Exit
                    exit = true;
                    System.out.println("Exiting...");
                    break;

                default:
                    // Invalid role
                    System.out.println("Invalid role, please try again.");
                    break;
            }
        }
    }
}