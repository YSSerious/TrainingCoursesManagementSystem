<%@include file="header.jsp"%>
<script src="<c:url value="/presentation/resources/js/group.js"/>"
	type="text/javascript">
	
</script>
<jsp:useBean id="type" class="java.lang.String" />
<jsp:useBean id="border" class="java.lang.String" />
<%
	type = "#fff";
	border = "#D3D3D3";
%>
<div class="row"> 
<div class="col-md-6 group-header">
	<div class="panel panel-default panel-horizontal top-panel">
		<div class="panel-heading">
			<h3 class="panel-title"><spring:message code="group.project.name"/></h3>
		</div>
		<div class="panel-body"> ${projectName}</div>
	</div>
	<div class="panel panel-default panel-horizontal bottom-panel">
		<div class="panel-heading">
			<h3 class="panel-title"><spring:message code="group.group.name"/></h3>
		</div>
		<div class="panel-body">${group.name}</div>
	</div>
</div>
</div>

<br />

<div class="meetings_template">
	<h2><spring:message code="group.meetings"/></h2>

	<div class="panel panel-primary"
		style="background-color:<%=type%>;border: 1px solid <%=border%>; border-radius: 7px;">
		<div class="panel-heading clearfix">


			<div data-toggle="collapse" data-target="#collapseMeetings"
				class="arrow col-md-1" style="color: black">
				<span class="glyphicon glyphicon-chevron-down"></span>
			</div>
		</div>


		<div id="collapseMeetings" class="panel-collapse collapse">
			<ul class="list-group">

				<c:forEach items="${meetings}" var="meeting">
					<li class="list-group-item"><a href="/meeting/${meeting.id}">${meeting.name}</a><span
						style='padding-left: 10px;'> </span> ${meeting.time} <span
						style='padding-left: 10px;'> </span> ${meeting.place} <br />
						<div class="btn rmv-cr-btn  pull-right" type='button'>
							<span class="glyphicon glyphicon-edit"></span>
						</div>
				</c:forEach>

			</ul>
		</div>

	</div>
	<br />
</div>

<div class="students_template">
	<h2><spring:message code="group.students"/></h2>
	<div class="panel panel-primary"
		style="background-color:<%=type%>;border: 1px solid <%=border%>; border-radius: 7px;">
		<div class="panel-heading clearfix">
			<button type="button" class="btn btn-default btn-xs pull-right"><spring:message code="group.button.add"/></button>
			<div data-toggle="collapse" data-target="#collapseStudents"
				class="arrow col-md-1" style="color: black">
				<span class="glyphicon glyphicon-chevron-down"></span>
			</div>
		</div>
		<div id="collapseStudents" class="panel-collapse collapse">
			<ul class="list-group">
				<c:forEach items="${students}" var="student">

					<li
						class="list-group-item group-${group.id}-${student.student.id} clearfix">
						<c:if test="${student.status.id == '1'}">
						<span class="label label-danger"><spring:message code="group.student.expelled"/></span>
						</c:if>
						<a href="/users/${student.student.id}">${student.student.firstName}</a>
						 <span style='padding-left: 10px;'> </span>
						${student.student.lastName} 
						<div id="${group.id}-${student.student.id}"
							class="btn rmv-cr-btn col-md-1 pull-right delete-student"
							type='button'>
							<span class="glyphicon glyphicon-remove delete"></span>
						</div> <br />
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<br />
</div>

<div id="studentDeleteError" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal">
					<span class="glyphicon glyphicon-remove"></span>
				</button>
				<h4 class="modal-title"><spring:message code="group.student.delete.error"/></h4>
			</div>
		</div>
	</div>
</div>

<div class="mentors_template">
	<h2><spring:message code="group.mentors"/></h2>
	<div class="panel panel-primary"
		style="background-color:<%=type%>;border: 1px solid <%=border%>; border-radius: 7px;">
		<div class="panel-heading clearfix">
			<button type="button" class="btn btn-default btn-xs pull-right"><spring:message code="group.button.add"/></button>
			<div data-toggle="collapse" data-target="#collapseMentors"
				class="arrow col-md-1" style="color: black">
				<span class="glyphicon glyphicon-chevron-down"></span>
			</div>
		</div>

		<div id="collapseMentors" class="panel-collapse collapse">
			<ul class="list-group">
				<c:forEach items="${mentors}" var="mentor">
					<li class="list-group-item group-${group.id}-${mentor.id} clearfix">

						<a href="/users/${mentor.id}">${mentor.firstName}</a>
						<span style='padding-left: 10px;'> </span>
						${mentor.lastName} <!-- <button id="${group.id}-${mentor.id}"
							class="btn delete-mentor pull-right"><span class="glyphicon glyphicon-remove"></span>
						</button> -->
						<div id="${group.id}-${mentor.id}"
							class="btn rmv-cr-btn col-md-1 pull-right delete-mentor"
							type='button'>
							<span class="glyphicon glyphicon-remove delete"></span>
						</div> <br />
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<br />
</div>

