<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 23.05.2018
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:wrapper>
    <div class="row">
        <div class="col-md-5">
            <div class="content-box-header">
                <div class="panel-title ">Обновление группы</div>
            </div>
            <div class="content-box-large box-with-header">
                <form class="form-horizontal" id="upd" action="${pageContext.request.contextPath}/views/updateGroup"
                      method="post">
                    <div class="form-group">
                        <label for="nameGr" class="col-sm-2 control-label">Введите</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="nameGr" value="${requestScope.get("groupName")}"
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
                ${requestScope.get("errorName")}
                <br/>
                <form class="form-horizontal" id="delete" action="${pageContext.request.contextPath}/views/deleteGroup"
                      method="post">
                    <div class="form-group">
                        <div class="col-sm-10">
                            <input type="hidden" class="form-control" value="${requestScope.get("id")}" id="id" name="id"
                                   readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-primary">удалить эту группу</button>
                        </div>
                    </div>
                </form>
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
                        <td><a href="${pageContext.request.contextPath}/views/group/deleteStudentFromGroup/${requestScope.get("id")}/${student.id}"
                               name="${student.id}">Удалить из группы</a></td>
                    </tr>

                </c:forEach>
                    </tbody>
                </table>
                <br/>
                <br/>
                <form class="form-horizontal" id="add" action="${pageContext.request.contextPath}/views/addStudent"
                      method="post">
                    <div class="form-group">
                        <h4> Добавить студента в группу</h4>
                        <label for="nameSt" ></label>
                        <div class="col-md-5">
                            <select class="form-control" name="studentId" id="nameSt">
                                <c:forEach var="studentWOG" items="${requestScope.get('studentsWOG')}">
                                    <option name="studentId" value="${studentWOG.id}">${studentWOG.firstName} ${studentWOG.secondName} <c:choose>
                                        <c:when test="${studentWOG.group.name==null}">
                                            без группы
                                            <br />
                                        </c:when>
                                        <c:otherwise>
                                            группа- ${studentWOG.group.name}
                                            <br />
                                        </c:otherwise>
                                    </c:choose></option>
                                </c:forEach>
                            </select>
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
                <br/>
            </div>
        </div>
    </div>

</t:wrapper>