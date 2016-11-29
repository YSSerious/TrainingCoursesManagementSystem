<%@include file="header.jsp"%>
<script src="<c:url value="/presentation/resources/js/group.js"/>"
	type="text/javascript" defer="defer">
	
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
				<h3 class="panel-title">
					<spring:message code="group.project.name" />
				</h3>
			</div>
			<div class="panel-body">${projectName}</div>
		</div>
		<div class="panel panel-default panel-horizontal bottom-panel">
			<div class="panel-heading">
				<h3 class="panel-title">
					<spring:message code="group.group.name" />
				</h3>
			</div>
			<div class="panel-body">${group.name}</div>
		</div>
	</div>
	<div class="col-md-6">
		<sec:authorize access="hasAnyRole('ADMIN', 'HR')">
			<button class="btn btn-primary pull-right" data-toggle="modal"
				data-target="#group-report-modal"><b><spring:message
						code="group.generate.report" /></b></button>
		</sec:authorize>
	</div>
</div>

<br />

<div class="row">
	<div class="col-md-12">
		<h2>
			<spring:message code="group.meetings" />
		</h2>
		<div class="panel-group" id="panelGroupId">
			<div class="panel panel-primary"
				style="background-color:<%=type%>;border: 1px solid <%=border%>; border-radius: 7px;">
				<div class="panel-heading clearfix">
						<button type="button" class="btn btn-default btn-xs pull-right"
							data-target="#addMeetingNoteModal" data-toggle="modal">
							Add note
						</button>
					<div data-toggle="collapse" data-target="#collapseMeetings"
						class="arrow col-md-1" style="color: black" onclick="changeSpan(this)">
						<span id="spanId" class="glyphicon glyphicon-chevron-down"></span>
						
					</div>
				</div>
				<div id="collapseMeetings" class="panel-collapse collapse clearfix">
					<ul class="list-group">
						<c:forEach items="${meetings}" var="meeting">
							<li class="list-group-item  clearfix"
								id="meetingId-${meeting.id}">
								<a href="/meeting/${meeting.id}" class="col-md-2"
								id="editMeetingNameId-${meeting.id}">${meeting.name}</a>
								<div class="col-md-2" id="editMeetingDateId-${meeting.id}">${meeting.time}</div>
								<div class="col-md-4" id="editMeetingPlaceId-${meeting.id}">${meeting.place}</div>
								
								<sec:authorize access="hasRole('ADMIN')">
									<c:if test="${!meeting.reviewed}">
										<div class="btn rmv-cr-btn col-md-1 pull-right" type='button'
											data-toggle="modal" data-target="#deleteMeetingModal"
											onclick="setMeeting(${meeting.id})">
											<span class="glyphicon glyphicon-remove"></span>
										</div>
									</c:if>
								</sec:authorize>
								<div class="btn rmv-cr-btn col-md-1 col-md-offset-2 "
									type='button' data-toggle="modal"
									data-target="#editMeetingModal"
									onclick="setMeeting(${meeting.id})">
									<span class="glyphicon glyphicon-edit"></span>
								</div></li>
						</c:forEach>
					</ul>
					<ul class="list-group noteList">
					<c:forEach items="${meetingNotes}" var="note">
					
					<li  id="attachment-${note.id}" class="list-group-item clearfix">
						 <!-- 	${attachment.name} <span style='padding-left: 10px;'> </span> -->
						 <a href="${note.attachmentScope}">${note.name } </a>
					 
						<div id="${note.id}" class="btn rmv-cr-btn col-md-1 pull-right delete"
							type='button'>
							<span class="glyphicon glyphicon-remove "></span>
						</div> 
						
					</li>
					</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>


	<div id="addMeetingNoteModal" class="modal fade" tabindex="-1" role="dialog" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal"><span
                        class="glyphicon glyphicon-remove"></span></button>
                <h4 class="modal-title">New Attachment</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        
                        <label for="usr">Name:</label>
                        <input type="text" class="form-control" id="noteAttachmentName" placeholder="attachment will have prefix 'meeting_note_'">
                    </div>
                    <div class="form-group">
                        <label for="usr">Link:</label>
                        <input type="text" class="form-control" id="noteAttachment" placeholder="paste link here...">
                    </div>
                    <input
								type=text   id="groupId" style="display:none"
								value="${groupId }">
                    <br>
                    <div role="button" class="btn btn-default btn-xs pull-right-btn btn-save collapse" id="save-att-btn">
                        Save
                    </div>
                    <button id="addNoteSubmitButton" type="button" class="btn btn-primary"  >Save</button>
                </form>
            </div>
        </div>
    </div>
