<%--
  Created by IntelliJ IDEA.
  User: Alex_Frankiv
  Date: 29.10.2016
  Time: 13:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>DMS: Log in</title>
</head>
<body>
<c:url value="/login" var="loginUrl"/>
<form action="<c:url value='/login' />" method="POST">
    <c:if test="${param.error != null}">
        <div>
            Invalid username and password.
        </div>
    </c:if>
    <c:if test="${param.logout != null}">
        <div>
            You have been logged out!
        </div>
    </c:if>
    <div>
        <label>Login: <input type="text" name="username"/></label>
    </div>
    <div>
        <label>Password: <input type="password" name="password"/></label>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div><input type="submit" value="Sign in"/></div>
</form>
</body>
</html>
