<%@include file="header.jsp"%>
<div class="col-sm-6 col-sm-offset-3">
	<div class="col-sm-10 col-sm-offset-1 form-wrapper">
		<h2>Create new project</h2>
		<form class="default-form">
			<div class="form-group required">
				<label for="name">Name</label> <input type="text"
					class="form-control" id="name" required>
			</div>
			<div class="form-group col-sm-5 required" data-provide="datepicker">
				<label for="start-date">Start date</label> <input type="date"
					class="form-control" id="start-date" required>
			</div>
			<div class="form-group col-sm-5 col-sm-offset-2 required">
				<label for="finish-date">Finish date</label> <input type="date"
					class="form-control" id="finish-date" required>
			</div>
			<div class="form-group required">
				<label for="description">Description</label>
				<textarea class="form-control" id="description" required></textarea>
			</div>
			<button type="submit" class="btn btn-primary">Create</button>
		</form>
	</div>
</div>
<%@include file="footer.jsp"%>
