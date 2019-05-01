<%@ page import="com.infoshare.logic.repository.BasketRepositoryDaoBean" %>
<%@ page import="com.infoshare.logic.domain.Basket" %>
<%@ page import="java.util.List" %>
<%@ page import="com.infoshare.logic.repository.BasketRepositoryDao" %>
<%@ page import="com.infoshare.logic.domain.OperationType" %>
<%@ page import="java.time.LocalDate" %>
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
    //    BasketRepositoryDao basketRepositoryDaoBean = new BasketRepositoryDaoBean();
//    List<Basket> basketList = basketRepositoryDaoBean.basketList();
    List<Basket> basketList = new ArrayList<>();
    if (request.getSession().getAttribute("basket") != null)
        basketList = (List<Basket>) request.getSession().getAttribute("basket");
%>

<article>
    <div class="content">
        <div class="contentInside">
            <br/>
            <%
                if (session.getAttribute("selectedUser") != null) {
                    User user = (User) session.getAttribute("selectedUser");
            %>

            <div class="d-flex">
                <div class="mr-auto p-2 align-items-start"><h4>Koszyk bieżących
                    operacji: <%=user.getLastName() + ", " + user.getFirstName()%>
                </h4>
                </div>
                <% if (basketList.size() != 0) {%>

                <div class="p2 align-items-end">
                    <form method="POST" action="SaveBasketServlet" class="addUser">
                        <input type="hidden" name="operationType" value="reservation"/>
                        <button type="submit" class="btn btn-success">Akceptuj operacje</button>
                    </form>
                </div>
                <%}%>
                <div class="p2 align-items-end">
                    &nbsp;&nbsp;
                </div>
                <div class="p2 align-items-end">
                    <form method="GET" action="OperationCancelServlet" class="addUser">
                        <input type="hidden" name="selectedUser" value="remove"/>
                        <% if (basketList.size() != 0) {%>
                        <button type="submit" class="btn btn-secondary">Anuluj</button>
                        <%} else {%>
                        <button type="submit" class="btn btn-secondary">Anuluj operację</button>
                        <%}%>
                    </form>
                </div>


            </div>
            <br/>
            <%}%>
            <table class="table table-bordered table-hover">
                <thead>
                <tr class="listofitemps">
                    <th scope="col">#</th>
                    <th scope="col">Tytuł/Autor</th>
                    <th scope="col">Operacja</th>
                    <th scope="col">Data operacji</th>
                    <th scope="col">Data ważności</th>
                    <th scope="col">Działanie</th>

                </tr>
                </thead>
                <tbody>
                <%
                    int rowNumber = 1;
                    for (Basket basket : basketList) {
                %>
                <tr class="listofitemps">
                    <th scope="row"><%=rowNumber%>
                    </th>
                    <td><a href="bookDescription.jsp?id=<%=basket.getBook().getId()%>">
                        <b><%=basket.getBook().getTitle()%>
                        </b>
                        <br/><i>
                        <%=basket.getBook().getAuthorLastName() + ", " + basket.getBook().getAuthorFirstName()%>
                    </i></a></td>
                    <td><% if (basket.getOperationType().equals(OperationType.RESERVATION)) {%> Rezerwacja<%} else {%>
                        Wypożyczenie<%}%>
                        </a>
                    </td>

                    <td>
                        <div class="form-group mx-sm-3 mb-2">
                            <input type="date" name="startDate" class="form-control" value="<%=basket.getStartDate()%>">
                        </div>
                    </td>
                    <td>
                        <%if (basket.getOperationType().equals(OperationType.RESERVATION)) {%>
                        <div class="form-group mx-sm-3 mb-2">
                            <input type="date" name="endDate" class="form-control" value="<%=basket.getEndDate()%>">
                        </div>
                        <%} else {%>
                        <div class="form-group mx-sm-3 mb-2">
                            <input type="text" name="endDate" class="form-control" disabled value=" --- ">
                                <%}%>
                    </td>
                    <td>
                        <form method="POST" action="RemoveItemFromBasketServlet" class="addUser">
                            <input type="hidden" name="removeItem" value="<%=rowNumber-1%>"/>
                            <button type="submit" class="btn btn-danger">Usuń</button>
                        </form>

                    </td>
                    <%
                            rowNumber++;
                        }
                    %>
                </tr>
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