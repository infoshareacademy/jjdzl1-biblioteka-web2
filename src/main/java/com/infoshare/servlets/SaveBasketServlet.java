package com.infoshare.servlets;

import com.infoshare.domain.Basket;
import com.infoshare.domain.User;
import com.infoshare.repository.BasketRepositoryDao;
import com.infoshare.repository.BasketRepositoryDaoBean;
import com.infoshare.repository.OperationsRepositoryDao;
import com.infoshare.repository.OperationsRepositoryDaoBeen;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/SaveBasketServlet")
public class SaveBasketServlet extends HttpServlet {

    @EJB
    private BasketRepositoryDao basketRepository;

    @EJB
    private OperationsRepositoryDao operationsRepository;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("selectedUser");

        List<Basket> basketList = basketRepository.basketList();

        operationsRepository.addNewOperation(basketList, user);

        if (req.getSession().getAttribute("user") != null) {
            session.removeAttribute("selectedUser");
            session.setAttribute("opertationSuccess","success");
            basketList.clear();
            resp.sendRedirect("loginSuccess.jsp");
        } else {
            resp.sendRedirect("index.jsp");
        }
    }
}
