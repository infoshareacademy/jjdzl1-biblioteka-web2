package com.infoshare.servlets;

import com.infoshare.logic.domain.Book;
import com.infoshare.logic.domain.BookStatus;
import com.infoshare.logic.repository.BooksRepositoryDao;
import com.infoshare.logic.repository.BooksRepositoryDaoBean;
import com.infoshare.logic.validation.BookValidator;

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
    @EJB
    private BookValidator validator;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Book book = createBookFromForm(req);

        if (req.getSession().getAttribute("user") != null && !validate(book).isEmpty()) {
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


    private List<String> validate(Book book) {
        validator.bookValidation(book);
        return validator.validationResult;
    }


    private Book createBookFromForm(HttpServletRequest req) {
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
}