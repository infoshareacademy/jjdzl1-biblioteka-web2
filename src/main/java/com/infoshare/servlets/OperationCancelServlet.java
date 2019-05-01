package com.infoshare.servlets;


import com.infoshare.logic.domain.Basket;
import com.infoshare.logic.repository.BasketRepositoryDao;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/OperationCancelServlet")
public class OperationCancelServlet extends HttpServlet {

    @EJB
    private BasketRepositoryDao basketRepositoryDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String selectedUser = req.getParameter("selectedUser");

        if (selectedUser != null && !selectedUser.isEmpty() && selectedUser.equals("remove")) {
            req.getSession().removeAttribute("selectedUser");

            List<Basket> basketToClear = (List<Basket>) req.getSession().getAttribute("basket");
            basketRepositoryDao.clearBasketList(basketToClear);
            req.getSession().removeAttribute("basket");

            resp.sendRedirect("loginSuccess.jsp");
        }
    }
}
