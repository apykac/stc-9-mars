<%@ tag description="Wrapper Tag" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="contentHeader" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<html>
<c:import url="/pageHeader.jsp"/>
<c:import url="/pageSidebar.jsp"/>
<div class="content-wrapper">
    <section class="content-header">
        <jsp:invoke fragment="contentHeader"/>
    </section>
    <section class="content container-fluid">
        <jsp:doBody/>
    </section>
</div>
<c:import url="/pageFooter.jsp"/>
<jsp:invoke fragment="footer"/>
</body>
</html>