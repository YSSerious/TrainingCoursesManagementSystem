<%@include file="header.jsp" %>
<%@include file="projectDataEditingModals.jsp" %>
<%@include file="projectGroupsModals.jsp" %>
<script
    src="<c:url value="/presentation/resources/js/certainProject.js"/>"
    type="text/javascript" defer="defer">
</script>

<div class="container certain-project" data-project-id="${project.id}">
    <!-- Example row of columns -->
    <div class="row">
        <div class="col-md-12">
            <div class="page-header">
                <sec:authorize access="hasAnyRole('ADMIN')">
                    <input id="can-edit" type="hidden" value="true" />
                </sec:authorize>
                <div class="editable-wrapper" id="project-name">
                    <!--                    <h3><b>Project:</b></h3>-->
                    <div class="editable-label editable">
                        <h3 style="font-size: 3rem">${project.name}</h3>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 editable-group">
                        <div class="panel panel-default panel-horizontal top-panel editable-wrapper" id="project-startdate">
                            <div class="panel-heading">
                                <h3 class="panel-title">Start date</h3>
                            </div>
                            <div class="panel-body editable-label editable">
                                <h5>${project.startDate}</h5>
                            </div>
                        </div>
                        <div class="panel panel-default panel-horizontal bottom-panel editable-wrapper" id="project-finishdate">
                            <div class="panel-heading">
                                <h3 class="panel-title">Finish date</h3>
                            </div>
                            <div class="panel-body editable-label editable">
                                <h5>${project.finishDate}</h5>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 editable-wrapper" id="project-description">
                        <div class="description-title editable-label">
                            <h4 class="desc-label" style="margin-top: 0px;"> Description</h4>
                        </div>
                        <div class="editable">
                            <h5>${project.description.trim()}</h5>
                        </div>
                    </div>
                </div>
                 <sec:authorize access="hasAnyRole('ADMIN', 'HR')"><font color="blue" data-toggle="modal" data-target="#project-report-modal"><b><spring:message code="project.generate.report"/></b></font>
            	</sec:authorize>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-12">
            <h2>
                Groups
            </h2>
            <div class="panel panel-default" id="project-groups">
                <div class="panel-heading collapsed">
                    <sec:authorize access="hasRole('ADMIN')">
                    <button type="button" class="btn btn-default btn-sm pull-right" id="showGroupsAndCriteria"
                            data-toggle="modal"
                            data-target="#meetingCreateModal">
                        <b>Add Meeting</b>
                    </button>
                    <button type="button" class="btn btn-default btn-sm pull-right right" id="create-group">
                        <b>Add</b>
                    </button>
                        </sec:authorize>
                    <div class="clearfix"></div>
                </div>
                <%--<%@include file="createGroup.jsp"%>--%>
                <div id="collapse-group" class="panel-collapse collapse col-sm-12">
                    <div class="panel-body">
                        <table class="table table-hover">
                            <tr>
                                <th>Name</th>
                                <th>Students amount</th>
                                <th>Upcoming meeting</th>
                                <th></th>
                            </tr>
                            <c:forEach items="${groups}" var="group">
                                <tr id="${group.id}" data-students-amount="${group.studentsAmount}">
                                    <td><a href="/groups/group?id=${group.id}">${group.name}</a></td>
                                    <td>${group.studentsAmount}</a></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty group.upcomingMeeting}">
                                                <a href="/meeting/${group.upcomingMeeting.id}">${group.upcomingMeeting.name}</a>
                                            </c:when>
                                            <c:otherwise>
                                                No upcoming meetings
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <button class="btn btn-collapse edit-group glyphicon-button">
                                            <span class="glyphicon glyphicon-edit"></span>
                                        </button>
                                        <button class="btn btn-collapse delete-group glyphicon-button">
                                            <span class="glyphicon glyphicon-remove"></span>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <h2>Criteria List </h2>
            <div class="panel-group" id="panelGroupId">
                <div class="panel panel-default">
                    <div class="panel-heading clearfix">
                        <div data-toggle="collapse" data-target="#collapseIn" class="arrow col-md-1" onclick="changeSpan(this)">
                            <span id="spanId" class="glyphicon glyphicon-chevron-down"></span>
                        </div>
                        <sec:authorize access="hasRole('ADMIN')">
                        <button type="button" class="btn btn-default btn-sm pull-right"
                                id="showAvailableCriteria"
                                data-toggle="modal"
                                data-target="#showAvailableCriteriaModal">
                            <b>Add criteria</b>
                        </button>
                        </sec:authorize>
                    </div>
                    <div id="collapseIn" class="panel-collapse collapse clearfix">
                            <ul class="list-group" id="collapseUL">
                                <c:forEach items="${criterions}" var="criterion">
                                    <li class="list-group-item  clearfix" id="criteriaId-${criterion.id}">
                                        <div class="col-md-11">${criterion.title}</div>
                                        <c:if test="${!criterion.rated}">
                                            <div class="btn rmv-cr-btn col-md-1" type='button'
                                                 data-button='{"id":"${criterion.id}","title": "${criterion.title}"}'>
                                                <span class="glyphicon glyphicon-remove"></span>
                                            </div>
                                        </c:if>
                                    </li>
                                </c:forEach>
                            </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div class="row">
        <div class="col-md-12">
            <h2>
                Attachments
            </h2>
            <div class="panel-heading">
				<div role="button" data-toggle="collapse" data-target="#att-collapse" class="arrow col-md-1" onclick="changeSpan(this)">
                    <!--Пожалуйста, не ставь зедсь уже использующийся ID. Если тебе так сильно нужен мой метод смены кнопки, используй class.-->
                                <span id="spanIId" class="pull-left glyphicon glyphicon-chevron-down" style="margin-top:5px;"></span>
                </div>
                <div role="button" class="btn btn-default btn-sm pull-right" id="add-att-btn" 
                	data-toggle="modal" data-target="#addAttachmentModal"><b>Add Attach</b>
                </div>
            </div>
	        <div class="panel-collapse collapse" id="att-collapse">
		        <ul class="list-group " id="attachment-group">
		            <c:forEach items="${attachments}" var="attachment">
		                <li class="list-group-item">
		                    <a href="${attachment.attachmentScope}">${attachment.name}</a>
		                    <div class="btn rmv-btn col-md-1" role='button' data-button='{"id_attachment": "${attachment.id}"}'>
		                        <span class="glyphicon glyphicon-remove"></span>
		                    </div>
		                </li>
		            </c:forEach>
		        </ul>
	        <div>
        </div>
    </div>
