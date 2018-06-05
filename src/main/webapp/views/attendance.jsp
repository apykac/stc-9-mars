<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:wrapper>
    <div class="row">
        <div class="col-md-12">
            <div class="content-box-header">
                <div class="panel-title ">Посещаемость урока</div>
            </div>
            <div class="content-box-large box-with-header">

                <form class="form-horizontal"
                      action="${pageContext.request.contextPath}/university/teacher/attendanceSelectGroup"
                      method="post">
                    <input type="hidden" value="${requestScope.get("lessonId")}" name="lessonId">
                    <div class="form-group">
                        <label for="selectGroup" class="col-sm-2 control-label">Выбрать группу</label>
                        <div class="col-sm-8">

                            <c:set var="selectedGroup" value="${requestScope.get('groupSelected')}"/>

                            <c:if test="${selectedGroup!=null}">
                                <input type="text" class="form-control" value="${selectedGroup.name}" disabled>
                            </c:if>
                            <c:if test="${selectedGroup==null}">
                                <select class="form-control" id="selectGroup" name="selectGroup">

                                    <c:forEach items="${requestScope.get('groups')}" var="group">
                                        <option value="${group.id}">${group.name}</option>
                                    </c:forEach>

                                </select>
                            </c:if>
                        </div>
                        <div class="col-sm-2">
                            <button type="submit" class="btn btn-primary">OK</button>
                        </div>
                    </div>
                </form>

                <form class="form-horisontal"
                      action="${pageContext.request.contextPath}/university/teacher/attendanceSendStudentsList"
                      method="post">
                    <div class="row text-center">
                        <div class="col-sm-12">
                            Отметить присутствовавших студентов
                        </div>
                    </div>
                    <c:forEach items="${requestScope.get('studentsInGroup')}" var="student">
                        <div class="form-group">
                            <label for="${student.id}"
                                   class="col-sm-8 control-label">${student.secondName} ${student.firstName} ${student.middleName}</label>
                            <div class="col-sm-1">
                                <input class="form-check-input" type="checkbox" name="list" value="${student.id}"
                                       id="${student.id}">
                            </div>
                        </div>
                    </c:forEach>
                    <input type="hidden" value="${requestScope.get("lessonId")}" name="lessonId">
                    <div class="row text-center">
                        <div class="col-md-12">
                            <button type="submit" class="btn btn-primary">OK</button>
                        </div>
                    </div>
                </form>
                <br/><br/>
            </div>
        </div>
    </div>
    
</t:wrapper>
