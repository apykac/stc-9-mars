<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin dashboard | Registration Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/styles.css" rel="stylesheet">
</head>
<body class="login-bg">
<div class="header">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <!-- Logo -->
                <div class="logo">
                    <h1><a href="${pageContext.request.contextPath}/">Admin dashboard | Registration Page</a></h1>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="page-content container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-wrapper">
                <div class="box">
                    <div class="content-wrap">
                        <form action="${pageContext.request.contextPath}/registration" class="login-form validate"
                              method="post">
                            <h6>Регистрация</h6>
                            <input class="form-control" type="text" placeholder="Логин" name="userName" required>
                            <input class="form-control" type="password" placeholder="Пароль" name="hash_password"
                                   required>
                            <input class="form-control" type="text" placeholder="Имя" name="first_name">
                            <input class="form-control" type="text" placeholder="Фамилия" name="second_name">
                            <input class="form-control" type="text" placeholder="Отчество" name="middle_name">
                            <div class="alert alert-info">
                                Логин/пароль не должны быть пустыми, пароль - цифры<br/>
                                По желанию введите свои ФИО (при вводе Имя/Фамилия обязательны)
                            </div>
                            <c:forEach items="${requestScope.get(\"errorMsg\")}" var="msg">
                                <div class="alert alert-danger">
                                        ${msg}
                                </div>
                            </c:forEach>
                            <div class="action">
                                <button class="btn btn-primary signup" type="submit">Регистрация</button>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="already">
                    <p>Уже есть аккаунт?</p>
                    <a href="${pageContext.request.contextPath}/login">Логин</a>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="css/bootstrap.min.js"></script>
</body>
</html>