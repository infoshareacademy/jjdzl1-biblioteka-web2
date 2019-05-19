package com.infoshare.servlets;

import com.infoshare.logic.repository.OperationsRepositoryDao;
import com.infoshare.logic.repository.StatsRepositoryDao;

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

    @EJB
    private OperationsRepositoryDao operationsRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        operationsRepository.restoreAvailableBookStatus();
        operationsRepository.removeReservationFromDatabase();

        HttpSession session=req.getSession();
        session.setAttribute("stats", statsRepository.generateStats());

        resp.sendRedirect("app/loginSuccess.jsp");
    }
}
