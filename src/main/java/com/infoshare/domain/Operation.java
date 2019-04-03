package com.infoshare.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Operation {
    private int id;
    private int userId;
    private String userName;
    private int bookId;
    private String bookTitle;
    private String author;
    private LocalDate operationDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private OperationType operationType;

    public Operation( int id, int userId, String userName, int bookId, String bookTitle,
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

    public Operation( int userId, String userName, int bookId, String bookTitle,
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
