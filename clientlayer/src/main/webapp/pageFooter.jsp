<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!-- Main Footer -->
<footer class="main-footer text-right">
    <!-- Default to the left -->
    <strong>Copyright &copy; 2018 <a href="#">Mars55</a>.</strong> All rights reserved.
    <sec:authorize access="hasAnyRole('ROLE_STUDENT', 'ROLE_TEACHER')">
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/university/profile/feedback">Обратная
            связь</a>
    </sec:authorize>
</footer>

</div>
<!-- ./wrapper -->
<script src="${pageContext.request.contextPath}/css/styles.css"></script>
<script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/adminlte.min.js"></script>