</div>

</div>
<hr>

<div id="addMeetingModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">&times;</button>
            <h4 class="modal-title">Create meeting</h4>
        </div>
    </div>
</div>

<!-- start showAvailableCriteria modal -->
<div id="showAvailableCriteriaModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal"><span
                        class="glyphicon glyphicon-remove"></span></button>
                <h4 class="modal-title">Add some criteria</h4>
                <hr>
                <div class="row">
                    <input type="text" id="search" placeholder="type search" class="col-md-offset-4">
                </div>
                <table id="criterionTable" class="table table-condensed table-hover table-responsive">
                    <thead class="table-head">
                        <tr>
                            <th><b>Name</b></th>
                            <th><b>Add</b></th>
                        </tr>
                    </thead>
                    <tbody id="criteriaTableId">
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<!-- finish showAvailableCriteria modal -->
<!-- start criteriaDeleteError modal -->
<div id="criteriaDeleteErrorModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal"><span
                        class="glyphicon glyphicon-remove"></span></button>
                <h4 class="modal-title">This criteria was rated, and cannot be deleted.</h4>
                <button data-dismiss="modal" class="btn btn-link">Close</button>
            </div>
        </div>
    </div>
</div>
<!-- finish criteriaDeleteError modal -->
<!-- start meetingAddError modal -->
<div id="meetingAddError" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal"><span
                        class="glyphicon glyphicon-remove"></span></button>
                <h4 class="modal-title">Meetings with this date already created.</h4>
                <button data-dismiss="modal" class="btn btn-link">Close</button>
            </div>
        </div>
    </div>
