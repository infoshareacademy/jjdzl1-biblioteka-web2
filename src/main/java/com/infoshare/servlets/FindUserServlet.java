package com.infoshare.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/FindUserServlet")
public class FindUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String findUserByName = req.getParameter("findUserByName");
        String operation = req.getParameter("operation");

        resp.setContentType("text/html;charset=UTF-8");
        if (operation != null && !operation.isEmpty() && operation.equals("newoperation"))
            resp.sendRedirect("GetAttributesUserRepository?findUserByName=" + findUserByName + "&operation=" + operation);
        else {
            resp.sendRedirect("GetAttributesUserRepository?findUserByName=" + findUserByName);
        }
    }
}
