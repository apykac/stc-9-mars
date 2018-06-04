<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<t:wrapper>
    <jsp:attribute name="footer">
        <script src="${pageContext.request.contextPath}/assets/js/setid.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-md-6">
                <div class="box box-info">
                    <div class="box-header with-border">
                        <h3 class="box-title">Список уроков</h3>

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
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th>Предмет</th>
                                            <th>Дата</th>
                                            <th>Название</th>
                                            <th>Действие</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${requestScope.get('lessons')}" var="lessons">
                                            <tr class="m_editLessons"
                                                idless="${lessons.id}" subjidless="${lessons.subject_id}"
                                                snameless="${lessons.sname}"
                                                dateless="${lessons.date}" nameless="${lessons.name}"
                                                style="cursor:pointer">
                                                <td>${lessons.sname}</td>
                                                <td>${lessons.date}</td>
                                                <td>${lessons.name}</td>
                                                </td>
                                                <td>
                                                    <a href=${pageContext.request.contextPath}/university/teacher/attendance?lessonId=${lessons.id}>Посещаемость</a>
                                                </td>
                                                <td>
                                                    <a href=${pageContext.request.contextPath}/university/teacher/marks?lessonId=${lessons.id}>Проверка
                                                        ДЗ</a></td>
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
                <div class="box box-primary" id="win_less" style="display:none;">
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
                        <form id="add" action="${pageContext.request.contextPath}/university/teacher/lessons"
                              method="post">
                            <div class="form-group">
                                    <%--<label for="l_sname">Предмет</label>
                                    <select id="l_sname" name="subject" class="form-control">
                                        <c:forEach items="${requestScope.get('subjects')}" var="subj">
                                            <option value="${subj.id}">${subj.sname}</option>
                                        </c:forEach>
                                    </select>--%>
                                <label for="l_date">Дата урока</label>
                                <input type="text" class="form-control" id="l_date" value="">
                                <label for="l_name2">Название урока</label>
                                <div class="input-group input-group-lg">
                                    <input type="text" class="form-control" id="l_name2" name="name">
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
                              action="${pageContext.request.contextPath}/university/teacher/lessons/delete"
                              method="post">
                            <div class="form-group">
                                <label for="l_name">Название урока</label>
                                <div class="input-group input-group-lg">
                                    <input type="text" class="form-control" id="l_name" value="" disabled>
                                    <input type="hidden" class="form-control" id="l_id" value="" name="idSubj" readonly>
                                    <span class="input-group-btn">
                                        <button type="submit" class="btn btn-info btn-flat disabled">OK</button>
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