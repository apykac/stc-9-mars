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

                <form class="form-horizontal" action="${pageContext.request.contextPath}/views/attendanceSelectGroup"
                      method="post">
                    <div class="form-group">
                        <label for="selectGroup" class="col-sm-3 control-label">Выбрать группу</label>
                        <div class="col-sm-9">
                            <select class="form-control" id="selectGroup" name="selectGroup">

                                <c:set var="selectedGroup" value="${requestScope.get('groupSelected')}"/>

                                <c:if test="${selectedGroup!=null}">
                                    <option value="${selectedGroup.name}" disabled>${selectedGroup.name}</option>
                                </c:if>

                                <c:forEach items="${requestScope.get('groups')}" var="group">
                                    <option value="${group.id}">${group.name}</option>
                                </c:forEach>

                            </select>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <button type="submit" class="btn btn-primary">OK</button>
                            </div>
                        </div>
                    </div>
                </form>

                <form class="form-horisontal" action="${pageContext.request.contextPath}/views/sendStudentsList"
                      method="post">
                    <c:forEach items="${requestScope.get('studentsInGroup')}" var="student">
                        <div class="form-group">
                            <div class="col-sm-3"></div>
                            <label for="${student.id}" class="col-sm-7 control-label">${student.firstName}</label>
                            <div class="col-sm-2">
                                <input class="form-check-input" type="checkbox" value="" id="${student.id}">
                            </div>
                        </div>
                    </c:forEach>
                    <div class="row">
                        <div class="col-sm-12">
                            <button type="submit" class="btn btn-primary">OK</button>
                        </div>
                    </div>
                </form>
                <br/><br/>
            </div>
        </div>
    </div>
</t:wrapper>
