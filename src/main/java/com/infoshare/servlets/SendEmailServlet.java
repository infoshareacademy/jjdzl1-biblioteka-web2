package com.infoshare.servlets;

import com.infoshare.logic.domain.Operation;
import com.infoshare.logic.domain.User;
import com.infoshare.logic.repository.OperationsRepositoryDao;
import com.infoshare.logic.repository.UsersRepositoryDao;
import com.infoshare.logic.utils.CalculateFeeToPay;
import com.infoshare.logic.utils.GoogleMail;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/SendEmailServlet")
public class SendEmailServlet extends HttpServlet {

    @EJB
    private OperationsRepositoryDao operationsRepository;

    @EJB
    private UsersRepositoryDao usersRepository;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer operationId = Integer.parseInt(req.getParameter("operation"));
        String operationType = req.getParameter("operationType");

        Operation operation = null;
        User user = null;

        try {
            operation = operationsRepository.getOperation(operationId);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        LocalDate endDate = operation.getStartDate().plusDays(30);
        Integer days = CalculateFeeToPay.getDays(operation.getStartDate()).getDays();
        BigDecimal payForBorrow = CalculateFeeToPay.calculateFeeToPay(operation.getStartDate(), endDate);

        try {
            user = usersRepository.getUserById(operation.getUser().getId());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String message = new StringBuilder()
                .append("Dzień dobry ").append(user.getFirstName()).append(" ").append(user.getLastName()).append("<br/>")
                .append("Informujemy żę wypożyczona przez Ciebie książka: ").append(operation.getBookTitle())
                .append(" ma przekroczony czas wypożyczenia o ").append(days).append(" dni. ")
                .append("Na dzień dzisiajszy tj:").append(LocalDate.now())
                .append(" książka jest przetrzymana o ").append(days).append(" dni i ")
                .append("została naliczona kara umowna w kwocie: ")
                .append(payForBorrow).append(" zł").append(".</br>")
                .append("Za każdy dzień zwłoki zwrotu książki doliczona zostanie dodatkowa opłata")
                .toString();

        String sendEmailAttribute = "Wysłano powiadomienie do " + operation.getUserName();
        req.getSession().setAttribute("sendEmail", sendEmailAttribute);

        String sendTo = user.getEmail();
        String bookTitle = operation.getBookTitle();

        // zakomentowane żeby nie rozsyłać maili na nieistniejące konta
        //GoogleMail.sendMail(sendTo, message, bookTitle);

        resp.sendRedirect("GetAttributeOperationRepository?operationType=" + operationType);
    }
}
