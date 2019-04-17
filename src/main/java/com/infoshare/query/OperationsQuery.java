package com.infoshare.query;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.infoshare.dao.DBCon.preparedStatement;

public class OperationsQuery {

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
}
