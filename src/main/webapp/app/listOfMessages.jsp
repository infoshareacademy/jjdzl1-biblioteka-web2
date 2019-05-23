<%@ page import="java.util.List" %>
<%@ page import="com.infoshare.logic.domain.*" %>
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
    String pageString = request.getParameter("page");
    Integer pages = Integer.parseInt(request.getParameter("pages"));
    Integer recordsPerPage = Integer.parseInt(ReadProperties.readPropertie("records-per-page"));

    if (pageString == null || pageString.isEmpty()) pageString = "1";
    int pageNumber = Integer.parseInt(pageString);

%>

<article>
    <div class="content">
        <div class="contentInside">

            <div class="d-flex">
                <div class="mr-auto p-2 align-items-start">
                    <br/>
                    <h4>Wysłane powiadomienia:
                    </h4>
                </div>
                <div class="p-2 align-items-end ">
                    <nav aria-label="Page navigation example">
                        <ul class="pagination justify-content-end">

                            <%if (pageNumber == 1) {%>
                            <li class="page-item disabled">
                                    <%}else{%>
                            <li class="page-item">
                                <%}%>
                                <a class="page-link"
                                   href="GetAttributeMessageRepository?page=<%=pageNumber-1%>"
                                   tabindex="-1">Wcześniejsza</a>
                            </li>
                            <%if (pageNumber == pages) {%>
                            <li class="page-item disabled">
                                <a class="page-link"
                                   href="GetAttributeMessageRepository?page=<%=pageNumber+1%>">Następna</a>
                            </li>
                            <%} else {%>
                            <li class="page-item">
                                <a class="page-link"
                                   href="GetAttributeMessageRepository?page=<%=pageNumber+1%>">Następna</a>
                            </li>
                            <%}%>
                        </ul>
                    </nav>
                    <br/>
                </div>
            </div>
            <table class="table table-bordered table-hover">
                <thead>
                <tr class="listofitemps">
                    <th scope="col">#</th>
                    <th scope="col">Użytkownik / Książka</th>
                    <th scope="col">Data powiadomienia</th>
                    <th scope="col">Data wypożyczenia</th>
                    <th scope="col">Data max zwrotu</th>
                    <th scope="col">Liczba dni opóźnienia</th>
                    <th scope="col">Naliczona opłata</th>
                    <% if (session.getAttribute("selectedUser") != null) {
                        User user = (User) session.getAttribute("selectedUser");
                    %>
                    <%}%>
                </tr>
                </thead>
                <tbody>
                <%
                    int rowNumber = 1 + (pageNumber * recordsPerPage) - recordsPerPage;
                    List<Message> messageList = (List<Message>) request.getSession().getAttribute("messageList");
                    for (Message message : messageList) {
                %>

                <tr class="listofitemps" title="<%=message.getMessage().replaceAll("<br/>"," ")%>">
                    <th scope="row"><%=rowNumber%>
                    </th>
                    <td>
                        <strong><%=message.getOperation().getUserName()%>
                        </strong> (<%=message.getOperation().getUser().getEmail()%>)<br/>
                        <i><%=message.getOperation().getBook().getTitle()%>
                        </i>
                    </td>
                    <td>
                        <%=message.getDate()%>
                    </td>
                    <td>
                        <%=message.getOperation().getStartDate()%>
                    </td>
                    <td>
                        <%=message.getOperation().getStartDate().plusDays(30)%>
                    </td>
                    <td>
                        <%=message.getDayOfBorrowDelay()%> dni
                    </td>
                    <td>
                        <%=message.getPayForBorrow()%> zł
                    </td>
                    <% rowNumber++;
                    }
                        if (session.getAttribute("selectedUser") == null) {
                    %>
                    <%
                    } else if (session.getAttribute("selectedUser") != null) {
                        User user = (User) session.getAttribute("selectedUser");
                    %>
                </tr>
                <%}%>
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