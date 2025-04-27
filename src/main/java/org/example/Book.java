package org.example;

import com.opencsv.bean.CsvBindByName;

/**
 * Represents a Book entity for the inventory system.
 * Each Book has an ID, title, author, and location.
 *
 * Mapped for CSV operations using OpenCSV annotations.
 */
public class Book {

    @CsvBindByName
    private String id;

    @CsvBindByName
    private String title;

    @CsvBindByName
    private String author;

    @CsvBindByName
    private String location;

    /**
     * Default no-argument constructor required for OpenCSV to instantiate objects.
     */
    public Book() {
    }

    /**
     * Creates a new Book with the specified ID, title, location, and author.
     *
     * @param id       the unique ID of the book
     * @param title    the title of the book
     * @param location the physical location of the book (e.g., store section)
     * @param author   the author of the book
     */
    public Book(String id, String title, String location, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.location = location;
    }

    /**
     * Returns the unique ID of the book.
     *
     * @return the book ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the title of the book.
     *
     * @return the book title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the location of the book.
     *
     * @return the book location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Returns the author of the book.
     *
     * @return the book author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns a string representation of the Book object.
     *
     * @return a string containing the book's ID, title, author, and location
     */
    @Override
    public String toString() {
        return "Book [ID=" + id +
                ", Title=\"" + title + "\"" +
                ", Author=\"" + author + "\"" +
                ", Location=\"" + location + "\"]";
    }

}
