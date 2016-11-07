<%@include file="header.jsp"%>
<c:url var="projectsUrl" value="/projects/create-project" />
<div class="col-sm-6 col-sm-offset-3">
	<div class="col-sm-10 col-sm-offset-1 form-wrapper">
		<h2>Create new project</h2>
		<form:form class="default-form" id='create-project-form' method="post"
			modelAttribute="projectForm" action="${projectsUrl}">
			<div class="form-group required">
				<form:label for="project-name" path="name">Name</form:label>
				<form:input type="text" class="form-control" id="project-name"
					 path="name" />
				<label id="name-message"></label>
				<form:errors path="name" />
				<spring:message code="NotEmpty.projectForm.name"/>
			</div>
			<div class="form-group col-sm-5 required" data-provide="datepicker">
				<form:label for="start-date" path="startDate">Start date</form:label>
				<form:input type="date" class="form-control" id="start-date"
					required="required" path="startDate" />
				<form:errors path="startDate" />
			</div>
			<div class="form-group col-sm-5 col-sm-offset-2 required">
				<form:label for="finish-date" path="finishDate">Finish date</form:label>
				<form:input type="date" class="form-control" id="finish-date"
					required="required" path="finishDate" />
				<form:errors path="finishDate" />	
			</div>
			<div class="form-group required">
				<form:label for="description" path="description">Description</form:label>
				<form:textarea class="form-control" id="description"
					 path="description"></form:textarea>
				<form:errors path="description" />
			</div>
			<button type="submit" class="btn btn-primary">Create</button>
		</form:form>
	</div>
</div>
<%@include file="footer.jsp"%>
