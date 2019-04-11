package com.infoshare.servlets;

import com.infoshare.query.OperationsQuery;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("ReturnBookServlet")
public class ReturnBookServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int operationID = Integer.parseInt(req.getParameter("operationId"));
        int bookID = Integer.parseInt(req.getParameter("bookId"));
        LocalDate endDate = LocalDate.parse( req.getParameter("endDate"));

        OperationsQuery.ReturnBook(operationID,bookID,endDate);

        resp.sendRedirect("listOfBorrow.jsp");
    }
}
