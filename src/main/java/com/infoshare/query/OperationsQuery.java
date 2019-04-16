package com.infoshare.query;

import com.infoshare.domain.Basket;
import com.infoshare.domain.OperationType;
import com.infoshare.domain.User;
import com.infoshare.utils.DateUtil;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static com.infoshare.dao.DBCon.preparedStatement;

public class OperationsQuery {

    String query = "SELECT * FROM `operation` " +
            "JOIN `user` ON operation.userId = user.id " +
            "JOIN book ON operation.bookId=book.id " +
            "WHERE user.id = 10";


    public static ResultSet listOfOperationsByUserId(int userID) throws SQLException, ClassNotFoundException {

        String query = "SELECT * FROM operation WHERE userID = " + userID;
        return preparedStatement(query).executeQuery();
    }

    public static ResultSet allOperations(String operationType, Integer userId) throws SQLException, ClassNotFoundException {


        String query = "SELECT * FROM `operation` " +
                "JOIN `user` ON operation.userId = user.id " +
                "JOIN book ON operation.bookId=book.id ";

        String userQuery = "";
        if (userId != null && userId != 0) {
            userQuery = " AND operation.userId =" + userId;
        }

        if (operationType.equals("reservation")) query += "WHERE operationTypeId = 0" + userQuery;
        else if (operationType.equals("borrow")) query += "WHERE operationTypeId = 1" + userQuery;
        else query += "WHERE 1";

        return preparedStatement(query).executeQuery();
    }

    public static ResultSet listOfBorrowedBookByUserId(int userID) throws SQLException, ClassNotFoundException {
        String endDate = "1970.01.01";
        String query = "SELECT * FROM operation " +
                "JOIN `user` ON operation.userId = user.id " +
                "JOIN book ON operation.bookId=book.id " +
                "WHERE userID = " + userID + " " +
                "AND endDate = '" + endDate + "'";
        return preparedStatement(query).executeQuery();
    }

    public static void ReturnBook(int id, int bookId, LocalDate endDate) {
        String endDateString = endDate.toString();
        // UPDATE `librarydb`.`operations` SET `endDate`='1970-01-02' WHERE `id`='25';
        String query = "UPDATE operation SET endDate='" + endDateString + "' WHERE id=" + id;
        String statusAvailableQuery = "UPDATE book SET status='Dostępna' WHERE id=" + bookId;
        try {
            preparedStatement(query).execute();
            preparedStatement(statusAvailableQuery).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
