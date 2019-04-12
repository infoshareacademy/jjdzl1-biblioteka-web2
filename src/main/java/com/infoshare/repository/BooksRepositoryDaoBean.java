package com.infoshare.repository;

import com.infoshare.domain.Book;
import com.infoshare.domain.BookStatus;
import com.infoshare.query.BooksQuery;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Stateless
public class BooksRepositoryDaoBean implements BooksRepositoryDao {

    @PersistenceContext(name = "librarydb")
    private EntityManager entityManager;

    @Override
    public List<Book> bookList(String order) throws SQLException, ClassNotFoundException {
        String stringQuery ="";
        if (order.equals("title"))
        stringQuery = "select u from Book u order by u.title";
        else stringQuery = "select u from Book u order by u.authorLastName";
        TypedQuery<Book> query = entityManager.createQuery(stringQuery, Book.class);
        List<Book> bookList = query.getResultList();
        return bookList;
    }

    @Override
    public Book getBookById(int id) throws SQLException, ClassNotFoundException {
        Book book = null;
        try (ResultSet rs = BooksQuery.findBookById(id)) {
            while (rs.next()) {
                int bookID = rs.getInt("id");
                String bookTitle = rs.getString("title");
                String authorFirstName = rs.getString("authorFirstName");
                String authorLastName = rs.getString("authorLastName");
                int relaseDate = rs.getInt("daterelease");
                String isbn = rs.getString("isbn");
                String description = rs.getString("description");
                String statusString = rs.getString("status");
                BookStatus status;
                if (statusString.equals("Dostępna"))
                    status = BookStatus.Dostępna;
                else if (statusString.equals("Zarezerwowana"))
                    status = BookStatus.Zarezerwowana;
                else status = BookStatus.Wypożyczona;

                book = new Book(bookID, bookTitle, authorFirstName, authorLastName, relaseDate, isbn, description, status);
            }
            rs.close();
            return book;
        }
    }

    public void addNewBook(Book book) {
        entityManager.persist(book);
    }
}