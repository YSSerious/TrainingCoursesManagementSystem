<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/presentation/header.jsp"%>
<div class = "row">
	<div class = "col-sm-12">
		<div class = "text-center">
			<h2>Sorry, but operation isn't complete. The reason is: </h2>
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
			<h2>Go back to the previous <a href="/users/${userId}">page</a></h2>
		</div>
	</div>
</div>
<%@include file="/presentation/footer.jsp"%>