<!--   <div id="attachment-title"><h2>Attachments</h2></div>-->





<div class="modal fade" id="addGroupAttachmentModal" role="dialog">
	<div class="modal-dialog">

		<div class="modal-content">
			<div class="modal-body" style="padding: 40px 50px;">
				<form role="form">
					<div class="form-group">
						<label for="groupAttachmentName"> <spring:message code="group.attachment.name"/> </label><br />
						<input type=text class="form-control" id="groupAttachmentName"
							placeholder="Enter attachment name" required="required">
						<label for="groupAttachment"><spring:message code="group.attachment"/></label><br /> <input
							type=text class="form-control" id="groupAttachment"
							placeholder="Enter attachment" required="required">

					</div>
					<button type="submit"
						class="btn btn-default btn-success pull-center"
						id="addAttachmentSubmitButton">
						<span class="glyphicon glyphicon-off"></span><spring:message code="group.send"/>
					</button>
				</form>
			</div>
			<div class="modal-footer">

				<button type="button" class="btn btn-default btn-lg"
					data-dismiss="modal" id="cancelButton">
					<span class="glyphicon glyphicon-remove"></span> <spring:message code="group.cancel"/>
				</button>
			</div>
		</div>

	</div>
</div>
<!--  -->
<div class="modal fade" id="editGroupAttachmentModal" role="dialog">
	<div class="modal-dialog">

		<div class="modal-content">
			<div class="modal-body" style="padding: 40px 50px;">
				<form role="form">
					<div class="form-group">
						<label for="groupAttachmentName"> <spring:message code="group.attachment.name"/> </label><br />
						<input type=text class="form-control" id="editGroupAttachmentName"
							placeholder="Enter attachment name" required="required">
						<label for="editGroupAttachment"><spring:message code="group.attachment"/> </label><br /> <input
							type=text class="form-control" id="groupAttachment"
							placeholder="Enter attachment" required="required">

					</div>
					<button type="submit"
						class="btn btn-default btn-success pull-center"
						id="editAttachmentSubmitButton">
						<span class="glyphicon glyphicon-off"></span><spring:message code="group.send"/>
					</button>
				</form>
			</div>
			<div class="modal-footer">

				<button type="button" class="btn btn-default btn-lg"
					data-dismiss="modal" id="editCancelButton">
					<span class="glyphicon glyphicon-remove"></span> <spring:message code="group.cancel"/>
				</button>
			</div>
		</div>

	</div>
</div>

<!--  -->
<div class="container attachment-group">
	<div class="row">
		<div class="col-md-12">
			<h2><spring:message code="group.attachments"/></h2>
			<div class="panel-group" id="panelGroupAttachment">
				<div class="panel panel-default">
					<div class="panel-heading attachment-group">
						<h4 class="panel-title row">
							<div data-toggle="collapse" data-target="#collapseIn"
								class="arrow col-md-1">
								<span class="glyphicon glyphicon-chevron-down"></span>
							</div>
							<div role="button"
								class="btn btn-default add-attachment-btn btn-sm pull-right"
								id="addGroupAttachmentButton" data-toggle="modal"
								data-target="#addGroupAttachmentModal">
								<b><spring:message code="group.add.attachment"/></b>
							</div>
						</h4>
					</div>
					<div id="collapseIn" class="panel-collapse collapse">
						<c:forEach items="${attachments}" var="attachment">
							<div class="panel-body row" id="attachmentId-${attachment.id}">
								<div class="col-md-10">${attachment.name}</div>

								<div id="d${attachment.id}"
									class="btn rmv-cr-btn col-md-1 pull-right delete" type='button'
									data-button='{"title": "${attachment.name}"}'>
									<span class="glyphicon glyphicon-remove "></span>
								</div>
								<!-- <div class="btn rmv-cr-btn col-md-1 pull-right" type='button'
									data-button='{"id":"e${attachment.id}","title": "${attachment.name}"}'>
									<span class="glyphicon glyphicon-edit edit"></span>
								</div>
   								-->
								<div role="button" class="btn rmv-cr-btn col-md-1 pull-right"
									id="addGroupAttachmentButton" data-toggle="modal"
									data-target="#addGroupAttachmentModal">
									<span class="glyphicon glyphicon-edit edit"></span>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>


<%@include file="footer.jsp"%>