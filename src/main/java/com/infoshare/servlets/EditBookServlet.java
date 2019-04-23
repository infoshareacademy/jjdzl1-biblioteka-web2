package com.infoshare.servlets;

import com.infoshare.domain.Book;
import com.infoshare.repository.BooksRepositoryDao;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/EditBookServlet")
public class EditBookServlet extends HttpServlet {

    @EJB
    private BooksRepositoryDao booksRepositoryDao;

    private String id = null;
    private String title = "";
    private String authorFirstName = "";
    private String authorLastName = "";
    private String isbn = "";
    private String daterelase = "";
    private String status = "";
    private String description = "";
    Book book = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        id = req.getParameter("id");

        try {
            book = booksRepositoryDao.getBookById(Integer.parseInt(id));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        HttpSession session = req.getSession();
        session.setAttribute("editedBook", book);
        resp.sendRedirect("editBook.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        id = req.getParameter("id");
        title = req.getParameter("title");
        authorFirstName = req.getParameter("authorFirstName");
        authorLastName = req.getParameter("authorLastName");
        isbn = req.getParameter("isbn");
        daterelase = req.getParameter("daterelase");
        status = req.getParameter("status");
        description = req.getParameter("description");

        Book book = null;

        try {
            book = booksRepositoryDao.getBookById(Integer.parseInt(id));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        emptyInputValidation(book);

        Book editedBook = book.builder()
                .id(Integer.parseInt(id))
                .title(title)
                .authorFirstName(authorFirstName)
                .authorLastName(authorLastName)
                .isbn(isbn)
                .daterelease(Integer.parseInt(daterelase))
                .description(description)
                .build();

        booksRepositoryDao.editBook(editedBook);

        req.getSession().setAttribute("opertationSuccess", "success");
        if (req.getSession().getAttribute("user") != null)
            resp.sendRedirect("loginSuccess.jsp");
        else
            resp.sendRedirect("index.jsp");
    }

    private void emptyInputValidation(Book book) {

        if (title.isEmpty()) title = book.getTitle();
        if (authorFirstName.isEmpty()) authorFirstName = book.getAuthorFirstName();
        if (authorLastName.isEmpty()) authorLastName = book.getAuthorLastName();
        if (isbn.isEmpty()) isbn = book.getIsbn();
        if (daterelase.isEmpty()) daterelase = String.valueOf(book.getDaterelease());
        if (description == null || description.isEmpty()) description = "Brak opisu";
    }
}
