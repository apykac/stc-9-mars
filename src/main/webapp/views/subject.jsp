<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<t:wrapper>
    <jsp:attribute name="footer">
        <script src="${pageContext.request.contextPath}/assets/js/setid.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-md-12 panel-warning">
                <div class="content-box-header">
                    <div class="panel-title ">Список предметов</div>
                </div>
                <div class="content-box-large box-with-header">
                    <c:if test="${requestScope.get('errorName') != null}">
                        <div class="row">
                            <div class="col-xs-12">
                                <div id="example_length" class="dataTables_length">
                                    <div class='alert alert-danger'>${requestScope.get("errorName")}</div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <table class="table">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Предмет</th>
                            <th>Действие</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.get('subjects')}" var="subjects">
                            <tr>
                                <td>${subjects.id}</td>
                                <td>${subjects.name}</td>
                                <td><span class="m_editSubject"
                                          idsubj="${subjects.id}"
                                          namesubj="${subjects.name}"
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
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title" id="myModalLabel">Редактирование записи</h4>
                    </div>
                    <div class="modal-body">
                        <div class="panel panel-default">
                            <div class="panel-heading">Добавить запись</div>
                            <div class="panel-body">
                                <form class="form-horizontal" id="add"
                                      action="${pageContext.request.contextPath}/university/teacher/subject"
                                      method="post">
                                    <div class="form-group">
                                        <label for="nameSubj" class="col-sm-2 control-label">Введите</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="nameSubj"
                                                   placeholder="Название предмета"
                                                   name="name">
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
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <div class="panel panel-default">
                                <div class="panel-heading">Удалить запись</div>
                                <div class="panel-body">
                                    <form class="form-horizontal" id="delmodal"
                                          action="${pageContext.request.contextPath}/university/teacher/subject/delete"
                                          method="post">
                                        <div class="form-group">
                                            <label for="m_name" class="col-sm-2 control-label">Имя</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="m_name" value=""
                                                       disabled>
                                                <input type="hidden" class="form-control" id="m_id" value=""
                                                       name="idSubj" readonly>
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
                        </sec:authorize>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Отмена</button>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:wrapper>