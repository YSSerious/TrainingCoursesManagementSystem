<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/presentation/header.jsp"%>
<div class = "row">
	<div class = "col-sm-12">
		<div class = "text-center">
			<h2><spring:message code="error.users.not.found"/></h2>
		</div>
	</div>
</div>
<br/>

<div class = "row">
	<div class = "col-sm-12">
		<div class="text-center">
			<h2><a href="/allUsers"><spring:message code="error.users.action.description"/></a></h2>
		</div>
	</div>
</div>
<%@include file="/presentation/footer.jsp"%>
