package com.infoshare.logic.repository;

import com.infoshare.logic.domain.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class OperationsRepositoryDaoBeen implements OperationsRepositoryDao {

    @EJB
    private BooksRepositoryDao booksRepository;

    @EJB
    private UsersRepositoryDao usersRepository;

    @PersistenceContext(name = "librarydb")
    private EntityManager entityManager;

    @Override
    public List<Operation> AllOperationList(String operationType, String userId, LocalDate firstDate, LocalDate lastDate) throws SQLException, ClassNotFoundException {

        String query = "select o from Operation o " +
                "inner join User u on o.user.id=u.id " +
                "inner join Book b on o.book.id=b.id ";

        if (operationType.equals("reservation")) {
            query += "where o.operationType='RESERVATION'";
        } else if (operationType.equals("borrow")) {
            query += "where o.operationType='BORROW'";
        }

        if (userId != null) query += " and o.user.id=" + userId;

        if (firstDate != null && lastDate != null) {
            query += " and o.startDate between '" + firstDate + "' and '" + lastDate + "'";
        }


        TypedQuery<Operation> operationResult = entityManager.createQuery(query, Operation.class);
        List<Operation> operationsList = operationResult.getResultList();

        return operationsList;
    }

    @Override
    public Operation getOperation(int id) throws SQLException, ClassNotFoundException {

        String query = "select o from Operation o " +
                "inner join User u on o.user.id=u.id " +
                "inner join Book b on o.book.id=b.id " +
                "and o.id=" + id;

        TypedQuery<Operation> operationResult = entityManager.createQuery(query, Operation.class);
        List<Operation> operationsList = operationResult.getResultList();

        if (operationsList.isEmpty()) {
            return null;
        } else {
            return operationsList.get(0);
        }
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
                    .book(basketItem.getBook())
                    .user(basketItem.getUser())
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

    public void addRestOperation(Operation operation) {
        entityManager.persist(operation);
    }

    @Override
    public void deleteOperation(int id) throws SQLException, ClassNotFoundException {
        Operation operation = getOperation(id);
        if (operation != null) {
            entityManager.remove(operation);
            Book book = booksRepository.getBookById(operation.getBook().getId());
            book.setStatus(BookStatus.Dostępna);
            booksRepository.editBook(book);
        }
    }

    @Override
    public void addNewUserReservation(int bookId, int userId) throws SQLException, ClassNotFoundException {
        Book book = booksRepository.getBookById(bookId);
        User user = usersRepository.getUserById(userId);
        LocalDate currentDate = LocalDate.now();

        Operation operation = Operation.builder()
                .author(book.getAuthorLastName() + ", " + book.getAuthorFirstName())
                .bookTitle(book.getTitle())
                .book(book)
                .user(user)
                .userName(user.getLastName() + ", " + user.getFirstName())
                .operationType(OperationType.RESERVATION)
                .operationDate(currentDate)
                .startDate(currentDate)
                .endDate(currentDate.plusDays(3))
                .build();

        book.setStatus(BookStatus.Zarezerwowana);
        entityManager.persist(operation);
        entityManager.merge(book);
    }

    @Override
    public List<Operation> operationListBorrowByUser(int userId) throws SQLException, ClassNotFoundException {

        String endDate = "1970.01.01";
        String query = "select o from Operation o " +
                "inner join User u on o.user.id=u.id " +
                "inner join Book b on o.book.id=b.id " +
                "where " +
                "u.id=" + userId + " and " +
                "o.endDate='" + endDate + "'";

        TypedQuery<Operation> borrowedBook = entityManager.createQuery(query, Operation.class);
        List<Operation> operationBorrowedByUserList = borrowedBook.getResultList();

        return operationBorrowedByUserList;
    }

    @Override
    public List<Operation> operationListReservationByUser(int userId) throws SQLException, ClassNotFoundException {

        String query = "select o from Operation o " +
                "inner join User u on o.user.id=u.id " +
                "inner join Book b on o.book.id=b.id " +
                "where " +
                "u.id=" + userId + " and " +
                "o.operationType='RESERVATION'";

        TypedQuery<Operation> borrowedBook = entityManager.createQuery(query, Operation.class);
        List<Operation> operationReservationByUserList = borrowedBook.getResultList();

        return operationReservationByUserList;
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

    public List<Operation> listOfReservationByUser(int userId) {

        LocalDate currentDate = LocalDate.now();

        String query = "select o from Operation o " +
                "where o.endDate>=" + currentDate + " " +
                "and o.operationType='RESERVATION' " +
                "and o.user.id=" + userId;

        TypedQuery<Operation> userReservation = entityManager.createQuery(query, Operation.class);
        List<Operation> listOfUserReservation = userReservation.getResultList();
        return listOfUserReservation;
    }

    public void borrowAfterReservation(Operation operation, Book book) {

        entityManager.merge(operation);
        entityManager.merge(book);

    }

    public void restoreAvailableBookStatus() {
        List<Operation> expiredReservation = new ArrayList<>();

        String query = "select o from Operation o where o.endDate < current_date and operationType='RESERVATION'";

        TypedQuery<Operation> expiredReservationQuery = entityManager.createQuery(query, Operation.class);
        expiredReservation = expiredReservationQuery.getResultList();

        for (Operation operation : expiredReservation) {
            operation.getBook().setStatus(BookStatus.Dostępna);

        }

    }

    public void removeReservationFromDatabase() {

        List<Operation> oldReservation = new ArrayList<>();
        LocalDate now = LocalDate.now().minusDays(3);

        String query = "select o from Operation o where o.endDate < " + now + " and o.operationType='RESERVATION'";

        TypedQuery<Operation> oldReservationQuery = entityManager.createQuery(query, Operation.class);
        oldReservation = oldReservationQuery.getResultList();
        if (!oldReservation.isEmpty()) {

            for (Operation operation : oldReservation) {
                entityManager.remove(operation);
            }
        }
    }


}
