import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.io.Serializable;

import javax.swing.*;
import java.awt.event.*;

public class LibraryAppGUI extends JFrame {

    private JButton registerButton;
    private JButton loginButton;
    private JButton exitButton;

    public LibraryAppGUI() {
        // Initialize components
        registerButton = new JButton("Register");
        loginButton = new JButton("Login");
        exitButton = new JButton("Exit");

        // Set up the layout
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        // Add buttons to the frame
        this.add(registerButton);
        this.add(loginButton);
        this.add(exitButton);

        // Set up the actions for the buttons
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the registerUser method or show registration form
                registerUser();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the loginUser method or show login form
                loginUser();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit the application
                System.exit(0);
            }
        });

        // Configure the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Library System");
        this.pack();
        this.setVisible(true);
    }

    private void registerUser() {
        // Show registration dialog or panel
    }

    private void loginUser() {
        // Show login dialog or panel
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LibraryAppGUI();
            }
        });
    }
}

public class LibraryApp {

    private static LibrarySystem librarySystem = new LibrarySystem();
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Welcome to the Library System");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Please enter an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting the system...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Select plan (base/premium): ");
        String plan = scanner.nextLine();

        boolean success = librarySystem.registerUser(username, password, plan);
        if (success) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed. User may already exist.");
        }
    }

    private static void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        currentUser = librarySystem.login(username, password);
        if (currentUser != null) {
            System.out.println("Login successful!");
            userMenu();
        } else {
            System.out.println("Login failed. Please check your credentials.");
        }
    }

    private static void userMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("User Menu:");
            System.out.println("1. Borrow a book");
            System.out.println("2. Add a magazine");
            System.out.println("3. Empty lists");
            System.out.println("4. Log out");
            System.out.println("6. Profile mode");
            System.out.print("Enter an option: ");

            int userOption = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (userOption) {
                case 1:
                    borrowBook();
                    break;
                case 2:
                    addMagazine();
                    break;
                case 3:
                    currentUser.emptyLists();
                    System.out.println("Lists emptied.");
                    break;
                case 4:
                    back = true;
                    currentUser = null;
                    System.out.println("Logged out.");
                    break;
                case 5:
                    loanMode();
                    break;
                case 6:
                    profileMode();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void profileMode() {
    boolean back = false;
    while (!back) {
        System.out.println("Profile Mode:");
        System.out.println("1. Change plan type (Non-premium)");
        System.out.println("2. Apply 15 days coupon (Premium)");
        System.out.println("3. Change password");
        System.out.println("4. Back to user menu");
        System.out.print("Enter an option: ");

        int profileOption = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        switch (profileOption) {
            case 1:
                System.out.print("Enter new plan type (base/premium): ");
                String newPlan = scanner.nextLine();
                currentUser.changePlan(newPlan);
                break;
            case 2:
                currentUser.applyCoupon();
                break;
            case 3:
                System.out.print("Enter old password: ");
                String oldPassword = scanner.nextLine();
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();
                currentUser.changePassword(oldPassword, newPassword);
                break;
            case 4:
                back = true;
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
}

    private static void loanMode() {
        System.out.println("Loan Mode:");
        // Implement the loan mode logic
    }
    
    private static void borrowBook() {
        System.out.print("Enter the title of the book: ");
        String book = scanner.nextLine();
        boolean success = currentUser.borrowBook(book);
        if (success) {
            System.out.println("Book borrowed successfully!");
        } else {
            System.out.println("You cannot borrow more books.");
        }
    }

    private static void addMagazine() {
        System.out.print("Enter the title of the magazine: ");
        String magazine = scanner.nextLine();
        currentUser.addMagazine(magazine);
        System.out.println("Magazine added successfully!");
    }
}



class User {
        private String username;
        private String password;
        private String plan;    
        private List<String> booksBorrowed;
        private List<String> magazinesAdded;
        private int daysForLoan;
        private String deliveryTime; // AM or PM (for premium)
        private String pickupBranch; // Branch for pickup (for base)
        private String shippingAddress; // Shipping address (for premium)
        private int pickupInHours; // 12 or 24 hours to pickup (for base)



    // Método para cambiar el tipo de plan si no es premium
public void changePlan(String newPlan) {
    if (!"premium".equals(this.plan)) {
        this.plan = newPlan;
        System.out.println("Plan updated to: " + newPlan);
    } else {
        System.out.println("Change not allowed for premium users.");
    }
}

// Método para aplicar un cupón si el plan es premium
public void applyCoupon() {
    if ("premium".equals(this.plan)) {
        this.daysForLoan += 15; // Suponiendo que daysForLoan ya está inicializado
        System.out.println("15 additional days have been added to your loan period.");
    } else {
        System.out.println("Coupons can only be applied to premium plans.");
    }
}

// Método para cambiar la contraseña
public void changePassword(String oldPassword, String newPassword) {
    if (this.password.equals(oldPassword)) {
        this.password = newPassword;
        System.out.println("Password successfully changed.");
    } else {
        System.out.println("Old password is incorrect.");
    }
}

    public void setDaysForLoan(int days) {
        this.daysForLoan = days;
    }

    public void setDeliveryTime(String time) {
        this.deliveryTime = time;
    }

    public void setPickupBranch(String branch) {
        this.pickupBranch = branch;
    }

    public void setShippingAddress(String address) {
        this.shippingAddress = address;
    }

    public void setPickupInHours(int hours) {
        this.pickupInHours = hours;
    }

    public User(String username, String password, String plan) {
        this.username = username;
        this.password = password;
        this.plan = plan;
        this.booksBorrowed = new ArrayList<>();
        this.magazinesAdded = new ArrayList<>();
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public boolean borrowBook(String book) {
        if ("base".equals(this.plan) && this.booksBorrowed.size() < 3 || "premium".equals(this.plan) && this.booksBorrowed.size() < 5) {
            this.booksBorrowed.add(book);
            return true;
        }
        return false;
    }

    public void addMagazine(String magazine) {
        this.magazinesAdded.add(magazine);
    }

    public void emptyLists() {
        this.booksBorrowed.clear();
        this.magazinesAdded.clear();
    }

    @Override
    public String toString() {
        return "User: " + this.username + ", Plan: " + this.plan + ", Books: " + this.booksBorrowed + ", Magazines: " + this.magazinesAdded;
    }
}

class LibrarySystem {
    private Map<String, User> users;

    public LibrarySystem() {
        this.users = new HashMap<>();
       // loadData(); // Cargar datos al iniciar el sistema
    }

    public void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("libraryData.bin"))) {
            out.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  //  public void loadData() {
       // try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("libraryData.bin"))) {
          //  users = (HashMap<String, User>) in.readObject();
      //  } catch (FileNotFoundException e) {
       //     System.out.println("No previous data found, starting fresh.");
       // } catch (IOException | ClassNotFoundException e) {
         //   e.printStackTrace();
       // }
   // }

    public boolean registerUser(String username, String password, String plan) {
        if (this.users.containsKey(username)) {
            return false;
        }
        this.users.put(username, new User(username, password, plan));
        return true;
    }

    public User login(String username, String password) {
        User user = this.users.get(username);
        if (user != null && user.checkPassword(password)) {
            return user;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (User user : this.users.values()) {
            sb.append(user.toString()).append("\n");
        }
        return sb.toString();
    }
}

