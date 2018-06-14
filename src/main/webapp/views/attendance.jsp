<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<t:wrapper>
    <jsp:attribute name="contentHeader">
        <h1>Посещаемость урока
            <small></small>
        </h1>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-md-3">
                <div class="box box-info">
                    <div class="box-header with-border">
                        <h3 class="box-title">Группа</h3>
                        <div class="box-tools pull-right">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                    class="fa fa-minus"></i>
                            </button>
                        </div>
                    </div>
                    <div class="box-body">
                        <form action="${pageContext.request.contextPath}/university/teacher/attendanceSelectGroup"
                              method="post">
                            <input type="hidden" value="${requestScope.get("lessonId")}" name="lessonId">
                            <div class="form-group">
                                <label for="selectGroup">Выбрать группу</label>
                                <div class="input-group input-group-lg">

                                    <c:set var="selectedGroup"
                                           value="${requestScope.get('groupSelected')}"/>

                                    <c:if test="${selectedGroup!=null}">

                                        <select class="form-control" id="selectGroup" name="selectGroup">

                                            <c:forEach items="${requestScope.get('groups')}" var="group">
                                                <option ${selectedGroup.name == group.name ? "selected" : ""}
                                                        value="${group.id}">${group.name}</option>
                                            </c:forEach>

                                        </select>

                                        <%--<input type="text" class="form-control" value="${selectedGroup.name}" disabled>--%>
                                    </c:if>
                                    <c:if test="${selectedGroup==null}">
                                        <select class="form-control" id="selectGroup" name="selectGroup">

                                            <c:forEach items="${requestScope.get('groups')}" var="group">
                                                <option value="${group.id}">${group.name}</option>
                                            </c:forEach>

                                        </select>
                                    </c:if>
                                    <span class="input-group-btn">
                                                    <button type="submit" class="btn btn-info btn-flat">OK</button>
                                                </span>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <c:if test="${selectedGroup!=null}">
                <div class="col-md-4">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">Настройки</h3>

                            <div class="box-tools pull-right">
                                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                        class="fa fa-minus"></i>
                                </button>
                            </div>
                        </div>
                        <div class="box-body">


                            <c:set var="studentsInGroup" value="${requestScope.get('studentsInGroup')}"/>

                            <c:if test="${fn:length(studentsInGroup) lt 1}">
                                <div class="alert alert-danger">
                                    В выбранной группе нет студентов =(
                                </div>
                            </c:if>

                            <c:if test="${fn:length(studentsInGroup) gt 0}">
                                <div class="row text-center panel-title">
                                    <div class="col-sm-12">
                                        <h4>Отметить присутствовавших студентов</h4>
                                    </div>
                                </div>

                                <div class="row text-center">
                                    <div class="col-md-12">

                                        <form class="form"
                                              action="${pageContext.request.contextPath}/university/teacher/attendanceSendStudentsList"
                                              method="post">
                                            <c:set var="savedAttendance"
                                                   value="${requestScope.get('savedAttendance')}"/>
                                            <c:forEach items="${requestScope.get('studentsInGroup')}" var="student">
                                                <label for="${student.id}"
                                                       class="col-md-10 control-label text-left">${student.secondName} ${student.firstName} ${student.middleName}</label>
                                                <div class="col-md-2 text-right">
                                                    <input type="checkbox"
                                                           name="list"
                                                           value="${student.id}"
                                                           id="${student.id}"

                                                        ${savedAttendance.get(student.id)==true ? "checked" : ""}
                                                    >
                                                </div>
                                            </c:forEach>
                                            <input type="hidden" value="${requestScope.get("lessonId")}"
                                                   name="lessonId">
                                            <input type="hidden" value="${selectedGroup.id}" name="groupSelected">
                                            <br/>
                                            <div class="row text-center">
                                                <div class="col-md-12">
                                                    <button type="submit" class="btn btn-primary">OK</button>
                                                </div>
                                            </div>
                                        </form>


                                    </div>
                                </div>

                            </c:if>


                        </div>
                    </div>
                </div>
            </c:if>
        </div>
        <c:set var="message" value="${requestScope.get('message')}"/>
        <c:if test="${message!=null}">
            <div class="col-md-3 alert alert-info">
                    ${message}
            </div>
        </c:if>
    </jsp:body>
</t:wrapper>
