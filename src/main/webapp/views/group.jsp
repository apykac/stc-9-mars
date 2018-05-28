<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 23.05.2018
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:wrapper>
    <div class="row">
        <div class="col-md-5">
            <div class="content-box-header">
                <div class="panel-title ">Обновление группы</div>
            </div>
            <div class="content-box-large box-with-header">
                <form class="form-horizontal" id="add" action="${pageContext.request.contextPath}/views/updateGroup"
                      method="post">
                    <div class="form-group">
                        <label for="nameGr" class="col-sm-2 control-label">Введите</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="nameGr" value="${requestScope.get("groupName")}"
                                   name="name">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <input type="hidden" value="${requestScope.get("id")}" name="id">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-primary">OK</button>
                        </div>
                    </div>
                </form>
                <br/><br/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-5">
            <div class="content-box-header">
                <div class="panel-title ">Удаление группы</div>
            </div>
            <div class="content-box-large box-with-header">
                <form class="form-horizontal" id="delete" action="${pageContext.request.contextPath}/views/deleteGroup"
                      method="post">
                    <div class="form-group">
                        <label for="id" class="col-sm-2 control-label">Введите</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" value="${requestScope.get("id")}" id="id" name="id"
                                   readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-primary">удалить эту группу</button>
                        </div>
                    </div>
                </form>
                <br/><br/>
            </div>
        </div>
    </div>
</t:wrapper>