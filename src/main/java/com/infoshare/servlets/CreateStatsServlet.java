package com.infoshare.servlets;

import com.infoshare.repository.StatsRepositoryDao;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/CreateStatsServlet")
public class CreateStatsServlet extends HttpServlet {

    @EJB
    private StatsRepositoryDao statsRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session=req.getSession();
        session.setAttribute("stats", statsRepository.generateStats());

        resp.sendRedirect("app/loginSuccess.jsp");
    }
}