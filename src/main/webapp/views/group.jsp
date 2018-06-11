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
            <div class="col-md-5">
                <div class="content-box-header">
                    <div class="panel-title ">Обновление группы</div>
                </div>
                <div class="content-box-large box-with-header">
                    <form class="form-horizontal" id="upd"
                          action="${pageContext.request.contextPath}/university/teacher/updateGroup"
                          method="post">
                        <div class="form-group">
                            <label for="nameGr" class="col-sm-2 control-label">Введите</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="nameGr"
                                       value="${requestScope.get("groupName")}"
                                       name="name">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-10">
                                <input type="hidden" value="${requestScope.get("id")}" name="id">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" class="btn btn-primary">OK</button>
                            </div>
                        </div>
                    </form>
                    <br/>
                    <div class="alert-error">${requestScope.get("errorName")}</div>
                    <br/>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <form class="form-horizontal" id="delete"
                              action="${pageContext.request.contextPath}/university/teacher/deleteGroup"
                              method="post">
                            <div class="form-group">
                                <div class="col-sm-10">
                                    <input type="hidden" class="form-control" value="${requestScope.get("id")}" id="id"
                                           name="id"
                                           readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="submit" class="btn btn-primary">удалить эту группу</button>
                                </div>
                            </div>
                        </form>
                    </sec:authorize>
                </div>
            </div>


                <%--список студентов--%>
            <div class="col-md-5">
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
                    <br/>
                    <br/>
                        <%--add filter--%>

                    <div class="col-sm-15">
                        <div id="example_length" class="dataTables_length">
                            <form action="${pageContext.request.contextPath}/university/teacher/group/${requestScope.get("id")}"
                                  method="post">
                                <div class="form-group">
                                            <span class="input-group margin">
                                            <select name="groupStatus" class="form-control">
                                                <option value="">Без группы</option>
                                                <c:forEach var="group" items="${requestScope.get('groups')}">
                                                    <c:if test="${group.id != requestScope.get('id')}">
                                                        <option value="${group.id}">${group.name}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                            <span class="input-group-btn">
                                                <button type="submit"
                                                        class="btn btn-info btn-flat"
                                                        name="form"
                                                        value="filterForm">Фильтр</button></span>
                                            </span>
                                </div>
                            </form>
                        </div>
                    <%--тут див--%>

                        <%--end filter--%>
                    <form class="form-horizontal" id="add"
                          action="${pageContext.request.contextPath}/university/teacher/addStudent"
                          method="post">
                        <div class="form-group">
                            <h4> Добавить студента в группу</h4>


                            <label for="nameSt"></label>
                            <div class="col-md-5">
                                <select class="form-control" name="studentId" id="nameSt">
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
                        </div>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <input type="hidden" value="${requestScope.get("id")}" name="id">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" class="btn btn-primary">OK</button>
                            </div>
                        </div>
                    </form>
                    </div>
                    <br/>
                    <br/>
                </div>
            </div>
        </div>
    </jsp:body>
</t:wrapper>