</div>
<!-- finish meetingAddError modal -->
<!-- start create Meeting modal -->
<div id="meetingCreateModal" class="modal fade" tabindex="-1" role="dialog" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal"><span
                        class="glyphicon glyphicon-remove"></span></button>
                <h4 class="modal-title">New Meeting</h4>
            </div>
            <div class="modal-body">
                <form id="newMeetingFormId">
                    <div class="form-group">
                        <label for="inputName">Meeting Name</label>
                        <input type="text" class="form-control" id="inputName">
                    </div>
                    <div class="form-group">
                        <label for="inputPlace">Place</label>
                        <input type="text" class="form-control" id="inputPlace">
                    </div>
                    <div class="form-group">
                        <label for="inputDate">Date/Time</label>
                        <input type="datetime-local" class="form-control" id="inputDate">
                    </div>
                    <label>Criteria</label>
                    <input type='checkbox' id="checkAllCriteriaId">
                    <p>
                    <div class="form-group" id="CriteriaCheckBoxId">
                    </div>
                    <label>Groups</label>
                    <input type='checkbox' id="checkAllGroupsId">
                    <p>
                    <div class="form-group" id="GroupsCheckBoxId">
                    </div>
                    <br>
                    <button id="saveMeeting" type="submit" class="btn btn-primary" data-dismiss="modal">Save</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- finish create Meeting modal -->
<!-- start create Attachment modal -->
<div id="addAttachmentModal" class="modal fade" tabindex="-1" role="dialog" data-backdrop="static">
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
                        <input type="text" class="form-control" id="att-name">
                    </div>
                    <div class="form-group">
                        <label for="usr">Link:</label>
                        <input type="text" class="form-control" id="att-link">
                    </div>
                    <br>
                    <div role="button" class="btn btn-default btn-xs pull-right-btn btn-save collapse" id="save-att-btn">
                        Save
                    </div>
                    <button id="saveAttachment" type="button" class="btn btn-primary" data-dismiss="modal">Save</button>
                </form>
            </div>
        </div>
    </div>
</div>
</div>

<script>
    $(document).ready(function () {

        $("#saveAttachment").click(function (event) {
            var name = $("#att-name").val();
            var link = $("#att-link").val();
            if (name && link) {
                $("#attachment-group").prepend('<li class="list-group-item" style="color:#d9534f;">Attachment "' + name + '" is added</a></li>');

                $("#add-att-btn").click();
                $("#att-name").val('');
                $("#att-link").val('');

                $.ajax({
                    url: "/addProjectAttachment",
                    type: "POST",
                    data: {
                        "attachmentName": name,
                        "attachmentLink": link
                    }
				 });
            }
        });


        $('.rmv-btn').click(function () {
            $(this).parent().remove();
            var id = $(this).data('button').id_attachment;
            $.ajax({
                url: "/removeProjectAttachment",
                type: "POST",
                data: {"id_attachment": id}

            });
        });
    });
</script>
<script>
    var projectId = "${project.id}"
</script>
<sec:authorize access="hasAnyRole('ADMIN', 'HR')">
<div id="project-report-modal" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"><spring:message code="project.generate.report"/></h4>
			</div>
			<div id="project-report-back" class="modal-body">
				<form id="project-form-report" action="/projectReport.xls">
				
				<spring:message code="report.select.students"/>:<br /> <select
						style="width: 100%;" multiple name="students">
						<c:forEach items="${students}" var="student">
							<option value="${student.id}">${student.firstName}
								${student.lastName}</option>
						</c:forEach>
					</select> <br />
					<hr />

				<spring:message code="report.select.categories"/>:<br /> <select style="width: 100%;" multiple
						name="categories">
						<c:forEach items="${categories}" var="category">
							<option value="${category.id}">${category.name}</option>
						</c:forEach>
					</select> <br />
					<hr />

				<spring:message code="report.select.criteria"/>:<br /> <select style="width: 100%;" multiple
						name="criteria">
						<c:forEach items="${criteria}" var="criterion">
							<option value="${criterion.id}">${criterion.title}</option>
						</c:forEach>
					</select> <br />
					<hr />
					* <spring:message code="report.note.students"/><br/>
					* <spring:message code="report.note.criteria"/><br/>
					<br/>
					<input type="hidden" name="projectId" value="${project.id}" />
					<input onclick="getProjectReport()" class="btn btn-primary pull-right" type="submit" value="<spring:message code="report.submit"/>"/>
					<br/>
					<br/>
				</form>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	function getProjectReport(){
		$('#project-report-modal').modal('hide');
		$('#project-form-report').submit();
	}
   	$('select').select2();
</script>
</sec:authorize>
<%@include file="footer.jsp" %>