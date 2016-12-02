<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/presentation/header.jsp"%>
<div class = "row">
	<div class = "col-sm-12">
		<div class = "text-center">
			<h2><spring:message code="error.status.switch.description"/></h2>
		</div>
	</div>
</div>
<br/>
<div class = "row">
	<div class="col-sm-8 col-sm-offset-2">
			<div class="alert alert-danger h4">
				${errMsg}
			</div>
	</div>
</div>

<div class = "row">
	<div class = "col-sm-12">
		<div class="text-center">
			<h2><a href="/users/${userId}"><spring:message code="error.status.switch.action.description"/></a></h2>
		</div>
	</div>
</div>
<%@include file="/presentation/footer.jsp"%>
