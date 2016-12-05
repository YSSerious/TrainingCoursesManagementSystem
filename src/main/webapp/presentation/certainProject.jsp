<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="header.jsp" %>
<%--<%@include file="projectDataEditingModals.jsp" %>--%>
<%@include file="createProjectModal.jsp" %>
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
                    <div class="project-controls">
                        <c:if test="${isEmpty == true}">
                            <button class="btn btn-primary pull-right" id="delete-project"><b>Delete project</b></button>
                            <script src="<c:url value="/presentation/resources/js/delete_project.js"/>" type="text/javascript" defer="defer"></script>
                        </c:if>
                        <button class="btn btn-primary pull-right" data-toggle="modal" data-target="#project-report-modal">
                            <b><spring:message code="project.generate.report"/></b></button>
                    </div>
                </sec:authorize>
                <div class="row">
                    <div class="col-md-4 editable-group project-dates">
                        <table class="table-bordered">
                            <tr class="editable-wrapper" id="project-startdate">
                                <td>
                                    <h4 class=""><spring:message code="project.startDate"/></h4>
                                </td>
                                <td class="editable-label editable">
                                    <h5>${project.startDate}</h5>
                                </td>
                            </tr>
                            <tr class="editable-wrapper" id="project-finishdate">
                                <td>
                                    <h4 class=""><spring:message code="project.finishDate"/></h4>
                                </td>
                                <td class="editable-label editable">
                                    <h5>${project.finishDate}</h5>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="col-md-6 col-sm-offset-2 editable-wrapper" id="project-description">
                        <div class="description-title editable-label">
                            <h4 class="desc-label" style="margin-top: 0px;">
                                <spring:message code="project.description"/>
                            </h4>
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
                <spring:message code="project.groups"/>
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
                            <b><spring:message code="project.groups.addMeeting"/></b>
                        </button>
                        <button type="button" class="btn btn-default btn-sm pull-right right" id="create-group">
                            <b><spring:message code="project.groups.add"/></b>
                        </button>
                    </sec:authorize>
                    <div class="clearfix"></div>
                </div>
                <%--<%@include file="createGroup.jsp"%>--%>
                <div id="collapse-group" class="panel-collapse collapse clearfix">
                    <div class="panel-body">
                        <table class="table table-hover">
                            <tr>
                                <th><spring:message code="project.groups.name"/></th>
                                <th><spring:message code="project.groups.studentsAmount"/></th>
                                <th><spring:message code="project.groups.upcomingMeeting"/></th>
                                <th></th>
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
                                                <spring:message code="project.groups.noUpcomingMeetings"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <button class="btn btn-collapse edit-group glyphicon-button">
                                            <span class="glyphicon glyphicon-edit"></span>
                                        </button>
                                    </td>
                                    <td>
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
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div role="button" data-toggle="collapse" data-target="#collapseIn"
                         class="arrow col-md-1" onclick="changeSpan(this)">
                        <span id="spanId" class="pull-left glyphicon glyphicon-chevron-down"
                              style="margin-top:5px;"></span>
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
                                    <button class="btn rmv-cr-btn col-md-1"
                                            data-button='{"id":"${criterion.id}","title": "${criterion.title}"}'>
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </button>
                                </c:if>
                            </li>
                        </c:forEach>
                    </ul>
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
                <div role="button" data-toggle="collapse" data-target="#att-collapse" class="arrow col-md-1"
                     onclick="changeSpan(this)">
                    <span id="spanIId" class="pull-left glyphicon glyphicon-chevron-down"
                          style="margin-top:5px;"></span>
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
                            <div class="btn rmv-btn col-md-1" role='button'
                                 data-button='{"id_attachment": "${attachment.id}"}'>
                                <span class="glyphicon glyphicon-remove"></span>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <hr>
        </div>

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

                    </div>
                    <div class="modal-body">
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
                    <div class="modal-footer">

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
                    <form id="newMeetingFormId" class="form-horizontal">
                        <div class="form-group" id="formMeetingNameId">
                            <label for="inputName" class="control-label col-sm-2">Meeting Name</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="inputName">
                            </div>
                            <h5 id="inputNameErrorId" class="text-danger hidden"></h5>
                        </div>
                        <div class="form-group" id="formMeetingPlaceId">
                            <label for="inputPlace" class="control-label col-sm-2">Place</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="inputPlace">
                            </div>
                            <h5 id="inputPlaceErrorId" class="text-danger hidden"></h5>
                        </div>
                        <div class="form-group" id="formMeetingDateId">
                            <label for="inputDate" class="control-label col-sm-2">Date/Time</label>
                            <div class="col-sm-5">
                                <input type="datetime-local" class="form-control" id="inputDate">
                            </div>
                            <h5 id="inputDateErrorId" class="text-danger hidden"></h5>
                        </div>
                        <label class="col-sm-2 text-right">Criteria</label>
                        <input type='checkbox' id="checkAllCriteriaId">
                        <div class="form-group col-sm-offset-2">
                            <div class="col-sm-12" id="CriteriaCheckBoxId">
                            </div>
                        </div>
                        <label class="col-sm-2 text-right">Groups</label>
                        <input type='checkbox' id="checkAllGroupsId">
                        <div class="form-group">
                            <div class="col-sm-12" id="GroupsCheckBoxId">
                            </div>
                        </div>
                        <br>
                    </form>
                </div>
                <div class="modal-footer">
                    <button id="saveMeeting" type="submit" class="btn btn-primary">Save
                    </button>
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
			
			<form:input type="hidden" path="projectId" value="${project.id }"/>
						
            <div class="row">
                <div class="form-project col-md-12">
                <div class="col-md-12">
                    <b>Upload a file:</b>
                    <br/>
                    <div style="color:red;" id="file-error">
                            
                    </div>
                    <form:input type="file" path="file" name="file" id="upload-file" class="form-control"/>
                    
                    <br/>
                    <b>Name:</b>
                    <div style="color:red;" id="name-error">
                            
                    </div>
                    <form:input type="text" path="name" id="upload-name" class="form-control"/>
  
                    <div style="color:red;" id="project-error">
                            
                    </div>
                    </div>
                </div>
            </div>
     
            <div class="row">
                <div class="text-center">
                	<br/>
                    <button id="uploadAttachmentButton" type="button" onclick="uploadProjectAttachment()" class="btn btn-primary">Upload</button>
                </div>
            </div>
        </form:form>
				</div>
        </div>
    </div>
