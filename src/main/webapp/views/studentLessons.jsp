<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 01.06.2018
  Time: 2:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<t:wrapper>
    <div class="row">
        <div class="col-md-12 panel-warning">
            <div class="content-box-header">
                <div class="panel-title ">Список уроков</div>
            </div>
            <div class="content-box-large box-with-header">
                <table class="table">
                    <thead>
                    <tr>
                        <th>Предмет</th>
                        <th>Дата</th>
                        <th>Название</th>
                        <th>Действие</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.get('lessons')}" var="lessons">
                        <tr>
                            <td>${requestScope.get('subject')}</td>
                            <td>${lessons.date}</td>
                            <td>${lessons.name}</td>
                            <td>
                                <a href=${pageContext.request.contextPath}/university/student/homework/lessonId/${lessons.id}>Добавить
                                    ДЗ</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="col-sm-offset-2 col-sm-10">
                    <form class="form-horizontal" id="dashBoard"
                          action="${pageContext.request.contextPath}/university/profile">
                        <button type="submit" class="btn btn-primary" >На главную</button>
                    </form>
                </div>
                <br/><br/>
            </div>
        </div>
    </div>
</t:wrapper>