<%--
  Created by IntelliJ IDEA.
  User: Alex_Frankiv
  Date: 24.11.2016
  Time: 23:18
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp" %>

<div class="container text-center">
    <br/><br/>
    <p>
    <h2><spring:message code="reports.label.select_projects"/></h2></p>
    <br/>
    <img src="<spring:url value="/presentation/resources/imgs/reports.jpg"/>">
    <br/><br/>
    <form method="get" action="/reports/get.xls">
        <div class="row">
            <select class="col-sm-offset-3 col-sm-6" name="projectIds" multiple name="projects" required>
                <c:forEach items="${projects}" var="proj">
                    <option value="${proj.id}">${proj.name}</option>
                </c:forEach>
            </select>
        </div>
        <br/><br/>
        <button class="btn btn-primary btn-lg" type="submit"><spring:message code="reports.btn.generate"/></button>
    </form>
</div>

<spring:message code="reports.select.placeholder" var="placeholder"/>
<script type="text/javascript">
    $('select').select2({
        placeholder: '${placeholder}',
        width: 'resolve'
    });
</script>
<%@include file="footer.jsp" %>