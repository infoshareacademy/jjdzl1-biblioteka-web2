package com.infoshare.servlets;

import com.infoshare.domain.Operation;
import com.infoshare.domain.User;
import com.infoshare.repository.OperationsRepositoryDao;

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


@WebServlet("/GetAttributesOperationRepository")
public class GetAttributesOperationRepository extends HttpServlet {

    @EJB
    private OperationsRepositoryDao operationsRepository;

    private List<Operation> operationsBorrowList = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        int userId = ((User) session.getAttribute("selectedUser")).getId();

        try {
            operationsBorrowList = operationsRepository.operationListBorrowByUser(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        session.setAttribute("borrowOperationDao", operationsBorrowList);

        resp.sendRedirect("listOfBorrow.jsp");
    }
}

