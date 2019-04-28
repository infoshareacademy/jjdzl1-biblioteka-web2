package com.infoshare.repository;

import com.infoshare.logic.domain.Basket;
import com.infoshare.logic.domain.Operation;
import com.infoshare.logic.domain.User;

import javax.ejb.Local;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Local
public interface OperationsRepositoryDao {
//    List<Operation> operationListByUserId(int userId) throws SQLException, ClassNotFoundException;
    List<Operation> operationListBookId(int booperationListByUserIdokId) throws SQLException, ClassNotFoundException;
    List<Operation> AllOperationList(String typoOfOperations, String userId) throws SQLException, ClassNotFoundException;
    void addNewOperation(List<Basket> basket, User user);
    List<Operation> operationListBorrowByUser(int userId) throws SQLException, ClassNotFoundException;
    void ReturnBook(int id, int bookId, LocalDate endDate);
}
