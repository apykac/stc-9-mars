<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin dashboard</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
</head>
<body>
<div class="header">
    <div class="container">
        <div class="row">
            <div class="col-md-8">
                <div class="logo">
                    <h1>Admin dashboard</h1>
                </div>
            </div>
            <div class="col-md-4">
                <div class="navbar navbar-inverse" role="banner">
                    <nav class="collapse navbar-collapse bs-navbar-collapse navbar-right" role="navigation">
                        <ul class="nav navbar-nav">
                            <li><a href="#"><%=(String) request.getSession().getAttribute("login")%>
                            </a></li>
                            <li><a href="${pageContext.request.contextPath}/login?action=logout">Выйти</a></li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>

