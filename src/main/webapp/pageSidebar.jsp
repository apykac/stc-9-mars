<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="sidebar content-box" style="display: block;">
    <ul class="nav">
        <!-- Main menu -->
        <li class="current"><a href="${pageContext.request.contextPath}/views/progress">Действия</a></li>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <li><a href="${pageContext.request.contextPath}/admin/profile">Редактировать свой профиль</a></li>
            <li><a href="${pageContext.request.contextPath}/views/progress">Прогресс</a></li>
            <li><a href="${pageContext.request.contextPath}/views/allgroup">Группы</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users_list">Пользователи</a></li>
            <li><a href="${pageContext.request.contextPath}/views/subject">Предметы</a></li>
            <li><a href="${pageContext.request.contextPath}/views/lessons">Уроки</a></li>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_TEACHER')">
            <li><a href="${pageContext.request.contextPath}/university/teacher/profile">Редактировать свой профиль</a>
            </li>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_STUDENT')">
            <li><a href="${pageContext.request.contextPath}/university/student/profile">Редактировать свой профиль</a>
            </li>
        </sec:authorize>

    </ul>
</div>