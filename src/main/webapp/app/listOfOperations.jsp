<%@ page import="com.infoshare.logic.domain.Operation" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.infoshare.logic.domain.OperationType" %>
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
    List<Operation> operations = (List) session.getAttribute("operationRepositoryDao");
%>
<article>
    <div class="content">
        <div class="contentInside">
            <br/>
            <% if (operations.size() == 0) {
            %>
            Brak operacji
            <%
            } else {
            %>
            <div class="d-flex">
                <div class="mr-auto p-2 align-items-start"><h4>Lista operacji</h4>
                </div>
                <div class="p2 align-items-end">
                    <form method="GET" action="SaveBasketServlet" class="addUser">
                        <table>
                            <tr>
                                <td>
                                    <input type="date" class="form-control" name="firstDate"
                                           value="<%=LocalDate.now().withDayOfMonth(1)%>"/>
                                </td>
                                <td>
                                    <input type="date" class="form-control" name="firstDate"
                                           value="<%=LocalDate.now()%>"/>
                                </td>
                                <td> &nbsp;&nbsp;&nbsp;</td>
                                <td>
                                    <button type="submit" class="btn btn-success">Dostosuj okres</button>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
            <table class="table table-bordered table-hover">
                <thead>
                <tr class="listofitemps">
                    <th scope="col">#</th>
                    <th scope="col">Użytkownik</th>
                    <th scope="col">Tytuł książki</th>
                    <th scope="col">Data operacji</th>
                    <th scope="col">Data wypożyczenia</th>
                    <th scope="col">Data zwrotu</th>
                    <th scope="col">Typ operacji</th>
                </tr>
                </thead>
                <tbody>
                <%
                    int rowNumber = 1;
                    for (Operation operation : operations) {
                %>
                <tr class="listofitemps">
                    <th scope="row"><%=rowNumber%>
                    </th>
                    <td><%=operation.getUserName()%>
                    </td>
                    <td><%=operation.getBookTitle()%><br/><%=operation.getAuthor()%>
                    </td>
                    <td><%=operation.getOperationDate()%>
                    </td>
                    <td><%=operation.getStartDate()%>
                    </td>
                    <td>
                        <%if (operation.getEndDate().equals(LocalDate.of(1970, 01, 01))) {%>
                        ---
                        <%} else {%>
                        <%=operation.getEndDate()%>
                        <%}%>
                    </td>
                    <td>
                        <%if (operation.getOperationType().equals(OperationType.RESERVATION)) {%> Rezerwacja<%}%>
                        <%if (operation.getOperationType().equals(OperationType.BORROW)) {%> Wypożyczenie<%}%>
                    </td>
                </tr>
                <%
                        rowNumber++;
                    }%>
                </tbody>
            </table>
            <%}%>
        </div>
    </div>
</article>

<footer>
    <%@include file="../include/footer.jsp" %>
</footer>

</body>
</html>