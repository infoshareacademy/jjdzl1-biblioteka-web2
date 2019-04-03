<%@ page import="com.infoshare.repository.BooksRepositoryDao" %>
<%@ page import="com.infoshare.repository.BooksRepositoryDaoBean" %>
<%@ page import="java.util.List" %>
<%@ page import="com.infoshare.domain.Book" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pl">

<head>
    <%@include file="include/head.jsp" %>
</head>
<body>
<header>
    <%@include file="include/header.jsp" %>
</header>


<%
    String order = request.getParameter("order");
    String bookTitle = request.getParameter("title");
    String orderTitle;
    if (order == null || order.isEmpty() || order.equals("title")) {
        orderTitle = " (wg tytułu)";
        order = "title";
    } else orderTitle = " (wg autora)";
%>
<article>
    <div class="content">
        <div class="contentInside">
            <br/>
            <h4>Spis książek<%=orderTitle%>
            </h4>
            <table class="table table-bordered table-hover">
                <thead class="listofitemps">
                <tr class="black white-text">
                    <th scope="col">#</th>
                    <th scope="col">Tytuł</th>
                    <th scope="col">Autor</th>
                    <th scope="col">Nr ISBN</th>
                    <th scope="col">Rok wydania</th>
                </tr>
                </thead>
                <tbody>
                <%
                    int rowNumber = 1;
                    BooksRepositoryDao booksRepository = new BooksRepositoryDaoBean();
                    List<Book> listOfBooks = booksRepository.bookList(bookTitle, order);
                    for (Book book : listOfBooks) {
                %>

                <tr class="listofitemps" style="cursor:pointer"
                    onclick="window.location='bookDescription.jsp?id=<%=book.getBookID()%>'" data-toggle="tooltip"
                    title="Zobacz więcej ...">
                    <th scope="row"><%=rowNumber%>
                    </th>
                    <td><%=book.getTitle()%>
                    </td>
                    <td><%=book.getAuthorLastName() + ", " + book.getAuthorFirstName()%>
                    </td>

                    <td><%=book.getIsbn()%>
                    </td>
                    <td><%=book.getRelaseDate()%>
                    </td>
                </tr>
                <%
                        rowNumber++;
                    }%>
                </tbody>
            </table>
            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-end">
                    <li class="page-item disabled">
                        <a class="page-link" href="#" tabindex="-1">Previous</a>
                    </li>
                    <li class="page-item"><a class="page-link" href="#">1</a></li>
                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                    <li class="page-item">
                        <a class="page-link" href="#">Next</a>
                    </li>
                </ul>
            </nav>
            <br/><br/><br/>
        </div>
    </div>
</article>

<footer>
    <%@include file="include/footer.jsp" %>
</footer>

</body>
</html>