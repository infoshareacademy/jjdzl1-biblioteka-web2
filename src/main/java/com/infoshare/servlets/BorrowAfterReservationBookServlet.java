package com.infoshare.servlets;

import com.infoshare.logic.domain.*;
import com.infoshare.logic.repository.BasketRepositoryDao;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("BorrowAfterReservationBookServlet")
public class BorrowAfterReservationBookServlet extends HttpServlet {

    @EJB
    private OperationsRepositoryDao operationsRepository;

    @EJB
    private BooksRepositoryDao booksRepository;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int userId = ((User) req.getSession().getAttribute("selectedUser")).getId();
        int operationId = Integer.parseInt(req.getParameter("operationId"));

        Operation operation = null;
        Book book=null;

        try {
            operation = operationsRepository.getOperation(operationId);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            book = booksRepository.getBookById(operation.getBook().getId());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        operation.setStartDate(LocalDate.now());
        operation.setEndDate(LocalDate.of(1970,01,01));
        operation.setOperationType(OperationType.BORROW);

        book.setStatus(BookStatus.Wypożyczona);

        operationsRepository.borrowAfterReservation(operation, book);
        req.getSession().setAttribute("borrowAfterReservation", book.getTitle());

        resp.sendRedirect("ReservationBookServlet?userId=" + userId);
    }
}
