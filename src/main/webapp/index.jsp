<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pl">

<head>
    <%@include file="include/head.jsp" %>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/Wruczek/Bootstrap-Cookie-Alert@gh-pages/cookiealert.css">
</head>
<body>
<div class="mainpage">
<header>
    <%@include file="include/header.jsp" %>
</header>


<% if (session.getAttribute("loginFalse") == "loginFalse") { %>
<div class="alert alert-danger alert-dismissible fade show" role="alert">
    <strong>Błędna nazwa użytkownika lub hasło!</strong>
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
</div>
<%
        request.getSession().removeAttribute("loginFalse");
    }
%>
<% if (request.getSession().getAttribute("addUser") == "userAdded") { %>
<div class="alert alert-success alert-dismissible fade show" role="alert">
    <strong>Twoje konto jest aktywne, możesz się zalogować</strong>
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
</div>
<%
    }
    request.getSession().removeAttribute("addUser");
%>



    <div class="row">
        <div class="col-lg-6">
            <div style="margin-top:70px;margin-right: 50px; margin-left: 30px;text-align: right">
                <p><i><b>"Czytanie książek to najpiękniejsza zabawa, jaką sobie ludzkość wymyśliła"</b></i></p>
                <p>Wisława Szymborska</p>

                <p><i><b>"Kto czyta książki, żyje podwójnie."</b></i></p>
                <p>Umberto Eco</p>

                <p><i><b>"Czytać to bardziej żyć, to żyć intensywniej."</b></i></p>
                <p>Carlos Ruiz Zafón – Cień wiatru</p>

            </div>

            <div style="margin-top: 80px; margin-left: 70px">
                <h2>Załóż darmowe konto</h2>
                <p>Poświęć nam 2 minutu i otrzymaj <br/>bezpłatny dostęp do naszych zasobów</p>
                <p><a class="btn btn-primary" href="addUser.jsp" role="button">Zakładam konto &raquo;</a></p>
            </div>
        </div>
        <div class="col-lg-4">
        </div>
    </div>
</div>
<!-- START Bootstrap-Cookie-Alert -->
<div class="alert text-center cookiealert" role="alert">
    <b>Lubisz ciasteczka?</b> &#x1F36A;<br/>
    <p>Używamy informacji zapisanych za pomocą plików cookies w celu zapewnienia maksymalnej wygody w korzystaniu z
        naszego serwisu.
        <br/>Jeżeli wyrażasz zgodę na zapisywanie informacji zawartej w cookies kliknij na przycisk Akceptuję.
        <br/>Jeżeli nie wyrażasz zgody, ustawienia dotyczące plików cookies możesz zmienić w swojej przeglądarce.
        <a href="https://wszystkoociasteczkach.pl/podstawa-prawna//" target="_blank">Dowiedz sie więcej</a>
        <button type="button" class="btn btn-primary btn-sm acceptcookies" aria-label="Close">
            Akceptuję
        </button>
</div>
<!-- END Bootstrap-Cookie-Alert -->

<footer>
    <%@include file="include/footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/gh/Wruczek/Bootstrap-Cookie-Alert@gh-pages/cookiealert.js"></script>
</footer>

</body>
</html>