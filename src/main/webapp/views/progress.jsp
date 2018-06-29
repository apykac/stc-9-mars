<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<t:wrapper>
    <jsp:attribute name="footer">
        <script src="${pageContext.request.contextPath}/assets/js/raphael.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/morris.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/morris.js"></script>
        <script>
            $(function () {
                "use strict";

                // LINE CHART
                var line = new Morris.Line({
                    element: 'line-chart',
                    resize: true,
                    data: [
                        <c:forEach items="${progress}" var="progress">
                        {y: '${progress.lesson.date}', item1: ${progress.value}},
                        </c:forEach>
                    ],
                    xkey: 'y',
                    ykeys: ['item1'],
                    labels: ['Item 1'],
                    lineColors: ['#3c8dbc'],
                    hideHover: 'auto'
                });

                //DONUT CHART
                var donut = new Morris.Donut({
                    element: 'myfirstchart',
                    resize: true,
                    colors: ["#3c8dbc", "#f56954", "#00a65a", "#ffa65a", "#0fff5a"],
                    data: [
                        {label: "Отлично", value: ${requestScope.get('amountMarks')[5]}},
                        {label: "Хорошо", value: ${requestScope.get('amountMarks')[4]}},
                        {label: "Посредственно", value: ${requestScope.get('amountMarks')[3]}},
                        {label: "Плохо", value: ${requestScope.get('amountMarks')[2]}},
                        {label: "Единица", value: ${requestScope.get('amountMarks')[1]}}
                    ],
                    hideHover: 'auto'
                });
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-lg-3 col-xs-6">
                <!-- small box -->
                <div class="small-box bg-aqua">
                    <div class="inner">
                        <h3><c:out value="${requestScope.get('amountMarks')[0]}"/></h3>

                        <p>Всего оценок</p>
                    </div>
                    <div class="icon">
                        <i class="ion ion-bag"></i>
                    </div>
                    <a href="#tableProgress" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
                </div>
            </div>
            <!-- ./col -->
            <div class="col-lg-3 col-xs-6">
                <!-- small box -->
                <div class="small-box bg-green">
                    <div class="inner">
                        <h3><c:out value="${requestScope.get('amountMarks')[5]}"/><sup style="font-size: 20px"></sup>
                        </h3>

                        <p>Пятерки</p>
                    </div>
                    <div class="icon">
                        <i class="ion ion-stats-bars"></i>
                    </div>
                    <a href="#tableProgress" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
                </div>
            </div>
            <!-- ./col -->
            <div class="col-lg-3 col-xs-6">
                <!-- small box -->
                <div class="small-box bg-yellow">
                    <div class="inner">
                        <h3><c:out value="${requestScope.get('missedLessons')}"/></h3>

                        <p>Пропуски</p>
                    </div>
                    <div class="icon">
                        <i class="ion ion-person-add"></i>
                    </div>
                    <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
                </div>
            </div>
            <!-- ./col -->
            <div class="col-lg-3 col-xs-6">
                <!-- small box -->
                <div class="small-box bg-red">
                    <div class="inner">
                        <h3>PDF</h3>

                        <p>Отчет</p>
                    </div>
                    <div class="icon">
                        <i class="ion ion-pie-graph"></i>
                    </div>
                    <a href="${pageContext.request.contextPath}/university/progress/pdf"
                       class="small-box-footer" target="_blank">Сформировать <i
                            class="fa fa-arrow-circle-right"></i></a>
                </div>
            </div>
            <!-- ./col -->
        </div>

        <div class="row">
            <div class="col-md-12 panel-warning">
                <div class="box box-info">
                    <div class="box-header with-border">
                        <h3 class="box-title">Все оценки</h3>

                        <div class="box-tools pull-right">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                    class="fa fa-minus"></i>
                            </button>
                            <button type="button" class="btn btn-box-tool" data-widget="remove"><i
                                    class="fa fa-times"></i>
                            </button>
                        </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <div id="example1_wrapper" class="dataTables_wrapper form-inline dt-bootstrap">
                            <div class="row">
                                <div class="col-sm-12">
                                    <div id="example_length" class="dataTables_length">
                                        <form action="${pageContext.request.contextPath}/university/progress/selmarks"
                                              method="post">
                                            <div class="form-group">
                                            <span class="input-group margin">
                                            <select name="marks" class="form-control">
                                                <option value="0-5">Все оценки</option>
                                                <option value="5-5">Отлично</option>
                                                <option value="4-4">Хорошо</option>
                                                <option value="3-3">Посредственно</option>
                                                <option value="1-2">Плохо</option>
                                                <option value="0-0">Без оценок</option>
                                            </select>
                                            <span class="input-group-btn">
                                                <button type="submit"
                                                        class="btn btn-info btn-flat"
                                                        name="form"
                                                        value="filterForm">Фильтр</button></span>
                                            </span>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <c:choose>
                                        <c:when test="${progress == null}">
                                            <div class='alert alert-danger'>Ошибка: no data</div>
                                        </c:when>
                                        <c:otherwise>
                                            <table class="table table-bordered table-striped dataTable"
                                                   id="tableProgress">
                                                <thead>
                                                <tr>
                                                    <th>Оценка</th>
                                                    <th>Имя</th>
                                                    <th>Урок</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach items="${progress}" var="progress">
                                                    <tr>
                                                        <td>${progress.value}</td>
                                                        <td>${progress.student.id}</td>
                                                        <td>${progress.lesson.id}</td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <!-- /.table-responsive -->
                        </div>
                    </div>
                    <!-- /.box-body -->
                </div>

            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="box box-danger">
                    <div class="box-header with-border">
                        <h3 class="box-title">Оценки</h3>

                        <div class="box-tools pull-right">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                    class="fa fa-minus"></i>
                            </button>
                            <button type="button" class="btn btn-box-tool" data-widget="remove"><i
                                    class="fa fa-times"></i>
                            </button>
                        </div>
                    </div>
                    <div class="box-body chart-responsive">
                        <div class="chart" id="myfirstchart" style="height: 300px; position: relative;"></div>
                    </div>
                    <!-- /.box-body -->
                </div>
                <!-- /.box -->
            </div>
            <div class="col-md-6">
                <div class="box box-info">
                    <div class="box-header with-border">
                        <h3 class="box-title">График</h3>

                        <div class="box-tools pull-right">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                    class="fa fa-minus"></i>
                            </button>
                            <button type="button" class="btn btn-box-tool" data-widget="remove"><i
                                    class="fa fa-times"></i></button>
                        </div>
                    </div>
                    <div class="box-body chart-responsive">
                        <div class="chart" id="line-chart" style="height: 300px;"></div>
                    </div>
                    <!-- /.box-body -->
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 panel-warning">
                <div class="box box-success">
                    <div class="box-header with-border">
                        <h3 class="box-title">Расписание</h3>

                        <div class="box-tools pull-right">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                    class="fa fa-minus"></i>
                            </button>
                            <button type="button" class="btn btn-box-tool" data-widget="remove"><i
                                    class="fa fa-times"></i>
                            </button>
                        </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <div class="dataTables_wrapper form-inline dt-bootstrap">
                            <div class="row">
                                <div class="col-sm-12">
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Предмет</th>
                                            <th>Дата</th>
                                            <th>Название</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${requestScope.get('lessons')}" var="lessons">
                                            <tr>
                                                <td>${lessons.id}</td>
                                                <td>${lessons.subject.id}</td>
                                                <td>${lessons.date}</td>
                                                <td>${lessons.name}</td>
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
        </div>
    </jsp:body>
</t:wrapper>