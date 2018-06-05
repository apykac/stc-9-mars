<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="${pageContext.request.contextPath}/assets/img/user2-160x160.jpg" class="img-circle"
                     alt="User Image">
            </div>
            <div class="pull-left info">
                <p>${sessionScope.get('entered_name')}
                </p>
                <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
        </div>
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu" data-widget="tree">
            <li class="header">MAIN NAVIGATION</li>
            <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_STUDENT', 'ROLE_TEACHER')">
                <li>
                    <a href="${pageContext.request.contextPath}/university/progress">
                        <i class="fa fa-pie-chart"></i> <span>Прогресс</span>
                        <span class="pull-right-container">
                            <small class="label pull-right bg-green">Отчет</small>
                        </span>
                    </a>
                </li>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_STUDENT')">
                <li>
                    <a href="${pageContext.request.contextPath}/university/student/studentDashBoard">
                        <i class="fa fa-users"></i> <span>Страница студента</span>
                    </a>
                </li>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')">
                <li>
                    <a href="${pageContext.request.contextPath}/university/teacher/allgroup">
                        <i class="fa fa-th"></i> <span>Группы</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/university/teacher/subject">
                        <i class="fa fa-list-ol"></i> <span>Предметы</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/university/teacher/lessons">
                        <i class="fa fa-book"></i> <span>Уроки</span>
                    </a>
                </li>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <li>
                    <a href="${pageContext.request.contextPath}/admin/users_list">
                        <i class="fa fa-users"></i> <span>Пользователи</span>
                    </a>
                </li>
            </sec:authorize>
        </ul>
    </section>
    <!-- /.sidebar -->
</aside>