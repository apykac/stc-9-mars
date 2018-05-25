<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="sidebar">
    <div id="news" class="boxed">
        <h2 class="title">Действия</h2>
        <div class="content">
            <ul>
                <li class="first">
                    <h3><a href="${pageContext.request.contextPath}/views/allgroup">Список групп</a> </h3>
                </li>
                <li>
                   тут еще ссылка
                </li>
                <li>
                    тут еще ссылка
                </li>
                <li>
                    <h3><a href="${pageContext.request.contextPath}/views/users-list">Список пользователей</a></h3>
                </li>
                <li>
                    тут еще ссылка
                </li>
            </ul>
        </div>
    </div>
</div>
<!-- end sidebar -->
