package com.infoshare.repository;

import com.infoshare.domain.Operation;
import com.infoshare.domain.OperationType;
import com.infoshare.domain.User;
import com.infoshare.query.OperationsQuery;

import javax.ejb.Stateless;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class OperationsRepositoryDaoBeen implements OperationsRepositoryDao {

    @Override
    public List<Operation> operationListByUserId(int userId) throws SQLException, ClassNotFoundException {

        List<Operation> operationsByUserId = new ArrayList<>();
        OperationType operationType;

        try (ResultSet rs = OperationsQuery.listOfOperationsByUserId(userId)) {

            while (rs.next()) {
                userId = rs.getInt("userId");
                String userName = rs.getString("lastName") + ", " + rs.getString("firstName");
                int bookID = rs.getInt("bookId");
                String bookTitle = rs.getString("title");
                String author = rs.getString("authorLastName") + ", " + rs.getString("authorFirstName");
                LocalDate operationDate = rs.getDate("operationDate").toLocalDate();
                LocalDate startDate = rs.getDate("startDate").toLocalDate();
                LocalDate endDate = rs.getDate("startDate").toLocalDate();
                int operationTypeId = rs.getInt("operationTypeId");
                if (operationTypeId == 0) operationType = OperationType.RESERVATION;
                else operationType = OperationType.BORROW;

                operationsByUserId.add(new Operation(userId, userName, bookID, bookTitle, author, operationDate, startDate, endDate, operationType));
            }
            rs.close();
            return operationsByUserId;
        }
    }


    @Override
    public List<Operation> operationListBookId(int bookId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Operation> AllOperationList(String typoOfOperations, Integer userId) throws SQLException, ClassNotFoundException {

        List<Operation> allOperationsList = new ArrayList<>();
        OperationType operationType;

        try (ResultSet rs = OperationsQuery.allOperations(typoOfOperations, userId)) {

            while (rs.next()) {
                userId = rs.getInt("userId");
                String userName = rs.getString("lastName") + ", " + rs.getString("firstName");
                int bookID = rs.getInt("bookId");
                String bookTitle = rs.getString("title");
                String author = rs.getString("authorLastName") + ", " + rs.getString("authorFirstName");
                LocalDate operationDate = rs.getDate("operationDate").toLocalDate();
                LocalDate startDate = rs.getDate("startDate").toLocalDate();
                LocalDate endDate = rs.getDate("endDate").toLocalDate();
                int operationTypeId = rs.getInt("operationTypeId");
                if (operationTypeId == 0) operationType = OperationType.RESERVATION;
                else operationType = OperationType.BORROW;

                allOperationsList.add(new Operation(userId, userName, bookID, bookTitle, author, operationDate, startDate, endDate, operationType));

            }
            rs.close();
            return allOperationsList;
        }
    }

    @Override
    public void addNewOperation(List basket, User user) {
        OperationsQuery.addNewOperation(basket, user);
    }


    @Override
    public List<Operation> operationListBorrowByUser(int userId) throws SQLException, ClassNotFoundException {

        List<Operation> operationBorrowedByUserList = new ArrayList<>();
        OperationType operationType;

        try (ResultSet rs = OperationsQuery.listOfBorrowedBookByUserId(userId)) {

            while (rs.next()) {
                int id= rs.getInt("id");
                userId = rs.getInt("userId");
                String userName = rs.getString("lastName") + ", " + rs.getString("firstName");
                int bookID = rs.getInt("bookId");
                String bookTitle = rs.getString("title");
                String author = rs.getString("authorLastName") + ", " + rs.getString("authorFirstName");
                LocalDate operationDate = rs.getDate("operationDate").toLocalDate();
                LocalDate startDate = rs.getDate("startDate").toLocalDate();
                LocalDate endDate = rs.getDate("endDate").toLocalDate();
                int operationTypeId = rs.getInt("operationTypeId");
                if (operationTypeId == 0) operationType = OperationType.RESERVATION;
                else operationType = OperationType.BORROW;

                operationBorrowedByUserList.add(new Operation(id, userId, userName, bookID, bookTitle, author, operationDate, startDate, endDate, operationType));

            }
            rs.close();
            return operationBorrowedByUserList;

        }
    }
}