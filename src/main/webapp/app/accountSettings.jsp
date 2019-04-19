<%@ page import="com.infoshare.repository.UsersRepositoryDaoBean" %>
<%@ page import="com.infoshare.domain.User" %>
<%@ page import="com.infoshare.repository.UsersRepositoryDao" %>
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
    User user = (User) session.getAttribute("UserObject");
%>


<article>
    <div class="addUserForm">
        <br/><br/><br/>
        <h4>Edytuj swoje konto</h4>
        <br/>
        <form method="POST" action="EditAccountServlet" class="addUser">
            <div class="form-row">
                <div>
                    <input type="text" class="form-control" name="login" placeholder="Login"
                           value="<%= user.getLogin()%>">
                </div>
                <div>&nbsp;</div>
                <div>
                    <input type="email" class="form-control" name="e-mail" placeholder="E-mail"
                           value="<%= user.getEmail()%>">
                </div>
            </div>
            <br/>
            <div class="form-row">
                <div>
                    <input type="text" class="form-control" name="firstName" placeholder="Imię"
                           value="<%= user.getFirstName()%>">
                </div>
                <div>&nbsp;</div>
                <div>
                    <input type="text" class="form-control" name="lastName" placeholder="Nazwisko"
                           value="<%= user.getLastName()%>">
                </div>
            </div>
            <br/>
            <div class="form-row">
                <div>
                    <input type="password" class="form-control" name="password1" placeholder="Nowe hasło">
                </div>
                <div>&nbsp;</div>
                <div>
                    <input type="password" class="form-control" name="password2" placeholder="Powtórz nowe hasło">
                </div>
            </div>
            <br/><br/><br/>
            <h6>Wpisz aktualne hasło, aby zatwierdzić wprowadzone zmiany</h6>
            <div class="form-row">
                <input type="password" class="form-control" name="password3" placeholder="Hasło">
            </div>
            <br/><br/>
            <button type="submit" class="btn btn-primary">Aktualizuj dane</button>
        </form>
    </div>
</article>

<footer>
    <%@include file="/./include/footer.jsp" %>
</footer>

</body>
</html>
