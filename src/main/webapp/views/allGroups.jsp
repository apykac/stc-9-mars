<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 23.05.2018
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:wrapper>
    <div class="row">
        <div class="col-md-8 panel-warning">
            <div class="content-box-header">
                <div class="panel-title ">Список групп</div>
            </div>
            <div class="content-box-large box-with-header">
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
                            <td><a href="${pageContext.request.contextPath}/university/teacher/group/${group.id}"
                                   name="${group.name}">Информация</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <br/><br/>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-5">
            <div class="content-box-header">
                <div class="panel-title ">Добавление группы</div>
            </div>
            <div class="content-box-large box-with-header">
                <form class="form-horizontal" id="add"
                      action="${pageContext.request.contextPath}/university/teacher/addgroups"
                      method="post">
                    <div class="form-group">
                        <label for="nameGr" class="col-sm-2 control-label">Введите</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="nameGr" placeholder="Название группы"
                                   name="name">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-primary">OK</button>
                        </div>
                    </div>
                </form>
                <br/>
                    ${requestScope.get("errorName")}
                <br/>
            </div>
        </div>
    </div>
</t:wrapper>




