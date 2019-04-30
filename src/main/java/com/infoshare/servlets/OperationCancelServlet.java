package com.infoshare.servlets;

import com.infoshare.logic.domain.Basket;
import com.infoshare.logic.repository.BasketRepositoryDao;
import com.infoshare.logic.repository.SelectUserData;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/OperationCancelServlet")
public class OperationCancelServlet extends HttpServlet {

    @EJB
    private BasketRepositoryDao basketRepositoryDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String canelOperation = req.getParameter("selectedUser");


        if (canelOperation.equals("remove")) {
            HttpSession session = req.getSession();
            session.removeAttribute("selectedUser");
            basketRepositoryDao.basket().clear();
        }
        resp.sendRedirect("loginSuccess.jsp");

    }
}
