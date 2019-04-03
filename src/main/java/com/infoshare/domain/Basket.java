package com.infoshare.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Basket {
    private Book book;
    private User user;
    private OperationType operationType;
    private LocalDate startDate;
    private LocalDate endDate;

    public Basket(Book book, User user, OperationType operationType, LocalDate startDate, LocalDate endDate) {
        this.book = book;
        this.user = user;
        this.operationType = operationType;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
