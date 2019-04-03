package com.infoshare.servlets;

import com.infoshare.query.StatsQuery;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/RefreshStatsServlet")
public class RefreshStatsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            StatsQuery.generateStats();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("loginSuccess.jsp");

    }
}
