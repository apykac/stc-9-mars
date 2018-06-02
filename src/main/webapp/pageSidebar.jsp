<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="sidebar content-box" style="display: block;">
    <ul class="nav">
        <!-- Main menu -->
        <li class="current"><a href="${pageContext.request.contextPath}/views/progress">Действия</a></li>
        <li><a href="${pageContext.request.contextPath}/views/progress">Прогресс</a></li>
        <li><a href="${pageContext.request.contextPath}/views/student/studentDashBoard">Страница студента</a></li>
        <li><a href="${pageContext.request.contextPath}/views/allgroup">Группы</a></li>
        <li><a href="${pageContext.request.contextPath}/views/users-list">Пользователи</a></li>
        <li><a href="${pageContext.request.contextPath}/views/subject">Предметы</a></li>
        <li><a href="${pageContext.request.contextPath}/views/lessons">Уроки</a></li>
    </ul>
</div>