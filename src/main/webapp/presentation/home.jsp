<%--
  Created by IntelliJ IDEA.
  User: Алексей
  Date: 15.10.2016
  Time: 19:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<html>
<head>
    <title>Title</title>
</head>
<body>
Logged in as: ${username}
<br/>
${user}
<sec:authorize access="hasRole('ROLE_ADMIN')">
    Logged in as ADMIN
</sec:authorize>
<sec:authorize access="hasRole('ROLE_MENTOR')">
    Logged in as MENTOR
</sec:authorize>
<sec:authorize access="hasRole('ROLE_HR')">
    Logged in as HR
</sec:authorize>
</body>
</html>
