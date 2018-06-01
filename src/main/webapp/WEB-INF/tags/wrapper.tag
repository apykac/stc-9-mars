<%@ tag description="Wrapper Tag" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/pageHeader.jsp"/>
<c:import url="/pageSidebar.jsp"/>
<div class="content-wrapper">
    <section class="content-header">
        <h1>

            <small></small>
        </h1>
    </section>
    <section class="content container-fluid">
        <jsp:doBody/>
    </section>
</div>
<c:import url="/pageFooter.jsp"/>