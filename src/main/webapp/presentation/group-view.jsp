<%@include file="header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="<c:url value="/presentation/resources/js/group.js"/>"
        type="text/javascript" defer="defer">
    var groupId = ${group.id};
</script>
<jsp:useBean id="type" class="java.lang.String"/>
<jsp:useBean id="border" class="java.lang.String"/>
<%
    type = "#fff";
    border = "#D3D3D3";
%>

<div style="padding-top:-20px;" class="container certain-project">
    <div class="row">
        <div class="col-md-12">
            <div class="page-header">
                <div class="row">
                    <div class="col-md-6">
                        <h3 style="font-size: 3rem">${group.name}</h3>
                    </div>
                    <div class="col-md-6" style="padding-top:20px;">
                        <sec:authorize access="hasAnyRole('ADMIN', 'HR')">
                            <button class="btn btn-primary pull-right" data-toggle="modal"
                                    data-target="#group-report-modal">
                                <b><spring:message code="group.generate.report"/></b>
                            </button>
                        </sec:authorize>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-md-6 editable-group">
                        <div class="panel panel-default panel-horizontal panel editable-wrapper"
                             id="project-finishdate">
                            <div class="panel-heading">
                                <h3 class="panel-title">Project</h3>
                            </div>
                            <div class="panel-body editable-label editable">
                                <h5><a href="/certainProject/${project.id}">${project.name}</a></h5>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 editable-wrapper" id="project-description">
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <h2>
                <spring:message code="group.meetings"/>
            </h2>
            <div class="panel-group" id="panelGroupId">
                <div class="panel panel-primary"
                     style="background-color:<%=type%>;border: 1px solid <%=border%>; border-radius: 7px;">
                    <div class="panel-heading clearfix">

                        <div data-toggle="collapse" data-target="#collapseMeetings"
                             class="arrow col-md-1" style="color: black" onclick="changeSpan(this)">
                            <span id="spanId" class="pull-left glyphicon glyphicon-chevron-down"
                                  style="margin-top:5px;"></span>

                        </div>
                    </div>
                    <div id="collapseMeetings" class="panel-collapse collapse clearfix">
                        <ul class="list-group">
                            <c:forEach items="${meetings}" var="meeting">
                                <li class="list-group-item  clearfix"
                                    id="meetingId-${meeting.id}">
                                    <a href="/meeting/${meeting.id}" class="col-md-2"
                                       id="editMeetingNameId-${meeting.id}">${meeting.name}</a>
                                    <div class="col-md-2" id="editMeetingDateId-${meeting.id}"><fmt:formatDate
                                            pattern="yyyy-MM-dd, HH:mm"
                                            value="${meeting.time}"/></div>
                                    <div class="col-md-4" id="editMeetingPlaceId-${meeting.id}">${meeting.place}</div>

                                    <sec:authorize access="hasRole('ADMIN')">
                                        <c:if test="${!meeting.reviewed}">
                                            <div class="btn rmv-cr-btn col-md-1 pull-right" type='button'
                                                 data-toggle="modal" data-target="#deleteMeetingModal"
                                                 onclick="setMeeting('${meeting.id}','${meeting.name}','${meeting.place}','${meeting.time}')">
                                                <span class="glyphicon glyphicon-remove"></span>
                                            </div>
                                        </c:if>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ADMIN')">
                                    <div class="btn rmv-cr-btn col-md-1 col-md-offset-2 "
                                         type='button' data-toggle="modal"
                                         data-target="#editMeetingModal"
                                         onclick="setMeeting('${meeting.id}','${meeting.name}','${meeting.place}','${meeting.time}')">
                                        <span class="glyphicon glyphicon-edit"></span>
                                    </div>
                                    </sec:authorize>
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
                <spring:message code="group.students"/>
            </h2>
            <div class="panel group"
                 style="background-color:<%=type%>;border: 1px solid <%=border%>; border-radius: 7px;">
                <div class="panel-heading clearfix">
                <sec:authorize access="hasAnyRole('ADMIN')">
                    <button type="button" class="btn btn-default btn-sm pull-right" id="showAvailableStudents"
                            data-toggle="modal"
                            data-target="#showAvailableStudentsModal">
                            
                        <spring:message code="group.button.add"/>
                    </button>
                    </sec:authorize>
                    <div data-toggle="collapse" data-target="#collapseStudents"
                         class="arrow col-md-1" style="color: black" onclick="changeSpan(this)">
                        <span class="pull-left glyphicon glyphicon-chevron-down" style="margin-top:5px;"></span>
                    </div>
                </div>
                <div id="collapseStudents" class="panel-collapse collapse">
                    <ul id="students-ul" class="list-group">
                        <c:forEach items="${students}" var="studentMap">

                            <li class="list-group-item group-${group.id}-${studentMap.key.student.id} clearfix">
                                <a href="/users/${studentMap.key.student.id}">
                                <!--  -->${studentMap.key.student.firstName}</a>
                                <span style='padding-left: 10px;'> </span>
                                    ${studentMap.key.student.lastName} <span
                                    style='padding-left: 10px;'> </span>

                                <div id="${group.id}-${studentMap.key.student.id}"
                                     class="btn rmv-cr-btn col-md-1 pull-right delete-student"
                                     type='button'>
                                     
                                    <sec:authorize access="hasAnyRole('ADMIN')">
                                    	<span class="glyphicon glyphicon-remove delete"></span>
                                	</sec:authorize>
                                </div>
                                <br/>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>


    <sec:authorize access="hasAnyRole('ADMIN')">
    <div id="showAvailableStudentsModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button class="close" type="button" data-dismiss="modal"><span
                            class="glyphicon glyphicon-remove"></span></button>
                    <h4 class="modal-title">Add student</h4>
                    <hr>
                    <div class="row">
                        <input type="text" id="search" placeholder="type search" class="col-md-offset-4">
                    </div>
                    <table id="studentsTable" class="table table-condensed table-hover table-responsive">
                        <thead class="table-head">
                        <tr>
                            <th><b>Name</b></th>
                            <th><b>Add</b></th>
                        </tr>
                        </thead>
                        <tbody id="studentsTableId">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    </sec:authorize>
    <!-- finish modal -->


    <div class="row">
        <div class="col-md-12">
            <h2>
                <spring:message code="group.attendance"/>
            </h2>
            <div class="panel panel-primary"
                 style="background-color:<%=type%>;border: 1px solid <%=border%>; border-radius: 7px;">
                <div class="panel-heading clearfix">

                    <div data-toggle="collapse" data-target="#collapseAttendance" class="arrow col-md-1"
                         style="color: black"
                         onclick="changeSpan(this)">
                        <span class="pull-left glyphicon glyphicon-chevron-down" style="margin-top:5px;"></span>
                    </div>
                </div>


                <div id="collapseAttendance" class="panel-collapse collapse">
                    <div class="row" style="margin-left:0px;margin-top:10px;margin-bottom:10px;margin-right:0px;">
                        <div class="col-md-10">
                            <select style="width:100%;" multiple class="students-select">
                                <c:forEach items="${attendance.attendance}" var="value">
                                    <option value="${value.id}">${value.fullName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <button style="min-width:100%;" class="btn btn-default pull-right"
                                    onclick="filterAttendance()">
                                <spring:message code="group.attendance.filter"/></button>
                        </div>
                    </div>
                    <div style="padding-bottom:0px; margin-bottom:0px;" class="table-responsive">
                        <table style="padding-bottom:0px; margin-bottom:0px;" id="attendance-table"
                               class="table table-bordered user-table">
                            <tr>
                                <th>#</th>

                                <c:forEach items="${attendance.meetings}" var="value">
                                    <th align="center"><a href="/meeting/${value.id}"><fmt:formatDate
                                            value="${value.time}"
                                            pattern="yyyy-MM-dd"/></a>
                                    </th>
                                </c:forEach>
                            </tr>
                            <c:forEach items="${attendance.attendance}" var="value">
                                <tr id="us-${value.id}">
                                    <td><b><a href="/users/${value.id}">${value.fullName}</a></b></td>

                                    <c:forEach items="${value.attendance}" var="state">
                                        <c:if test="${state eq 'E'}">
                                            <td title="<spring:message code="attendance.evaluated"/>" style="background-color: #93C54B"></td>
                                        </c:if>
                                        <c:if test="${state eq 'A'}">
                                            <td title="<spring:message code="attendance.absent"/>" style="background-color: #F4FC5A"></td>
                                        </c:if>
                                        <c:if test="${empty state}">
                                            <td title="<spring:message code="attendance.upcoming"/>" style="background-color: #5ABFFC"></td>
                                        </c:if>
                                        <c:if test="${state eq 'L'}">
                                            <td title="<spring:message code="attendance.leaved"/>" style="background-color: #FC655A"></td>
                                        </c:if>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </table>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id="studentDeleteError" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button class="close" type="button" data-dismiss="modal">
                        <span class="glyphicon glyphicon-remove"></span>
                    </button>
                    <h4 class="modal-title">
                        <spring:message code="group.student.delete.error"/>
                    </h4>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <h2>
                <spring:message code="group.mentors"/>
            </h2>
            <div class="panel panel-primary"
                 style="background-color:<%=type%>;border: 1px solid <%=border%>; border-radius: 7px;">
                <div class="panel-heading clearfix">
                    <sec:authorize access="hasAnyRole('ADMIN')">
                    <button type="button" class="btn btn-default btn-sm pull-right" id="showAvailableMentors"
                            data-toggle="modal"
                            data-target="#showAvailableMentorsModal">
                        <spring:message code="group.button.add"/>
                    </button>
                    </sec:authorize>
                    <div data-toggle="collapse" data-target="#collapseMentors"
                         class="arrow col-md-1" style="color: black" onclick="changeSpan(this)">
                        <span class="pull-left glyphicon glyphicon-chevron-down" style="margin-top:5px;"></span>
                    </div>
                </div>

                <div id="collapseMentors" class="panel-collapse collapse">
                    <ul class="list-group">
                        <c:forEach items="${mentors}" var="mentor">
                            <li class="list-group-item group-${group.id}-${mentor.id} clearfix">

                                <a href="/users/${mentor.id}">${mentor.firstName}</a> <span
                                    style='padding-left: 10px;'> </span> ${mentor.lastName}
                                <!-- <button id="${group.id}-${mentor.id}"
							class="btn delete-mentor pull-right"><span class="glyphicon glyphicon-remove"></span>
						</button> -->
                                <div id="${group.id}-${mentor.id}"
                                     class="btn rmv-cr-btn col-md-1 pull-right delete-mentor"
                                     type='button'>
                                    <sec:authorize access="hasAnyRole('ADMIN')">
                                    <span class="glyphicon glyphicon-remove delete"></span>
                                    </sec:authorize>
                                </div>
                                <br/>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <!-- start -->
    <sec:authorize access="hasAnyRole('ADMIN')">
    <div id="showAvailableMentorsModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button class="close" type="button" data-dismiss="modal"><span
                            class="glyphicon glyphicon-remove"></span></button>
                    <h4 class="modal-title">Add mentor</h4>
                    <hr>
                    <div class="row">
                        <input type="text" id="search_mentor" placeholder="type search" class="col-md-offset-4">
                    </div>
                    <table id="mentorsTable" class="table table-condensed table-hover table-responsive">
                        <thead class="table-head">
                        <tr>
                            <th><b>Name</b></th>
                            <th><b>Add</b></th>
                        </tr>
                        </thead>
                        <tbody id="mentorsTableId">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    </sec:authorize>
    <!-- finish modal -->


    <div class="row">
        <div class="col-md-12">
            <h2>
                <spring:message code="group.attachments"/>
            </h2>
            <div class="panel panel-default">
                <div class="panel-heading clearfix">
                    <button type="button" class="btn btn-default btn-sm pull-right"
                            data-target="#addGroupAttachmentModal" data-toggle="modal">
                        <spring:message code="group.button.add"/>
                    </button>
                    <div data-toggle="collapse" data-target="#collapseAttachment"
                         class="arrow col-md-1" style="color: black"
                         onclick="changeSpan(this)">
                        <span class="pull-left glyphicon glyphicon-chevron-down" style="margin-top:5px;"></span>
                    </div>
                </div>
                <div id="collapseAttachment" class="panel-collapse collapse">
                    <ul class="list-group " id="attachment-group">
                        <c:forEach items="${attachments}" var="attachment">
                            <li class="list-group-item clearfix">
                                <a href="/groups/groupAttachment/${ attachment.id}"
                                   class="col-md-2">${attachment.name }
                                </a>
                                <div class="btn rmv-btn col-md-1" role='button'
                                     data-button='{"id_attachment": "${attachment.id}"}'>
                                    <span class="glyphicon glyphicon-remove"></span>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <sec:authorize access="hasAnyRole('ADMIN', 'HR')">
    <div id="group-report-modal" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">
                        <spring:message code="group.generate.report"/>
                    </h4>
                </div>
                <div id="group-report-back" class="modal-body">
                    <form id="group-form-report" action="/groupReport.xls">
                        <spring:message code="report.select.students"/>
                        :<br/> <select style="width: 100%;" multiple name="students">
                        <c:forEach items="${selectStudents}" var="student">
                            <option value="${student.id}">${student.firstName}
                                    ${student.lastName}</option>
                        </c:forEach>
                    </select> <br/>
                        <hr/>

                        <spring:message code="report.select.categories"/>
                        :<br/> <select style="width: 100%;" multiple name="categories">
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}">${category.name}</option>
                        </c:forEach>
                    </select> <br/>
                        <hr/>

                        <spring:message code="report.select.criteria"/>
                        :<br/> <select style="width: 100%;" multiple name="criteria">
                        <c:forEach items="${criteria}" var="criterion">
                            <option value="${criterion.id}">${criterion.title}</option>
                        </c:forEach>
                    </select> <br/>
                        <hr/>
                        *
                        <spring:message code="report.note.students"/>
                        <br/> *
                        <spring:message code="report.note.criteria"/>
                        <br/> <br/> <input type="hidden" name="groupId"
                                           value="${group.id}"/> <input onclick="getGroupReport()"
                                                                        class="btn btn-primary pull-right"
                                                                        type="submit"
                                                                        value="<spring:message code="report.submit"/>"/>
                        <br/> <br/>
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
                    <form id="editMeetingFormId" class="form-horizontal">
                        <div class="form-group" id="form-meeting-name-id">
                            <label class="control-label col-sm-2" for="editMeetingName">Meeting Name</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="editMeetingName">
                            </div>
                            <h5 id="meetingNameErrorId" class="text-danger hidden"></h5>
                        </div>
                        <div class="form-group" id="form-meeting-place-id">
                            <label class="control-label col-sm-2" for="editMeetingPlace">Place</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="editMeetingPlace">
                            </div>
                            <h5 id="meetingPlaceErrorId" class="text-danger hidden"></h5>
                        </div>
                        <div class="form-group" id="form-meeting-date-id">
                            <label class="control-label col-sm-2" for="editMeetingDate">Date/Time</label>
                            <div class="col-sm-5">
                                <input type="datetime-local" class="form-control" id="editMeetingDate">
                            </div>
                            <h5 id="meetingDateErrorId" class="text-danger hidden"></h5>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button id="editMeetingButton" type="submit"
                            class="btn btn-primary">Save
                    </button>
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
                                class="btn btn-link">Yes
                        </button>
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
                        <h4 class="modal-title" id="meetingDeleteErrorModal"></h4>
                        <button data-dismiss="modal" class="btn btn-link">Close</button>
                    </div>
                </div>
            </div>
        </div>
        
        </sec:authorize>
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


                        <form:form id="addAttachmentFormSend" method="POST" action="/groups/addAttachment"
                                   modelAttribute="groupAttachmentForm"
                                   enctype="multipart/form-data" class="form-horizontal">

                            <form:input type="hidden" path="groupId" value="${groupId }"/>

                            <div class="row">
                                <div class="form-group col-md-12">
                                    <div class="col-md-12">
                                        <b>Upload a file:</b>
                                        <br/>
                                        <div style="color:red;" id="file-error">

                                        </div>
                                        <form:input type="file" id="upload-file" path="file" name="file"
                                                    class="form-control"/>

                                        <br/>
                                        <b>Name:</b>
                                        <div style="color:red;" id="name-error">

                                        </div>
                                        <form:input type="text" id="upload-name" path="name" class="form-control"/>

                                        <div style="color:red;" id="group-error">

                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="text-center">
                                    <button type=button onclick="uploadGroupAttachment()" class="btn btn-primary">
                                        Upload
                                    </button>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
        <!-- finish add attachment -->
        <script type="text/javascript">
            function getGroupReport() {
                $('#group-report-modal').modal('hide');
                $('#group-form-report').submit();
            }
            $('select').select2();
        </script>
        
    </div>
    <script>

        $(document).ready(function () {
            bindRemove();
        });

        function uploadGroupAttachment() {
            $('#uploadAttachmentButton').prop('disabled', true);

            var formData = new FormData($("#addAttachmentFormSend")[0]);

            $.ajax({
                type: "post",
                data: formData,
                url: "/groups/addAttachment",
                contentType: false,
                processData: false,
                async: false,
                statusCode: {
                    200: function (response) {
                        console.log(response);
                        switch (response.code) {
                            case '200':
                                var newAttach = getAttachDiv(response.messages['name'], response.messages['id']);
                                $('#attachment-group').html(newAttach + $('#attachment-group').html());
                                $('.rmv-btn').unbind('click');

                                $('#file-error').html('');
                                $('#name-error').html('');
                                $('#group-error').html('');

                                $('#addGroupAttachmentModal').modal('hide');

                                bindRemove();

                                $('#upload-file').prop('value', '');
                                $('#upload-name').prop('value', '');

                                $('#uploadAttachmentButton').prop('disabled', false);
                                break;
                            case '204':
                                $('#file-error').html('');
                                $('#name-error').html('');
                                $('#group-error').html('');
                                $.each(response.messages, function (index, value) {
                                    if (index == 'file') {
                                        $('#file-error').html(value);
                                    } else if (index = 'name') {
                                        $('#name-error').html(value);
                                    } else if (index = 'group') {
                                        $('#group-error').html(value);
                                    }
                                });
                                $('#uploadAttachmentButton').prop('disabled', false);
                                break;
                        }
                    }
                }

            });
        }

        function bindRemove() {
            $('.rmv-btn').click(function () {

                var div = $(this);
                var id = div.data('button').id_attachment;

                div.unbind('click');

                function removeAttach() {
                    div.parent().remove();
                }

                $.ajax({
                    url: "/groups/deleteAttachment",
                    type: "POST",
                    data: {"id": id},
                    success: function () {
                        removeAttach();
                    },
                    error: function () {
                        alert('Try again later!');
                    }

                });
            });

        }

        function getAttachDiv(name, id) {
            var div = '<li style="background-color:#EDF8FC;" class="list-group-item clearfix">';
            div += '<a href="/groups/groupAttachment/' + id + '" class="col-md-2">' + name + ' </a>';
            div += '<div class="btn rmv-btn col-md-1" role="button" data-button=\'{"id_attachment": "' + id + '"}\'>';
            div += '<span class="glyphicon glyphicon-remove"></span>';
            div += '</div>';
            div += '</li>';

            return div;
        }

        var $table = $('#attendance-table');

        var $fixedColumn = $table.clone().insertBefore($table).addClass('fixed-column');
        $fixedColumn.removeClass('table-bordered');
        $fixedColumn.find('th:not(:first-child),td:not(:first-child)').remove();

        $fixedColumn.find('tr').each(function (i, elem) {
            $(this).height($table.find('tr:eq(' + i + ')').height());
            $(this).width($table.find('tr:eq(' + i + ')').width());
        });

        function filterAttendance() {
            var selectedStudents = [];

            $('.students-select :selected').each(function () {
                selectedStudents.push($(this).val());
            });

            $('#attendance-table > tbody  > tr').each(function (index, value) {
                if ($(value).css('display') == 'none')
                    $(value).show();
            });

            if (selectedStudents.length != 0) {

                $('#attendance-table > tbody  > tr').each(function (index, value) {
                    var id = value.id;
                    var keys = id.split("-");

                    if (keys.length == 2 && $.inArray(keys[1], selectedStudents) == -1) {
                        $(value).hide();
                    }

                });
            }
        }

        $('select').select2();
    </script>
<%@include file="footer.jsp" %>