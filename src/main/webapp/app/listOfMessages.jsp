<%@ page import="java.util.List" %>
<%@ page import="com.infoshare.logic.utils.RecordPerPage" %>
<%@ page import="com.infoshare.logic.domain.*" %>
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
<%Integer rowNumber = 1;%>
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
                    <th scope="col">Działania</th>
                    <%}%>
                </tr>
                </thead>
                <tbody>
                <%
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
                    <%
                        }
                        if (session.getAttribute("selectedUser") == null) {
                    %>
                    <%
                    } else if (session.getAttribute("selectedUser") != null) {
                        User user = (User) session.getAttribute("selectedUser");
                    %>
                    <td>
                        aaaa
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
    <%@include file="../include/footer.jsp" %>
</footer>

</body>
</html>