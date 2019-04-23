package com.infoshare.repository;

import com.infoshare.domain.Book;

import javax.ejb.Local;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@Local
public interface BooksRepositoryDao {


    List<Book> bookList(String order, Integer page) throws SQLException, ClassNotFoundException, FileNotFoundException;
    Book getBookById (int id) throws SQLException,ClassNotFoundException;
    List<Book> getBookByTitle(String title) throws SQLException,ClassNotFoundException;
    void addNewBook(Book book);
    void editBook (Book book);
}