package com.infoshare.repository;

import com.infoshare.domain.Basket;
import com.infoshare.domain.Operation;
import com.infoshare.domain.User;

import javax.ejb.Local;
import java.sql.SQLException;
import java.util.List;

@Local
public interface OperationsRepositoryDao {
    List<Operation> operationListByUserId(int userId) throws SQLException, ClassNotFoundException;
    List<Operation> operationListBookId(int bookId) throws SQLException, ClassNotFoundException;
    List<Operation> AllOperationList(String typoOfOperations, Integer userId) throws SQLException, ClassNotFoundException;
    void addNewOperation(List<Basket> basket, User user);
    List<Operation> operationListBorrowByUser(int userId) throws SQLException, ClassNotFoundException;
}
