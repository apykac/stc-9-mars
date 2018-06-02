<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>NPE</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ionicons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/AdminLTE.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/skins/_all-skins.min.css">

    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body class="hold-transition skin-red sidebar-mini">
<div class="wrapper">

    <!-- Main Header -->
    <header class="main-header">

        <!-- Logo -->
        <a href="#" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"><b>M</b>55</span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><b>MARS</b>55</span>
        </a>
        <nav class="navbar navbar-static-top" role="navigation">
            <!-- Navbar Right Menu -->
            <div class="navbar-custom-menu">
            </div>
        </nav>

    </header>
    <div class="content-wrapper" style="margin-left:0px;">
        <!-- Main content -->
        <section class="content">
            <div class="error-page">
                <h2 class="headline text-red"> NPE</h2>

                <div class="error-content">
                    <h3><i class="fa fa-warning text-red"></i> java.lang.NullPointerException</h3>

                    <p>
                        Вы можете воспользоваться поиском.
                    </p>

                    <form class="search-form" action="${pageContext.request.contextPath}/" method="post">
                        <div class="input-group">
                            <input type="text" name="search" class="form-control" placeholder="Поиск">

                            <div class="input-group-btn">
                                <button type="submit" class="btn btn-danger btn-flat"><i
                                        class="fa fa-search"></i>
                                </button>
                            </div>
                        </div>
                        <!-- /.input-group -->
                    </form>
                </div>
                <!-- /.error-content -->
            </div>
            <!-- /.error-page -->

            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">Exception</h3>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body table-responsive no-padding">
                            <table class="table">
                                <tr>
                                    <td width="10%"><b>Error:</b></td>
                                    <td>${pageContext.exception}</td>
                                </tr>
                                <tr>
                                    <td><b>URI:</b></td>
                                    <td>${pageContext.errorData.requestURI}</td>
                                </tr>
                                <tr>
                                    <td><b>Status code:</b></td>
                                    <td>${pageContext.errorData.statusCode}</td>
                                </tr>
                                <tr>
                                    <td><b>Stack trace:</b></td>
                                    <td>
                                        <c:forEach var="trace" items="${pageContext.exception.stackTrace}">
                                            <p>${trace}</p>
                                        </c:forEach>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- Main Footer -->
    <footer class="main-footer" style="margin-left:0px;">
        <!-- Default to the left -->
        <strong>Copyright &copy; 2018 <a href="#">Mars55</a>.</strong> All rights reserved.
    </footer>

</div>
<!-- ./wrapper -->

<script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/adminlte.min.js"></script>
</body>
</html>