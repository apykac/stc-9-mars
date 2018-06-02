<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="header">
    <div id="logo">
        <h1><a href="http://www.innopolis.com/">Innopolis</a></h1>
        <h2>Students v-1.0</h2>
    </div>
    <div id="menu">
        <ul>
            <li class="active"> <a> Добро пожаловать, ${sessionScope.get("name")} </a></li>
            <li><a href="/j_spring_security_logout">Выйти</a></li>

        </ul>
    </div>
</div>

