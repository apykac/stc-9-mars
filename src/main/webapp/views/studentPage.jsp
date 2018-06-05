<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 01.06.2018
  Time: 0:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:wrapper>
    <div class="row">
        <div class="col-md-12 panel-warning">
            <div class="content-box-header">
                <div class="panel-title ">Редактирование пользователя</div>
            </div>
            <div class="content-box-large box-with-header">
                <form class="form-horizontal" action="${pageContext.request.contextPath}/university/student/update"
                      method="post">
                    <fieldset>
                        <legend>Основные данные</legend>
                        <div class="form-group">
                            <div class="col-sm-10"><input type="hidden" class="form-control" id="userId"
                                                          placeholder="User ID"
                                                          name="userId"
                                                          value="${requestScope.get('student').id}" readonly></div>
                        </div>
                        <div class="form-group">
                            <label for="userId" class="col-sm-2 control-label">Group</label>
                            <div class="col-sm-10"><input type="text" class="form-control" id="groupName"
                                                          placeholder="Group"
                                                          name="group"
                                                          value="${requestScope.get('student').group.name}" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="editLogin" class="col-sm-2 control-label">Логин</label>
                            <div class="col-sm-10"><input type="text" class="form-control" id="editLogin"
                                                          placeholder="Login" name="editLogin"
                                                          value="${requestScope.get('student').login}" readonly></div>
                        </div>
                        <div class="form-group">
                            <label for="editFirstName" class="col-sm-2 control-label">Имя</label>
                            <div class="col-sm-10"><input type="text" class="form-control" id="editFirstName"
                                                          placeholder="First name"
                                                          name="editFirstName"
                                                          value="${requestScope.get('student').firstName}"></div>
                        </div>
                        <div class="form-group">
                            <label for="editLastName" class="col-sm-2 control-label">Фамилия</label>
                            <div class="col-sm-10"><input type="text" class="form-control" id="editLastName"
                                                          placeholder="Last name"
                                                          name="editLastName"
                                                          value="${requestScope.get('student').secondName}"></div>
                        </div>
                        <div class="form-group">
                            <label for="editMiddleName" class="col-sm-2 control-label">Отчество</label>
                            <div class="col-sm-10"><input type="text" class="form-control" id="editMiddleName"
                                                          placeholder="Middle name"
                                                          name="editMiddleName"
                                                          value="${requestScope.get('student').middleName}"></div>
                        </div>
                    </fieldset>

                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-primary">Сохранить</button>
                        </div>
                    </div>
                </form>
                <br/><br/>
            </div>

            <div class="col-md-5">
                <div class="content-box-header">
                    <div class="panel-title ">Предметы</div>
                </div>
                <div class="content-box-large box-with-header">
                    <table class="table">
                        <thead>
                        <tr>

                            <th>Предмет</th>
                            <th>Уроки</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.get('subject')}" var="subject">
                            <tr>
                                <td>${subject.name}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/university/student/subject/${subject.id}"
                                       name="${subject.name}">Информация</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</t:wrapper>
