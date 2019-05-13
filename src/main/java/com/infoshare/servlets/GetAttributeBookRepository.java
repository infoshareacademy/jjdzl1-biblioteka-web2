package com.infoshare.servlets;

import com.infoshare.logic.domain.Book;
import com.infoshare.logic.repository.BooksRepositoryDao;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/GetAttributeBookRepository")
public class GetAttributeBookRepository extends HttpServlet {
    @EJB
    private BooksRepositoryDao booksRepositoryDao;
    private List<Book> bookList = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String titleOfBook = req.getParameter("title");
        try {
            bookList = booksRepositoryDao.getBookByTitle(titleOfBook);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        HttpSession session = req.getSession();
        session.setAttribute("bookList", bookList);
        session.setAttribute("booksRepositoryDao", booksRepositoryDao);

        if (session.getAttribute("nameOfUser") != null) {
            resp.sendRedirect(req.getContextPath() + "/app/listOfBooks.jsp?order=title&titleOfBook=" + titleOfBook);
        } else {
            resp.sendRedirect("listOfBooks.jsp?order=title&titleOfBook=" + titleOfBook);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String order = req.getParameter("order");
        String page = req.getParameter("page");
        String edit = req.getParameter("edit");
        String reservation = req.getParameter("reservation");

        if (page == null) page = "1";
        if (order == null) order = "title";
        if (edit == null || edit.isEmpty()) edit = "false";
        if (reservation == null) reservation = "";
        try {
            bookList = booksRepositoryDao.bookList(order, Integer.parseInt(page));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        HttpSession session = req.getSession();
        session.setAttribute("bookList", bookList);
        session.setAttribute("booksRepositoryDao", booksRepositoryDao);

        if (session.getAttribute("nameOfUser") != null && !reservation.equals("user")) {
            resp.sendRedirect(req.getContextPath() + "/app/listOfBooks.jsp?order=" + order + "&page=" + page + "&edit=" + edit);
        } else if (reservation.equals("user")) {
            resp.sendRedirect("listOfBooks.jsp?order=" + order + "&page=" + page + "&reservation=" + reservation);
        } else
            resp.sendRedirect("listOfBooks.jsp?order=" + order + "&page=" + page);

    }
}
