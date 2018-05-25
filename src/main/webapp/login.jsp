<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8"/>
    <meta charset="utf-8"/>
    <title>Admin Dashboard</title>
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body class="error-body">
<div class="container">
    <div class="row login-container column-seperation">
        <div class="col-md-4">
            <form action="${pageContext.request.contextPath}/login" class="login-form validate" id="login-form"
                  method="post" name="login-form">
                <%=("authError".equals(request.getParameter("errorMsg"))) ? "Неверное имя/пароль" : ""%>
                <%=("noAuth".equals(request.getParameter("errorMsg"))) ? "Ошибка авторизации" : ""%>
                <div class="row">
                    <div class="form-group col-md-10">
                        <label class="form-label">Username</label><br>
                        <input class="form-control" id="txtusername" name="userName" type="text" value="admin" required>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-10">
                        <label class="form-label">Password</label> <span class="help"></span>
                        <input class="form-control" id="txtpassword" name="userPassword" type="password" value="123"
                               required>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-10">
                        <button class="btn btn-primary btn-cons pull-right" type="submit">Login</button>
                        <a class="btn btn-primary btn-cons pull-right"
                           href="${pageContext.request.contextPath}/registration">Registration</a>
                    </div>
                </div>
                <br>
            </form>
            <%if ((request.getParameter("registration") != null) && request.getParameter("registration").equals("true")) {%>
            Регистрация прошла успешно
            <%}%>
        </div>
    </div>
</div>
</body>
</html>
