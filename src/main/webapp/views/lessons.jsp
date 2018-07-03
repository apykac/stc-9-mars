<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<t:wrapper>
    <jsp:attribute name="contentHeader">
        <h1>Уроки
            <small>для редактирования урока выберите его в списке</small>
        </h1>
    </jsp:attribute>
    <jsp:attribute name="footer">
        <script src="${pageContext.request.contextPath}/assets/js/setid.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-md-8">
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
                                        <c:forEach items="${lessons}" var="lesson">
                                            <tr class="m_editLessons"
                                                idless="${lesson.id}" subjidless="${lesson.subject.id}"
                                                snameless="${lesson.subject.name}"
                                                dateless="${lesson.date}" nameless="${lesson.name}"
                                                style="cursor:pointer">
                                                <td>${lesson.subject.name}</td>
                                                <td>${lesson.date}</td>
                                                <td>${lesson.name}</td>
                                                <td>
                                                    <a href=${pageContext.request.contextPath}/university/teacher/attendance?lessonId=${lesson.id}>
                                                        Посещаемость
                                                    </a>
                                                </td>
                                                <td>
                                                    <a href=${pageContext.request.contextPath}/university/teacher/marks?lessonId=${lesson.id}>
                                                        Проверка ДЗ
                                                    </a>
                                                </td>
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

                    <%--<div class="row">
                        <div class="box box-primary" id="edit_less" style="display:none;">
                            <div class="box-header with-border">
                                <h3 class="box-title">Редактирование урока</h3>

                                <div class="box-tools pull-right">
                                    <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                            class="fa fa-minus"></i>
                                    </button>
                                </div>
                                <div class="box-body">
                                    <form id="edit" action="${pageContext.request.contextPath}/university/teacher/lessons"
                                          method="post">
                                        <div class="form-group">
                                            <label for="edit_sname">Предмет</label>
                                            <select id="edit_sname" class="form-control" name="edit_subject_id">
                                                <c:forEach items="${subjects}" var="subject">
                                                    <option value="${subject.id}">${subject.name}</option>
                                                </c:forEach>
                                            </select>
                                            <label for="edit_date">Дата урока</label>
                                            <div class="input-group">
                                                <div class="input-group-addon">
                                                    <i class="fa fa-calendar"></i>
                                                </div>
                                                <input type="date" class="form-control" id="edit_date"
                                                       data-inputmask="'alias': 'dd/mm/yyyy'" data-mask="" name="add_date">
                                            </div>
                                            <label for="add_name">Название урока</label>
                                            <div class="input-group input-group-lg">
                                                <input type="text" class="form-control" id="edit_name" name="add_name">
                                                <span class="input-group-btn">
                                            <button type="submit" class="btn btn-info btn-flat">Сохранить</button>
                                        </span>
                                            </div>
                                        </div>
                                    </form>
                                </div>

                            </div>
                        </div>
                    </div>
                    <div class="row">

                    </div>--%>


                <div class="box box-primary" id="win_less" <%--style="display:none;"--%>>
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
                                <label for="add_sname">Предмет</label>
                                <select id="add_sname" class="form-control" name="add_subject_id">
                                    <c:forEach items="${subjects}" var="subject">
                                        <option value="${subject.id}">${subject.name}</option>
                                    </c:forEach>
                                </select>
                                <label for="add_date">Дата урока</label>
                                <div class="input-group">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                    <input type="date" class="form-control" id="add_date"
                                           data-inputmask="'alias': 'dd/mm/yyyy'" data-mask="" name="add_date">
                                </div>
                                <label for="add_name">Название урока</label>
                                <div class="input-group input-group-lg">
                                    <input type="text" class="form-control" id="add_name" name="add_name">
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
                                    <input type="hidden" class="form-control" id="l_id" value="" name="idLesson"
                                           readonly>
                                    <input type="text" class="form-control" id="l_name" value="" disabled>
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