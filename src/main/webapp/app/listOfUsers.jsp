<%@ page import="com.infoshare.logic.domain.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.infoshare.logic.domain.UserStatus" %>
<%@ page import="com.infoshare.logic.utils.RecordPerPage" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pl">

<head>
    <%@include file="/./include/head.jsp" %>
</head>
<body>
<header>
    <%@include file="/./include/appHeader.jsp" %>
</header>
<%
    String operation = request.getParameter("operation");
    String pageString = request.getParameter("page");
    Integer pages = Integer.parseInt(request.getParameter("pages"));
    Integer recordsPerPage= RecordPerPage.readProperties();

    if (pageString == null || pageString.isEmpty()) pageString = "1";
    int pageNumber = Integer.parseInt(pageString);
    if (session.getAttribute("normalUser") == null) {
%>

<article>
    <div class="content">
        <div class="contentInside">
            <br/>

            <div class="d-flex">

                <div class="mr-auto p-2 align-items-start">
                    <% if (operation != null && !operation.isEmpty() && operation.equals("newoperation")) {%>
                    <h4> Nowa operacja: wybierz użytkownika</h4>
                    <%} else if (operation != null && !operation.isEmpty() && operation.equals("returnbook")) {%>
                    <h4> Zwrot książki: wybierz użytkownika</h4>
                    <%} else {%>
                    <h4>Kliknij użytkownika, którego chcesz edytować</h4>
                    <%}%>
                </div>

                <div class="p2 align-items-end ">

                    <form action="FindUserServlet" class="form-inline" method="get">
                        <div class="form-row align-items-center">
                            <div class="col-auto inline-block">
                                <input type="text" name="findUserByName" class="form-control" id="inlineFormInput1"
                                       placeholder="Wpisz nazwisko użytkownika">

                                <%if (operation != null && !operation.isEmpty() && operation.equals("newoperation")) {%>
                                <input type="hidden" name="operation" value="newoperation">
                                <%}%>

                                <button type="submit" class="btn btn-outline-info">Szukaj</button>
                            </div>
                        </div>
                    </form>

                </div>

            </div>
            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-end">

                    <%if (pageNumber == 1) {%>
                    <li class="page-item disabled">
                            <%}else{%>
                    <li class="page-item">
                        <%}%>
                        <a class="page-link"
                           href="GetAttributesUserRepository?page=<%=pageNumber-1%>"
                           tabindex="-1">Wcześniejsza</a>
                    </li>
                    <%if (pageNumber < pages) {%>
                    <li class="page-item">
                        <a class="page-link"
                           href="GetAttributesUserRepository?page=<%=pageNumber+1%>">Następna</a>
                    </li>
                    <%} else {%>
                    <li class="page-item disabled">
                        <a class="page-link"
                           href="GetAttributesUserRepository?page=<%=pageNumber+1%>">Następna</a>
                    </li>
                    <%}%>
                </ul>
            </nav>
            <br/>
            <table class="table table-bordered table-hover">
                <thead>
                <tr class="listofitemps">
                    <th scope="col">#</th>
                    <th scope="col"> Login</th>
                    <th scope="col"> Nazwisko, Imię</th>
                    <th scope="col"> Email</th>
                    <th scope="col"> Administrator</th>
                    <th scope="col"> Status</th>
                    <%if (operation != null && !operation.isEmpty()) {%>
                    <th scope="col">Działanie</th>
                    <%}%>

                </tr>
                </thead>

                <tbody>
                <%
                    List<User> listOfUsers = (List<User>) request.getSession().getAttribute("userRepositoryDao");
                    int rowNumber = 1 + (pageNumber * recordsPerPage) - recordsPerPage;
                    for (User user : listOfUsers) {
                %>
                <tr class="listofitemps" style="cursor:pointer"
                    onclick="window.location='GetUserToEditServlet?userId=<%=user.getId()%>';">
                    <th scope="row"><%=rowNumber%>
                    </th>
                    <td><%= user.getLogin()%>
                    </td>
                    <td><%=  user.getLastName() + ", " + user.getFirstName()%>
                    </td>
                    <td><%= user.getEmail()%>
                    </td>
                    <td>
                        <%if (user.getAdmin().equals(UserStatus.ADMIN)) {%> Administrator <%}%>
                        <%if (user.getAdmin().equals(UserStatus.USER)) {%> Użytkownik <%}%>
                    </td>
                    <td>
                        <%if (user.getStatus().equals("Nieaktywny")) {%>
                        <p class="text-danger"><%=user.getStatus()%>
                        </p>
                        <%} else {%>
                        <%= user.getStatus()%>
                        <%}%>
                    </td>

                    <%if (operation != null && !operation.isEmpty() && operation.equals("newoperation")) {%>
                    <td>
                        <form method="GET" action="SelectUserServlet" class="addUser">
                            <input type="hidden" name="userid" value="<%=user.getId()%>"/>
                            <input type="hidden" name="operation" value="newoperation"/>
                            <%if (user.getStatus().equals("Nieaktywny")) {%>
                            <button type="submit" class="btn btn-secondary btn-sm" disabled>Wyłączone</button>
                            <%} else {%>
                            <button type="submit" class="btn btn-success btn-sm">Wybierz</button>
                            <%}%>
                        </form>
                    </td>
                    <%} else if (operation != null && !operation.isEmpty() && operation.equals("returnbook")) {%>
                    <td>
                        <form method="GET" action="SelectUserServlet" class="addUser">
                            <input type="hidden" name="userid" value="<%=user.getId()%>"/>
                            <input type="hidden" name="operation" value="returnbook"/>
                            <%if (user.getStatus().equals("Nieaktywny")) {%>
                            <button type="submit" class="btn btn-secondary btn-sm" disabled>Wyłączone</button>
                            <%} else {%>
                            <button type="submit" class="btn btn-success btn-sm">Wybierz</button>
                            <%}%>
                        </form>
                    </td>
                    <%}%>
                </tr>
                <% rowNumber++;
                }%>
                </tbody>

            </table>
            <br/><br/><br/>
        </div>
    </div>
</article>
<%}%>
<footer>
    <%@include file="/./include/footer.jsp" %>
</footer>

</body>
</html>