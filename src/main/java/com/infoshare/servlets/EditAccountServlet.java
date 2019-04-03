package com.infoshare.servlets;

import com.infoshare.domain.User;
import com.infoshare.query.UsersQuery;
import com.infoshare.repository.UsersRepositoryDao;
import com.infoshare.repository.UsersRepositoryDaoBean;
import com.infoshare.utils.Hasher;
import com.infoshare.utils.PBKDF2Hasher;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

@WebServlet("/EditAccountServlet")
public class EditAccountServlet extends HttpServlet implements Serializable {
    private static final long serialVersionUID = 1321721107243360065L;
    private String login = "";
    private String password1 = "";
    private String password2 = "";
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String hashedPass;

    @EJB
    private UsersRepositoryDao usersRepository;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userCookie")) userName = cookie.getValue();
            }
        }
        login = req.getParameter("login");
        password1 = req.getParameter("password1");
        password2 = req.getParameter("password2");
        Hasher hasher = new PBKDF2Hasher();
        if (!password2.isEmpty()) hashedPass = hasher.hash(password2);
        firstName = req.getParameter("firstName");
        lastName = req.getParameter("lastName");
        email = req.getParameter("e-mail");

        try {
            emptyInputValidation(userName);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        User editedUser = new User(null, login, firstName, lastName, hashedPass, email, null, null);
        try {
            UsersQuery.updateAccountQuery(userName, editedUser);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        req.getSession().setAttribute("userEdited", "userEdited");
        if (req.getSession().getAttribute("user") != null)
            resp.sendRedirect("loginSuccess.jsp");
        else
            resp.sendRedirect("index.jsp");
    }

    private void emptyInputValidation(String userName) throws SQLException, ClassNotFoundException {
        User user = usersRepository.getUserByLogin(userName);
        if (login.isEmpty()) login = user.getLogin();
        if (password1.isEmpty() || password2.isEmpty()) hashedPass = user.getPassword();
        if (firstName.isEmpty()) firstName = user.getFirstName();
        if (lastName.isEmpty()) lastName = user.getLastName();
        if (email.isEmpty()) email = user.getEmail();
    }

}