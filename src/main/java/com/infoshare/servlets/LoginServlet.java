package com.infoshare.servlets;

import com.infoshare.logic.domain.User;
import com.infoshare.logic.domain.UserStatus;
import com.infoshare.logic.repository.UsersRepositoryDao;
import com.infoshare.logic.utils.Hasher;
import com.infoshare.logic.utils.PBKDF2Hasher;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet implements Serializable {

    private static final long serialVersionUID = 5384381614337933147L;

    @EJB
    private UsersRepositoryDao usersRepositoryDao;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String formLogin = request.getParameter("user");
        String formPassword = request.getParameter("pwd");

        String login = "";
        String password = "";
        boolean admin = false;
        String status = "";
        String userName = "";
        Integer userId = null;

        List<User> userList = usersRepositoryDao.findUserByLogin(formLogin);
        if (userList.size() > 0) {
            for (User user : userList) {
                login = user.getLogin();
                password = user.getPassword();
                if (user.getAdmin().equals(UserStatus.ADMIN)) admin = true;
                status = user.getStatus();
                userName = user.getFirstName() + ", " + user.getLastName();
                userId = user.getId();
            }
        }

        Hasher hasher = new PBKDF2Hasher();
        boolean checkPass;
        if (!formPassword.isEmpty() && !password.isEmpty())
            checkPass = hasher.checkPassword(formPassword, password);
        else checkPass = false;

        if (login.equals(formLogin) && checkPass && !formLogin.isEmpty() && !formPassword.isEmpty() && !status.equals("Nieaktywny")) {
            HttpSession session = request.getSession();
            session.setAttribute("user", request.getParameter("user"));
            session.setMaxInactiveInterval(30 * 60);
            Cookie loginCookie = new Cookie("userCookie", formLogin);
            loginCookie.setMaxAge(30 * 60);
            response.addCookie(loginCookie);
            session.setAttribute("nameOfUser", userName);
            session.setAttribute("userId", userId);
            if (!admin) {
                session.setAttribute("normalUser", "normalUser");
                session.setAttribute("nameOfUser", userName);
            }
            response.sendRedirect("CreateStatsServlet");
        } else {
            response.sendRedirect("index.jsp");
            HttpSession session = request.getSession();
            session.setAttribute("loginFalse", "loginFalse");
        }
    }
}