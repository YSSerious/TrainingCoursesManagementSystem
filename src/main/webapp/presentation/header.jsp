<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>${title}</title>
<!--<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">-->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/presentation/resources/third-party/bootstrap/css/bootstrap.min.css"/>">
<link
	href="<c:url value="/presentation/resources/third-party/bootstrap/fonts/glyphicons-halflings-regular.woff"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/presentation/resources/third-party/jquery-ui/css/jquery-ui.css"/>">
<script
	src="<c:url value="/presentation/resources/third-party/jquery/jquery.min.js"/>">
	
</script>
<script type="text/javascript"
	src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.min.js"/> ">
	
</script>
<script src="<c:url value="/presentation/resources/js/create_group.js"/>"></script>
<link rel="stylesheet"
	href=" <c:url value="/presentation/resources/third-party/jqpagination/css/jqpagination.css" />"
	type="text/css">
<script
	src="<c:url value="/presentation/resources/third-party/jqpagination/js/jquery.jqpagination.js" />"
	type="text/javascript"></script>
<link
	href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/css/select2.min.css" />"
	type="text/css" rel="stylesheet" />
<script src="http://code.jquery.com/ui/1.12.1/jquery-ui.min.js"
	integrity="sha256-VazP97ZCwtekAsvgPBSUwPFKdrwD3unUfSGVYrahUqU="
	crossorigin="anonymous"></script>
<script
	src="<c:url value="/presentation/resources/third-party/bootstrap/js/bootstrap.min.js"/>"></script>
<script src="https://d3js.org/d3.v4.js"></script>
<!--Own styles-->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/presentation/resources/css/general.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/presentation/resources/css/header.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/presentation/resources/css/login-form.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/presentation/resources/css/forms.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/presentation/resources/css/table.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/presentation/resources/css/charts.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/presentation/resources/css/footer.css"/>">
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/presentation/resources/css/certainProject.css"/>">
    
    <!--Own js-->
    <script src="<c:url value="/presentation/resources/js/forms.js"/>" defer="defer"></script>
    <script src="<c:url value="/presentation/resources/js/charts.js"/>" defer="defer"></script>
    <script src="<c:url value="/presentation/resources/js/user.js"/>" defer="defer"></script>
    <script src="<c:url value="/presentation/resources/js/meeting.js"/>" defer="defer"></script>
    <!--<script src="<c:url value="/presentation/resources/js/create-project-ajax.js"/>"></script>-->
    <script src="<c:url value="/presentation/resources/js/group_attachments.js"/>" defer="defer"></script>
</head>
<body>
<div class="container">
    <nav class="nav navbar-default net-navbar">
        <div class="container-fluid">
            <div class="navbar-header">
                <a href="#" class="navbar-brand"> <img
                        src="<c:url value="/presentation/resources/imgs/logo.png"/>"
                        class="net-logo">
                </a>
            </div>
            <sec:authorize access="hasAnyRole('ADMIN', 'MENTOR', 'HR')">
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="/profile" class="btn btn-link" role="button">
                            My profile
                        </a>
                    </li>
                    <li>
                        <div class="dropdown">
                            <button type="button" class="btn btn-link"
                                    data-toggle="dropdown">
                                Navigate <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-left" role="menu">
                                <li><a href="/projects">Projects</a></li>
                                <sec:authorize access="hasRole('ADMIN')">
                                    <li><a href="/allUsers">Users</a></li>
                                    <li><a href="/category">Categories</a></li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('MENTOR', 'HR')">
                                    <li><a href="/students">Students</a></li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('ADMIN', 'HR')">
                                    <li><a href="/reports">Reports</a></li>
                                </sec:authorize>
                                <li class="divider"></li>
                                <li><a href="/roles">Change my role</a></li>
                            </ul>
                        </div>
                    </li>
                    <li><form:form method="POST" action="/logout">
                        <button type="submit" class="btn btn-link">Log out</button>
                    </form:form></li>
                </ul>
                </sec:authorize>
            </div>
        </div>
    </nav>
    <div class="panel panel-default">
        <div class="panel-body">