</div>	


<div class="students_template">
	<h2>
		<spring:message code="group.students" />
	</h2>
	<div class="panel panel-primary"
		style="background-color:<%=type%>;border: 1px solid <%=border%>; border-radius: 7px;">
		<div class="panel-heading clearfix">
			<button type="button" class="btn btn-default btn-xs pull-right">
				<spring:message code="group.button.add" />
			</button>
			<div data-toggle="collapse" data-target="#collapseStudents"
				class="arrow col-md-1" style="color: black" onclick="changeSpan(this)">
				<span class="glyphicon glyphicon-chevron-down"></span>
			</div>
		</div>
		<div id="collapseStudents" class="panel-collapse collapse">
			<ul class="list-group">
				<c:forEach items="${students}" var="studentMap">

					<li
						class="list-group-item group-${group.id}-${studentMap.key.student.id} clearfix">
						<c:if test="${studentMap.key.status.id == '1'}">
							<span class="label label-danger"><spring:message
									code="group.student.expelled" /></span>
						</c:if> <a href="/users/${studentMap.key.student.id}"> <!--  -->${studentMap.key.student.firstName}</a>
						<span style='padding-left: 10px;'> </span>
						${studentMap.key.student.lastName} <span
						style='padding-left: 10px;'> </span>
						 <c:forEach items="${studentMap.value}" var="review">
							<span style='padding-left: 10px;'></span>
							<c:choose>
								<c:when test="${review.type == 'A'}">
									<span style='padding-left: 5px;' class="label label-danger" data-toggle="tooltip" title="Absent">${review.meeting.name}</span>
								</c:when>
								<c:when test="${review.type == 'E'}">
									<span style='padding-left: 5px;' class="label label-success" data-toggle="tooltip" title="Evaluated">${review.meeting.name}</span>
								</c:when>
								<c:otherwise>
									<span style='padding-left: 5px;' class="label label-primary" data-toggle="tooltip" title="Not reviewed">${review.meeting.name}</span>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<div id="${group.id}-${student.key.student.id}"
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
				<h4 class="modal-title">
					<spring:message code="group.student.delete.error" />
				</h4>
			</div>
		</div>
	</div>
</div>

<div class="mentors_template">
	<h2>
		<spring:message code="group.mentors" />
	</h2>
	<div class="panel panel-primary"
		style="background-color:<%=type%>;border: 1px solid <%=border%>; border-radius: 7px;">
		<div class="panel-heading clearfix">
			<button type="button" class="btn btn-default btn-xs pull-right">
				<spring:message code="group.button.add" />
			</button>
			<div data-toggle="collapse" data-target="#collapseMentors"
				class="arrow col-md-1" style="color: black" onclick="changeSpan(this)">
				<span class="glyphicon glyphicon-chevron-down"></span>
			</div>
		</div>

		<div id="collapseMentors" class="panel-collapse collapse">
			<ul class="list-group">
				<c:forEach items="${mentors}" var="mentor">
					<li class="list-group-item group-${group.id}-${mentor.id} clearfix">

						<a href="/users/${mentor.id}">${mentor.firstName}</a> <span
						style='padding-left: 10px;'> </span> ${mentor.lastName} <!-- <button id="${group.id}-${mentor.id}"
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


<h2>
	<spring:message code="group.attachments" />
</h2>
<div class="panel panel-primary"
	style="background-color:<%=type%>;border: 1px solid <%=border%>; border-radius: 7px;">
	<div class="panel-heading clearfix">
		<button type="button" class="btn btn-default btn-xs pull-right"
			data-target="#addGroupAttachmentModal" data-toggle="modal">
			<spring:message code="group.button.add" />
		</button>
		<div data-toggle="collapse" data-target="#collapseAttachment"
			class="arrow col-md-1" style="color: black" onclick="changeSpan(this)">
			<span class="glyphicon glyphicon-chevron-down"></span>
		</div>
	</div>


		<div id="collapseAttachment" class="panel-collapse collapse">
			<ul id="listAttachments" class="list-group">
			 
						<c:forEach items="${attachments}" var="attachment">
						<li  id="attachment-${attachment.id}" class="list-group-item group-attachment clearfix">
						 <!-- 	${attachment.name} <span style='padding-left: 10px;'> </span> -->
						 <a href="${ attachment.attachmentScope}">${attachment.name } </a>
					 
						<div id="${attachment.id}" class="btn rmv-cr-btn col-md-1 pull-right delete"
							type='button'>
							<span class="glyphicon glyphicon-remove "></span>
						</div> 
						
					</li>
							 
							
						</c:forEach>
						</ul>
					 
				</div>
