package com.infoshare.logic.repository;

import com.infoshare.logic.domain.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class OperationsRepositoryDaoBeen implements OperationsRepositoryDao {

    @PersistenceContext(name = "librarydb")
    private EntityManager entityManager;

    @Override
    public List<Operation> operationListBookId(int bookId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Operation> AllOperationList(String operationType, String userId) throws SQLException, ClassNotFoundException {

        String query = "select o from Operation o " +
                "inner join User u on o.userId=u.id " +
                "inner join Book b on o.bookId=b.id ";

        if (operationType.equals("reservation")) {
            query += "where o.operationType='RESERVATION'";
        } else if (operationType.equals("borrow")) {
            query += "where o.operationType='BORROW'";
        }

        if (userId != null) query += " and o.userId=" + userId;

        TypedQuery<Operation> operationResult = entityManager.createQuery(query, Operation.class);
        List<Operation> operationsList = operationResult.getResultList();

        return operationsList;
    }

    @Override
    public void addNewOperation(List<Basket> basket, User user) {

        LocalDate currentDate = LocalDate.now();

        int id;
        String querySelectBook = "";

        for (Basket basketItem : basket) {
            BookStatus status = BookStatus.Wypożyczona;

            if (basketItem.getOperationType().equals(OperationType.RESERVATION)) {
                status = BookStatus.Zarezerwowana;
            }
            id = basketItem.getBook().getId();
            querySelectBook = "select u from Book u where u.id=" + id;
            TypedQuery<Book> book = entityManager.createQuery(querySelectBook, Book.class);
            Book selectedBook = book.getSingleResult();
            selectedBook.setStatus(status);

            Operation operation = Operation.builder()
                    .author(basketItem.getBook().getAuthorLastName() + ", " + basketItem.getBook().getAuthorFirstName())
                    .bookTitle(basketItem.getBook().getTitle())
                    .bookId(basketItem.getBook().getId())
                    .userId(basketItem.getUser().getId())
                    .userName(basketItem.getUser().getLastName() + ", " + basketItem.getUser().getFirstName())
                    .operationType(basketItem.getOperationType())
                    .operationDate(currentDate)
                    .startDate(basketItem.getStartDate())
                    .endDate(basketItem.getEndDate())
                    .build();

            entityManager.persist(operation);
            entityManager.merge(selectedBook);
        }
    }

    @Override
    public List<Operation> operationListBorrowByUser(int userId) throws SQLException, ClassNotFoundException {

        String endDate = "1970.01.01";
        String query = "select o from Operation o " +
                "inner join User u on o.userId=u.id " +
                "inner join Book b on o.bookId=b.id " +
                "where " +
                "u.id=" + userId + " and " +
                "o.endDate='" + endDate + "'";

        TypedQuery<Operation> borrowedBook = entityManager.createQuery(query, Operation.class);
        List<Operation> operationBorrowedByUserList = borrowedBook.getResultList();

        return operationBorrowedByUserList;
    }

    public void ReturnBook(int id, int bookId, LocalDate endDate) {

        String operationQuery = "select o from Operation o where o.id=" + id;
        String bookQuery = "select b from Book b where b.id=" + bookId;

        TypedQuery<Operation> operationResult = entityManager.createQuery(operationQuery, Operation.class);
        Operation operation = operationResult.getSingleResult();

        TypedQuery<Book> bookResult = entityManager.createQuery(bookQuery, Book.class);
        Book book = bookResult.getSingleResult();

        operation.setEndDate(endDate);
        book.setStatus(BookStatus.Dostępna);

        entityManager.merge(operation);
        entityManager.merge(book);
    }
}