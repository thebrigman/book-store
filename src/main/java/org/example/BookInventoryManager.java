package org.example;

import java.util.Scanner;

/**
 * Manages the console-based interactions for the Book Inventory application.
 * Handles running the program, navigating options, and coordinating book operations
 * like viewing, adding, searching, and removing books.
 */
public class BookInventoryManager {

    /**
     * Runs the main program loop, showing options to the user and handling their commands.
     *
     * @param scnr the Scanner object used to read user input from the console
     */
    public static void runProgram(Scanner scnr) {
        boolean isExit = false;

        while (!isExit) {
            printOptions();

            String input = scnr.nextLine().toLowerCase().trim();

            // Validate input
            while (!(input.equals("view") || input.equals("search")
                    || input.equals("add") || input.equals("remove")
                    || input.equals("exit"))) {

                System.out.println("'" + input + "' is invalid.");
                printOptions();
                input = scnr.nextLine().toLowerCase().trim();
            }

            // Handle valid user command
            switch (input) {
                case "view":
                    BookInventoryOperations.viewBooks();
                    break;
                case "search":
                    BookSearchOperations.searchBooks(scnr);
                    break;
                case "add":
                    addBook(scnr);
                    break;
                case "remove":
                    removeBook(scnr);
                    break;
                case "exit":
                    System.out.println("Goodbye!");
                    isExit = true;
                    break;
            }
            System.out.println("\n");
        }
    }

    /**
     * Prints the available options for the user to choose from.
     */
    private static void printOptions() {
        System.out.println("Options:\n" +
                "'view'   - View all books\n" +
                "'search' - Search for a book\n" +
                "'add'    - Add a new book\n" +
                "'remove' - Remove a book\n" +
                "'exit'   - Exit the program");
    }

    /**
     * Handles the logic for adding a new book based on user input.
     * Prompts the user for book details and saves the new book to the inventory.
     *
     * @param scnr the Scanner object used to read user input
     */
    private static void addBook(Scanner scnr) {
        String newId = BookInventoryOperations.getNextBookId(); // Safely generate next available ID

        System.out.println("Enter the title of the book:");
        String title = scnr.nextLine().trim();

        System.out.println("Enter the author of the book:");
        String author = scnr.nextLine().trim();

        System.out.println("Enter the location of the book:");
        String location = scnr.nextLine().trim();

        Book newBook = new Book(newId, title, location, author);
        BookInventoryOperations.add(newBook);
        BookInventoryOperations.saveBooks(); // Immediately save changes
        System.out.println("✅ Added: " + BookInventoryOperations.findById(newId));
    }

    /**
     * Handles the logic for removing a book from the inventory.
     * Prompts the user for a book ID and removes the corresponding book.
     *
     * @param scnr the Scanner object used to read user input
     */
    private static void removeBook(Scanner scnr) {
        System.out.println("Enter the ID of the book to remove:");
        String id = scnr.nextLine().trim();

        if(BookInventoryOperations.remove(id)) {
            BookInventoryOperations.saveBooks(); // Immediately save changes
            System.out.println("✅ Book with ID " + id + " removed.");
        }else {
            System.out.println("❌ No book found with ID " + id + ".");
        }

    }
}
