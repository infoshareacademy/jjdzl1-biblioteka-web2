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
        <div class="d-flex justify-content-center">
            <h2>Nasz zespół</h2>
        </div>
        <br/><br/>
        <ul class="row">
            <li class="col-12 col-md-6 col-lg-4">
                <div class="cnt-block equal-hight" style="height: 349px;">
                    <figure><img
                            src="https://mini-figurki.pl/3731-thickbox_default/lego-minifig-the-big-bang-theory.jpg"
                            class="img-responsive" alt=""></figure>
                    <h3><a href="#">Arkadiusz Kałka</a></h3>
                    <p>Student infoShare Academy Lublin</p>
                    <ul class="follow-us clearfix">
                        <li><a href="#"><i class="fa fa-facebook" aria-hidden="true"></i></a></li>
                        <li><a href="#"><i class="fa fa-twitter" aria-hidden="true"></i></a></li>
                        <li><a href="#"><i class="fa fa-linkedin" aria-hidden="true"></i></a></li>
                    </ul>
                </div>
            </li>
            <li class="col-12 col-md-6 col-lg-4">
                <div class="cnt-block equal-hight" style="height: 349px;">
                    <figure><img src="https://mini-figurki.pl/1210-thickbox_default/lego-minifig-mutt-williams.jpg"
                                 class="img-responsive" alt=""></figure>
                    <h3><a href="#">Łukasz Bezłada</a></h3>
                    <p>Student infoShare Academy Lublin</p>
                    <ul class="follow-us clearfix">
                        <li><a href="#"><i class="fa fa-facebook" aria-hidden="true"></i></a></li>
                        <li><a href="#"><i class="fa fa-twitter" aria-hidden="true"></i></a></li>
                        <li><a href="#"><i class="fa fa-linkedin" aria-hidden="true"></i></a></li>
                    </ul>
                </div>
            </li>
            <li class="col-12 col-md-6 col-lg-4">
                <div class="cnt-block equal-hight" style="height: 349px;">
                    <figure><img
                            src="https://mini-figurki.pl/3987-thickbox_default/figurka-lego-custom-android-vs-apple.jpg"
                            class="img-responsive" alt=""></figure>
                    <h3><a href="#">Michał Mrozik</a></h3>
                    <p>Student infoShare Academy Lublin</p>
                    <ul class="follow-us clearfix">
                        <li><a href="#"><i class="fa fa-facebook" aria-hidden="false"></i></a></li>
                        <li><a href="#"><i class="fa fa-twitter" aria-hidden="false"></i></a></li>
                        <li><a href="#"><i class="fa fa-linkedin" aria-hidden="false"></i></a></li>
                    </ul>
                </div>
            </li>
        </ul>
    </div>
</section>

<footer>
    <%@include file="include/footer.jsp" %>
</footer>

</body>
</html>
