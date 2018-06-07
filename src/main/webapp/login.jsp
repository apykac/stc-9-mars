<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Admin dashboard | Login Page</title>
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <link rel="stylesheet" href="assets/css/bootstrap.min.css">
  <link rel="stylesheet" href="assets/css/font-awesome.min.css">
  <link rel="stylesheet" href="assets/css/ionicons.min.css">
  <link rel="stylesheet" href="assets/css/AdminLTE.min.css">

  <link rel="stylesheet"
        href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <a href="${pageContext.request.contextPath}/"><b>MARS</b>55</a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg">Sign in to start your session</p>

    <form action="/j_spring_security_check" method="post">
      <div class="form-group has-feedback">
        <input type="text" class="form-control" placeholder="login" name="j_username">
        <span class="glyphicon fa fa-envelope form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" placeholder="Password" name="j_password">
        <span class="glyphicon fa fa-key form-control-feedback"></span>
      </div>
      <c:if test="${param.errorMsg != null}">
        <c:if test="${param.errorMsg == 'authError'}">
          <div class='alert alert-danger'>Неверное имя/пароль</div>
        </c:if>
        <c:if test="${param.errorMsg == 'noAuth'}">
          <div class='alert alert-danger'>Ошибка авторизации</div>
        </c:if>
      </c:if>
      <div class="row">
        <div class="col-xs-8">
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>
        </div>
        <!-- /.col -->
      </div>
    </form>

    <a href="${pageContext.request.contextPath}/registration" class="text-center">Зарегистрироваться</a><br/>
    <a href="${pageContext.request.contextPath}/" class="text-center">Назад</a><br/>
    <c:if test="${param.registration != null && param.registration == 'true'}">
      <div class="alert alert-success"><p>Регистрация прошла успешно</p></div>
    </c:if>

  </div>
</div>

<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
</body>
</html>
