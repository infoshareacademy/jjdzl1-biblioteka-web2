package com.infoshare.logic.repository;

import com.infoshare.logic.domain.Basket;
import com.infoshare.logic.domain.Book;
import com.infoshare.logic.domain.Operation;
import com.infoshare.logic.domain.User;

import javax.ejb.Local;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Local
public interface OperationsRepositoryDao {
    List<Operation> AllOperationList(String typoOfOperations, String userId, LocalDate firstDate, LocalDate lastDate) throws SQLException, ClassNotFoundException;

    void addNewOperation(List<Basket> basket, User user);

    List<Operation> operationListBorrowByUser(int userId) throws SQLException, ClassNotFoundException;

    List<Operation> operationListReservationByUser(int userId) throws SQLException, ClassNotFoundException;

    void ReturnBook(int id, int bookId, LocalDate endDate);

    Operation getOperation(int id) throws SQLException, ClassNotFoundException;

    void addRestOperation(Operation operation);

    void deleteOperation(int id) throws SQLException, ClassNotFoundException;

    void addNewUserReservation(int bookId, int userId) throws SQLException, ClassNotFoundException;

    List<Operation> listOfReservationByUser(int userId);

    void borrowAfterReservation(Operation operation, Book book);
}
