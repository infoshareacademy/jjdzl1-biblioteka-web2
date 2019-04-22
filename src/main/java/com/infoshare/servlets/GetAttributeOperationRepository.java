package com.infoshare.servlets;

import com.infoshare.domain.Operation;
import com.infoshare.domain.User;
import com.infoshare.repository.OperationsRepositoryDao;
import com.infoshare.repository.UsersRepositoryDao;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
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
        if (operationType == null || operationType.isEmpty()) operationType = "all";

        try {
            operationsList = operationsRepository.AllOperationList(operationType, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        HttpSession session = req.getSession();
        session.setAttribute("operationRepositoryDao", operationsList);

        resp.sendRedirect("listOfOperations.jsp");
    }
}

