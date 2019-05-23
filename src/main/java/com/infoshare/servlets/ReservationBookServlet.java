package com.infoshare.servlets;

import com.infoshare.logic.domain.Operation;
import com.infoshare.logic.domain.User;
import com.infoshare.logic.repository.OperationsRepositoryDao;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("ReservationBookServlet")
public class ReservationBookServlet extends HttpServlet {

    @EJB
    private OperationsRepositoryDao operationsRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Operation> reservations = new ArrayList<>();
        int userId = ((User) req.getSession().getAttribute("selectedUser")).getId();

        try {
            reservations = operationsRepository.operationListReservationByUser(userId);
            req.getSession().setAttribute("reservedOperationDao", reservations);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("listOfReservations.jsp?userId=" + userId);
    }
}
