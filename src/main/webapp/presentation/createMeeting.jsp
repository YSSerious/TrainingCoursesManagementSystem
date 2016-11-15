<%@include file="header.jsp"%>
<div class="col-sm-6 col-sm-offset-3">
	<div class="col-sm-10 col-sm-offset-1 form-wrapper">
		<h2>Create new meeting</h2>
		<form:form class="default-form col-sm-12" id='create-meeting-form'
			method="post" modelAttribute="meetingForm" action="${url}"
			novalidate="">
			<div class="form-group col-sm-12 required">
				<div class="col-sm-12">
					<form:label for="meeting-name" path="name">Name</form:label>
					<form:errors path="name" class="form-error" />
				</div>
				<div class="col-sm-12">
					<form:input type="text" class="form-control" id="meeting-name"
						path="name" />
					<label id="name-message"></label>
				</div>
			</div>
			<div class="form-group col-sm-12 required">
				<div class="col-sm-12">
					<form:label for="time" path="time">Time</form:label>
					<form:errors path="time" class="form-error" />
				</div>
				<div class="col-sm-6">
					<form:input type="date" class="form-control" id="time" path="time" />
				</div>
			</div>
			<div class="form-group col-sm-12 required">
				<div class="col-sm-12">
					<form:label for="place" path="place">Place</form:label>
					<form:errors path="place" class="form-error" />
				</div>
				<div class="col-sm-12">
					<form:input type="text" class="form-control" id="place"
						path="place" />
				</div>
			</div>
			<div class="form-group col-sm-12 required">
				<div class="col-sm-12">
					<form:label for="criterions" path="criterions">Criteria</form:label>
					<form:errors path="criterions" class="form-error" />
				</div>
				<div class="col-md-12">
					<form:checkboxes items="${criteria}" class="form-control" id="criterions"
						path="criterions" />
				</div>
			</div>

			<div class="col-sm-12">
				<button type="submit" class="btn btn-primary">Create</button>
			</div>
		</form:form>
	</div>
</div>

<%@include file="footer.jsp"%>
