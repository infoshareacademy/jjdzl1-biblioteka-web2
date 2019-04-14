package com.infoshare.repository;

import com.infoshare.domain.Book;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

@Stateless
public class BooksRepositoryDaoBean implements BooksRepositoryDao {

    @PersistenceContext(name = "librarydb")
    private EntityManager entityManager;

    @Override
    public List<Book> bookList(String order) throws SQLException, ClassNotFoundException {
        String stringQuery = "select u from Book u order by u." + order;
        TypedQuery<Book> query = entityManager.createQuery(stringQuery, Book.class);
        List<Book> bookList = query.getResultList();
        return bookList;
    }

    @Override
    public Book getBookById(int id) throws SQLException, ClassNotFoundException {
        String stringQuery = "select u from Book u where u.id=" + id;

        TypedQuery<Book> query = entityManager.createQuery(stringQuery, Book.class);
        Book book = query.getSingleResult();
        return book;
    }

    @Override
    public List<Book> getBookByTitle(String title) throws SQLException, ClassNotFoundException {
        String stringQuery = "select u from Book u where u.title like '%" + title + "%'";

        TypedQuery<Book> query = entityManager.createQuery(stringQuery, Book.class);
        List<Book> bookList = query.getResultList();
        return bookList;
    }

    @Override
    public void addNewBook(Book book) {
        entityManager.persist(book);
    }
}