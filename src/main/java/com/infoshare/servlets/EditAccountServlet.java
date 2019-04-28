package com.infoshare.servlets;

import com.infoshare.logic.domain.User;
import com.infoshare.repository.UsersRepositoryDao;
import com.infoshare.logic.utils.Hasher;
import com.infoshare.logic.utils.PBKDF2Hasher;
import com.infoshare.validation.UserValidator;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/EditAccountServlet")
public class EditAccountServlet extends HttpServlet implements Serializable {

    @EJB
    private UsersRepositoryDao usersRepository;

    @EJB
    private UserValidator validator;

    private static final long serialVersionUID = -7579180123749243850L;

    private Integer id = null;
    private String password1 = "";
    private String password2 = "";
    private String password3 = "";
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String hashedPass = "";

    private static final Logger LOGGER = Logger.getLogger(EditAccountServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        id = Integer.parseInt(req.getParameter("userId"));
        firstName = req.getParameter("firstName");
        lastName = req.getParameter("lastName");
        email = req.getParameter("e-mail");
        password1 = req.getParameter("password1");
        password2 = req.getParameter("password2");
        password3 = req.getParameter("password3");

        User user = null;
        Hasher hasher = new PBKDF2Hasher();

        try {
            user = usersRepository.getUserById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<String> validationResult = validator.validationResult;
        if (hasher.checkPassword(password3, user.getPassword())) {
            validationResult.clear();
            validator.isEmailOrLoginExist(user, req);
            emptyInputPasswordValidation(user, hasher);
            createUserToEdit(user, req);
            validator.userToEditValidation(user);

            if (!validationResult.isEmpty())
                resp.sendRedirect("accountSettings.jsp");
            else {
                usersRepository.updateUserAfterEdit(user);
                LOGGER.info("User account : " + user.getLogin() + " was edited");
                req.getSession().setAttribute("userEdited", "userEdited");
                if (req.getSession().getAttribute("user") != null)
                    resp.sendRedirect("loginSuccess.jsp");
                else
                    resp.sendRedirect("index.jsp");
            }
        } else {
            validationResult.clear();
            validationResult.add("Błędne aktualne hasło!");
            resp.sendRedirect("accountSettings.jsp");
        }
    }

    private void emptyInputPasswordValidation(User user, Hasher hasher) {

        if (password1.isEmpty() || password2.isEmpty() && !password1.equals(password2)) hashedPass = user.getPassword();
        if (!password1.isEmpty() && password1.equals(password2)) hashedPass = hasher.hash(password2);
    }


    private User createUserToEdit(User user, HttpServletRequest req) {

        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(hashedPass);

        return user;
    }
}