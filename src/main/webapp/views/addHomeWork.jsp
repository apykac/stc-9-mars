<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 01.06.2018
  Time: 3:07
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
                <div class="panel-title ">Введите ссылку на домашнее задание:</div>
            </div>
            <div class="content-box-large box-with-header">
                <form class="form-horizontal" id="add"
                      action="${pageContext.request.contextPath}/university/student/homework/add"
                      method="post">
                    <div class="form-group">
                        <label for="nameGr" class="col-sm-2 control-label">Введите</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="nameGr" placeholder="URL"
                                   name="url">
                            <input type="hidden" class="form-control" id="lessonId" placeholder="URL"
                                   name="lessonId" value="${requestScope.get('lessonId')}">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-primary">OK</button>
                        </div>
                    </div>
                </form>
                <br/>
                <h4>${requestScope.get('error')}</h4>
                <br/>
            </div>
        </div>
    </div>
</t:wrapper>