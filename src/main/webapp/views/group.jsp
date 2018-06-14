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
            <div class="col-md-6">
                <div class="box box-success">
                    <div class="box-header with-border">
                        <h3 class="box-title">Администрирование группы</h3>
                        <div class="box-tools pull-right">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                    class="fa fa-minus"></i>
                            </button>
                        </div>
                    </div>
                    <div class="box-body">
                        <form id="upd"
                              action="${pageContext.request.contextPath}/university/teacher/updateGroup"
                              method="post">
                            <div class="form-group">
                                <label for="nameGr">Сменить название</label>
                                <div class="input-group input-group-lg">
                                    <input type="text" class="form-control" id="nameGr"
                                           value="${requestScope.get("groupName")}"
                                           name="name">
                                    <input type="hidden" value="${requestScope.get("id")}" name="id">
                                    <span class="input-group-btn">
                                        <button type="submit" class="btn btn-info btn-flat">OK</button>
                                    </span>
                                </div>
                            </div>
                        </form>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <br/>
                            <form id="delete"
                                  action="${pageContext.request.contextPath}/university/teacher/deleteGroup"
                                  method="post">
                                <input type="hidden"
                                       class="form-control"
                                       value="${requestScope.get("id")}"
                                       id="id"
                                       name="id"
                                       readonly>
                                <button type="submit" class="btn btn-danger">Удалить эту группу</button>
                            </form>
                            <br/>
                        </sec:authorize>
                        <div class="row">
                            <div class="alert-error">${requestScope.get("errorName")}</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="box box-success">
                    <div class="box-header with-border">
                        <h3 class="box-title">Перемещение студентов из других групп</h3>
                        <div class="box-tools pull-right">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                    class="fa fa-minus"></i>
                            </button>
                        </div>
                    </div>
                    <div class="box-body">
                        <form action="${pageContext.request.contextPath}/university/teacher/group/${requestScope.get("id")}"
                              method="post">
                            <div class="form-group">
                                <label for="groupStatus">Выбрать группу</label>
                                <div class="input-group input-group-lg">
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
                                    <span class="input-group-btn">
                                        <button type="submit" class="btn btn-primary">OK</button>
                                    </span>
                                </div>
                            </div>
                        </form>
                        <br/>
                        <form id="add"
                              action="${pageContext.request.contextPath}/university/teacher/addStudent"
                              method="post">
                            <div class="form-group">
                                <label for="nameSt">Выбрать студента</label>
                                <div class="input-group input-group-lg">
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
                                    <input type="hidden" value="${requestScope.get("id")}" name="id">
                                    <span class="input-group-btn">
                                        <button type="submit" class="btn btn-primary">OK</button>
                                    </span>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="box box-info">
                    <div class="box-header with-border">
                        <h3 class="box-title">Список студентов</h3>
                        <div class="box-tools pull-right">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                    class="fa fa-minus"></i>
                            </button>
                        </div>
                    </div>
                    <div class="box-body">
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
            </div>
        </div>
    </jsp:body>
</t:wrapper>