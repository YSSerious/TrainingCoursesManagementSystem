<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/presentation/header.jsp"%>
<div class = "row">
	<div class = "col-sm-12">
		<div class = "text-center">
			<h2><spring:message code="error.empty.not.found"/></h2>
		</div>
	</div>
</div>
<br/>
<div class="text-center">
	<img width="256" height="256" src="/presentation/resources/imgs/error.png">
</div>
<div class = "row">
	<div class = "col-sm-12">
		<div class="text-center">
			<h2><a href="/"><spring:message code="error.empty.action.description"/></a></h2>
		</div>
	</div>
</div>
<%@include file="/presentation/footer.jsp"%>
