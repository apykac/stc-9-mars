<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:wrapper>
    <jsp:attribute name="contentHeader">
        <h1>Домашние задания
            <small>проверка</small>
        </h1>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-md-12">
                <div class="box box-info">
                    <div class="box-header with-border">
                        <h3 class="box-title">Список студентов, сдавших домашнее задание по
                            уроку <b>${requestScope.get("lessonName")}</b></h3>

                        <div class="box-tools pull-right">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                    class="fa fa-minus"></i>
                            </button>
                        </div>
                    </div>
                    <div class="box-body">
                        <div class="dataTables_wrapper form-inline dt-bootstrap">
                            <div class="row">
                                <div class="col-sm-12">
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
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/university/teacher/editMark/${pair.value.id}">Просмотр
                                                        ДЗ/Редактирование оценки</a></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <!-- /.table-responsive -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:wrapper>
