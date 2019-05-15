<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.infoshare.logic.domain.*" %>
<%@ page import="java.util.ArrayList" %>
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

<article>
    <div class="content">
        <div class="contentInside">
            <br/>
            <%
                List<Operation> operations = new ArrayList<>();
                if (session.getAttribute("selectedUser") != null) {
                    User user = (User) session.getAttribute("selectedUser");
                    operations = (List<Operation>) session.getAttribute("borrowOperationDao");
                    session.removeAttribute("borrowOperationDao");
            %>

            <div class="d-flex">
                <div class="mr-auto p-2 align-items-start"><h4>Lista
                    wypożyczeń: <%=user.getLastName() + ", " + user.getFirstName()%>
                </h4>
                </div>
                <% if (operations.size() != 0) {%>

                <div class="p2 align-items-end">
                    <form method="POST" action="SaveBasketServlet" class="addUser">
                        <input type="hidden" name="operationType" value="reservation"/>
                        <button type="submit" class="btn btn-success">Zwróć wszystkie</button>
                    </form>
                </div>
                <%}%>

                <div class="p2 align-items-end">
                    &nbsp;&nbsp;
                </div>
                <div class="p2 align-items-end">
                    <form method="GET" action="OperationCancelServlet" class="addUser">
                        <input type="hidden" name="selectedUser" value="remove"/>
                        <% if (operations.size() != 0) {%>
                        <button type="submit" class="btn btn-secondary">Anuluj</button>
                        <%} else {%>
                        <button type="submit" class="btn btn-secondary">Zakończ operację</button>
                        <%}%>
                    </form>
                </div>


            </div>
            <br/>
            <%
                }
                if (operations.size() == 0) {
            %> Brak operacji na koncie<%} else {%>

            <table class="table table-bordered table-hover">
                <thead>
                <tr class="listofitemps">
                    <th scope="col">#</th>
                    <th scope="col">Tytuł/Autor</th>
                    <th scope="col">Data wypożyczenia</th>
                    <th scope="col">Data zwrotu</th>
                    <th scope="col">Działanie</th>

                </tr>
                </thead>
                <tbody>
                <%}%>
                <%
                    int rowNumber = 1;
                    for (Operation operation : operations) {
                %>

                <tr class="listofitemps">
                    <th scope="row"><%=rowNumber%>
                    </th>
                    <td>
                        <b><%=operation.getBookTitle()%>
                        </b>
                        <br/>
                        <i><%=operation.getAuthor()%>
                        </i>
                    </td>

                    <td>
                        <%=operation.getStartDate()%>
                    </td>
                    <td>
                        <form method="POST" action="ReturnBookServlet" class="addUser">
                            <div class="form-group mx-sm-3 mb-2">
                                <input type="date" name="endDate" class="form-control" value="<%=LocalDate.now()%>">
                            </div>

                    </td>
                    <td>
                        <input type="hidden" name="bookId" value="<%=operation.getBook().getId()%>"/>
                        <input type="hidden" name="operationId" value="<%=operation.getId()%>"/>
                        <button type="submit" class="btn btn-success">Zwróć</button>
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