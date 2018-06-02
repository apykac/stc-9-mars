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
                <form class="form-horizontal" action="${pageContext.request.contextPath}/admin/edit-user" method="post">
                    <fieldset>
                        <legend>Основные данные</legend>
                        <div class="form-group">
                            <label for="userId" class="col-sm-2 control-label">Id</label>
                            <div class="col-sm-10"><input type="text" class="form-control" id="userId"
                                                          placeholder="User ID"
                                                          name="id"
                                                          value="${user.id}" readonly></div>
                        </div>
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
                                                          name="first_name" value="${user.firstName}"></div>
                        </div>
                        <div class="form-group">
                            <label for="editLastName" class="col-sm-2 control-label">Фамилия</label>
                            <div class="col-sm-10"><input type="text" class="form-control" id="editLastName"
                                                          placeholder="Last name"
                                                          name="second_name" value="${user.secondName}"></div>
                        </div>
                        <div class="form-group">
                            <label for="editMiddleName" class="col-sm-2 control-label">Отчество</label>
                            <div class="col-sm-10"><input type="text" class="form-control" id="editMiddleName"
                                                          placeholder="Middle name"
                                                          name="middle_name" value="${user.middleName}"></div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Права доступа</label>
                            <div class="col-sm-10">
                                <select class="form-control" name="permission_group">
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
                        <div class="form-group">
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
                                                          name="hash_password" value=""></div>
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
                        <c:forEach items="${errors}" var="error"><div class="alert alert-success">${error}</div></c:forEach>
                    </c:if>

                </form>
                <br/><br/>
            </div>
        </div>
    </div>
</t:wrapper>
