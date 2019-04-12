package com.infoshare.query;

import com.infoshare.dao.DBCon;
import com.infoshare.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.infoshare.dao.DBCon.preparedStatement;

public class BooksQuery {

    public static ResultSet listOfBooks(String title, String order) throws SQLException, ClassNotFoundException {


        String query = "SELECT * FROM book WHERE title LIKE '%" + title + "%' ORDER BY " + order;

        return preparedStatement(query).executeQuery();
    }

    public static ResultSet listOfBooksFromTo(String order, int from, int to) throws SQLException, ClassNotFoundException {

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

//    public static void addNewBook(Book book) {
//
//        String query = "INSERT INTO `book`(`title`, `authorFirstName`, `authorLastName`, `daterelease`, `isbn`) VALUES ('" +
//                book.getTitle() + "', '" +
//                book.getAuthorFirstName() + "', '" +
//                book.getAuthorLastName() + "', '" +
//                book.getDaterelease() + "', '" +
//                book.getIsbn() + "' )";
//        try {
//            preparedStatement(query).execute();
//            DBCon.connClose();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
}
