<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Dashboard</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/cover.css" rel="stylesheet">
</head>
<body>
<div class="site-wrapper">
    <div class="site-wrapper-inner">
        <div class="cover-container">
            <div class="masthead clearfix">
                <div class="inner">
                    <h3 class="masthead-brand">MARS55</h3>
                    <ul class="nav masthead-nav">
                        <li class="active"><a href="#">Об университете</a></li>
                        <li><a href="#">Курсы</a></li>
                        <li><a href="#">Контакты</a></li>
                    </ul>
                </div>
            </div>
            <div class="inner cover">
                <h1 class="cover-heading">Учет посещаемости студентов</h1>
                <p class="lead">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
                    incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation
                    ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                <p class="lead">
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-lg btn-default">Войти</a>
                </p>
            </div>
            <div class="mastfoot">
                <div class="inner">
                    <p>Cover template for Bootstrap, by @mdo</p>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="css/bootstrap.min.js"></script>
</body>
</html>