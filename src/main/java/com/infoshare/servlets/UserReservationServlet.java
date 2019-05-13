package com.infoshare.servlets;

import com.infoshare.logic.domain.Book;
import com.infoshare.logic.repository.BooksRepositoryDao;
import com.infoshare.logic.repository.OperationsRepositoryDao;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/UserReservationServlet")
public class UserReservationServlet extends HttpServlet {
    @EJB
    private OperationsRepositoryDao operationsRepository;

    @EJB
    private BooksRepositoryDao booksRepository;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int bookId = Integer.parseInt(req.getParameter("bookId"));
        int userId = (int) req.getSession().getAttribute("userId");

        try {
            operationsRepository.addNewUserReservation(bookId, userId);
            Book book = booksRepository.getBookById(bookId);
            req.getSession().setAttribute("userReservationAdded", book.getTitle());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        resp.sendRedirect("loginSuccess.jsp");
    }
}
