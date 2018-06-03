<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Admin dashboard | Registration Page</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/ionicons.min.css">
    <link rel="stylesheet" href="assets/css/AdminLTE.min.css">
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body class="hold-transition register-page">
<div class="register-box">
    <div class="register-logo">
        <a href="${pageContext.request.contextPath}/"><b>MARS</b>55</a>
    </div>

    <div class="register-box-body">
        <p class="login-box-msg">Регистрация</p>

        <form action="${pageContext.request.contextPath}/registration" method="post">
            <div class="form-group has-feedback">
                <input type="text" class="form-control" placeholder="Логин" name="login" required>
                <span class="glyphicon fa fa-user form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" class="form-control" placeholder="Пароль" name="hash_password" required>
                <span class="glyphicon fa fa-lock form-control-feedback"></span>
            </div>
            <br/>
            <div class="form-group has-feedback">
                <input type="text" class="form-control" placeholder="Имя" name="first_name">
                <span class="glyphicon fa fa-user-plus form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="text" class="form-control" placeholder="Фамилия" name="second_name">
                <span class="glyphicon fa fa-user-plus form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="text" class="form-control" placeholder="Отчество" name="middle_name">
                <span class="glyphicon fa fa-user-plus form-control-feedback"></span>
            </div>

            <div class="alert alert-info">
                Логин/пароль не должны быть пустыми, пароль - цифры<br/>
                По желанию введите свои ФИО
            </div>
            <c:forEach items="${requestScope.get(\"errorMsg\")}" var="msg">
                <div class="alert alert-danger">
                        ${msg}
                </div>
            </c:forEach>

            <div class="row">
                <div class="col-xs-8">
                </div>
                <!-- /.col -->
                <div class="col-xs-4">
                    <button type="submit" class="btn btn-primary btn-block btn-flat">Регистрация</button>
                </div>
                <!-- /.col -->
            </div>
        </form>

        <a href="${pageContext.request.contextPath}/login" class="text-center">Обратно к логину</a>
    </div>
    <!-- /.form-box -->
</div>
<!-- /.register-box -->

<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
</body>
</html>