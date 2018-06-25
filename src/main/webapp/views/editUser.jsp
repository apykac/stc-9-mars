<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:wrapper>
    <div class="row">
        <div class="col-md-12 panel-warning">
            <div class="content-box-header">
                <div class="panel-title ">Редактирование пользователя</div>
            </div>
            <a class="content-box-large box-with-header">
                <form class="form-horizontal" action="${pageContext.request.contextPath}/temp/edit_user" method="post">
                    <fieldset>
                        <legend>Основные данные</legend>
                        <div class="form-group hidden">
                            <div class="col-sm-10"><input type="text" class="form-control"
                                                          placeholder="is Owner"
                                                          name="isOwner"
                                                          value="${isOwner}"></div>
                        </div>
                        <div class="form-group hidden">
                            <div class="col-sm-10"><input type="number" class="form-control"
                                                          placeholder="User ID"
                                                          name="id"
                                                          value="${user.id}"></div>
                        </div>
                        <c:if test="${(sessionScope.get('entered_role') == 'ROLE_STUDENT') && (user.group != null)}">
                            <div class="form-group">
                                <label for="group" class="col-sm-2 control-label">Группа</label>
                                <div class="col-sm-10"><input type="text" class="form-control" id="group"
                                                              placeholder="Group" name="group"
                                                              value="${user.group.name}" readonly></div>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label for="editLogin" class="col-sm-2 control-label">Логин</label>
                            <div class="col-sm-10"><input type="text" class="form-control" id="editLogin"
                                                          placeholder="Login" name="login"
                                                          value="${user.login}"></div>
                        </div>
                        <div class="form-group">
                            <label for="editFirstName" class="col-sm-2 control-label">Имя</label>
                            <div class="col-sm-10"><input type="text" class="form-control" id="editFirstName"
                                                          placeholder="First name"
                                                          name="firstName" value="${user.firstName}"></div>
                        </div>
                        <div class="form-group">
                            <label for="editLastName" class="col-sm-2 control-label">Фамилия</label>
                            <div class="col-sm-10"><input type="text" class="form-control" id="editLastName"
                                                          placeholder="Last name"
                                                          name="secondName" value="${user.secondName}"></div>
                        </div>
                        <div class="form-group">
                            <label for="editMiddleName" class="col-sm-2 control-label">Отчество</label>
                            <div class="col-sm-10"><input type="text" class="form-control" id="editMiddleName"
                                                          placeholder="Middle name"
                                                          name="middleName" value="${user.middleName}"></div>
                        </div>
                        <c:choose>
                        <c:when test="${user.id == sessionScope.get('entered_user_id') || sessionScope.get('entered_role') != 'ROLE_ADMIN'}">
                        <div class="form-group hidden">
                            </c:when>
                            <c:otherwise>
                            <div class="form-group">
                                </c:otherwise>
                                </c:choose>
                            <label class="col-sm-2 control-label">Права доступа</label>
                            <div class="col-sm-10">
                                <select class="form-control" name="permissionGroup">
                                    <option disabled>Необходимо выбрать роль пользователя</option>
                                    <option ${user.permissionGroup == 'ROLE_ADMIN' ? "selected" : ""} value="ROLE_ADMIN">Admin
                                    </option>
                                    <option ${user.permissionGroup == 'ROLE_TEACHER' ? "selected" : ""} value="ROLE_TEACHER">Teacher
                                    </option>
                                    <option ${user.permissionGroup == 'ROLE_STUDENT' ? "selected" : ""} value="ROLE_STUDENT">Student
                                    </option>
                                </select>
                            </div>
                        </div>
                                <c:choose>
                                <c:when test="${user.id == sessionScope.get('entered_user_id') || sessionScope.get('entered_role') != 'ROLE_ADMIN'}">
                                <div class="form-group hidden">
                                    </c:when>
                                <c:otherwise>
                                <div class="form-group">
                                    </c:otherwise>
                                    </c:choose>
                            <label class="col-sm-2 control-label">Включение/Отключение аккаунта</label>
                            <div class="col-sm-10">
                                <select class="form-control" name="enabled">
                                    <option disabled>Выбирите бедт ли активен аккаунт</option>
                                    <option ${user.enabled == 1 ? "selected" : ""} value="1">Активен
                                    </option>
                                    <option ${user.enabled == 0 ? "selected" : ""} value="0">Неактивен
                                    </option>
                                </select>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <legend>Смена пароля</legend>
                        <div class="form-group">
                            <label for="oldPassword" class="col-sm-2 control-label">Старый пароль</label>
                            <div class="col-sm-10"><input class="form-control" type="password" id="oldPassword"
                                                          plaseholder="Old password"
                                                          name="hashPassword" value=""></div>
                        </div>
                        <div class="form-group">
                            <label for="newPassword" class="col-sm-2 control-label">Новый пароль</label>
                            <div class="col-sm-10"><input class="form-control" type="password" id="newPassword"
                                                          plaseholder="New password"
                                                          name="newPassword" value=""></div>
                        </div>
                        <div class="form-group">
                            <label for="repeatNewPassword" class="col-sm-2 control-label">Повтор</label>
                            <div class="col-sm-10"><input class="form-control" type="password" id="repeatNewPassword"
                                                          plaseholder="Repeat new password"
                                                          name="repeatNewPassword" value=""></div>
                        </div>
                    </fieldset>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-primary">Сохранить</button>
                        </div>
                    </div>
                    <c:if test="${errors != null}">
                        <div class="alert alert-danger"><c:forEach items="${errors}" var="error">${error}
                            <br/></c:forEach></div>
                    </c:if>
                    <c:if test="${success_list != null}">
                        <div class="alert alert-success"><c:forEach items="${success_list}" var="success">${success}
                            <br/></c:forEach></div>
                    </c:if>
                </form>
                <br/><br/>
                <c:if test="${isOwner == true}"><a type="button" class="btn btn-danger form-horizontal"
                                                   href="${pageContext.request.contextPath}/university/profile/delete">Удалить
                аккаунт</a></c:if>
                <c:if test="${user.enabled == 0}">
                <form class="form-horizontal"
                      action="${pageContext.request.contextPath}/admin/edit_user/${user.id}/delete" method="post">
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-primary">Удалить аккаунт ${user.login}</button>
                        </div>
                    </div>
                </form>
                </c:if>
                <br/><br/>
                <c:if test="${(sessionScope.get('entered_role') == 'ROLE_STUDENT') && (user.group.subjects != null)}">
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
                            <c:forEach items="${user.group.subjects}" var="subject">
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
                </c:if>
            </div>
        </div>
</t:wrapper>