</div>


		 
 

<sec:authorize access="hasAnyRole('ADMIN', 'HR')">
	<div id="group-report-modal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">
						<spring:message code="group.generate.report" />
					</h4>
				</div>
				<div id="group-report-back" class="modal-body">
					<form id="group-form-report" action="/groupReport.xls">
						<spring:message code="report.select.students" />
						:<br /> <select style="width: 100%;" multiple name="students">
							<c:forEach items="${selectStudents}" var="student">
								<option value="${student.id}">${student.firstName}
									${student.lastName}</option>
							</c:forEach>
						</select> <br />
						<hr />

						<spring:message code="report.select.categories" />
						:<br /> <select style="width: 100%;" multiple name="categories">
							<c:forEach items="${categories}" var="category">
								<option value="${category.id}">${category.name}</option>
							</c:forEach>
						</select> <br />
						<hr />

						<spring:message code="report.select.criteria" />
						:<br /> <select style="width: 100%;" multiple name="criteria">
							<c:forEach items="${criteria}" var="criterion">
								<option value="${criterion.id}">${criterion.title}</option>
							</c:forEach>
						</select> <br />
						<hr />
						*
						<spring:message code="report.note.students" />
						<br /> *
						<spring:message code="report.note.criteria" />
						<br /> <br /> <input type="hidden" name="groupId"
							value="${group.id}" /> <input onclick="getGroupReport()"
							class="btn btn-primary pull-right" type="submit"
							value="<spring:message code="report.submit"/>" /> <br /> <br />
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- start edit meeting modal -->
	<div id="editMeetingModal" class="modal fade" data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal">
						<span class="glyphicon glyphicon-remove"></span>
					</button>
					<h4 class="modal-title">Edit Meeting</h4>
					<form id="editMeetingFormId">
						<div class="form-group">
							<label for="editMeetingName">Meeting Name</label> <input
								type="text" class="form-control" id="editMeetingName">
						</div>
						<div class="form-group">
							<label for="editMeetingPlace">Place</label> <input type="text"
								class="form-control" id="editMeetingPlace">
						</div>
						<div class="form-group">
							<label for="editMeetingDate">Date/Time</label> <input
								type="datetime-local" class="form-control" id="editMeetingDate">
						</div>
						<br>
						<button id="editMeetingButton" type="submit"
							class="btn btn-primary" data-dismiss="modal">Save</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- finish edit category modal -->
	<!-- start delete meeting modal -->
	<div id="deleteMeetingModal" class="modal fade" data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal">
						<span class="glyphicon glyphicon-remove"></span>
					</button>
					<h4 class="modal-title">Are you sure?</h4>
					<button data-dismiss="modal" id="deleteMeetingButton"
						class="btn btn-link">Yes</button>
					<button data-dismiss="modal" class="btn btn-link">NO</button>
				</div>
			</div>
		</div>
	</div>
	<!-- finish delete meeting modal -->
	<!-- start meetingDeleteError modal -->
	<div id="meetingDeleteError" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal">
						<span class="glyphicon glyphicon-remove"></span>
					</button>
					<h4 class="modal-title">This meeting was reviewed and cannot
						be deleted.</h4>
					<button data-dismiss="modal" class="btn btn-link">Close</button>
				</div>
			</div>
		</div>
	</div>
	<!-- finish meetingDeleteError modal -->
	<!-- start add attachment modal -->
	<div id="addGroupAttachmentModal" class="modal fade" tabindex="-1" role="dialog" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal"><span
                        class="glyphicon glyphicon-remove"></span></button>
                <h4 class="modal-title">New Attachment</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="usr"><spring:message code="group.attachment.name"/></label>
                        <input type="text" class="form-control" id="groupAttachmentName">
                    </div>
                    <div class="form-group">
                        <label for="usr"><spring:message code="group.attachment"/></label>
                        <input type="url" class="form-control" id="groupAttachment">
                    </div>
                    <input
								type=text   id="groupId" style="display:none"
								value="${groupId }">
                    <br>
                    <div role="button" class="btn btn-default btn-xs pull-right-btn btn-save collapse" id="save-att-btn">
                        Save
                    </div>
                    <button id="addAttachmentSubmitButton" type="button" class="btn btn-primary"  >Save</button>
                </form>
            </div>
        </div>
    </div>
</div>	 
 <!-- finish add attachment -->
	<script type="text/javascript">
	function getGroupReport(){
		$('#group-report-modal').modal('hide');
		$('#group-form-report').submit();
	}
   	$('select').select2();
</script>
</sec:authorize>
<%@include file="footer.jsp"%>