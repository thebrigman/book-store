package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class BookInventoryOperationsTest {

    private static final String TEST_FILE_PATH = "src/test/resources/test_books.csv";

    @BeforeEach
    void setUp() throws IOException {
        // Make sure we start fresh each test
        BookInventoryOperations.getBooks().clear();

        // Prepare test CSV file
        Files.createDirectories(Paths.get("src/test/resources"));
        Files.write(Paths.get(TEST_FILE_PATH),
                List.of("id,title,location,author"),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    @AfterEach
    void tearDown() throws IOException {
        // Clean up test CSV
        Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
    }

    @Test
    void testAddAndViewBooks() {
        Book book = new Book("1", "Java Basics", "Springfield", "John Doe");
        BookInventoryOperations.add(book);

        List<Book> books = BookInventoryOperations.getBooks();
        assertEquals(1, books.size());
        assertEquals("Java Basics", books.get(0).getTitle());
    }

    @Test
    void testFindById() {
        Book book = new Book("2", "Advanced Java", "Hill Valley", "Jane Smith");
        BookInventoryOperations.getBooks().add(book);

        Book found = BookInventoryOperations.findById("2");
        assertNotNull(found);
        assertEquals("Advanced Java", found.getTitle());
    }

    @Test
    void testGetNextBookId() {
        BookInventoryOperations.getBooks().add(new Book("5", "Some Book", "Location", "Author"));
        BookInventoryOperations.getBooks().add(new Book("10", "Another Book", "Location", "Author"));

        String nextId = BookInventoryOperations.getNextBookId();
        assertEquals("11", nextId);
    }

    @Test
    void testRemove() {
        Book book = new Book("3", "Delete Me", "Quahog", "Remover");
        BookInventoryOperations.getBooks().add(book);

        boolean removed = BookInventoryOperations.remove("3");
        assertTrue(removed);
        assertTrue(BookInventoryOperations.getBooks().isEmpty());
    }

    @Test
    void testSaveAndLoadBooks() throws IOException {
        Book book1 = new Book("1", "SaveBook1", "Location1", "Author1");
        Book book2 = new Book("2", "SaveBook2", "Location2", "Author2");
        BookInventoryOperations.getBooks().addAll(List.of(book1, book2));

        BookInventoryOperations.saveBooks();
        BookInventoryOperations.getBooks().clear(); // Clear memory
        BookInventoryOperations.loadBooksFromCsv(); // Reload from file

        List<Book> loadedBooks = BookInventoryOperations.getBooks();
        assertEquals(2, loadedBooks.size());
        assertEquals("SaveBook1", loadedBooks.get(0).getTitle());
        assertEquals("SaveBook2", loadedBooks.get(1).getTitle());
    }

}