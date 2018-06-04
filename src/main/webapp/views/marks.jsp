<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:wrapper>
    <div class="row">
        <div class="col-md-12 panel-warning">
            <div class="content-box-header">
                <div class="panel-title ">Список студентов, сдавших домашнее задание по
                    уроку ${requestScope.get("lessonName")}</div>
            </div>
            <div class="content-box-large box-with-header">
                <table class="table">
                    <thead>
                    <tr>
                        <th>ФИО Студента</th>
                        <th>Оценка</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.get('marks')}" var="pair">
                        <tr>
                            <td>${pair.key}</td>
                            <td>${pair.value.value}</td>
                            <td><a href="${pageContext.request.contextPath}/views/editMark/${pair.value.id}">Просмотр
                                ДЗ/Редактирование оценки</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <br/><br/>
            </div>
        </div>
    </div>
</t:wrapper>
