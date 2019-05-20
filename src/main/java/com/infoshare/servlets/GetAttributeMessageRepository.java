package com.infoshare.servlets;

import com.infoshare.logic.domain.Message;
import com.infoshare.logic.repository.MessageRepositoryDao;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/GetAttributeMessageRepository")
public class GetAttributeMessageRepository extends HttpServlet {

    @EJB
    private MessageRepositoryDao messageRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userId = req.getParameter("userId");

        List<Message> messageList = new ArrayList<>();
        if (userId == null) {
            messageList = messageRepository.getMessage(null);
        } else {
            messageList = messageRepository.getMessage(Integer.parseInt(userId));
        }

        HttpSession session = req.getSession();
        session.setAttribute("messageList", messageList);

        resp.sendRedirect("listOfUsers.jsp");
    }
}



