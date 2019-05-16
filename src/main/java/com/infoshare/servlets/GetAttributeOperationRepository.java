package com.infoshare.servlets;

import com.infoshare.logic.domain.Operation;
import com.infoshare.logic.repository.OperationsRepositoryDao;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/GetAttributeOperationRepository")
public class GetAttributeOperationRepository extends HttpServlet {

    @EJB
    private OperationsRepositoryDao operationsRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Operation> operationsList = new ArrayList<>();
        String operationType = req.getParameter("operationType");
        String userId = req.getParameter("userId");
        String stringFirstDate = req.getParameter("firstDate");
        String stringLastDate = req.getParameter("lastDate");
        LocalDate firstDate = null;
        LocalDate lastDate = null;

        if (stringFirstDate != null && stringLastDate != null) {
            firstDate = LocalDate.parse(req.getParameter("firstDate"));
            lastDate = LocalDate.parse(req.getParameter("lastDate"));
        }

        if (operationType == null || operationType.isEmpty()) operationType = "all";

        try {
            operationsList = operationsRepository.AllOperationList(operationType, userId, firstDate, lastDate);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        HttpSession session = req.getSession();
        session.setAttribute("operationRepositoryDao", operationsList);

        resp.sendRedirect("listOfOperations.jsp?operationType=" + operationType);
    }
}

