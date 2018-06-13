<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 23.05.2018
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<t:wrapper>
    <jsp:attribute name="contentHeader">
        <h1>Группы
            <small>информация</small>
        </h1>
    </jsp:attribute>
    <jsp:body>

        <div class="row">
            <div class="col-md-12 content-box-header">
                <div class="panel-title ">Обновление группы</div>
            </div>
        </div>
        <!--Блок редактирования названия группы-->
        <div class="row">
            <div class="col-md-10">
                <form class="form-horizontal" id="upd"
                      action="${pageContext.request.contextPath}/university/teacher/updateGroup"
                      method="post">
                    <div class="form-group">
                        <label for="nameGr" class="col-md-2 control-label">Сменить название</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" id="nameGr"
                                   value="${requestScope.get("groupName")}"
                                   name="name">
                        </div>
                        <div class="col-md-1">
                            <input type="hidden" value="${requestScope.get("id")}" name="id">
                            <button type="submit" class="btn btn-primary">OK</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-md-2">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <form class="form-horizontal" id="delete"
                          action="${pageContext.request.contextPath}/university/teacher/deleteGroup"
                          method="post">
                        <input type="hidden"
                               class="form-control"
                               value="${requestScope.get("id")}"
                               id="id"
                               name="id"
                               readonly>
                        <div class="form-group">
                            <div class="col-md-12">
                                <button type="submit" class="btn btn-danger">Удалить эту группу</button>
                            </div>
                        </div>
                    </form>
                </sec:authorize>
            </div>
        </div>
        <div class="row">
            <div class="alert-error">${requestScope.get("errorName")}</div>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="content-box-header">
                    <div class="panel-title ">Список студентов</div>
                </div>
                <div class="content-box-large box-with-header">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Студент</th>
                            <th>Действие</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="student" items="${requestScope.get('students')}">
                            <tr>
                                <td><h5>${student.firstName} ${student.secondName}</h5></td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/university/teacher/group/deleteStudentFromGroup/${requestScope.get("id")}/${student.id}"
                                       name="${student.id}">Удалить из группы</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col-md-6">
                <div class="content-box-header" style="margin-bottom: 10px">
                    <div class="panel-title">Перемещение студентов из других групп</div>
                </div>

                <!--Форма выбора группы-->
                <div class="row content-box-large box-with-header">
                    <form class="form-horizontal"
                          action="${pageContext.request.contextPath}/university/teacher/group/${requestScope.get("id")}"
                          method="post">
                        <div class="form-group">
                            <label for="groupStatus" class="col-md-3 control-label">Выбрать группу</label>
                            <div class="col-md-7">
                                <select name="groupStatus" class="form-control" id="groupStatus"
                                        data-toggle="tooltip" data-placement="top"
                                        title="Выбрать группу из которой нужно перенести студента">
                                    <option value="">Без группы</option>
                                    <c:forEach var="group" items="${requestScope.get('groups')}">
                                        <c:if test="${group.id != requestScope.get('id')}">
                                            <option value="${group.id}">${group.name}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <button type="submit"
                                        class="btn btn-primary"
                                        name="form"
                                        value="filterForm">OK
                                </button>
                            </div>
                        </div>
                    </form>
                </div>

                <!--Форма выбора студента для перемещения-->
                <div class="row content-box-large box-with-header">
                    <form class="form-horizontal" id="add"
                          action="${pageContext.request.contextPath}/university/teacher/addStudent"
                          method="post">
                        <div class="form-group">
                            <label for="nameSt" class="col-md-3 control-label">Выбрать студента</label>
                            <div class="col-md-7">
                                <select class="form-control" name="studentId" id="nameSt"
                                        data-toggle="tooltip" data-placement="top"
                                        title="Выбрать студента для перемещения в текущую группу">
                                    <c:forEach var="studentWOG" items="${requestScope.get('studentsWOG')}">
                                        <option name="studentId"
                                                value="${studentWOG.id}">${studentWOG.firstName} ${studentWOG.secondName}
                                            <c:choose>
                                                <c:when test="${studentWOG.group.name==null}">
                                                    без группы
                                                    <br/>
                                                </c:when>
                                                <c:otherwise>
                                                    группа- ${studentWOG.group.name}
                                                    <br/>
                                                </c:otherwise>
                                            </c:choose></option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <input type="hidden" value="${requestScope.get("id")}" name="id">
                                <button type="submit" class="btn btn-primary">OK</button>
                            </div>
                        </div>
                    </form>
                </div>

            </div>
        </div>

        </div>
    </jsp:body>
</t:wrapper>