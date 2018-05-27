<%@ tag description="Wrapper Tag" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/pageHeader.jsp"/>
<div class="page-content">
    <div class="row">
        <div class="col-md-2">
            <% if ((Integer) request.getSession().getAttribute("role") == 0) {%>
            <c:import url="/pageSidebar.jsp"/>
            <%}%>
        </div>

        <div class="col-md-10">
            <jsp:doBody/>
        </div>
    </div>
</div>
<c:import url="/pageFooter.jsp"/>