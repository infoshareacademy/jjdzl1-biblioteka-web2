package com.infoshare.query;

import com.infoshare.dao.DBCon;
import com.infoshare.domain.Basket;
import com.infoshare.domain.Operation;
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

    String query = "SELECT * FROM `operations` " +
            "JOIN `user` ON operations.userId = user.id " +
            "JOIN book ON operations.bookId=book.id " +
            "WHERE user.id = 10";


    public static ResultSet listOfOperationsByUserId(int userID) throws SQLException, ClassNotFoundException {

        String query = "SELECT * FROM operations WHERE userID = " + userID;
        return preparedStatement(query).executeQuery();
    }

    public static ResultSet allOperations(String operationType, Integer userId) throws SQLException, ClassNotFoundException {


        String query = "SELECT * FROM `operations` " +
                "JOIN `user` ON operations.userId = user.id " +
                "JOIN book ON operations.bookId=book.id ";

        String userQuery = "";
        if (userId != null && userId != 0) {
            userQuery = " AND operations.userId =" + userId;
        }

        if (operationType.equals("reservation")) query += "WHERE operationTypeId = 0" + userQuery;
        else if (operationType.equals("borrow")) query += "WHERE operationTypeId = 1" + userQuery;
        else query += "WHERE 1";

        return preparedStatement(query).executeQuery();
    }

    public static void addNewOperation(List userBasket, User selectedUser) {

        Date currentDate = Date.valueOf(DateUtil.currentDate());
        Date endOfReservationDate = Date.valueOf(DateUtil.currentPlusThreeDays());
        Date emptyDate = Date.valueOf(DateUtil.emptyDate());

        Date endDate = emptyDate;
        int operationTypeId = 1;
        String status = "Wypożyczona";

        User user = selectedUser;
        List<Basket> basket = userBasket;
        for (Basket basketItem : basket) {

            if (basketItem.getOperationType() == OperationType.RESERVATION) {
                endDate = endOfReservationDate;
                operationTypeId = 0;
                status = "Zarezerwowana";
            }

            String query = "INSERT INTO `operations` " +
                    "(`userId`, `bookId`, `operationDate`, `startDate`, `endDate`, `operationTypeId`) " +
                    "VALUES ('" +

                    user.getId() + "', '" +
                    basketItem.getBook().getId() + "', '" +
                    currentDate + "', '" +
                    currentDate + "', '" +
                    endDate + "', '" +
                    operationTypeId + "' )";


            String changeStatusQuery = "UPDATE book SET status='" + status + "' WHERE id=" + basketItem.getBook().getId();

            try {
                preparedStatement(query).execute();
                preparedStatement(changeStatusQuery).execute();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static ResultSet listOfBorrowedBookByUserId(int userID) throws SQLException, ClassNotFoundException {
        String endDate = "1970.01.01";
        String query = "SELECT * FROM operations " +
                "JOIN `user` ON operations.userId = user.id " +
                "JOIN book ON operations.bookId=book.id " +
                "WHERE userID = " + userID + " " +
                "AND endDate = '" + endDate + "'";
        return preparedStatement(query).executeQuery();
    }

    public static void ReturnBook(int id, LocalDate endDate) {
       String endDateString=endDate.toString();
       // UPDATE `librarydb`.`operations` SET `endDate`='1970-01-02' WHERE `id`='25';
        String query = "UPDATE operations SET endDate='" + endDateString + "' WHERE id=" + id;
        String statusAvailableQuery = "UPDATE book SET status='Dostępna' WHERE id=" + id;
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
