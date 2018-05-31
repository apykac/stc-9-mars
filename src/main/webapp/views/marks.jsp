<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:wrapper>
    <div class="row">
        <div class="col-md-12 panel-warning">
            <div class="content-box-header">
                <div class="panel-title ">Оценки за урок ...</div>
            </div>
            <div class="content-box-large box-with-header">
                <div class="row">
                    <div class="col-md-9">
                        ФИО Студента
                    </div>
                    <div class="col-md-1">
                        Оценка
                    </div>
                </div>
                <form id="editMarks" action="${pageContext.request.contextPath}/views/updateMarks" method="post">
                    <c:forEach items="${requestScope.get('marks')}" var="pair">
                        <div class="row">
                            <div class="col-md-9">
                                    ${pair.key}
                            </div>
                            <div class="col-md-3">
                                <input type="number" name="{pair.value.id}" value="${pair.value.value}">
                            </div>
                        </div>
                    </c:forEach>
                    <div class="row">
                        <input type="submit" value="Сохранить">
                    </div>
                </form>


                <table class="table">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>ФИО Студента</th>
                        <th>Оценка</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.get('marks')}" var="pair">
                        <tr>
                            <td>${pair.key}</td>
                            <td>${pair.value.value}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <br/><br/>
            </div>
        </div>
    </div>
</t:wrapper>
