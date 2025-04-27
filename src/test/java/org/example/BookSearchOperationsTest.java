package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class BookSearchOperationsTest {
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream)); // Capture console output
        BookInventoryOperations.getBooks().clear();   // Start fresh
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut); // Restore normal System.out
    }

    @Test
    void testSearchById_Found() {
        Book book = new Book("1", "Java Programming", "Springfield", "John Doe");
        BookInventoryOperations.getBooks().add(book);

        String input = "id\n1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        BookSearchOperations.searchBooks(scanner);

        String output = outputStream.toString();
        assertTrue(output.contains("👉"));
        assertTrue(output.contains("Java Programming"));
        assertTrue(output.contains("✅ Found 1 matching book."));
    }

    @Test
    void testSearchById_NotFound() {
        String input = "id\n999\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        BookSearchOperations.searchBooks(scanner);

        String output = outputStream.toString();
        assertTrue(output.contains("⚠️ No book exists with that ID."));
    }

    @Test
    void testSearchByTitle_Found() {
        BookInventoryOperations.getBooks().add(new Book("2", "Effective Java", "Quahog", "Joshua Bloch"));

        String input = "title\nEffective\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        BookSearchOperations.searchBooks(scanner);

        String output = outputStream.toString();
        assertTrue(output.contains("👉"));
        assertTrue(output.contains("Effective Java"));
        assertTrue(output.contains("✅ Found 1 matching book(s)."));
    }

    @Test
    void testSearchByTitle_NotFound() {
        BookInventoryOperations.getBooks().add(new Book("5", "Unknown Title", "Location", "Author"));

        String input = "title\nNonexistent\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        BookSearchOperations.searchBooks(scanner);

        String output = outputStream.toString();
        assertTrue(output.contains("⚠️ No matching books found."));
    }

    @Test
    void testSearchByAuthor_Found() {
        BookInventoryOperations.getBooks().add(new Book("3", "Some Book", "Springfield", "Jane Doe"));

        String input = "author\nJane\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        BookSearchOperations.searchBooks(scanner);

        String output = outputStream.toString();
        assertTrue(output.contains("👉"));
        assertTrue(output.contains("Jane Doe"));
        assertTrue(output.contains("✅ Found 1 matching book(s)."));
    }

    @Test
    void testSearchByAuthor_NotFound() {
        BookInventoryOperations.getBooks().add(new Book("6", "Another Book", "Location", "Different Author"));

        String input = "author\nNoAuthor\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        BookSearchOperations.searchBooks(scanner);

        String output = outputStream.toString();
        assertTrue(output.contains("⚠️ No matching books found."));
    }

    @Test
    void testSearchByLocation_Found() {
        BookInventoryOperations.getBooks().add(new Book("4", "Another Book", "Hill Valley", "Author McAuthor"));

        String input = "location\nHill\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        BookSearchOperations.searchBooks(scanner);

        String output = outputStream.toString();
        assertTrue(output.contains("👉"));
        assertTrue(output.contains("Hill Valley"));
        assertTrue(output.contains("✅ Found 1 matching book(s)."));
    }

    @Test
    void testSearchByLocation_NotFound() {
        BookInventoryOperations.getBooks().add(new Book("7", "Some Book", "Atlantis", "Author"));

        String input = "location\nNowhere\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        BookSearchOperations.searchBooks(scanner);

        String output = outputStream.toString();
        assertTrue(output.contains("⚠️ Location not found."));
    }

    @Test
    void testInvalidSearchOption() {
        // Simulate invalid input first, then correct it
        BookInventoryOperations.getBooks().add(new Book("8", "Another Book", "Somewhere", "Someone"));

        String input = "foobar\ntitle\nAnother\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        BookSearchOperations.searchBooks(scanner);

        String output = outputStream.toString();
        assertTrue(output.contains("'foobar' is invalid."));
        assertTrue(output.contains("👉"));
        assertTrue(output.contains("Another Book"));
    }
}