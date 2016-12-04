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
                    <input id="can-edit" type="hidden" value="true"/>
                </sec:authorize>
                <div class="editable-wrapper" id="project-name">
                    <!--                    <h3><b>Project:</b></h3>-->
                    <div class="editable-label editable">
                        <h3 style="font-size: 3rem">${project.name}</h3>
                    </div>
                </div>
                <sec:authorize access="hasAnyRole('ADMIN', 'HR')">
                    <button class="btn btn-primary pull-right" data-toggle="modal" data-target="#project-report-modal">
                        <b><spring:message code="project.generate.report"/></b></button>
                </sec:authorize>
                <div class="row">
                    <div class="col-md-6 editable-group">
                        <div class="panel panel-default panel-horizontal top-panel editable-wrapper"
                             id="project-startdate">
                            <div class="panel-heading">
                                <h3 class="panel-title">Start date</h3>
                            </div>
                            <div class="panel-body editable-label editable">
                                <h5>${project.startDate}</h5>
                            </div>
                        </div>
                        <div class="panel panel-default panel-horizontal bottom-panel editable-wrapper"
                             id="project-finishdate">
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
                    <div class="col-md-1">
                        <span id="spanIdd" class="glyphicon glyphicon-chevron-down"></span>
                    </div>
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
                                    <td>${group.studentsAmount}</td>
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
                        <div role="button" data-toggle="collapse" data-target="#collapseIn"
                             class="arrow col-md-1" onclick="changeSpan(this)">
                            <span id="spanId" class="pull-left glyphicon glyphicon-chevron-down"></span>
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
                    <span id="spanIId" class="pull-left glyphicon glyphicon-chevron-down" style="margin-top:5px;"></span>
                </div>
                <div role="button" class="btn btn-default btn-sm pull-right" id="add-att-btn" 
                	data-toggle="modal" data-target="#addProjectAttachmentModal"><b>Add Attach</b>
                </div>
            </div>
	        <div class="panel-collapse collapse clearfix" id="att-collapse">
		        <ul class="list-group " id="attachment-group">
		            <c:forEach items="${attachments}" var="attachment">
		                <li class="list-group-item clearfix">
		                    <a href="/projectAttachment/${ attachment.id}" class="col-md-2">${attachment.name } </a>
		                    <div class="btn rmv-btn col-md-1" role='button' data-button='{"id_attachment": "${attachment.id}"}'>
		                        <span class="glyphicon glyphicon-remove"></span>
		                    </div>
		                </li>
		            </c:forEach>
		        </ul>
	        <div>
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
    <!-- start ErrorModal modal -->
    <div id="ErrorModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button class="close" type="button" data-dismiss="modal"><span
                            class="glyphicon glyphicon-remove"></span></button>
                    <h4 class="modal-title" id=projectErrorModal></h4>
                    <button data-dismiss="modal" class="btn btn-link">Close</button>
                </div>
            </div>
        </div>
    </div>
    <!-- finish ErrorModal modal -->
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
                        <button id="saveMeeting" type="submit" class="btn btn-primary" data-dismiss="modal">Save
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- finish create Meeting modal -->
<div id="addProjectAttachmentModal" class="modal fade" tabindex="-1" role="dialog" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal"><span
                        class="glyphicon glyphicon-remove"></span></button>
                <h4 class="modal-title">New Attachment</h4>
            </div>
            <div class="modal-body">
			
			
			<form:form id="addAttachmentFormSend" method="POST" action="/addProjectAttachment" modelAttribute="projectAttachmentForm"
						enctype="multipart/form-data" class="form-horizontal">
			
			<form:input type="hidden" path="projectId" value="${project.id }" />
						
            <div class="row">
                <div class="form-project col-md-12">
                <div class="col-md-12">
                    <b>Upload a file:</b>
                    <br/>
                    <div style="color:red;" id="file-error">
                            
                    </div>
                    <form:input type="file" path="file" name="file" id="file" class="form-control"/>
                    
                    <br/>
                    <b>Name:</b>
                    <div style="color:red;" id="name-error">
                            
                    </div>
                    <form:input type="text" path="name" id="name" class="form-control"/>
  
                    <div style="color:red;" id="project-error">
                            
                    </div>
                    </div>
                </div>
            </div>
     
            <div class="row">
                <div class="text-center">
                	<br/>
                    <button type=button onclick="uploadProjectAttachment()" class="btn btn-primary">Upload</button>
                </div>
            </div>
        </form:form>
				</div>
        </div>
    </div>
</div>	


<script>
    $(document).ready(function () {

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

                        <spring:message code="report.select.students"/>:<br/> <select
                            style="width: 100%;" multiple name="students">
                        <c:forEach items="${students}" var="student">
                            <option value="${student.id}">${student.firstName}
                                    ${student.lastName}</option>
                        </c:forEach>
                    </select> <br/>
                        <hr/>

                        <spring:message code="report.select.categories"/>:<br/> <select style="width: 100%;" multiple
                                                                                        name="categories">
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}">${category.name}</option>
                        </c:forEach>
                    </select> <br/>
                        <hr/>

<script>
function uploadProjectAttachment(){
		
	var formData = new FormData($("#addAttachmentFormSend")[0]);

	$.ajax({
    	type:"post",
    	data:formData,
    	url:"/addProjectAttachment",
    	contentType: false,
    	processData: false,
    	async: false,
    	statusCode: {
            200: function (response) {
                console.log(response);
                switch (response.code) {
                    case '200':
                    	location.reload();
                        break;
                    case '204':
                    	$('#file-error').html('');
                    	$('#name-error').html('');
                    	$('#project-error').html('');
                    	$.each(response.messages, function( index, value ) {
                    		  if(index=='file'){
                    			  $('#file-error').html(value);
                    		  }else if(index='name'){
                    			  $('#name-error').html(value);
                    		  }else if(index='project'){
                    			  $('#project-error').html(value);
                    		  }
                    		});
                    	break;
                }
            }
        }

	});

}
</script>

<script type="text/javascript">
	function getProjectReport(){
		$('#project-report-modal').modal('hide');
		$('#project-form-report').submit();
	}
   	$('select').select2();
</script>

</sec:authorize>
<%@include file="footer.jsp" %>