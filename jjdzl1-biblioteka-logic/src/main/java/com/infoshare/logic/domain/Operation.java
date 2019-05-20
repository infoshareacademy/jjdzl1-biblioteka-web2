package com.infoshare.logic.domain;

import lombok.Builder;
import lombok.Data;

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

    @ManyToOne
    private User user;

    @Column
    private String userName;

    @ManyToOne
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

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getUserName() {
        return userName;
    }

    public Book getBook() {
        return book;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }
}
