package com.infoshare.servlets;

import com.infoshare.logic.repository.BasketRepositoryDao;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RemoveItemFromBasketServlet")
public class RemoveItemFromBasketServlet extends HttpServlet {

    @EJB
    private BasketRepositoryDao basketRepository;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer itemNumber=Integer.parseInt(req.getParameter("removeItem"));

        basketRepository.removeItemFromBasket(itemNumber);

        resp.sendRedirect("userBasket.jsp");
    }
}
