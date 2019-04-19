package com.infoshare.servlets;

import com.infoshare.domain.User;
import com.infoshare.repository.UsersRepositoryDao;
import com.infoshare.utils.Hasher;
import com.infoshare.utils.PBKDF2Hasher;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

@WebServlet("/EditAccountServlet")
public class EditAccountServlet extends HttpServlet implements Serializable {

    @EJB
    private UsersRepositoryDao usersRepository;

    private static final long serialVersionUID = -7579180123749243850L;

    private Integer id = null;
    private String password1 = "";
    private String password2 = "";
    private String password3 = "";
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String hashedPass = "";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        id = Integer.parseInt(req.getParameter("userId"));
        firstName = req.getParameter("firstName");
        lastName = req.getParameter("lastName");
        email = req.getParameter("e-mail");
        password1 = req.getParameter("password1");
        password2 = req.getParameter("password2");
        password3 = req.getParameter("password3");

        User user = null;

        try {
            user = usersRepository.getUserById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        emptyInputValidation(user);
        Hasher hasher = new PBKDF2Hasher();

        if (hasher.checkPassword(password3, user.getPassword())) {
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(hashedPass);

            usersRepository.updateUserAfterEdit(user);
        }

        req.getSession().setAttribute("userEdited", "userEdited");
        if (req.getSession().getAttribute("user") != null)
            resp.sendRedirect("loginSuccess.jsp");
        else
            resp.sendRedirect("index.jsp");
    }

    private void emptyInputValidation(User user) {

        Hasher hasher = new PBKDF2Hasher();

        if (password1.isEmpty() || password2.isEmpty() && !password1.equals(password2)) hashedPass = user.getPassword();
        if (!password1.isEmpty() && password1.equals(password2)) hashedPass = hasher.hash(password2);
        if (firstName.isEmpty()) firstName = user.getFirstName();
        if (lastName.isEmpty()) lastName = user.getLastName();
        if (email.isEmpty()) email = user.getEmail();
    }
}