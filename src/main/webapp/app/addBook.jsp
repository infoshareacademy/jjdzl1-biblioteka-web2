<%@ page import="com.infoshare.logic.validation.BookValidator" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pl">

<head>
    <%@include file="/include/head.jsp" %>
</head>
<body>
<header>
    <%@include file="../include/appHeader.jsp" %>
</header>


<div class="addUserForm">
    <%
        List<String> validationResult = BookValidator.validationResult;
    %>

    <% if (validationResult.size() > 0) { %>
    <br/><br/><br/>
    <ul class="list-group" style="vertical-align: center">
        <%
            for (String s : validationResult) {
        %>
        <li>
            <%=s%>
        </li>
        <%}%>
    </ul>
    <%}%>
    <br/><br/>
    <h4>Dodaj książkę</h4>
    <br/>
    <form method="POST" action="AddBookServlet" class="addUser" >

        <div>
            <div>
                <input type="text" class="form-control" name="title" placeholder="Tytuł książki">
            </div>
        </div>
        <br/>
        <div class="form-row">
            <div>
                <input type="text" class="form-control" name="lastName" placeholder="Nazwisko autora">
            </div>
            <div>&nbsp;</div>
            <div>
                <input type="text" class="form-control" name="firstName" placeholder="Imię autora">
            </div>
        </div>
        <br/>
        <div class="form-row">
            <div>
                <input type="number"  class="form-control no-spin" name="isbn" placeholder="Numer ISBN (tylko cyfry)">
            </div>
            <div>&nbsp;</div>
            <div>
                <input type="number" class="form-control no-spin" name="daterelease"
                       placeholder="Rok wydania (tylko cyfry)">
            </div>
        </div>
        <br/>
        <div>
            <div>
                <textarea name="description" class="form-control" placeholder="Opis książki"rows="5"></textarea>
            </div>
        </div>
        <br/>
        <br/>
        <button type="submit" class="btn btn-primary">Dodaj książkę</button>
    </form>
</div>

<footer>
<%@include file="../include/footer.jsp" %>
</footer>

</body>
</html>