package com.infoshare.servlets;

import com.infoshare.domain.User;
import com.infoshare.repository.UsersRepositoryDao;
import com.infoshare.repository.UsersRepositoryDaoBean;
import lombok.Data;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;


@Data
@WebServlet("/GetUserToEditServlet")
public class GetUserToEditServlet extends HttpServlet implements Serializable {
    private static final long serialVersionUID = -6564924863409642949L;

    @EJB
    private UsersRepositoryDao usersRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userID = req.getParameter("userID");

        User user = null;
        try {
            user = usersRepository.getUserById(Integer.parseInt(userID));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        HttpSession session = req.getSession();
        session.setAttribute("UserObject", user);

        resp.sendRedirect("editUser.jsp");
    }
}