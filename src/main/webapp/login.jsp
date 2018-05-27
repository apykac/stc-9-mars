<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin dashboard | Login Page</title>
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
                    <h1><a href="${pageContext.request.contextPath}/">Admin dashboard | Login Page</a></h1>
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
                        <form action="${pageContext.request.contextPath}/login" class="login-form validate"
                              method="post">
                            <h6>Вход</h6>
                            <input class="form-control" type="text" placeholder="Логин" name="userName">
                            <input class="form-control" type="password" placeholder="Пароль" name="userPassword">
                            <%=("authError".equals(request.getParameter("errorMsg"))) ? "<div class='alert alert-danger'>" +
                                    "Неверное имя/пароль" +
                                    "</div>" : ""%>
                            <%=("noAuth".equals(request.getParameter("errorMsg"))) ? "<div class='alert alert-danger'>" +
                                    "Ошибка авторизации" +
                                    "</div>" : ""%>
                            <div class="action">
                                <button class="btn btn-primary signup" type="submit">Логин</button>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="already">
                    <p>Еще нет аккаунта?</p>
                    <a href="${pageContext.request.contextPath}/registration">Регистрация</a>
                    <br/>
                    <p>Хотите вернуться?</p>
                    <a href="${pageContext.request.contextPath}/">Назад</a>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="css/bootstrap.min.js"></script>
</body>
</html>