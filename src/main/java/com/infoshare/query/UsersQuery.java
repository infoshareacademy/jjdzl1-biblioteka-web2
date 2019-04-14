package com.infoshare.query;

import com.infoshare.domain.User;
import com.infoshare.domain.UserStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.infoshare.dao.DBCon.preparedStatement;

public class UsersQuery {

    public static ResultSet listOfUsersFromTo(String order, int from, int to) throws SQLException, ClassNotFoundException {

        String query = "SELECT * FROM user ORDER BY " + order + " LIMIT " + from + "," + to;
        return preparedStatement(query).executeQuery();
    }

    public static ResultSet CountAllUsers() throws SQLException, ClassNotFoundException {

        String query = "SELECT COUNT(*) FROM user WHERE 1";
        return preparedStatement(query).executeQuery();

    }

    public static ResultSet findUserById(int id) throws SQLException, ClassNotFoundException {

        String query = "SELECT * FROM user WHERE id = " + id;
        return preparedStatement(query).executeQuery();
    }

    public static ResultSet findUserByLogin(String login) throws SQLException, ClassNotFoundException {

        String query = "SELECT * FROM user WHERE login = '" + login + "'";
        return preparedStatement(query).executeQuery();
    }

    public static void updateUserQuery(String userLogin, User user) throws SQLException, ClassNotFoundException {

        String query = "UPDATE user SET login = ?, firstName = ?, lastName = ?, email = ?, admin = ?, status = ? WHERE login = '" + userLogin + "'";

        PreparedStatement ps = preparedStatement(query);
        ps.setString(1, user.getLogin());
        ps.setString(2, user.getFirstName());
        ps.setString(3, user.getLastName());
        ps.setString(4, user.getEmail());
        boolean admin = user.getAdmin() == UserStatus.ADMIN ? true : false;
        ps.setBoolean(5, admin);
        ps.setString(6, user.getStatus());

        ps.execute();
        ps.close();
    }

    public static void updateAccountQuery(String userName, User user) throws SQLException, ClassNotFoundException {

        String query = "UPDATE user SET login = ?, firstName = ?, lastName = ?, email = ?, password = ?" +
                " WHERE login = '" + userName + "'";

        PreparedStatement ps = preparedStatement(query);
        ps.setString(1, user.getLogin());
        ps.setString(2, user.getFirstName());
        ps.setString(3, user.getLastName());
        ps.setString(4, user.getEmail());
        ps.setString(5, user.getPassword());
        ps.execute();
        ps.close();
    }
}
