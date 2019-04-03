package com.infoshare.servlets;

import com.infoshare.dao.DBCon;
import com.infoshare.domain.User;
import com.infoshare.domain.UserStatus;
import com.infoshare.query.UsersQuery;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet implements Serializable {
    private static final long serialVersionUID = 2911687195703070806L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("UserObject");
        String userLogin = user.getLogin();

        String login = req.getParameter("login");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("e-mail");
        String[] admin = req.getParameterValues("adminUser");
        Boolean checkAdmin = admin != null ? admin[0].equals("1") :  false;
        UserStatus enumAdmin = admin[0].equals("1") ? UserStatus.ADMIN : UserStatus.USER;
        String status = req.getParameter("status");
        if (status.equals("1"))
        status = "Aktywny";
        else status = "Nieaktywny";

        User editedUser = new User(null,login, firstName, lastName, null, email, enumAdmin, status);
        try {
            UsersQuery.updateUserQuery(userLogin, editedUser);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        req.removeAttribute("UserObject");
        req.getSession().setAttribute("userEdited", "userEdited");
        if (req.getSession().getAttribute("user") != null)
            resp.sendRedirect("loginSuccess.jsp");
        else
            resp.sendRedirect("index.jsp");
    }
}