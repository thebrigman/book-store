package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class BookInventoryManagerTest {
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream)); // Redirect System.out to capture outputs
        BookInventoryOperations.getBooks().clear();   // Start clean
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut); // Restore normal System.out
    }

    @Test
    void testRunProgram_ViewAndExit() {
        // Simulate user typing 'view' then 'exit'
        String input = "view\nexit\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        BookInventoryManager.runProgram(scanner);

        String output = outputStream.toString();
        assertTrue(output.contains("Options:"));
        assertTrue(output.contains("'view'   - View all books"));
        assertTrue(output.contains("Goodbye!"));
    }

    @Test
    void testAddBookAndExit() {
        // Simulate adding a book, then exiting
        String input = "add\nTest Book Title\nTest Book Author\nTest Location\nexit\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        BookInventoryManager.runProgram(scanner);

        String output = outputStream.toString();
        assertTrue(output.contains("Enter the title of the book:"));
        assertTrue(output.contains("✅ Added:"));
        assertTrue(output.contains("Goodbye!"));

        // Verify that a book was added
        assertEquals(1, BookInventoryOperations.getBooks().size());
        Book addedBook = BookInventoryOperations.getBooks().get(0);
        assertEquals("Test Book Title", addedBook.getTitle());
        assertEquals("Test Book Author", addedBook.getAuthor());
        assertEquals("Test Location", addedBook.getLocation());
    }

    @Test
    void testRemoveBook() {
        // Preload a book
        Book book = new Book("1", "Delete Me", "Springfield", "John Doe");
        BookInventoryOperations.getBooks().add(book);

        // Simulate removing that book and exiting
        String input = "remove\n1\nexit\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        BookInventoryManager.runProgram(scanner);

        String output = outputStream.toString();
        assertTrue(output.contains("✅ Book with ID 1 removed."));
        assertTrue(output.contains("Goodbye!"));
        assertEquals(0, BookInventoryOperations.getBooks().size());
    }

    @Test
    void testInvalidCommandThenExit() {
        // Simulate wrong command 'foo', then 'exit'
        String input = "foo\nexit\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        BookInventoryManager.runProgram(scanner);

        String output = outputStream.toString();
        assertTrue(output.contains("'foo' is invalid."));
        assertTrue(output.contains("Options:")); // Printed again after invalid input
        assertTrue(output.contains("Goodbye!"));
    }
}