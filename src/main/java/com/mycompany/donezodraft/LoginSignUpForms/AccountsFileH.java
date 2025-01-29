package com.mycompany.donezodraft.LoginSignUpForms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class AccountsFileH {
    static String delimiter = "a36f9a45416c"; // Delimiter for splitting fields in the file

    /**
     * Reads users from a file and returns them as an ArrayList.
     *
     * @param filePath The path to the file containing user data.
     * @return ArrayList of User objects.
     */
    public static ArrayList<User> funcReadUsersFromFile(String filePath) {
        ArrayList<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] row = line.split(delimiter);

                if (row.length < 3) {
                    System.err.println("Skipping line: Not enough rows -> " + line);
                    continue;
                }

                try {
                    String name = row[0];
                    String username = row[1];
                    String password = row[2];

                    users.add(new User(name, username, password)); // Create a new User object and add to the list
                } catch (Exception e) {
                    System.err.println("Skipping line due to error: " + line);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Writes a list of users to a file.
     *
     * @param filePath The path to the file where user data will be written.
     * @param users    The list of User objects to write to the file.
     */
    public static void funcWriteUsersToFile(String filePath, ArrayList<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : users) {
                writer.write(String.join(delimiter,
                    user.getName(),
                    user.getUsername(),
                    user.getPassword()
                ));
                writer.newLine(); // Add a newline to separate users
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log error
        }
    }

    /**
     * Adds a single user to the file.
     *
     * @param filePath The path to the file where the user will be added.
     * @param user     The User object to add to the file.
     */
    public static void funcAddUserToFile(String filePath, User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(String.join(delimiter,
                user.getName(),
                user.getUsername(),
                user.getPassword()
            ));
            writer.newLine(); // Add a newline to separate users
            System.out.println("User added successfully.");
        } catch (Exception e) {
            e.printStackTrace(); // Log error
            System.out.println("Error adding!");
        }
    }

    /**
     * Clears the contents of a file.
     *
     * @param filePath The path to the file to clear.
     */
    public static void funcClearFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(""); // Clear file
            System.out.println("File cleared successfully.");
        } catch (Exception e) {
            e.printStackTrace(); // Log error
        }
    }
}