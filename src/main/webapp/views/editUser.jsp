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
                <form class="form-horizontal" action="${pageContext.request.contextPath}/views/edit-user" method="post">
                    <fieldset>
                        <legend>Основные данные</legend>
                        <div class="form-group">
                            <label for="userId" class="col-sm-2 control-label">Id</label>
                            <div class="col-sm-10"><input type="text" class="form-control" id="userId"
                                                          placeholder="User ID"
                                                          name="userId"
                                                          value="${user.getId()}" readonly></div>
                        </div>
                        <div class="form-group">
                            <label for="editUsername" class="col-sm-2 control-label">Логин</label>
                            <div class="col-sm-10"><input type="text" class="form-control" id="editUsername"
                                                          placeholder="Username" name="editUsername"
                                                          value="${login.getUserName()}"></div>
                        </div>
                        <div class="form-group">
                            <label for="editFirstName" class="col-sm-2 control-label">Имя</label>
                            <div class="col-sm-10"><input type="text" class="form-control" id="editFirstName"
                                                          placeholder="First name"
                                                          name="editFirstName" value="${user.getFirstName()}"></div>
                        </div>
                        <div class="form-group">
                            <label for="editLastName" class="col-sm-2 control-label">Фамилия</label>
                            <div class="col-sm-10"><input type="text" class="form-control" id="editLastName"
                                                          placeholder="Last name"
                                                          name="editLastName" value="${user.getSecondName()}"></div>
                        </div>
                        <div class="form-group">
                            <label for="editMiddleName" class="col-sm-2 control-label">Отчество</label>
                            <div class="col-sm-10"><input type="text" class="form-control" id="editMiddleName"
                                                          placeholder="Middle name"
                                                          name="editMiddleName" value="${user.getMiddleName()}"></div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"></label>
                            <div class="col-sm-10">
                                <select class="form-control" name="editRole">
                                    <option disabled>Необходимо выбрать роль пользователя</option>
                                    <option ${login.getPermissionGroup() == 0 ? "selected" : ""} value="0">Admin
                                    </option>
                                    <option ${login.getPermissionGroup() == 1 ? "selected" : ""} value="1">Teacher
                                    </option>
                                    <option ${login.getPermissionGroup() == 2 ? "selected" : ""} value="2">Student
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
                                                          name="oldPassword" value=""></div>
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

                    <div>${userUpdateMessage}</div>
                    <div>${loginUpdateMessage}</div>
                        ${passwordUpdateMessage == null ? "" : "<div class='alert alert-danger'>"+passwordUpdateMessage+"</div>"}

                </form>
                <br/><br/>
            </div>
        </div>
    </div>
</t:wrapper>