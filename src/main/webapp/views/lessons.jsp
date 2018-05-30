<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<t:wrapper>
    <div class="row">
        <div class="col-md-12 panel-warning">
            <div class="content-box-header">
                <div class="panel-title ">Список уроков</div>
            </div>
            <div class="content-box-large box-with-header">
                <table class="table">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Предмет</th>
                        <th>Дата</th>
                        <th>Название</th>
                        <th>Действие</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.get('lessons')}" var="lessons">
                        <tr>
                            <td>${lessons.id}</td>
                            <td>${lessons.subject_id}</td>
                            <td>${lessons.date}</td>
                            <td>${lessons.name}</td>
                            <td><span class="m_editLessons"
                                      idless="${lessons.id}"
                                      subjidless="${lessons.subject_id}"
                                      dateless="${lessons.date}"
                                      nameless="${lessons.name}"
                                      data-toggle="modal"
                                      data-target="#myModal">Редактировать</span>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <br/><br/>
            </div>
        </div>
    </div>
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">Редактирование записи</h4>
                </div>
                <div class="modal-body">
                        <%--                    <div class="panel panel-default">
                                                <div class="panel-heading">Добавить запись</div>
                                                <div class="panel-body">
                                                    <form:form class="form-horizontal" id="add" commandName="lesson"
                                                          action="${pageContext.request.contextPath}/views/lessons"
                                                          method="post">
                                                        <div class="form-group">
                                                            <label for="nameLesson" class="col-sm-2 control-label">Введите</label>
                                                            <div class="col-sm-10">
                                                                <input type="text" class="form-control" id="nameLesson"
                                                                       placeholder="Название урока"
                                                                       name="name">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <div class="col-sm-offset-2 col-sm-10">
                                                                <button type="submit" class="btn btn-primary">OK</button>
                                                            </div>
                                                        </div>
                                                    </form:form>
                                                </div>
                                            </div>
                        --%>
                    <div class="panel panel-default">
                        <div class="panel-heading">Удалить запись</div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="delmodal"
                                  action="${pageContext.request.contextPath}/views/lessons/delete"
                                  method="post">
                                <div class="form-group">
                                    <label for="l_name" class="col-sm-2 control-label">Имя</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="l_name" value=""
                                               disabled>
                                        <input type="hidden" class="form-control" id="l_id" value=""
                                               name="idLesson" readonly>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-primary">OK</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Отмена</button>
                </div>
            </div>
        </div>
    </div>
</t:wrapper>