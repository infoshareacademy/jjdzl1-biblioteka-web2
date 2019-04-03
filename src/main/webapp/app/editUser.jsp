<%@ page import="com.infoshare.domain.User" %>
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


<article>
    <div class="addUserForm">
        <br/><br/><br/>
        <% User user = (User) request.getSession(true).getAttribute("UserObject");

            if (user != null) {
                int id = user.getId();
                String login = user.getLogin();
                String firstName = user.getFirstName();
                String lastName = user.getLastName();
                String email = user.getEmail();
        %>
        <h4>Edytuj użytkownika <%= firstName + " " + lastName%>


        </h4>
        <br/>
        <form method="POST" action="EditUserServlet" class="addUser">

            <div class="form-row">
                <div>
                    <input type="text" class="form-control" name="login" placeholder="Login" value="<%= login%>">
                </div>
                <div>&nbsp;</div>
                <div>
                    <input type="email" class="form-control" name="e-mail" placeholder="E-mail" value="<%= email%>">
                </div>
            </div>
            <br/>
            <div class="form-row">
                <div>
                    <input type="text" class="form-control" name="firstName" placeholder="Imię" value="<%= firstName%>">
                </div>
                <div>&nbsp;</div>
                <div>
                    <input type="text" class="form-control" name="lastName" placeholder="Nazwisko"
                           value="<%= lastName%>">
                </div>
            </div>
            <br/>
            <div class="form-row">
                <select class="form-control" id="adminUser" name="adminUser">
                    <option selected>Wybierz rodzaj użytkownika</option>
                    <option value="1">Administrator</option>
                    <option value="2">Czytelnik</option>
                </select>
            </div>
            <br/>
            <div class="form-row">
                <select class="form-control" id="status" name="status">
                    <option selected>Wybierz status konta użytkownika</option>
                    <option value="1">Aktywny</option>
                    <option value="2">Nieaktywny</option>
                </select>
            </div>
            <br/><br/><br/>
            <button type="submit" class="btn btn-primary">Edytuj użytkownika</button>
        </form>
        <% } %>
    </div>
</article>

<footer>
    <%@include file="/./include/footer.jsp" %>
</footer>

</body>
</html>