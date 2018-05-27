<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:wrapper>
    <div class="row">
        <div class="col-md-12 panel-warning">
            <div class="content-box-header">
                <div class="panel-title ">Список предметов</div>
            </div>
            <div class="content-box-large box-with-header">
                <table class="table">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Предмет</th>
                        <th>Действие</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.get('subjects')}" var="subjects">
                        <tr>
                            <td>${subjects.id}</td>
                            <td>${subjects.name}</td>
                            <td><a href="${pageContext.request.contextPath}/views"
                                   name="${subjects.name}">Информация</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <br/><br/>
            </div>
        </div>
    </div>
</t:wrapper>