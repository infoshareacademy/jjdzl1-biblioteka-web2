package com.infoshare.servlets;

import com.infoshare.domain.User;
import com.infoshare.domain.UserStatus;
import com.infoshare.repository.UsersRepositoryDao;
import com.infoshare.validation.UserValidator;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@Slf4j
@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {

    @EJB
    private UsersRepositoryDao usersRepository;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = createUserFromForm(req);

        try {
            if (validate(user, req).size() > 0) {
                resp.sendRedirect("addUser.jsp");
            } else {
                usersRepository.addNewUser(user);
                req.getSession().setAttribute("addUser", "userAdded");
                if (req.getSession().getAttribute("user") != null)
                    resp.sendRedirect("loginSuccess.jsp");
                else
                    resp.sendRedirect("index.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private User createUserFromForm(HttpServletRequest req) {

        return User.builder()
                .login(req.getParameter("login"))
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .password(req.getParameter("password"))
                .email(req.getParameter("e-mail"))
                .admin(isChecked(req, "admin") ? UserStatus.ADMIN : UserStatus.USER)
                .build();
    }

    private boolean isChecked(HttpServletRequest req, String fieldname) {

        String[] value = req.getParameterValues(fieldname);
        return value != null ? value[0].equals("on") : false;
    }

    private List<String> validate(User user, HttpServletRequest req) throws SQLException, ClassNotFoundException {

        UserValidator validator = new UserValidator();
        validator.userValidation(user);

        String password = user.getPassword();
        if (password != null && !password.equals(req.getParameter("password2"))) {
            validator.validationResult.add("Hasła są różne !");
        }

        try {
            validator.checkIsLoginOrEmailExist(user.getLogin(), user.getEmail());
        } catch (SQLException e) {
            log.error("User validation - SQL error", e);
        } catch (ClassNotFoundException e) {
            log.error("User validation - Class not found", e);
        }

        return validator.validationResult;
    }
}