</div>	

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

<script>

$(document).ready(function () {
	bindRemove();
});

function uploadProjectAttachment(){
	$('#uploadAttachmentButton').prop('disabled', true);

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
                    	var newAttach = getAttachDiv(response.messages['name'], response.messages['id']);
                    	$('#attachment-group').html(newAttach+$('#attachment-group').html());
                    	$('.rmv-btn').unbind('click');
                    	
                    	$('#file-error').html('');
                    	$('#name-error').html('');
                    	$('#project-error').html('');
                    	
                    	$('#addProjectAttachmentModal').modal('hide');
                    	
                    	bindRemove();
                    	
                    	$('#upload-file').prop('value', '');
                    	$('#upload-name').prop('value', '');
                    	
                    	$('#uploadAttachmentButton').prop('disabled', false);
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
                    	$('#uploadAttachmentButton').prop('disabled', false);
                    	break;
                }
            }
        }

	});
}

function bindRemove(){
	$('.rmv-btn').click(function () {

		var div = $(this);
		var id = div.data('button').id_attachment;
		
		div.unbind('click');
		
		function removeAttach(){
        	div.parent().remove();
		}
		
        $.ajax({
            url: "/removeProjectAttachment",
            type: "POST",
            data: {"id_attachment": id},
            success: function(){ 
                removeAttach();
            },
            error: function(){
            	alert('Try again later!');
            }
			
        });
    });
	
}

function getAttachDiv(name, id){
    var div = '<li style="background-color:#EDF8FC;" class="list-group-item clearfix">';
    div += '<a href="/projectAttachment/'+id+'" class="col-md-2">'+name+' </a>';
    div += '<div class="btn rmv-btn col-md-1" role="button" data-button=\'{"id_attachment": "'+id+'"}\'>';
    div += '<span class="glyphicon glyphicon-remove"></span>';
    div += '</div>';
    div += '</li>';
    
    return div;
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