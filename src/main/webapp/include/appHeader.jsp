<%@ page import="com.infoshare.logic.domain.User" %>
<%@ page import="com.infoshare.logic.domain.Basket" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String userName = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userCookie")) userName = cookie.getValue();
        }
    }
    String nameOfUser = (String) session.getAttribute("nameOfUser");
    Integer userId= (Integer)session.getAttribute("userId");
%>
<nav class="navbar navbar-default "></nav>
<nav class="navbar navbar-dark bg navbar-expand-lg fixed-top">

    <a class="navbar-brand" href="${pageContext.request.contextPath}/app/loginSuccess.jsp"><img src="${pageContext.request.contextPath}/img/logo.png"
                                                         width="30" height="30"
                                                         class="d-inline-block mr-1 align-bottom" alt=""> Biblioteka</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#mainmenu"
            aria-controls="mainmenu" aria-expanded="false" aria-label="Przełącznik nawigacji">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="mainmenu">

        <ul class="navbar-nav mr-auto">

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" role="button"
                   aria-expanded="false" id="submenu1" aria-haspopup="true"> Książki </a>
                <div class="dropdown-menu" aria-labelledby="submenu">
                    <a class="dropdown-item" href="GetAttributeBookRepository?order=title"> Przeglądaj wg tytułów </a>
                    <a class="dropdown-item" href="GetAttributeBookRepository?order=authorLastName"> Przeglądaj wg autorów </a>

                    <% if (session.getAttribute("normalUser") == null) {%>
                    <a class="dropdown-item"> --- </a>
                    <a class="dropdown-item" href="addBook.jsp"> Dodaj książkę </a>
                    <a class="dropdown-item" href="GetAttributeBookRepository?order=title&edit=true"> Edytuj książkę </a>
                    <%}%>

                </div>
            </li>

            <% if (session.getAttribute("normalUser") == null) {%>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" role="button"
                   aria-expanded="false" id="submenu" aria-haspopup="true"> Użytkownicy </a>
                <div class="dropdown-menu" aria-labelledby="submenu">

                    <form action="FindUserServlet" class="form-inline dropdown-item" method="get">
                        <div class="form-row align-items-center">
                            <div class="col-auto inline-block">
                                <input type="text" name="findUserByName" class="form-control" id="inlineFormInput1"
                                       placeholder="Wpisz nazwisko użytkownika">
                                <button type="submit" class="btn btn-outline-info">Szukaj</button>
                            </div>
                        </div>
                    </form>

                    <a class="dropdown-item" href="addUser.jsp"> Dodaj użytkownika </a>
                    <% if (session.getAttribute("selectedUser") != null) {
                        User user = (User) session.getAttribute("selectedUser");
                    %>
                    <a class="dropdown-item" href="listOfUsers.jsp"> Lista użytkowników </a>

                    <%} else {%>

                    <a class="dropdown-item" href="GetAttributesUserRepository"> Edytuj użytkownika </a>
                    <%}%>
                </div>
            </li>
            <%}%>

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" role="button"
                   aria-expanded="false" id="submenu2" aria-haspopup="true"> Operacje </a>
                <div class="dropdown-menu" aria-labelledby="submenu">
                    <% if (session.getAttribute("normalUser") == null) {%>
                    <a class="dropdown-item" href="GetAttributesUserRepository?operation=newoperation"> Rezerwuj / Wypożycz </a>
                    <a class="dropdown-item" href="GetAttributesUserRepository?operation=returnbook"> Zwrot książki </a>

                    <% if (session.getAttribute("selectedUser") != null) {%>
                    <a class="dropdown-item" href="loginSuccess.jsp?selectedUser=remove"> Anuluj operację </a>
                    <%}%>

                    <a class="dropdown-item"> --- </a>
                    <a class="dropdown-item" href="GetAttributeOperationRepository?operationType=reservation"> Wszystkie
                        rezerwacje </a>
                    <a class="dropdown-item" href="GetAttributeOperationRepository?operationType=borrow"> Wszystkie
                        wypożyczenia </a>
                    <a class="dropdown-item" href="GetAttributeOperationRepository"> Razem wszystkie
                        operacje </a>

                    <% } else { %>
                    <a class="dropdown-item" href="GetAttributeBookRepository?reservation=user"> Rezerwuj książkę </a>
                    <a class="dropdown-item"> --- </a>
                    <a class="dropdown-item" href="GetAttributeOperationRepository?operationType=reservation&userId=<%=userId%>"> Moje rezerwacje </a>
                    <a class="dropdown-item" href="GetAttributeOperationRepository?operationType=borrow&userId=<%=userId%>"> Moje wypożyczenia </a>
                    <a class="dropdown-item" href="GetAttributeOperationRepository?operationType=all&userId=<%=userId%>"> Wszystkie operacje </a>
                    <%}%>
                </div>
            </li>

            <li>&nbsp;&nbsp;&nbsp;</li>

            <li>
                <form action="GetAttributeBookRepository?order=title" class="form-inline" method="post">
                    <div class="form-row align-items-center">
                        <div class="col-auto">
                            <input type="text" name="title" class="form-control" id="inlineFormInput"
                                   placeholder="Wpisz tytuł książki">
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-info">Szukaj</button>
                        </div>
                    </div>
                </form>
            </li>

            <% if (session.getAttribute("selectedUser") != null) {
                User user = (User) session.getAttribute("selectedUser");
            %>

            <li>&nbsp;&nbsp;&nbsp;</li>

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" role="button" aria-expanded="false"
                   aria-haspopup="true">
                    <%
                        List<Basket> basketList = new ArrayList<>();
                        if (request.getSession().getAttribute("basket") != null)
                            basketList = (List<Basket>) request.getSession().getAttribute("basket");
                        String userFullName = user.getFirstName() + ", " + user.getLastName();
                        Integer userID = user.getId();
                    %>
                    Wybrano: <%=userFullName%>
                    <% if (basketList.size()!=0){%><span class="badge badge-danger"><%=basketList.size()%></span><%}%>
                </a>
                <div class="dropdown-menu" aria-labelledby="submenu">
                    <a class="dropdown-item" href="userBasket.jsp"> Koszyk operacji </a>
                    <a class="dropdown-item" href="userBasket.jsp"> Zwrot książki </a>
                    <a class="dropdown-item" href="loginSuccess.jsp?selectedUser=remove"> Anuluj bieżące operacje </a>
                    <a class="dropdown-item"> --- </a>
                    <a class="dropdown-item" href="GetAttributeOperationRepository?operationType=all&userId=<%=userID%>"> Aktualne rezerwacje i wypożyczenia </a>
                    <a class="dropdown-item" href="GetAttributeOperationRepository?operationType=reservation&userId=<%=userID%>">
                        Historia rezerwacji </a>
                    <a class="dropdown-item" href="GetAttributeOperationRepository?operationType=borrow&userId=<%=userID%>">
                        Historia wypożyczeń </a>
                    <a class="dropdown-item"> --- </a>
                    <a class="dropdown-item" href="#"> Wyślij przypomnienie (email) </a>
                </div>
                </a>
            </li>
            <%}%>
        </ul>

        <ul class="navbar-nav mr-right">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" role="button"
                   aria-expanded="false" id="submenu4" aria-haspopup="true">Witaj <%=nameOfUser%>
                </a>
                <div class="dropdown-menu" aria-labelledby="submenu3">
                    <a class="dropdown-item" href="GetUserToEditServlet?edit=account&userId=<%=userId%>"> Ustawienia konta </a>
                    <a class="dropdown-item" href="#"> Powiadomienia </a>
                </div>
            </li>
        </ul>

        <form action="LogoutServlet" method="post">
            <button class="btn btn-primary" type="submit">Wyloguj</button>
        </form>

    </div>
</nav>
