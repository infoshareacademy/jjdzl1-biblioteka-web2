package com.infoshare.logic.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@Entity
@Table(name = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 5436987190449710420L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100)
    private String title;

    @Column(length = 40)
    private String authorFirstName;

    @Column(length = 40)
    private String authorLastName;

    @Column
    private int daterelease;

    @Column(length = 13)
    private String isbn;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    public Book() {
        // for JPA
    }

    public Book(int id, String title, String authorFirstName, String authorLastName, int daterelease,
                String isbn, String description, BookStatus status) {
        this.id = id;
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.daterelease = daterelease;
        this.isbn = isbn;
        this.description = description;
        this.status = status;
    }

    public Book(int id, String title, String authorFirstName, String authorLastName, int daterelease, String isbn, BookStatus status) {
        this.id = id;
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.daterelease = daterelease;
        this.isbn = isbn;
        this.status = status;
    }

}