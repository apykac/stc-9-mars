<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8"/>
    <meta charset="utf-8"/>
    <title>Registration</title>
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body class="error-body">
<div class="container">
    <div class="row login-container column-seperation">
        <div class="col-md-4">
            <form action="${pageContext.request.contextPath}/registration" class="login-form validate" id="login-form"
                  method="post" name="login-form">
                <div class="row">
                    <div class="form-group col-md-10">
                        <label class="form-label">Логин*</label><br>
                        <input class="form-control" id="txtusername" name="userName" type="text" required>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-10">
                        <label class="form-label">Пароль*</label> <span class="help"></span>
                        <input class="form-control" id="txtpassword" name="hash_password" type="password" required>
                    </div>
                </div>
                <br>
                <br>
                <br>
                <b>Ввод ФИО</b>
                <br>
                <br>
                <div class="row">
                    <div class="form-group col-md-10">
                        <label class="form-label">Имя*</label><br>
                        <input class="form-control" name="first_name" type="text">
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-10">
                        <label class="form-label">Фамилия*</label><br>
                        <input class="form-control" name="second_name" type="text">
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-10">
                        <label class="form-label">Отчество</label><br>
                        <input class="form-control" name="middle_name" type="text">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-10">
                        <button class="btn btn-primary btn-cons pull-right" type="submit">Register</button>
                    </div>
                </div>
            </form>
        </div>
        <i>
            Логин/пароль не должны быть пустыми, пароль - цифры<br>
            По желанию введите свои ФИО (при вводе Имя/Фамилия обязательны)
        </i>
        <br>
        <c:forEach items="${requestScope.get(\"errorMsg\")}" var="msg">
            <b>${msg}</b><br>
        </c:forEach>
    </div>
</div>
</body>
</html>