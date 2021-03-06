<%@ page import="com.infoshare.logic.domain.Operation" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.infoshare.logic.domain.OperationType" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.infoshare.logic.utils.CalculateFeeToPay" %>
<%@ page import="com.infoshare.logic.utils.ReadProperties" %>
<%@ page import="javax.persistence.criteria.CriteriaBuilder" %>
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
    Integer pages = Integer.parseInt(request.getParameter("pages"));
    Integer user = null;
    Integer selectedUserId = null;
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
    String pageString = request.getParameter("page");
    String edit = request.getParameter("edit");
    String reservation = request.getParameter("reservation");
    if (reservation == null) reservation = "";
    if (session.getAttribute("selectedUser") != null) {
        User selectedUser = (User) session.getAttribute("selectedUser");
        selectedUserId = selectedUser.getId();
    }
    Integer recordsPerPage = Integer.parseInt(ReadProperties.readPropertie("records-per-page"));
    if (pageString == null || pageString.isEmpty()) pageString = "1";
    int pageNumber = Integer.parseInt(pageString);
%>


<% if (request.getSession().getAttribute("sendEmail") != null) { %>
<div class="alert alert-success alert-dismissible fade show" role="alert">
    <strong><%=request.getSession().getAttribute("sendEmail")%>
    </strong>
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
</div>
<%
    }
    request.getSession().removeAttribute("sendEmail");
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
                    <nav aria-label="Page navigation example">
                        <ul class="pagination justify-content-end">

                            <%if (pageNumber == 1) {%>
                            <li class="page-item disabled">
                                    <%}else{%>
                            <li class="page-item">
                                <%}%>
                                <a class="page-link"
                                        <%if (selectedUserId == null) {%>
                                   href="GetAttributeOperationRepository?page=<%=pageNumber-1%>&operationType=<%=operationType%>&page=<%=pageNumber%>"
                                        <%} else {%>
                                   href="GetAttributeOperationRepository?page=<%=pageNumber-1%>&operationType=<%=operationType%>&page=<%=pageNumber%>"
                                        <%}%>
                                   tabindex="-1">Wcześniejsza</a>
                            </li>
                            <%if (pageNumber == pages || pages == 0) {%>
                            <li class="page-item disabled">
                                <a class="page-link"
                                   href="">Następna</a>
                            </li>
                            <%} else {%>
                            <li class="page-item">
                                <a class="page-link"
                                        <%if (selectedUserId == null) {%>
                                   href="GetAttributeOperationRepository?page=<%=pageNumber+1%>&operationType=<%=operationType%>&page=<%=pageNumber%>">Następna</a>
                                <%} else {%>
                                href="GetAttributeOperationRepository?page=<%=pageNumber + 1%>&operationType=<%=operationType%>&page=<%=pageNumber%>&userId=<%=selectedUserId%>">Następna</a>
                                <%}%>
                            </li>
                            <%}%>
                        </ul>
                    </nav>
                    <br/>
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
                        %>Data rezerwacji<%
                    } else if (operationType.equals("borrow")) {
                    %>Data wypożyczenia<%
                    } else {%>
                        Data początkowa
                        <%}%>
                    </th>
                    <th scope="col">
                        <%
                            if (operationType.equals("reservation")) {
                        %>Data ważności<%
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
                    int rowNumber = 1 + (pageNumber * recordsPerPage) - recordsPerPage;
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
                        <% LocalDate startDate = operation.getStartDate();
                            LocalDate endDate = startDate.plusDays(30);
                            BigDecimal payForBorrow = CalculateFeeToPay.calculateFeeToPay(startDate, endDate);
                            Integer expiredDays = CalculateFeeToPay.getDays(endDate).getDays();
                            if (LocalDate.now().isAfter(endDate)) {
                        %><p class="text-danger">Przeterminowana: <%=expiredDays%> dni
                        <br/>Opłata: <%=payForBorrow%> zł
                    </p>
                        <%
                            }
                        } else {
                        %>
                        <%=operation.getEndDate()%>
                        <%}%>
                    </td>
                    <td>
                        <%if (operation.getOperationType().equals(OperationType.RESERVATION)) {%> Rezerwacja
                        <% if (operation.getEndDate().isBefore(LocalDate.now())) {%>
                        <br/>
                        <p class="text-danger">Przeterminowana</p><%} else {%>
                        <p class="text-primary">Aktywna</p>
                        <%
                                }
                            }
                        %>
                        <%if (operation.getOperationType().equals(OperationType.BORROW)) {%> Wypożyczenie<%
                        if (!operation.getEndDate().isEqual(LocalDate.of(1970, 01, 01))) {
                    %>
                        <p class="text-success">Zwrócona</p>
                        <%} else {%>
                        <p class="text-primary">Trwa

                                <% LocalDate startDate = operation.getStartDate();
                                LocalDate endDate = startDate.plusDays(30);
                                Integer expiredDays=CalculateFeeToPay.getDays(endDate).getDays();
                                BigDecimal payForBorrow = CalculateFeeToPay.calculateFeeToPay(startDate, endDate);
                                if (LocalDate.now().isAfter(endDate)) {
                            %>
                        <form method="POST" action="SendEmailServlet" class="addUser">
                            <input type="hidden" name="operation" value="<%=operation.getId()%>">
                            <input type="hidden" name="days" value="<%=expiredDays%>"/>
                            <input type="hidden" name="payForBorrow" value="<%=payForBorrow%>"/>
                            <input type="hidden" name="operationType" value="<%=operationType%>"/>
                            <input type="hidden" name="selectedUserId" value="<%=user%>"/>
                            <button type="submit" class="btn btn-warning">Wyślij powiadomienie</button>
                        </form>
                        <%}%>
                        </p>
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