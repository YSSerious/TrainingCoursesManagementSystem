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
    <h3>Sorry, you have no permission to view this page!</h3>
    <form:form method="POST" action="logout">
        <button type="submit" class="btn btn-primary">Log in with different role or account</button>
    </form:form>
</div>

<%@include file="footer.jsp"%>