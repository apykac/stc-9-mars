<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<t:wrapper>
    <div class="row text-center">
        <div class="col-md-12 panel-warning">
            <div class="content-box-header">
                <div class="panel-title ">Способность говорить — не признак интеллекта</div>
            </div>
            <div class="content-box-large box-with-header">
                <div class="row">
                    <div class="col-xs-12">
                        <div id="example_length" class="dataTables_length">
                            <h1>Мой йуный падаван выбирай действие</h1>
                        </div>
                        <c:if test="${message == 'deleted'}">
                            <div class="alert alert-success">Сообщение успешно удалено</div>
                            <a href="${pageContext.request.contextPath}/university/messages" class="btn-link">продолжить
                                работу с сообщениями</a>
                        </c:if>
                    </div>
                </div>
                <br/><br/>
            </div>
        </div>
    </div>
</t:wrapper>
