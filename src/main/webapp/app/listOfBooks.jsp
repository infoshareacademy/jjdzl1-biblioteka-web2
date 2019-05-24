<%@ page import="java.util.List" %>
<%@ page import="com.infoshare.logic.domain.Book" %>
<%@ page import="com.infoshare.logic.domain.BookStatus" %>
<%@ page import="com.infoshare.logic.utils.ReadProperties" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pl">

<head>
    <%@include file="../include/head.jsp" %>
</head>
<body>
<header>
    <%@include file="../include/appHeader.jsp" %>
</header>

<%
    String order = request.getParameter("order");
    String pageString = request.getParameter("page");
    String stringPages = request.getParameter("pages");
    if (stringPages == null) stringPages = "1";
    Integer pages = Integer.parseInt(stringPages);
    String edit = request.getParameter("edit");
    String reservation = request.getParameter("reservation");
    if (reservation == null)
        reservation = "";

    if (pageString == null || pageString.isEmpty()) pageString = "1";
    int pageNumber = Integer.parseInt(pageString);

    String orderTitle;
    int recordsPerPage = Integer.parseInt(ReadProperties.readPropertie("records-per-page"));

    if (order == null || order.isEmpty() || order.equals("title")) {
        orderTitle = " (wg tytułu)";
        order = "title";
    } else orderTitle = " (wg autora)";
%>
<article>
    <div class="content">
        <div class="contentInside">

            <div class="d-flex">
                <div class="mr-auto p-2 align-items-start">
                    <br/>
                    <h4>Spis książek<%=orderTitle%> Strona: <%=pageNumber%>
                    </h4>
                </div>
                <div class="p-2 align-items-end ">
                    <br/>
                    <nav aria-label="Page navigation example">
                        <ul class="pagination justify-content-end">

                            <%if (pageNumber == 1) {%>
                            <li class="page-item disabled">
                                    <%}else{%>
                            <li class="page-item">
                                <%}%>
                                <%if (reservation != null) {%>
                                <a class="page-link"
                                   href="GetAttributeBookRepository?order=<%=order%>&page=<%=pageNumber-1%>&reservation=user"
                                   tabindex="-1">Wcześniejsza</a>
                                <%} else {%>
                                <a class="page-link"
                                   href="GetAttributeBookRepository?order=<%=order%>&page=<%=pageNumber-1%>"
                                   tabindex="-1">Wcześniejsza</a>
                                <%}%>
                            </li>
                            <%if (pageNumber == pages) {%>
                            <li class="page-item disabled">
                                <a class="page-link"
                                   href="#">Następna</a>
                            </li>
                            <%} else {%>
                            <li class="page-item">
                                <%if (reservation != null) {%>
                                <a class="page-link"
                                   href="GetAttributeBookRepository?order=<%=order%>&page=<%=pageNumber+1%>&reservation=user">Następna</a>
                                <%} else {%>
                                <a class="page-link"
                                   href="GetAttributeBookRepository?order=<%=order%>&page=<%=pageNumber+1%>">Następna</a>
                                <%}%>

                            </li>
                            <%}%>
                        </ul>
                    </nav>
                </div>
            </div>

            <table class="table table-bordered table-hover">
                <thead>
                <tr class="listofitemps">
                    <th scope="col">#</th>
                    <th scope="col">Tytuł</th>
                    <th scope="col">Autor</th>
                    <th scope="col">Nr ISBN</th>
                    <th scope="col">Rok wydania</th>
                    <% if (session.getAttribute("selectedUser") != null || reservation.equals("user")) {
                        User user = (User) session.getAttribute("selectedUser");
                    %>
                    <th scope="col">Działania</th>
                    <%}%>
                </tr>
                </thead>
                <tbody>
                <%
                    int rowNumber = 1 + (pageNumber * recordsPerPage) - recordsPerPage;
                    List<Book> listOfBooks = (List<Book>) request.getSession().getAttribute("bookList");

                    for (Book book : listOfBooks) {
                %>

                <tr class="listofitemps" style="cursor:pointer"
                        <%if (edit != null && edit.equals("true")) {%>
                    onclick="window.location='EditBookServlet?id=<%=book.getId()%>'" data-toggle="tooltip"
                        <%} else if (edit == null || edit.isEmpty() || edit != "truegi") {%>
                    onclick="window.location='bookDescription.jsp?id=<%=book.getId()%>'" data-toggle="tooltip"
                        <%}%>
                    title="Zobacz więcej ...">
                    <th scope="row"><%=rowNumber%>
                    </th>
                    <td>
                        <%=book.getTitle()%>
                    </td>
                    <td>
                        <%=book.getAuthorLastName() + ", " + book.getAuthorFirstName()%>
                    </td>
                    <td>
                        <%=book.getIsbn()%>
                    </td>
                    <td>
                        <%=book.getDaterelease()%>
                    </td>

                    <% if (book.getStatus() != BookStatus.Dostępna &&
                            (reservation.equals("user") || session.getAttribute("selectedUser") != null)) {%>
                    <td>
                        <button type="submit" class="btn btn-secondary btn-sm" disabled><%=book.getStatus()%>
                        </button>
                    </td>
                    <%} else if (session.getAttribute("selectedUser") == null && reservation.equals("user")) { %>
                    <td>
                        <div>
                            <form method="POST" action="UserReservationServlet" class="addUser">
                                <input type="hidden" name="bookId" value="<%=book.getId()%>"/>
                                <button type="submit" class="btn btn-info" data-toggle="tooltip" title="Rezerwuj">
                                    Rezerwuj
                                </button>
                            </form>
                        </div>
                    </td>
                    <%
                    } else if (session.getAttribute("selectedUser") != null) {
                        User user = (User) session.getAttribute("selectedUser");
                    %>
                    <td>
                        <div>
                            <form method="GET" action="UserBasketServlet" class="addUser">
                                <input type="hidden" name="bookId" value="<%=book.getId()%>"/>
                                <input type="hidden" name="operationType" value="reservation"/>
                                <button type="submit" class="btn btn-info" data-toggle="tooltip" title="Rezerwuj">R
                                </button>
                            </form>
                            <form method="GET" action="UserBasketServlet" class="addUser">
                                <input type="hidden" name="bookId" value="<%=book.getId()%>"/>
                                <input type="hidden" name="operationType" value="borrow"/>
                                <button type="submit" class="btn btn-success" data-toggle="tooltip"
                                        title="Wypożycz">W
                                </button>
                            </form>
                        </div>
                    </td>
                    <%}%>
                </tr>
                <%
                        rowNumber++;
                    }%>
                </tbody>
            </table>
            <br/><br/><br/>
        </div>
    </div>
</article>

<footer>
    <%@include file="../include/footer.jsp" %>
</footer>

</body>
</html>