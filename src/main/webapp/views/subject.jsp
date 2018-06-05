<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<t:wrapper>
    <jsp:attribute name="contentHeader">
        <h1>Предметы
            <small>для редактирования предмета выберите его в списке</small>
        </h1>
    </jsp:attribute>
    <jsp:attribute name="footer">
        <script src="${pageContext.request.contextPath}/assets/js/setid.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-md-3">
                <div class="box box-info">
                    <div class="box-header with-border">
                        <h3 class="box-title">Список предметов</h3>

                        <div class="box-tools pull-right">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                    class="fa fa-minus"></i>
                            </button>
                        </div>
                    </div>
                    <div class="box-body">
                        <div class="dataTables_wrapper form-inline dt-bootstrap">
                            <div class="row">
                                <div class="col-sm-12">
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
                                            <th>Предмет</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${requestScope.get('subjects')}" var="subjects">
                                            <tr class="m_editSubject"
                                                idsubj="${subjects.id}"
                                                namesubj="${subjects.name}"
                                                style="cursor:pointer">

                                                <td>${subjects.name}</td>
                                                <td>Edit</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <!-- /.table-responsive -->
                        </div>
                    </div>
                    <!-- /.box-body -->
                </div>
            </div>
            <div class="col-md-4">
                <div class="box box-primary" id="win_subj" style="display:none;">
                    <div class="box-header with-border">
                        <h3 class="box-title">Настройки</h3>

                        <div class="box-tools pull-right">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                    class="fa fa-minus"></i>
                            </button>
                        </div>
                    </div>
                    <div class="box-body">
                        <h4>Добавить запись</h4>
                        <form id="add" action="${pageContext.request.contextPath}/university/teacher/subject"
                              method="post">
                            <div class="form-group">
                                <label for="nameSubj">Введите название</label>
                                <div class="input-group input-group-lg">
                                    <input type="text" class="form-control" id="nameSubj"
                                           placeholder="Название предмета" name="name">
                                    <span class="input-group-btn">
                                        <button type="submit" class="btn btn-info btn-flat">OK</button>
                                    </span>
                                </div>
                            </div>
                        </form>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <br/>
                        <h4>Удалить запись</h4>
                        <form id="delmodal"
                              action="${pageContext.request.contextPath}/university/teacher/subject/delete"
                              method="post">
                            <div class="form-group">
                                <label for="m_name">Название предмета</label>
                                <div class="input-group input-group-lg">
                                    <input type="text" class="form-control" id="m_name" value="" disabled>
                                    <input type="hidden" class="form-control" id="m_id" value="" name="idSubj" readonly>
                                    <span class="input-group-btn">
                                        <button type="submit" class="btn btn-info btn-flat">OK</button>
                                    </span>
                                </div>
                            </div>
                        </form>
                        </sec:authorize>
                        <!-- /.table-responsive -->
                    </div>
                    <!-- /.box-body -->
                </div>
            </div>
        </div>
    </jsp:body>
</t:wrapper>