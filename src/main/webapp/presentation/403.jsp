<%--
  Created by IntelliJ IDEA.
  User: Alex_Frankiv
  Date: 29.10.2016
  Time: 18:26
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>

<div class="container error-forbidden-box">
    <h1>403 Forbidden</h1>
    <h3><spring:message code="403.msg"/></h3>
    <a href="/roles" class="btn btn-primary"><spring:message code="403.changerole.msg"/></a>
    <form:form method="POST" action="logout">
        <button type="submit" class="btn btn-primary"><spring:message code="403.relogin.msg"/></button>
    </form:form>
</div>

<%@include file="footer.jsp"%>