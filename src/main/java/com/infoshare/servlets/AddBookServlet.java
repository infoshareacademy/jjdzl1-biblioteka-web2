package com.infoshare.servlets;

import com.infoshare.domain.Book;
import com.infoshare.repository.BooksRepositoryDao;
import com.infoshare.repository.BooksRepositoryDaoBean;
import com.infoshare.validation.BookValidator;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/AddBookServlet")
public class AddBookServlet extends HttpServlet {

    @EJB
    private BooksRepositoryDao booksRepository;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Book book = createBookFromForm(req);

        if (req.getSession().getAttribute("user") != null && validate(book).size() > 0) {
            resp.sendRedirect("addBook.jsp");
        } else {
            booksRepository.addNewBook(book);
            req.getSession().setAttribute("addBook", "bookAdded");
            if (req.getSession().getAttribute("user") != null)
                resp.sendRedirect("loginSuccess.jsp");
            else
                resp.sendRedirect("index.jsp");
        }
    }

    private Book createBookFromForm(HttpServletRequest req) {

        return Book.builder()
                .title(req.getParameter("title"))
                .authorFirstName(req.getParameter("firstName"))
                .authorLastName(req.getParameter("lastName"))
                .relaseDate(dateChecked(req))
                .isbn(req.getParameter("isbn"))
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

    private List<String> validate(Book book) {

        BookValidator validator = new BookValidator();
        validator.bookValidation(book);
        return validator.validationResult;
    }
}
