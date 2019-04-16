package com.infoshare.query;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.infoshare.dao.DBCon.preparedStatement;

public class BooksQuery {

    public static ResultSet listOfBooksFomTo(String order, int from, int to) throws SQLException, ClassNotFoundException {

        String query = "SELECT * FROM book ORDER BY " + order + " LIMIT " + from + "," + to;

        return preparedStatement(query).executeQuery();
    }

    public static ResultSet CountAllBooks() throws SQLException, ClassNotFoundException {

        String query = "SELECT COUNT(*) FROM book WHERE 1";
        return preparedStatement(query).executeQuery();

    }

    public static ResultSet findBookById(int id) throws SQLException, ClassNotFoundException {

        String query = "SELECT * FROM book WHERE id = " + id;
        return preparedStatement(query).executeQuery();

    }

    public static ResultSet findBookByTitle(String title, String order) throws SQLException, ClassNotFoundException {

        String query = "SELECT * FROM book WHERE title LIKE '%" + title + "%' ORDER BY " + order;
        return preparedStatement(query).executeQuery();
    }
}
