package com.infoshare.repository;

import com.infoshare.domain.Book;

import javax.ejb.Local;
import java.sql.SQLException;
import java.util.List;

@Local
public interface BooksRepositoryDao {


    List<Book> bookList(String order) throws SQLException, ClassNotFoundException;
    Book getBookById (int id) throws SQLException,ClassNotFoundException;
    void addNewBook(Book book);
}