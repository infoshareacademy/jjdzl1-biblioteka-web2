package com.infoshare.logic.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Builder
@Entity
@Table(name = "message")
public class Message implements Serializable {

    private static final long serialVersionUID = 8753363123127615928L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int dayOfBorrowDelay;

    @Column
    private String message;

    @Column
    private BigDecimal payForBorrow;

    @ManyToOne
    private Operation operation;

    public Message() {

        // for JPA
    }

    public Message(int id, int dayOfBorrowDelay, String message, BigDecimal payForBorrow, Operation operation) {
        this.dayOfBorrowDelay = dayOfBorrowDelay;
        this.message = message;
        this.payForBorrow = payForBorrow;
        this.operation = operation;
    }

    public Message(int dayOfBorrowDelay, String message, BigDecimal payForBorrow, Operation operation) {
        this.dayOfBorrowDelay = dayOfBorrowDelay;
        this.message = message;
        this.payForBorrow = payForBorrow;
        this.operation = operation;
    }

    public int getId() {
        return id;
    }

    public int getDayOfBorrowDelay() {
        return dayOfBorrowDelay;
    }

    public String getMessage() {
        return message;
    }

    public BigDecimal getPayForBorrow() {
        return payForBorrow;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setDayOfBorrowDelay(int dayOfBorrowDelay) {
        this.dayOfBorrowDelay = dayOfBorrowDelay;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPayForBorrow(BigDecimal payForBorrow) {
        this.payForBorrow = payForBorrow;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

}
