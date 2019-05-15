package com.infoshare.logic.domain;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@Entity
@Table(name = "operation")
public class Operation implements Serializable {

    private static final long serialVersionUID = -2122250031742176764L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private User user;

    @Column
    private String userName;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Book book;

    @Column
    private String bookTitle;

    @Column
    private String author;

    @Column
    private LocalDate operationDate;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;


    public Operation() {
        //for JPA
    }

    public Operation(int id, User user, String userName, Book book, String bookTitle,
                     String author, LocalDate operationDate, LocalDate startDate,
                     LocalDate endDate, OperationType operationType) {

        this.id = id;
        this.user = user;
        this.userName = userName;
        this.book = book;
        this.bookTitle = bookTitle;
        this.author = author;
        this.operationDate = operationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.operationType = operationType;
    }

    public Operation(User user, String userName, Book book, String bookTitle,
                     String author, LocalDate operationDate, LocalDate startDate,
                     LocalDate endDate, OperationType operationType) {

        this.user = user;
        this.userName = userName;
        this.book = book;
        this.bookTitle = bookTitle;
        this.author = author;
        this.operationDate = operationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.operationType = operationType;
    }

}
