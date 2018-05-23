<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 23.05.2018
  Time: 19:39
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
            <h1 class="title">Обновление группы:</h1>
            <form id="add" action="${pageContext.request.contextPath}/views/group" method="post">
                <input type="text" value="${requestScope.get("groupName")}" name="name"> Новое название группы<BR>
                <input type="hidden" value="${requestScope.get("id")}" name="id">
                <input type="submit" value="OK">
            </form>
            <form id="delete" action="${pageContext.request.contextPath}/views/deleteGroup" method="post">
                <input type="hidden" value="${requestScope.get("id")}" name="id">
                <input type="submit" value="Del"> удалить эту группу
            </form>
        </div>
    </div>
</div>

</body>
</html>
