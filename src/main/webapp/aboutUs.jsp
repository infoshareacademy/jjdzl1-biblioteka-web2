<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pl">

<head>
    <%@include file="include/head.jsp" %>
</head>
<body>
<% if (session.getAttribute("user") == null) {%>

<header>
    <%@include file="include/header.jsp" %>
</header>

<% } else {%>

<header>
    <%@include file="include/appHeader.jsp" %>
</header>

<%}%>

<section class="our-webcoderskull padding-lg">
    <div class="container">
        <h2>Nasz zespół</h2>
        <div class="justify-content-left">
            Niniejsza aplikacja webowa - Biblioteka online jest aplikacją wykonaną przez nas jako projekt zaliczeniowy
            na zakończenie sześciomiesiącznego szkolenia z programowania w języku Java.
            <br/>
            Aplikacja tworzona była w metodologi Scrum i rozwijana w trzech sprintach od aplikacji konsolowej,
            przez aplikację webową, kończąc na aplikacji webowej z REST API, EJB i Hibernate
            <br/>
            W projekcie użyto technologii Java Standard Edition i JAVA Enterprise Edition
            <br/>
            Jako narzędzie programistyczne używaliśny oprogramowania firmy JetBrains IntelliJ IDEA 2018 w wersji
            Ultimate.
            W projekcie użyto technologii Hibernate jako implemantacja Java Persistance API, Enterprise Java Beans, Java
            Server Pages, Maven, MySQL, Bootstrap, Html, CSS, JavaScript.
        <br/>
            Aplikacja została uruchomiona na serwerze aplikacyjnym WildFly 15(JBoss) a baza danych na Dockerze - kontener z serwerem MySQL.
            <br/>
            Podczas tworzenia aplikacji używaliśmy również narządzi pomocniczych Jira (Scrum), Slack (komunikator).
        </div>
        <br/>
        <div class="d-flex justify-content-left">
            <br/>
            <br/>
            Więcej o nas znajdziesz w linkach do GitHub i LinkedIn
        </div>
        <br/>
        <ul class="row">
            <li class="col-12 col-md-6 col-lg-4">
                <div class="cnt-block">
                    <figure><img
                            src="https://mini-figurki.pl/3731-thickbox_default/lego-minifig-the-big-bang-theory.jpg"
                            class="img-responsive" alt="" width="148px" height="148px"></figure>
                    <h3><a href="mailto:arkadiusz.kalka@gmail.com">Arkadiusz Kałka</a></h3>
                    <p>Student infoShare Academy Lublin</p>
                    <ul>
                        <a href="https://www.linkedin.com/in/arkadiusz-kalka/"><img src="img/linked.png" width="50px"
                                                                                    height="50px"/></a>
                        <a href="https://github.com/ArkadiuszKalka"><img src="img/github.png" width="50px"
                                                                         height="50px"/></a>
                    </ul>

                </div>
            </li>
            <li class="col-12 col-md-6 col-lg-4">
                <div class="cnt-block">
                    <figure><img
                            src="https://mini-figurki.pl/1210-thickbox_default/lego-minifig-mutt-williams.jpg"
                            class="img-responsive" alt="" width="148px" height="148px"></figure>
                    <h3><a href="#">Łukasz Bezłada</a></h3>
                    <p>Student infoShare Academy Lublin</p>
                    <ul>
                        <a href="https://www.linkedin.com/in/lukaszbezlada/"><img src="img/linked.png" width="50px" height="50px"/></a>
                        <a href="https://github.com/lukaszbezlada"><img src="img/github.png" width="50px" height="50px"/></a>
                    </ul>

                </div>
            </li>
            <li class="col-12 col-md-6 col-lg-4">
                <div class="cnt-block">
                    <figure><img
                            src="https://mini-figurki.pl/3987-thickbox_default/figurka-lego-custom-android-vs-apple.jpg"
                            class="img-responsive" alt="" width="148px" height="148px"></figure>
                    <h3><a href="#">Michał Mrozik</a></h3>
                    <p>Student infoShare Academy Lublin</p>
                    <ul>
                        <a href="#"><img src="img/linked.png" width="50px" height="50px"/></a>
                        <a href="#"><img src="img/github.png" width="50px" height="50px"/></a>
                    </ul>

                </div>
            </li>
        </ul>
        <div class="d-flex justify-content-center">
            <a href="https://infoshareacademy.com/"><img src="img/logo.svg" width="150px"></a>
        </div>

    </div>
</section>

<footer>
    <%@include file="include/footer.jsp" %>
</footer>

</body>
</html>
