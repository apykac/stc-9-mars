<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:wrapper>
    <div class="row">
        <div class="col-md-12 panel-warning">
            <div class="content-box-header">
                <div class="panel-title ">Список предметов</div>
            </div>
            <div class="content-box-large box-with-header">
                <div class="row">
                    <div class="col-xs-12">
                        <div id="example_length" class="dataTables_length">
                            <label>
                                <button type="button" class="btn btn-primary btn-xs" data-toggle="modal"
                                        data-target="#myModal">
                                    Добавить предмет
                                </button>
                            </label>
                        </div>
                    </div>
                </div>
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
                            <td>
                                <a href="${pageContext.request.contextPath}/views/subject/del?id=${subjects.id}">&times;</a>
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
                    <h4 class="modal-title" id="myModalLabel">Добавить запись</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" id="add" action="${pageContext.request.contextPath}/views/subject"
                          method="post">
                        <div class="form-group">
                            <label for="nameSubj" class="col-sm-2 control-label">Введите</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="nameSubj" placeholder="Название предмета"
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
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Отмена</button>
                </div>
            </div>
        </div>
    </div>
</t:wrapper>