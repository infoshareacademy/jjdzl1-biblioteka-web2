package com.infoshare.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class Book {
    private int bookID;
    private String title;
    private String authorFirstName;
    private String authorLastName;
    private int relaseDate;
    private String isbn;
    private String description;
    private BookStatus status;

    public Book(int bookID, String title, String authorFirstName, String authorLastName, int relaseDate,
                String isbn, String description, BookStatus status) {
        this.bookID = bookID;
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.relaseDate = relaseDate;
        this.isbn = isbn;
        this.description = description;
        this.status = status;
    }

    public Book(int bookID, String title, String authorFirstName, String authorLastName, int relaseDate, String isbn, BookStatus status) {
        this.bookID = bookID;
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.relaseDate = relaseDate;
        this.isbn = isbn;
        this.status = status;
    }
}

