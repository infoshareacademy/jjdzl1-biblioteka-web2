<%@ page import="java.util.Map" %>
<%@ page import="com.infoshare.query.StatsQuery" %>
<%@ page import="java.sql.SQLException" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <%@include file="/./include/head.jsp" %>
</head>
<%
    String selectedUser = request.getParameter("selectedUser");

    if (selectedUser != null && !selectedUser.isEmpty() && selectedUser.equals("remove")) {
        session.removeAttribute("selectedUser");
    }
%>
<body>
<div class="mainpage">

    <header>
        <%@include file="/./include/appHeader.jsp" %>
    </header>


    <% if (request.getSession().getAttribute("addUser") == "userAdded") { %>
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <strong>Dodano użytkownika do biblioteki</strong>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <%
        }
        request.getSession().removeAttribute("addUser");
    %>

    <% if (request.getSession().getAttribute("userEdited") == "userEdited") { %>
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <strong>Edytowano dane użytkownika w bazie</strong>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <%
        }
        request.getSession().removeAttribute("userEdited");
    %>

    <% if (request.getSession().getAttribute("addBook") == "bookAdded") { %>
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <strong>Dodano książkę do biblioteki</strong>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <%
        }
        request.getSession().removeAttribute("addBook");
    %>

    <% if (request.getSession().getAttribute("opertationSuccess") == "success") { %>
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <strong>Operacja została zapisana</strong>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <%
        }
        request.getSession().removeAttribute("opertationSuccess");
    %>

    <%
        String time = null;
        String countBooks = null;
        String activeUsers = null;
        String disabledUsers = null;
        Map<String, String> stats = StatsQuery.statsMap;
        if (stats.size() == 0) {
            try {
                StatsQuery.generateStats();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        time = stats.get("time");
        countBooks = stats.get("booksCount");
        activeUsers = stats.get("activeUsers");
        disabledUsers = stats.get("disabledUsers");
    %>

    <% if (session.getAttribute("normalUser") == null) {%>
    <div class="row">
        <div class="col-lg-6">
            <div style="margin-top:70px;margin-right: 50px; margin-left: 70px;text-align: left">


                <div class="d-flex">

                    <div class="mr-auto p-2 align-items-start">
                        <h4><b>Statystyki biblioteki</b></h4>
                    </div>

                </div>

                <table class="table table-borderless">
                    <thead class="listofitemps">
                    <tr>
                        <td>Ilość książek w bibliotece:</td>
                        <td><%=countBooks%>
                        </td>
                    </tr>
                    <tr>
                        <td>Ilość aktywnych użytkowników:</td>
                        <td><%=activeUsers%>
                        </td>
                        <td>Ilość zablokowanych użytkowników:</td>
                        <td><%=disabledUsers%>
                        </td>
                    </tr>
                    </thead>
                </table>

                <h4><b>Statystyki wypożyczeń</b></h4>
                <table class="table table-borderless">
                    <thead class="listofitemps">
                    <tr>
                        <td>Aktualnie wypożyczonych:</td>
                        <td>5</td>
                        <td>Ilość rezerwacji:</td>
                        <td>10</td>
                    </tr>
                    <tr>
                        <td>Przeterminowanych wypożyczeń:</td>
                        <td>10</td>
                    </tr>

                    </thead>
                </table>
                <br/>
                <br/>
                <div class="d-flex">
                    <div class="mr-auto p-2 align-items-start">
                    </div>
                    <div class="p2 align-items-end">
                        <form method="GET" action="RefreshStatsServlet" class="addUser">
                            <button type="submit" class="btn btn-outline-secondary">Odśwież statystyki ...</button>
                        </form>
                        <br/>
                        <div class="mr-auto p-2 align-items-start" style="font-size: 14px">Zaktualizowano o: <%=time%>
                        </div>
                    </div>
                </div>


            </div>


        </div>
        <div class="col-lg-4">
        </div>
    </div>
    <%}%>

</div>

<footer>
    <%@include file="/./include/footer.jsp" %>
</footer>

</body>
</html>