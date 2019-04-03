package com.infoshare.servlets;

import com.infoshare.dao.DBCon;
import com.infoshare.utils.Hasher;
import com.infoshare.utils.PBKDF2Hasher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet implements Serializable {
    private static final long serialVersionUID = 5384381614337933147L;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");

        String query = "SELECT * FROM users WHERE login =?";

        String login = "";
        String password = "";
        PreparedStatement ps;
        Boolean admin = true;
        String status = "";
        try {
            ps = DBCon.preparedStatement(query);
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            String userName = "";
            while (rs.next()) {
                login = rs.getString("login");
                password = rs.getString("password");
                admin = rs.getBoolean("admin");
                status = rs.getString("status");
                userName = rs.getString("firstName") + ", " + rs.getString("lastName");
            }
            rs.close();

            Hasher hasher = new PBKDF2Hasher();
            boolean checkPass;
            if (!pwd.isEmpty() && !password.isEmpty())
                checkPass = hasher.checkPassword(pwd, password);
            else checkPass = false;

            if (login.equals(user) && checkPass && !user.isEmpty() && !pwd.isEmpty() && !status.equals("Nieaktywny")) {
                HttpSession session = request.getSession();
                session.setAttribute("user", request.getParameter("user"));
                session.setMaxInactiveInterval(30 * 60);
                Cookie loginCookie = new Cookie("userCookie", user);
                loginCookie.setMaxAge(30 * 60);
                response.addCookie(loginCookie);
                session.setAttribute("nameOfUser", userName);
                if (!admin) {
                    session.setAttribute("normalUser", "normalUser");
                    session.setAttribute("nameOfUser", userName);
                }
                response.sendRedirect("app/loginSuccess.jsp");
            } else {
                response.sendRedirect("index.jsp");
                HttpSession session = request.getSession();
                session.setAttribute("loginFalse", "loginFalse");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}