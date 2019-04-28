<%@ page import="com.infoshare.logic.validation.BookValidator" %>
<%@ page import="java.util.List" %>
<%@ page import="com.infoshare.logic.domain.Book" %>
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
        Book book = (Book) session.getAttribute("editedBook");
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
    <h4>Edycja książki: <%=book.getTitle()%></h4>
    <br/>
    <form method="POST" action="EditBookServlet" class="addUser">

        <div>
            <div>
                <input type="hidden" name="id" value="<%=book.getId()%>">
                <input type="text" class="form-control" name="title"
                       placeholder="Tytuł książki" value="<%=book.getTitle()%>">
            </div>
        </div>
        <br/>
        <div class="form-row">
            <div>
                <input type="text" class="form-control" name="authorLastName"
                       placeholder="Nazwisko autora" value="<%=book.getAuthorLastName()%>">
            </div>
            <div>&nbsp;</div>
            <div>
                <input type="text" class="form-control" name="authorFirstName"
                       placeholder="Imię autora" value="<%=book.getAuthorFirstName()%>">
            </div>
        </div>
        <br/>
        <div class="form-row">
            <div>
                <input type="number" class="form-control no-spin" name="isbn"
                       placeholder="Numer ISBN (tylko cyfry)" value="<%=book.getIsbn()%>">
            </div>
            <div>&nbsp;</div>
            <div>
                <input type="number" class="form-control no-spin" name="daterelase"
                       placeholder="Rok wydania (tylko cyfry)" value="<%=book.getDaterelease()%>">
            </div>
        </div>
        <br/>
        <div>
            <div>
                <textarea name="description" class="form-control"
                          placeholder="Opis książki" rows="5" ><%=book.getDescription()%></textarea>
            </div>
        </div>
        <br/>
        <br/>
        <button type="submit" class="btn btn-primary">Zapisz zmiany</button>
    </form>
</div>

<footer>
    <%@include file="../include/footer.jsp" %>
</footer>

</body>
</html>