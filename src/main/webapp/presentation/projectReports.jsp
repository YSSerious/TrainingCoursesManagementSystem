<%--
  Created by IntelliJ IDEA.
  User: Alex_Frankiv
  Date: 24.11.2016
  Time: 23:18
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>

<br/><br/>
<p><h3><spring:message code="reports.label.select_projects"/></h3></p>
<br/>
<div class="container-fluid text-center">
    <form method="get" action="/reports/get.xls">
    <select name="projectIds" multiple name="projects" style="min-width: 88%" required>
        <c:forEach items="${projects}" var="proj">
            <option value="${proj.id}">${proj.name}</option>
        </c:forEach>
    </select>
    <br/><br/>
    <button class="btn btn-primary btn-lg" type="submit"><spring:message code="reports.btn.generate"/> </button>
    </form>
</div>

<spring:message code="reports.select.placeholder" var="placeholder"/>
<script type="text/javascript">
    $('select').select2({
        placeholder: '${placeholder}',
        width: 'resolve'
    });
</script>
<%@include file="footer.jsp"%>