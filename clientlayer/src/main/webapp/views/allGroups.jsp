<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 23.05.2018
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:wrapper>
    <jsp:attribute name="contentHeader">
        <h1>Группы
            <small>страница для управления группами</small>
        </h1>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-md-6">
                <div class="box box-info">
                    <div class="box-header with-border">
                        <h3 class="box-title">Список групп</h3>

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
                                            <th>#</th>
                                            <th>Группа</th>
                                            <th>Действие</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${requestScope.get('groups')}" var="group">
                                            <tr>
                                                <td>${group.id}</td>
                                                <td>${group.name}</td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/university/teacher/group/${group.id}"
                                                       name="${group.name}">Информация</a></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <!-- /.table-responsive -->
                        </div>
                    </div>
                    <!-- /.box-body -->
                </div>
            </div>
            <div class="col-md-6">
                <div class="box box-primary">
                    <div class="box-header with-border">
                        <h3 class="box-title">Добавление группы</h3>

                        <div class="box-tools pull-right">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                    class="fa fa-minus"></i>
                            </button>
                        </div>
                    </div>
                    <div class="box-body">
                        <form id="add" action="${pageContext.request.contextPath}/university/teacher/addgroups"
                              method="post">
                            <div class="form-group">
                                <label for="nameGr">Введите</label>
                                <div class="input-group input-group-lg">
                                    <input type="text" class="form-control" id="nameGr" placeholder="Название группы"
                                           name="name">
                                    <span class="input-group-btn">
                                        <button type="submit" class="btn btn-info btn-flat">OK</button>
                                    </span>
                                </div>
                            </div>
                        </form>
                        <br/>
                        <div class="alert-error">${requestScope.get("errorName")}</div>
                        <br/>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:wrapper>




