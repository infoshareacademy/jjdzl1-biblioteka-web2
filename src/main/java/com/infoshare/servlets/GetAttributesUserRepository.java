package com.infoshare.servlets;

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


@WebServlet("/GetAttributesUserRepository")
public class GetAttributesUserRepository extends HttpServlet {

    @EJB
    private UsersRepositoryDao usersRepositoryDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String opertation = req.getParameter("operation");
        String findUserByName=req.getParameter("findUserByName");

        List<User> userList = new ArrayList<>();
        try {
            userList = usersRepositoryDao.listOfUsers(findUserByName);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        HttpSession session = req.getSession();
        session.setAttribute("userRepositoryDao", userList);

        if (opertation != null && opertation.equals("newoperation")) {
            resp.sendRedirect("listOfUsers.jsp?operation=newoperation");
        } else if (opertation != null && opertation.equals("returnbook")) {
            resp.sendRedirect("listOfUsers.jsp?operation=returnbook");
        } else {
            resp.sendRedirect("listOfUsers.jsp");
        }
    }
}
