<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<t:wrapper>
    <div class="row">
        <div class="col-md-12 panel-warning">
            <div class="content-box-header">
                <div class="panel-title ">Все оценки</div>
            </div>
            <div class="content-box-large box-with-header">
                <table class="table">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Оценка</th>
                        <th>Имя</th>
                        <th>Фамилия</th>
                        <th>Группа</th>
                        <th>Урок</th>
                        <th>Дата</th>
                        <th>Предмет</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.get('progress')}" var="progress">
                        <tr>
                            <td>${progress.id}</td>
                            <td>${progress.value}</td>
                            <td>${progress.firstName}</td>
                            <td>${progress.secondName}</td>
                            <td>${progress.groupName}</td>
                            <td>${progress.lessonsName}</td>
                            <td>${progress.date}</td>
                            <td>${progress.subjectName}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <br/><br/>
            </div>
        </div>
    </div>
</t:wrapper>