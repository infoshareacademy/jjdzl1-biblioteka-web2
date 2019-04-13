<%@ page import="java.util.List" %>
<%@ page import="com.infoshare.domain.Book" %>
<%@ page import="com.infoshare.domain.BookStatus" %>
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
    String titleOfBook = request.getParameter("titleOfBook");
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
                <thead>
                <tr class="listofitemps">
                    <th scope="col">#</th>
                    <th scope="col">Tytuł</th>
                    <th scope="col">Autor</th>
                    <th scope="col">Nr ISBN</th>
                    <th scope="col">Rok wydania</th>
                    <% if (session.getAttribute("selectedUser") != null) {
                        User user = (User) session.getAttribute("selectedUser");
                    %>
                    <th scope="col">Działania</th>
                    <%}%>
                </tr>
                </thead>
                <tbody>
                <%
                    int rowNumber = 1;
                    List<Book> listOfBooks = (List<Book>) request.getSession().getAttribute("bookRepositoryDao");
                    for (Book book : listOfBooks) {
                %>
                <tr class="listofitemps " style="cursor:pointer"
                    onclick="window.location='bookDescription.jsp?id=<%=book.getId()%>'" data-toggle="tooltip"
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

                    <% if (session.getAttribute("selectedUser") != null) {
                        User user = (User) session.getAttribute("selectedUser");

                        if (book.getStatus() != BookStatus.Dostępna) {%>
                    <td>
                        <button type="submit" class="btn btn-secondary btn-sm" disabled><%=book.getStatus()%>
                        </button>
                    </td>
                    <%} else {%>
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
                                <button type="submit" class="btn btn-success" data-toggle="tooltip" title="Wypożycz">W
                                </button>
                            </form>
                        </div>
                    </td>
                    <%
                            }
                        }
                    %>
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
    <%@include file="../include/footer.jsp" %>
</footer>

</body>
</html>