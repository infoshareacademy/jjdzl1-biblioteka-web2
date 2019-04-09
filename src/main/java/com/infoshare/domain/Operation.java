package com.infoshare.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data

@Entity
@Table(name = "operations")
public class Operation implements Serializable
{
    private static final long serialVersionUID = -2122250031742176764L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int userId;

    @Column
    private String userName;

    @Column
    private int bookId;

    @Transient
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

    public Operation(int id, int userId, String userName, int bookId, String bookTitle,
                     String author, LocalDate operationDate, LocalDate startDate,
                     LocalDate endDate, OperationType operationType) {

        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.author = author;
        this.operationDate = operationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.operationType = operationType;
    }

    public Operation(int userId, String userName, int bookId, String bookTitle,
                     String author, LocalDate operationDate, LocalDate startDate,
                     LocalDate endDate, OperationType operationType) {

        this.userId = userId;
        this.userName = userName;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.author = author;
        this.operationDate = operationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.operationType = operationType;
    }

}
