package com.infoshare.servlets;

import com.infoshare.logic.domain.Operation;
import com.infoshare.logic.repository.OperationsRepositoryDao;
import com.infoshare.logic.repository.StatsRepositoryDao;
import com.infoshare.logic.utils.ReadProperties;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/GetAttributeOperationRepository")
public class GetAttributeOperationRepository extends HttpServlet {

    @EJB
    private OperationsRepositoryDao operationsRepository;

    @EJB
    private StatsRepositoryDao statsRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Operation> operationsList = new ArrayList<>();
        String operationType = req.getParameter("operationType");
        String stringUserId = req.getParameter("userId");
        String stringFirstDate = req.getParameter("firstDate");
        String stringLastDate = req.getParameter("lastDate");
        Integer userId = null;
        if (stringUserId != null) userId = Integer.parseInt(stringUserId);
        String stringPage = req.getParameter("page");
        Integer page = null;
        if (stringPage == null) {
            stringPage = "1";
        }
        page = Integer.parseInt(stringPage);
        BigDecimal pages = null;
        Integer countOperation = null;

        LocalDate firstDate = null;
        LocalDate lastDate = null;

        if (stringFirstDate != null && stringLastDate != null) {
            firstDate = LocalDate.parse(req.getParameter("firstDate"));
            lastDate = LocalDate.parse(req.getParameter("lastDate"));
        }

        if (operationType == null || operationType.isEmpty()) operationType = "all";

        try {
            countOperation = operationsRepository.countAllOperationList(operationType, stringUserId, firstDate, lastDate);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        BigDecimal recordsPerPage = new BigDecimal(Integer.parseInt(ReadProperties.readPropertie("records-per-page")));
        pages = new BigDecimal(countOperation).divide(recordsPerPage, 0, RoundingMode.UP);

        try {
            operationsList = operationsRepository.AllOperationList(operationType, stringUserId, firstDate, lastDate, page);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        HttpSession session = req.getSession();
        session.setAttribute("operationRepositoryDao", operationsList);

        String redirect = "listOfOperations.jsp?operationType=" + operationType + "&pages=" + pages + "&page=" + page;
        if (userId != null) {
            redirect += "&userId=" + userId;
        }
        if (lastDate != null && firstDate != null) {
            redirect += "&firstDate=" + firstDate + "&lastDate=" + lastDate + "&pages=" + pages + "&page=" + page;
        }

        resp.sendRedirect(redirect);
    }
}

