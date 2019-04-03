package com.infoshare.servlets;

import com.infoshare.domain.Basket;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/OperationCancelServlet")
public class OperationCancelServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String canelOperation = req.getParameter("selectedUser");


        if (canelOperation.equals("remove")) {
            HttpSession session = req.getSession();
            session.removeAttribute("selectedUser");
            SelectUserServlet.basket.clear();
        }
        resp.sendRedirect("loginSuccess.jsp");

    }
}
