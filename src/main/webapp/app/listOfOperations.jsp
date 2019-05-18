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
    String StringUser = request.getParameter("userId");
    String firstDate = request.getParameter("firstDate");
    String lastDate = request.getParameter("lastDate");
    Integer user = null;
    if (StringUser != null) user = Integer.parseInt(StringUser);
    String operationTypePl = "";
    String operationType = request.getParameter("operationType");
    if (operationType == null || operationType.equals("all")) {
        operationTypePl = "Wszystkie operacje";
    } else if (operationType.equals("reservation")) {
        operationTypePl = "Rezerwacje";
    } else {
        operationTypePl = "Wypożyczenia";
    }
%>
<article>
    <div class="content">
        <div class="contentInside">
            <br/>
            <div class="d-flex">
                <div class="mr-auto p-2 align-items-start"><h4>Lista operacji: <%=operationTypePl%>
                </h4>
                    <%
                        if (firstDate != null && lastDate != null) {
                    %> w okresie od <%=firstDate%> do <%=lastDate%>
                    <%}%>
                </div>
                <div class="p2 align-items-end">
                    <form method="GET" action="GetAttributeOperationRepository" class="addUser">
                        <input type="hidden" name="operationType" value="<%=operationType%>">
                        <% if (user != null) {%>
                        <input type="hidden" name="userId" value="<%=user%>">
                        <%}%>
                        <table>
                            <tr>
                                <td>
                                    <input type="date" class="form-control" name="firstDate"
                                           value="<%=LocalDate.now().withDayOfMonth(1)%>"/>
                                </td>
                                <td>
                                    <input type="date" class="form-control" name="lastDate"
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
            <% if (operations.size() == 0) {
            %>
            Brak operacji
            <%
            } else {
            %>
            <table class="table table-bordered table-hover">
                <thead>
                <tr class="listofitemps">
                    <th scope="col">#</th>
                    <th scope="col">Użytkownik</th>
                    <th scope="col">Tytuł książki</th>
                    <th scope="col">Data operacji</th>

                    <th scope="col">
                        <%
                            if (operationType.equals("reservation")) {
                        %>Date rezerwacji<%
                    } else if (operationType.equals("borrow")) {
                    %>Data wypożyczenia<%
                    } else {%>
                        Data początkowa
                        <%}%>
                    </th>
                    <th scope="col">
                        <%
                            if (operationType.equals("reservation")) {
                        %>Date ważności<%
                    } else if (operationType.equals("borrow")) {
                    %>Data zwrotu<%
                    } else {%>
                        Data zakończenia
                        <%}%>
                    </th>
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
                        <%if (operation.getOperationType().equals(OperationType.RESERVATION)) {%> Rezerwacja
                        <% if(operation.getEndDate().isBefore(LocalDate.now())){%>
                        <br/><p class="text-danger">Przeterminowana</p><%}else{%>
                        <p class="text-success">Aktywna</p>
                        <%}}%>
                        <%if (operation.getOperationType().equals(OperationType.BORROW)) {%> Wypożyczenie<%
                        if (!operation.getEndDate().isEqual(LocalDate.of(1970, 01, 01))) {
                    %>
                        <p class="text-success">Zwrócona</p>
                        <%} else {%>
                        <p class="text-primary">Trwa</p>
                        <%
                                }
                            }
                        %>
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