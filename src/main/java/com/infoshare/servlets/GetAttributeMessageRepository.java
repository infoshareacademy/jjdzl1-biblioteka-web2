package com.infoshare.servlets;

import com.infoshare.logic.domain.Message;
import com.infoshare.logic.repository.MessageRepositoryDao;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/GetAttributeMessageRepository")
public class GetAttributeMessageRepository extends HttpServlet {

    @EJB
    private MessageRepositoryDao messageRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String stringPage = req.getParameter("page");
        Integer page = 1;
        if (stringPage != null) page = Integer.parseInt(stringPage);

        BigDecimal pages = null;
        Integer userId = null;

        if (req.getSession().getAttribute("normalUser") != null &&
                req.getSession().getAttribute("normalUser").equals("normalUser")) {
            userId = (Integer) req.getSession().getAttribute("userId");
        }

        BigDecimal recordsPerPage = new BigDecimal(Integer.parseInt(ReadProperties.readPropertie("records-per-page")));
        BigDecimal countAllMessage = null;

        List<Message> messageList = new ArrayList<>();
        if (userId == null) {
            messageList = messageRepository.getMessage(null, page);
            countAllMessage = new BigDecimal(messageRepository.countMessage(null));

        } else {
            messageList = messageRepository.getMessage(userId, page);
            countAllMessage = new BigDecimal(messageRepository.countMessage(userId));
        }

        pages = countAllMessage.divide(recordsPerPage, 0, RoundingMode.UP);
        if (pages.equals(new BigDecimal(0))) pages = new BigDecimal(1);

        HttpSession session = req.getSession();
        session.setAttribute("messageList", messageList);

        resp.sendRedirect("listOfMessages.jsp?pages=" + pages + "&page=" + page);
    }
}
