<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:wrapper>
    <div class="row">
    <div class="col-md-12 panel-warning">
        <div class="content-box-header">
            <div class="panel-title "></div>
        </div>
        <div class="content-box-large box-with-header">
            <form class="form-horizontal" action="${pageContext.request.contextPath}/university/profile/feedback"
                  method="post">
                <fieldset>
                    <legend>Написать сообщение</legend>
                    <c:choose>
                    <c:when test="${sessionScope.get('entered_role') == 'ROLE_STUDENT'}">
                    <div class="form-group">
                        </c:when>
                        <c:otherwise>
                        <div class="form-group hidden">
                            </c:otherwise>
                            </c:choose>
                            <label class="col-sm-2 control-label">Группа получателей</label>
                            <div class="col-sm-10">
                                <select class="form-control" name="toUserGroup">
                                    <option disabled>Кому написать?</option>
                                    <option value="ROLE_ADMIN" selected>Администрации сайта</option>
                                    <option value="ROLE_TEACHER">Преподавательскому составу</option>
                                </select>
                            </div>
                        </div>
                            <div class="form-group">
                                <label for="theme" class="col-sm-2 control-label">Тема сообщения</label>
                                <div class="col-sm-10"><input type="text" class="form-control" id="theme" name="theme">
                                </div>
                            </div>
                        <div class="form-group">
                            <label for="message" class="col-sm-2 control-label">Текст сообщения</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" id="message" name="text" rows="3"></textarea>
                            </div>
                        </div>
                </fieldset>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary">Сохранить</button>
                    </div>
                </div>
                <c:if test="${(errors != null) && (errors.size() != 0)}">
                    <div class="alert alert-danger"><c:forEach items="${errors}" var="error">${error}
                        <br/></c:forEach></div>
                </c:if>
                <c:if test="${(success_list != null) && (success_list.size() != 0)}">
                    <div class="alert alert-success"><c:forEach items="${success_list}" var="success">${success}
                        <br/></c:forEach></div>
                </c:if>
            </form>
        </div>
    </div>
</t:wrapper>
