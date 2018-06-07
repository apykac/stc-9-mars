<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:wrapper>
    <div class="row">
        <div class="col-md-12">
            <div class="content-box-header">
                <div class="panel-title ">Редактирование оценки</div>
            </div>
            <div class="content-box-large box-with-header">
                <div class="row">
                    <div class="col-sm-12">
                        Студент: ${requestScope.get("studentName")}
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12">
                        Урок: ${requestScope.get("lessonName")}
                    </div>
                </div>

                <form class="form-horizontal" id="homeWorkContent">

                    <c:set var="homeWorkIsUrl" value="${requestScope.get('homeWorkIsUrl')}"/>

                    <div class="form-group">
                        <label for="comment" class="col-sm-3 control-label">Ссылка на ДЗ</label>
                        <div class="col-sm-9">

                            <c:if test="${homeWorkIsUrl==true}">
                                <a href="${requestScope.get("homeworkContent")}">${requestScope.get("homeworkContent")}</a>
                            </c:if>

                            <c:if test="${homeWorkIsUrl==false}">
                            <textarea class="form-control" id="homeWork" name="homeWork"
                                      rows="1">${requestScope.get("homeworkContent")}</textarea>
                            </c:if>

                        </div>
                    </div>

                </form>

                <form class="form-horizontal" id="add"
                      action="${pageContext.request.contextPath}/university/teacher/updateMark"
                      method="post">

                        <%--<div class="form-group">
                            <label for="value" class="col-sm-3 control-label">Ссылка на ДЗ</label>
                            <div class="col-sm-9"><input type="text" class="form-control" id="homeworkContent1"
                                                         placeholder="Homework content"
                                                         name="homeworkContent"
                                                         value="${requestScope.get("homeworkContent")}"
                                                         readonly></div>
                        </div>--%>

                    <div class="form-group">
                        <label for="value" class="col-sm-3 control-label">Оценка</label>
                        <div class="col-sm-9"><input type="text" class="form-control" id="value"
                                                     placeholder="Mark value"
                                                     name="value"
                                                     value="${requestScope.get("value")}"></div>
                    </div>
                    <div class="form-group">
                        <label for="comment" class="col-sm-3 control-label">Комментарий</label>
                        <div class="col-sm-9">
                            <textarea class="form-control" id="comment" name="comment"
                                      rows="3">${requestScope.get("comment")}</textarea>
                        </div>
                    </div>
                    <input type="hidden" value="${requestScope.get("id")}" name="id">
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-primary">Сохранить</button>
                        </div>
                    </div>

                </form>
                <br/><br/>
            </div>
        </div>
    </div>
</t:wrapper>