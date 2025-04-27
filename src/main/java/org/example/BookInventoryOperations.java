package org.example;

import com.opencsv.CSVWriter;
import com.opencsv.bean.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all operations related to managing the book inventory,
 * including loading from CSV, saving, viewing, adding, removing,
 * and searching books.
 */
public class BookInventoryOperations {
    private static List<Book> books = new ArrayList<>();
    private static final String FILE_PATH = "src/main/resources/books_1000.csv";

    /**
     * Displays all books currently loaded in the inventory.
     */
    public static void viewBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    /**
     * Returns the list of all books in the inventory.
     *
     * @return a list of Book objects
     */
    public static List<Book> getBooks() {
        return books;
    }

    /**
     * Finds a book by its ID.
     *
     * @param id the ID of the book to search for
     * @return the Book if found, otherwise null
     */
    public static Book findById(String id) {
        for (Book book : books) {
            if (id.equals(book.getId())) {
                return book;
            }
        }
        return null;
    }

    /**
     * Saves all books to the CSV file.
     * Overwrites the entire CSV file, including writing a header row.
     */
    public static void saveBooks() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            ColumnPositionMappingStrategy<Book> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Book.class);
            strategy.setColumnMapping("id", "title", "location", "author");

            // Write header manually
            writer.write("id,title,location,author\n");

            StatefulBeanToCsv<Book> beanToCsv = new StatefulBeanToCsvBuilder<Book>(writer)
                    .withApplyQuotesToAll(false)
                    .withOrderedResults(true)
                    .withMappingStrategy(strategy)
                    .withSeparator(',')
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();

            beanToCsv.write(books);
            System.out.println("✅ Saved " + books.size() + " books to CSV.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new book to the inventory and appends it to the CSV file.
     *
     * @param book the Book object to add
     */
    public static void add(Book book) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            ColumnPositionMappingStrategy<Book> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Book.class);
            strategy.setColumnMapping("id", "title", "location", "author");

            StatefulBeanToCsv<Book> beanToCsv = new StatefulBeanToCsvBuilder<Book>(writer)
                    .withApplyQuotesToAll(false)
                    .withOrderedResults(true)
                    .withMappingStrategy(strategy)
                    .withSeparator(',')
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();

            // Append book to the CSV
            beanToCsv.write(List.of(book));
            // Also add to in-memory list
            books.add(book);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculates and returns the next available book ID.
     * Finds the maximum ID currently in use and increments it.
     *
     * @return the next available ID as a String
     */
    public static String getNextBookId() {
        int maxId = 0;
        for (Book book : books) {
            try {
                int id = Integer.parseInt(book.getId());
                if (id > maxId) {
                    maxId = id;
                }
            } catch (NumberFormatException e) {
                // Skip any invalid (non-integer) ID values
            }
        }
        return String.valueOf(maxId + 1);
    }

    /**
     * Removes a book from the inventory based on its ID.
     *
     * @param id the ID of the book to remove
     * @return true if a book was removed; false if no book with the ID was found
     */
    public static boolean remove(String id) {
        return books.removeIf(book -> book.getId().equals(id));
    }

    /**
     * Loads all books from the CSV file into the in-memory list.
     * This method should be called once at the start of the program.
     */
    public static void loadBooksFromCsv() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            CsvToBean<Book> csvToBean = new CsvToBeanBuilder<Book>(reader)
                    .withType(Book.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            books = csvToBean.parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

