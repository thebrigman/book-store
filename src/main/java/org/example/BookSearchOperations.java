package org.example;

import java.util.Scanner;

/**
 * Handles user search operations for the Book Inventory system.
 * Provides options to search books by ID, title, author, or location.
 */
public class BookSearchOperations {

    /**
     * Main method that asks the user which type of search to perform
     * and delegates the search to the appropriate helper method.
     *
     * @param scnr the Scanner object used to read user input
     */
    public static void searchBooks(Scanner scnr) {
        System.out.println("Search by id, title, author, or location?");
        String input = scnr.nextLine().toLowerCase().trim();

        // Validate user input
        while (!(input.equalsIgnoreCase("id") || input.equalsIgnoreCase("title")
                || input.equalsIgnoreCase("location") || input.equalsIgnoreCase("author"))) {
            System.out.println("'" + input + "' is invalid.");
            System.out.println("Search by id, title, author, or location?");
            input = scnr.nextLine().toLowerCase();
        }

        // Dispatch to the correct search method
        switch (input.toLowerCase()) {
            case "id":
                searchById(scnr);
                break;
            case "location":
                searchByLocation(scnr);
                break;
            case "author":
                searchByAuthor(scnr);
                break;
            case "title":
                searchByTitle(scnr);
                break;
            default:
                // Should never happen due to input validation
        }
    }

    /**
     * Searches for books that match the given title (case-insensitive).
     *
     * @param scnr the Scanner object used to read user input
     */
    private static void searchByTitle(Scanner scnr) {
        System.out.println("Enter title:");
        String theTitle = scnr.nextLine().trim();

        System.out.println("Finding books titled '" + theTitle + "'...");
        int count = 0;

        for (Book book : BookInventoryOperations.getBooks()) {
            if (book.getTitle().toLowerCase().contains(theTitle.toLowerCase())) {
                System.out.println("👉 " + book);
                count++;
            }
        }

        // Summary of results
        if (count > 0) {
            System.out.println("✅ Found " + count + " matching book(s).");
        } else {
            System.out.println("⚠️ No matching books found.");
        }
    }


    /**
     * Searches for books that match the given author name (case-insensitive).
     *
     * @param scnr the Scanner object used to read user input
     */
    private static void searchByAuthor(Scanner scnr) {
        System.out.println("Enter author:");
        String theAuthor = scnr.nextLine().trim();

        System.out.println("Finding books by '" + theAuthor + "'...");
        int count = 0;

        for (Book book : BookInventoryOperations.getBooks()) {
            if (book.getAuthor().toLowerCase().contains(theAuthor.toLowerCase())) {
                System.out.println("👉 " + book);
                count++;
            }
        }

        if (count > 0) {
            System.out.println("✅ Found " + count + " matching book(s).");
        } else {
            System.out.println("⚠️ No matching books found.");
        }
    }


    /**
     * Searches for books located in the specified location (case-insensitive).
     * If no books are found, displays a "Location not found" message.
     *
     * @param scnr the Scanner object used to read user input
     */
    private static void searchByLocation(Scanner scnr) {
        System.out.println("Enter location:");
        String theLocation = scnr.nextLine().trim();

        System.out.println("Finding books in the '" + theLocation + "' location...");
        int count = 0;

        for (Book book : BookInventoryOperations.getBooks()) {
            if (book.getLocation().toLowerCase().contains(theLocation.toLowerCase())) {
                System.out.println("👉 " + book);
                count++;
            }
        }

        if (count > 0) {
            System.out.println("✅ Found " + count + " matching book(s).");
        } else {
            System.out.println("⚠️ Location not found.");
        }
    }


    /**
     * Searches for a book by its exact ID.
     * If the book is not found, informs the user.
     *
     * @param scnr the Scanner object used to read user input
     */
    private static void searchById(Scanner scnr) {
        System.out.println("Enter id:");
        String id = scnr.nextLine().trim();

        Book bookFoundById = BookInventoryOperations.findById(id);

        if (bookFoundById == null) {
            System.out.println("⚠️ No book exists with that ID.");
        } else {
            System.out.println("👉 " + bookFoundById);
            System.out.println("✅ Found 1 matching book.");
        }
    }

}
