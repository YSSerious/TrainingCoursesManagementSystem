<%--
  Created by IntelliJ IDEA.
  User: Alex_Frankiv
  Date: 24.11.2016
  Time: 23:18
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>

<br/><br/>
<h3>Select projects to form the report</h3>
<br/>
<div class="container-fluid text-center">
    <form method="get" action="/reports/get.xls">
    <select name="projectIds" multiple name="projects" style="min-width: 88%">
        <c:forEach items="${projects}" var="proj">
            <option value="${proj.id}">${proj.name}</option>
        </c:forEach>
    </select>
    <br/><br/>
    <button class="btn btn-primary btn-lg" type="submit">Generate report</button>
    </form>
</div>

<script type="text/javascript">
    $('select').select2();
</script>
<%@include file="footer.jsp"%>