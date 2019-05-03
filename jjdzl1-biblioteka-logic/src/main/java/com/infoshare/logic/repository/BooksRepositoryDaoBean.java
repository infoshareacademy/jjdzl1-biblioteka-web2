package com.infoshare.logic.repository;

import com.infoshare.logic.domain.Book;
import com.infoshare.logic.domain.BookStatus;
import com.infoshare.logic.utils.RecordPerPage;
import com.infoshare.logic.validation.BookValidator;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@Stateless
public class BooksRepositoryDaoBean implements BooksRepositoryDao {

    @PersistenceContext(name = "librarydb")
    private EntityManager entityManager;

    @EJB
    BookValidator validator;

    String stringQuery = "";

    @Override
    public List<Book> bookList(String order, Integer page) throws SQLException, ClassNotFoundException, FileNotFoundException {

        Integer recordPerPage = null;
        Integer offset = null;

        try {
            recordPerPage = RecordPerPage.readProperties();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (page == null) page = 1;
        offset = recordPerPage * page - recordPerPage;


        if (order != null) {
            stringQuery = "select u from Book u order by u." + order;
        } else
            stringQuery = "select u from Book u";
        TypedQuery<Book> query = entityManager.createQuery(stringQuery, Book.class);
        List<Book> bookList = query
                .setMaxResults(recordPerPage)
                .setFirstResult(offset)
                .getResultList();
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

    @Override
    public void editBook(Book book) {
        entityManager.merge(book);
    }

    @Override
    public Book createBookFromForm(HttpServletRequest req) {
        return Book.builder()
                .title(req.getParameter("title"))
                .authorFirstName(req.getParameter("firstName"))
                .authorLastName(req.getParameter("lastName"))
                .daterelease(dateChecked(req))
                .isbn(req.getParameter("isbn"))
                .status(BookStatus.Dostępna)
                .description(req.getParameter("description"))
                .build();
    }

    private Integer dateChecked(HttpServletRequest req) {

        Integer date;
        try {
            date = Integer.parseInt(req.getParameter("daterelease"));
        } catch (NumberFormatException e) {
            date = 0;
        }
        return date;
    }

    @Override
    public List<String> validate(Book book) {
        validator.bookValidation(book);
        return validator.validationResult;
    }

}