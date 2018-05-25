<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%%>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>Students</title>
    <link href="/css/default.css" rel="stylesheet" type="text/css" />
    <!--link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css"/-->
</head>
<body>
<%@include file="../header.jsp"%>
<%@include file="../sideBar.jsp"%>
<div id="page">
    <div id="content">
        <div style="margin-bottom: 20px;">
            <h1 class="title">Список пользователей:</h1>

            <c:forEach items="${requestScope.get(\"usersList\")}" var="user">
                <div>
                    ${user.id} <a href="${pageContext.request.contextPath}/views/edit-user?user-id=${user.id}" name="${user.firstName}">${user.firstName} ${user.secondName} </a><br>
                </div>
            </c:forEach>
            <div>

            </div>
        </div>
    </div>
</div>

</body>
</html>