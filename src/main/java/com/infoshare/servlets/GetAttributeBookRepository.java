package com.infoshare.servlets;

import com.infoshare.domain.Book;
import com.infoshare.repository.BooksRepositoryDao;

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
        resp.sendRedirect("listOfBooks.jsp?order=title&titleOfBook=" + titleOfBook);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String order = req.getParameter("order");
        String page= req.getParameter("page");

        if (page == null) page = "1";
        if (order==null) order="title";
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
        resp.sendRedirect("listOfBooks.jsp?order=" + order + "&page=" + page);
    }
}