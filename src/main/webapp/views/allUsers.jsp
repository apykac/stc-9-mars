<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:wrapper>
    <jsp:attribute name="contentHeader">
        <h1>Пользователи
            <small>Страница для управления пользователями</small>
        </h1>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-md-12">
                <div class="box box-info">
                    <div class="box-header with-border">
                        <h3 class="box-title">Список пользователей</h3>

                        <div class="box-tools pull-right">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                    class="fa fa-minus"></i>
                            </button>
                        </div>
                    </div>
                    <div class="box-body">
                        <div class="dataTables_wrapper form-inline dt-bootstrap">
                            <div class="row">
                                <div class="col-sm-12">
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th>Логин</th>
                                            <th>Имя</th>
                                            <th>Фамилия</th>
                                            <th>Отчество</th>
                                            <th>Активен/Неактивен</th>
                                            <th>Действие</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${requestScope.get('usersList')}" var="user">
                                            <c:if test="${((user.id != sessionScope.get('entered_user_id')) && (user.permissionGroup != 'ROLE_ADMIN')) || (user.enabled == 0)}">
                                                <tr>
                                                    <td>${user.login}</td>
                                                    <td>${user.firstName}</td>
                                                    <td>${user.secondName}</td>
                                                    <td>${user.middleName}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${user.enabled == 1}">Активен</c:when>
                                                            <c:when test="${user.enabled == 0}">Неактивен</c:when>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <a href="${pageContext.request.contextPath}/admin/edit_user/${user.id}"
                                                           name="${user.firstName}">Редактировать
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <!-- /.table-responsive -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:wrapper>