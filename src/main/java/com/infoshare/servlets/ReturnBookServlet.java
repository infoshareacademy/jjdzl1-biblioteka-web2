package com.infoshare.servlets;

import com.infoshare.repository.OperationsRepositoryDao;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("ReturnBookServlet")
public class ReturnBookServlet extends HttpServlet {

    @EJB
    private OperationsRepositoryDao operationsRepository;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int operationId = Integer.parseInt(req.getParameter("operationId"));
        int bookId = Integer.parseInt(req.getParameter("bookId"));
        LocalDate endDate = LocalDate.parse(req.getParameter("endDate"));

        operationsRepository.ReturnBook(operationId, bookId, endDate);

        resp.sendRedirect("GetAttributeUserOperationRepository");
    }
}
