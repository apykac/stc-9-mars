<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 23.05.2018
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>Groups</title>
    <link href="/css/default.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%@include file="../header.jsp"%>
<%@include file="../sideBar.jsp"%>
<div id="page">
    <div id="content">
        <div style="margin-bottom: 20px;">
            <h1 class="title">Список групп:</h1>
            <c:forEach items="${requestScope.get(\"groups\")}" var="group">
                <a href="${pageContext.request.contextPath}/views/group?id=${group.id}" name="${group.name}">${group.name} </a><br>
            </c:forEach>
            <br>
            <h1 class="title">Добавление группы:</h1>
            <form id="add" action="${pageContext.request.contextPath}/views/allgroup" method="post">
                <input type="text" value="" name="name"> Название группы<BR>
                <input type="submit" value="OK">
            </form>
        </div>
    </div>
</div>

</body>
</html>
