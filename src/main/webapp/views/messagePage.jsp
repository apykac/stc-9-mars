<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<t:wrapper>
    <jsp:attribute name="contentHeader">
        <h1>Сообщение</h1>
    </jsp:attribute>
    <jsp:attribute name="footer">
        <script src="${pageContext.request.contextPath}/assets/js/setid.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-md-3">
                <div class="box box-info">
                    <div class="box-header with-border">
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
                                        <tbody>
                                        <tr class="m_editSubject" style="cursor:pointer">
                                            <td><b>От кого:</b>${message.uname}</td>
                                        </tr>
                                        <tr class="m_editSubject" style="cursor:pointer">
                                            <td><b>Тема:</b>${message.theme}</td>
                                        </tr>
                                        <tr class="m_editSubject" style="cursor:pointer">
                                            <td>${message.text}</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <a href="${pageContext.request.contextPath}/university/messages"
                                       class="btn btn-primary">Вернуться к списку
                                        сообщений</a>
                                </div>
                            </div>
                            <br/><br/>
                            <c:if test="${error != null}">
                                <div class="alert alert-danger">${error}</div>
                            </c:if>
                            <c:if test="${success != null}">
                                <div class="alert alert-success">${success}</div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="box box-primary" id="win_subj" style="display:none;">
                    <div class="box-header with-border">
                        <h3 class="box-title">Действия</h3>

                        <div class="box-tools pull-right">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                    class="fa fa-minus"></i>
                            </button>
                        </div>
                    </div>
                    <div class="box-body">
                        <form class="form-horizontal"
                              action="${pageContext.request.contextPath}/university/messages/${message.id}/reply"
                              method="post">
                            <div class="form-group hidden">
                                <label for="to_user_id" class="col-sm-2 control-label">Тема</label>
                                <div class="col-sm-10"><input type="text" class="form-control" id="to_user_id"
                                                              placeholder="to_user_id"
                                                              name="toUserId" value="${message.fromUser.id}"></div>
                            </div>
                            <div class="form-group hidden">
                                <label for="to_user_id" class="col-sm-2 control-label">Тема</label>
                                <div class="col-sm-10"><input type="text" class="form-control" id="from_user_id"
                                                              placeholder="from_user_id"
                                                              name="fromUserId" value="${fromUser}"></div>
                            </div>
                            <div class="form-group">
                                <label for="editTheme" class="col-sm-2 control-label">Тема</label>
                                <div class="col-sm-10"><input type="text" class="form-control" id="editTheme"
                                                              placeholder="Theme"
                                                              name="theme" value="RE: ${message.theme}"></div>
                            </div>
                            <div class="form-group">
                                <label for="editText" class="col-sm-2 control-label">Текст</label>
                                <div class="col-sm-10"><textarea class="form-control" id="editText"
                                                                 placeholder="Text" rows="5"
                                                                 name="text">RE: ${message.text}</textarea></div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="submit" class="btn btn-primary">Ответить</button>
                                </div>
                            </div>
                        </form>
                        <form class="form-horizontal"
                              action="${pageContext.request.contextPath}/university/messages/${message.id}/delete"
                              method="post">
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="submit" class="btn btn-primary">Удалить сообщение</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:wrapper>