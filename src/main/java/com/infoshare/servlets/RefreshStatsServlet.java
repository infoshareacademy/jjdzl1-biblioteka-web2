package com.infoshare.servlets;

import com.infoshare.logic.repository.StatsRepositoryDao;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RefreshStatsServlet")
public class RefreshStatsServlet extends HttpServlet {

    @EJB
    private StatsRepositoryDao statsRepositoryDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        statsRepositoryDao.generateStats();
        resp.sendRedirect("loginSuccess.jsp");

    }
}