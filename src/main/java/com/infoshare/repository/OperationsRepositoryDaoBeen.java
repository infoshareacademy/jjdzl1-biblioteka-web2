package com.infoshare.repository;

import com.infoshare.domain.*;
import com.infoshare.query.OperationsQuery;
import com.infoshare.utils.DateUtil;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Stateless
public class OperationsRepositoryDaoBeen implements OperationsRepositoryDao {

    @PersistenceContext(name = "librarydb")
    private EntityManager entityManager;

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
    public void addNewOperation(List<Basket> basket, User user) {

        LocalDate currentDate = LocalDate.now();
       // Date endOfReservationDate = Date.valueOf(DateUtil.currentPlusThreeDays());
       // Date emptyDate = Date.valueOf(DateUtil.emptyDate());

        //Date endDate = emptyDate;
       // int operationTypeId = 1;
        String status = "Wypożyczona";

        for (Basket basketItem : basket) {

            if (basketItem.getOperationType() == OperationType.RESERVATION) {
//                endDate = endOfReservationDate;
//                operationTypeId = 0;
                status = "Zarezerwowana";
            }

            Operation operation = Operation.builder()
                    .author(basketItem.getBook().getAuthorLastName() + ", " + basketItem.getBook().getAuthorFirstName())
                    .bookId(basketItem.getBook().getId())
                    .userId(basketItem.getUser().getId())
                    .userName(basketItem.getUser().getLogin())
                    .operationType(basketItem.getOperationType())
                    .operationDate(currentDate)
                    .startDate(basketItem.getStartDate())
                    .endDate(basketItem.getEndDate())
                    .build();

            entityManager.persist(operation);

        }
    }


    @Override
    public List<Operation> operationListBorrowByUser(int userId) throws SQLException, ClassNotFoundException {

        List<Operation> operationBorrowedByUserList = new ArrayList<>();
        OperationType operationType;

        try (ResultSet rs = OperationsQuery.listOfBorrowedBookByUserId(userId)) {

            while (rs.next()) {
                int id = rs.getInt("id");
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