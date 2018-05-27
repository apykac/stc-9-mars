<%@ page import="ru.innopolis.stc9.pojo.Login" %>
<%@ page import="ru.innopolis.stc9.pojo.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    User user = (User)request.getAttribute("user");
    Login login = (Login)request.getAttribute("login");
    String loginUpdateMessage = (String) request.getAttribute("loginUpdateMessage");
    String userUpdateMessage = (String) request.getAttribute("userUpdateMessage");
    String passwordUpdateMessage = (String) request.getAttribute("passwordUpdateMessage");
%>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>Students</title>
    <link href="/css/default.css" rel="stylesheet" type="text/css" />
    <!--link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css"/-->
</head>
<body>
<%@include file="../pageSidebar.jsp" %>
<div id="page">
    <div id="content">
        <div style="margin-bottom: 20px;">
            <h1 class="title">Редактирование пользователя</h1>

            <form action="${pageContext.request.contextPath}/views/edit-user" method="post">
                <div>
                    <label for="userId">Id</label>
                    <input type="text" class="form-control" id="userId" placeholder="User ID" name="userId"
                           value="<%=user.getId()%>" readonly>
                </div>
                <div>
                    <label for="editUsername">Логин</label>
                    <input type="text" class="form-control" id="editUsername" placeholder="Username" name="editUsername" value="<%=login.getUserName()%>">
                </div>
                <div>
                    <label for="editFirstName">Имя</label>
                    <input type="text" class="form-control" id="editFirstName" placeholder="First name" name="editFirstName" value="<%=user.getFirstName()%>">
                </div>
                <div>
                    <label for="editLastName">Фамилия</label>
                    <input type="text" class="form-control" id="editLastName" placeholder="Last name" name="editLastName" value="<%=user.getSecondName()%>">
                </div>
                <div>
                    <label for="editMiddleName">Отчество</label>
                    <input type="text" class="form-control" id="editMiddleName" placeholder="Middle name"
                           name="editMiddleName" value="<%=user.getMiddleName()%>">
                </div>
                <b>Роль пользователя</b>
                <div>
                    <select name="editRole">
                        <option <%=login.getPermissionGroup() == 0 ? "selected" : ""%> value="0">Admin</option>
                        <option <%=login.getPermissionGroup() == 1 ? "selected" : ""%> value="1">Teacher</option>
                        <option <%=login.getPermissionGroup() == 2 ? "selected" : ""%> value="2">Student</option>
                    </select>
                </div>
                <b>Изменить пароль</b>
                <div>
                    <label for="oldPassword">Старый пароль</label>
                    <input type="password" id="oldPassword" plaseholder="Old password" name="oldPassword" value="">
                </div>
                <div>
                    <label for="newPassword">Новый пароль</label>
                    <input type="password" id="newPassword" plaseholder="New password" name="newPassword" value="">
                </div>
                <div>
                    <label for="repeatNewPassword">Повтор</label>
                    <input type="password" id="repeatNewPassword" plaseholder="Repeat new password" name="repeatNewPassword" value="">
                </div>
                <div>
                    <input type="submit" value="Сохранить">
                </div>
                <div>
                    <%=userUpdateMessage%>
                </div>
                <div>
                    <%=loginUpdateMessage%>
                </div>
                <div>
                    <%=passwordUpdateMessage == null ? "" : passwordUpdateMessage%>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>