<%@ page import="java.util.List" %>
<%@ page import="com.infoshare.domain.Book" %>
<%@ page import="com.infoshare.domain.BookStatus" %>
<%@ page import="com.infoshare.utils.RecordPerPage" %>
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
    String edit = request.getParameter("edit");

    if (pageString == null || pageString.isEmpty()) pageString = "1";
    int pageNumber = Integer.parseInt(pageString);
    String titleOfBook = request.getParameter("titleOfBook");

    String orderTitle;
    int recordsPerPage = RecordPerPage.readProperties();

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
                                <a class="page-link"
                                   href="GetAttributeBookRepository?order=<%=order%>&page=<%=pageNumber-1%>"
                                   tabindex="-1">Wcześniejsza</a>
                            </li>
                            <li class="page-item"><a class="page-link"
                                                     href="GetAttributeBookRepository?order=<%=order%>&page=<%=pageNumber%>"><%=pageNumber%>
                            </a></li>
                            <li class="page-item"><a class="page-link"
                                                     href="GetAttributeBookRepository?order=<%=order%>&page=<%=pageNumber+1%>"><%=pageNumber + 1%>
                            </a></li>
                            <li class="page-item"><a class="page-link"
                                                     href="GetAttributeBookRepository?order=<%=order%>&page=<%=pageNumber+2%>"><%=pageNumber + 2%>
                            </a></li>
                            <li class="page-item">
                                <a class="page-link"
                                   href="GetAttributeBookRepository?order=<%=order%>&page=<%=pageNumber+1%>">Następna</a>
                            </li>
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
                    <% if (session.getAttribute("selectedUser") != null) {
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
//                    request.getSession().removeAttribute("bookList");
                    for (Book book : listOfBooks) {
                %>
                <tr class="listofitemps " style="cursor:pointer"
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
                                <button type="submit" class="btn btn-success" data-toggle="tooltip"
                                        title="Wypożycz">W
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
            <br/><br/><br/>
        </div>
    </div>
</article>

<footer>
    <%@include file="../include/footer.jsp" %>
</footer>

</body>
</html>