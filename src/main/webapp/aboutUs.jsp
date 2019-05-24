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
        <h2>Kilka słów o nas i o projekcie</h2>
        <hr>
        <div class="justify-content-left">
            Niniejsza aplikacja webowa <strong>Biblioteka online</strong> jest aplikacją wykonaną jako projekt
            zaliczeniowy na zakończenie sześciomiesiącznego szkolenia z programowania w języku Java prowadzonego przez
            infoShare Academy w Lublinie na przełomie 2018 i 2019 roku.
            <br/><br/>
            Aplikacja tworzona była w metodologi Scrum i rozwijana w trzech sprintach od aplikacji konsolowej,
            przez aplikację webową, kończąc na aplikacji modułowej z podziałem na:
            <br/><br/>
            <ul>
                <li> - moduł logika - EJB, Hibernate (JPA)</li>
                <li> - moduł REST API</li>
                <li> - moduł webowy</li>
            </ul>
            <br/>
            W projekcie użyto technologii <strong>Java Standard Edition</strong> i <strong>JAVA Enterprise
            Edition</strong>
            <br/>
            Jako narzędzie programistyczne (IDE) używaliśny oprogramowania firmy <a class="aboutUs"
                                                                                    href="https://www.jetbrains.com/idea/">JetBrains
            IntelliJ IDEA 2018 w wersji
            Ultimate.</a><br/><br/>
            <div class="d-flex justify-content-center">
                <img src="img/logotypy_pasek.png" width="850px">
            </div>
            <br/>
            W projekcie użyto technologii Hibernate jako implemantacja Java Persistance API, Enterprise Java
            Beans, Java Server Pages, Maven, MySQL, Bootstrap, Html, CSS, JavaScript, oraz inne.
            <br/>
            Aplikacja została uruchomiona na serwerze aplikacyjnym WildFly 15 (JBoss) a baza danych na Dockerze -
            kontener z serwerem MySQL.
            <br/>
            Podczas tworzenia aplikacji używaliśmy również narzędzi pomocniczych MySQL Workbench, Jira (Scrum), Slack
            (komunikator).
        </div>
        <br/>
        <div class="d-flex justify-content-left">
            <br/>
            <br/>
            Więcej informacji o nas znajdziesz w linkach do GitHub i LinkedIn
        </div>
        <br/>
        <h2>Biblioteka Team</h2>
        <ul class="row">
            <li class="col-12 col-md-6 col-lg-4">
                <div class="cnt-block">
                    <figure><img
                            src="https://mini-figurki.pl/3731-thickbox_default/lego-minifig-the-big-bang-theory.jpg"
                            class="img-responsive" alt="" width="148px" height="148px"></figure>
                    <h3><a href="mailto:arkadiusz.kalka@gmail.com">Arkadiusz Kałka</a></h3>
                    <p>Junior Java Developer<br/>
                        Student infoShare Academy Lublin</p>
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
                    <p>Junior Java Developer<br/>
                        Student infoShare Academy Lublin</p>
                    <ul>
                        <a href="https://www.linkedin.com/in/lukaszbezlada/"><img src="img/linked.png" width="50px"
                                                                                  height="50px"/></a>
                        <a href="https://github.com/lukaszbezlada"><img src="img/github.png" width="50px"
                                                                        height="50px"/></a>
                    </ul>

                </div>
            </li>
            <li class="col-12 col-md-6 col-lg-4">
                <div class="cnt-block">
                    <figure><img
                            src="https://mini-figurki.pl/3987-thickbox_default/figurka-lego-custom-android-vs-apple.jpg"
                            class="img-responsive" alt="" width="148px" height="148px"></figure>
                    <h3><a href="#">Michał Mrozik</a></h3>
                    <p>Junior Java Developer<br/>
                        Student infoShare Academy Lublin</p>
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
        <br/><br/>
    </div>
</section>

<footer>
    <%@include file="include/footer.jsp" %>
</footer>

</body>
</html>
