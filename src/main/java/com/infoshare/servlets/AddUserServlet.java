package com.infoshare.servlets;

import com.infoshare.logic.domain.User;
import com.infoshare.logic.repository.UsersRepositoryDao;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {

    @EJB
    private UsersRepositoryDao usersRepository;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = usersRepository.createUserFromForm(req);

        if (usersRepository.validate(user, req).size() > 0) {
            resp.sendRedirect("addUser.jsp");
        } else {
            usersRepository.addNewUser(user);
            req.getSession().setAttribute("addUser", "userAdded");
            if (req.getSession().getAttribute("user") != null)
                resp.sendRedirect("loginSuccess.jsp");
            else
                resp.sendRedirect("index.jsp");
        }
    }

}
