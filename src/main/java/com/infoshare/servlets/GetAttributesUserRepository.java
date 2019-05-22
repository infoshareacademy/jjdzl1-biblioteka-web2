package com.infoshare.servlets;

import com.infoshare.logic.domain.User;
import com.infoshare.logic.repository.StatsRepositoryDao;
import com.infoshare.logic.repository.UsersRepositoryDao;
import com.infoshare.logic.utils.ReadProperties;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@WebServlet("/GetAttributesUserRepository")
public class GetAttributesUserRepository extends HttpServlet {

    @EJB
    private UsersRepositoryDao usersRepositoryDao;

    @EJB
    private StatsRepositoryDao statsRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String opertation = req.getParameter("operation");
        String findUserByName = req.getParameter("findUserByName");
        String page = req.getParameter("page");
        if (page == null) page = "1";

        //        Integer pages = null;
//        Integer recordsPerPage = Integer.parseInt(ReadProperties.readPropertie("records-per-page"));

        BigDecimal pages = null;

        BigDecimal recordsPerPage = new BigDecimal(Integer.parseInt(ReadProperties.readPropertie("records-per-page")));
        BigDecimal countAllBooks = new BigDecimal(Integer.parseInt(statsRepository.countUsers("all")));

        pages = countAllBooks.divide(recordsPerPage, 0, RoundingMode.UP);

        List<User> userList = new ArrayList<>();
        try {
            userList = usersRepositoryDao.listOfUsers(findUserByName, Integer.parseInt(page));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        HttpSession session = req.getSession();
        session.setAttribute("userRepositoryDao", userList);

        if (opertation != null && opertation.equals("newoperation")) {
            resp.sendRedirect("listOfUsers.jsp?operation=newoperation&page=" + page + "&pages=" + pages);
        } else if (opertation != null && opertation.equals("returnbook")) {
            resp.sendRedirect("listOfUsers.jsp?operation=returnbook&page=" + page + "&pages=" + pages);
        } else {
            resp.sendRedirect("listOfUsers.jsp?page=" + page + "&pages=" + pages);
        }
    }
}
