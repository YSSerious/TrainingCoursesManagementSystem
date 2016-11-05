<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>New project</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/presentation/resources/third-party/bootstrap/css/bootstrap.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/presentation/resources/third-party/jquery-ui/css/jquery-ui.css"/>">
<script
	src="<c:url value="/presentation/resources/third-party/jquery/jquery.min.js"/>">
</script>
<script
	src="<c:url value="/presentation/resources/third-party/jquery-ui/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/presentation/resources/third-party/bootstrap/js/bootstrap.min.js"/>"></script>
<!--Own styles-->
<link rel="stylesheet" type="text/css" href="<c:url value="/presentation/resources/css/general.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/presentation/resources/css/header.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/presentation/resources/css/forms.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/presentation/resources/css/footer.css"/>">
<!--Own js-->
<script src="<c:url value="/presentation/resources/js/forms.js"/>"></script>
</head>
<body>
	<div class="container">
		<nav class="nav navbar-default net-navbar">
			<div class="container-fluid">
				<div class="navbar-header">
					<a href="#" class="navbar-brand"> <img src="<c:url value="/presentation/resources/imgs/logo.png"/>"
						class="net-logo">
					</a>
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav navbar-right">
						<li>
							<button type="button" href="#" class="btn btn-link">Username</button>
						</li>
						<li>
							<div class="dropdown">
								<button type="button" class="btn btn-link"
									data-toggle="dropdown">
									Navigate <span class="caret"></span>
								</button>
								<ul class="dropdown-menu dropdown-menu-left" role="menu">
									<li><a href="#">First</a></li>
									<li><a href="#">Second</a></li>
									<li><a href="#">Third</a></li>
									<li class="divider"></li>
									<li><a href="#">Last</a></li>
								</ul>
							</div>
						</li>
						<li>
							<button type="button" href="#" class="btn btn-link">Log
								out</button>
						</li>
					</ul>
				</div>
			</div>
		</nav>
		<div class="panel panel-default">
			<div class="panel-body">