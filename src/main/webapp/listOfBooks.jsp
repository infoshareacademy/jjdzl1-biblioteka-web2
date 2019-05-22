<%@ page import="java.util.List" %>
<%@ page import="com.infoshare.logic.domain.Book" %>
<%@ page import="com.infoshare.logic.utils.RecordPerPage" %>
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
    String pageString = request.getParameter("page");
    Integer pages=Integer.parseInt(request.getParameter("pages"));

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
                            <%if ((pageNumber+1) > pages) {%>
                            <li class="page-item disabled">
                                <a class="page-link"
                                   href="GetAttributeBookRepository?order=<%=order%>&page=<%=pageNumber+1%>">Następna</a>
                            </li>
                            <%}else{%>
                            <li class="page-item">
                                <a class="page-link"
                                   href="GetAttributeBookRepository?order=<%=order%>&page=<%=pageNumber+1%>">Następna</a>
                            </li>
                            <%}%>
                        </ul>
                    </nav>
                </div>
            </div>
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
                    int rowNumber = 1 + (pageNumber * recordsPerPage) - recordsPerPage;
                    List<Book> listOfBooks = (List<Book>) request.getSession().getAttribute("bookList");
                    for (Book book : listOfBooks) {
                %>

                <tr class="listofitemps" style="cursor:pointer"
                    onclick="window.location='bookDescription.jsp?id=<%=book.getId()%>'" data-toggle="tooltip"
                    title="Zobacz więcej ...">
                    <th scope="row"><%=rowNumber%>
                    </th>
                    <td><%=book.getTitle()%>
                    </td>
                    <td><%=book.getAuthorLastName() + ", " + book.getAuthorFirstName()%>
                    </td>

                    <td><%=book.getIsbn()%>
                    </td>
                    <td><%=book.getDaterelease()%>
                    </td>
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
    <%@include file="include/footer.jsp" %>
</footer>

</body>
</html>