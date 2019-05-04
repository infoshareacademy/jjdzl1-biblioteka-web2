package com.infoshare.servlets;

import com.infoshare.logic.domain.Book;
import com.infoshare.logic.repository.BooksRepositoryDao;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AddBookServlet")
public class AddBookServlet extends HttpServlet {

    @EJB
    private BooksRepositoryDao booksRepository;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Book book = booksRepository.createBookFromForm(req);

        if (req.getSession().getAttribute("user") != null && !booksRepository.validate(book).isEmpty()) {
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

}