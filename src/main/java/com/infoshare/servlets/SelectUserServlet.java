package com.infoshare.servlets;

import com.infoshare.logic.domain.Basket;
import com.infoshare.logic.domain.User;
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

@WebServlet("/SelectUserServlet")
public class SelectUserServlet extends HttpServlet {

    public static List<Basket> basket = new ArrayList<>();

    @EJB
    private UsersRepositoryDao usersRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int userId = Integer.parseInt(req.getParameter("userid"));
        String operationType = req.getParameter("operation");
        String redirection = "";

        User user = null;
        try {
            user = usersRepository.getUserById(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        basket.clear();
        HttpSession session = req.getSession();
        session.setAttribute("selectedUser", user);
        if (operationType.equals("newoperation")) redirection = "GetAttributeBookRepository";
        if (operationType.equals("returnbook")) redirection = "GetAttributeUserOperationRepository";

        resp.sendRedirect(redirection);
    }
}
