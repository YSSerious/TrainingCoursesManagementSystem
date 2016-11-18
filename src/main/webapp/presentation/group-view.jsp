<%@include file="header.jsp" %>
<script src="<c:url value="/presentation/resources/js/group.js"/>"
        type="text/javascript">

</script>
<jsp:useBean id="type" class="java.lang.String"/>
<jsp:useBean id="border" class="java.lang.String"/>
<%
    type = "#fff";
    border = "#D3D3D3";
%>
<h1>Project: ${projectName}</h1>
<h2>Group:${group.name}</h2>
<br/>

<div class="meetings_template">
    <h2>Meetings</h2>

    <div class="panel panel-primary"
         style="background-color:<%=type%>;border: 1px solid <%=border%>; border-radius: 7px;">
        <div class="panel-heading clearfix">
            <button type="button" class="btn btn-default btn-xs pull-right">Edit</button>

            <div data-toggle="collapse" data-target="#collapseMeetings"
                 class="arrow col-md-1" style="color:black">
                <span class="glyphicon glyphicon-chevron-down"></span>
            </div>
        </div>


        <div id="collapseMeetings" class="panel-collapse collapse">
            <ul class="list-group">

                <c:forEach items="${meetings}" var="meeting">
                <li class="list-group-item">${meeting.name}<span
                        style='padding-left: 10px;'> </span> ${meeting.time} <span
                        style='padding-left: 10px;'> </span> ${meeting.place} <br/>
                    </c:forEach>

            </ul>
        </div>

    </div>
    <br/>
</div>

<div class="students_template">
    <h2>Students</h2>
    <div class="panel panel-primary"
         style="background-color:<%=type%>;border: 1px solid <%=border%>; border-radius: 7px;">
        <div class="panel-heading clearfix">
            <button type="button" class="btn btn-default btn-xs pull-right">Add</button>
            <div data-toggle="collapse" data-target="#collapseStudents"
                 class="arrow col-md-1" style="color:black">
                <span class="glyphicon glyphicon-chevron-down"></span>
            </div>
        </div>
        <div id="collapseStudents" class="panel-collapse collapse">
            <ul class="list-group">
                <c:forEach items="${students}" var="student">

                    <li
                            class="list-group-item group-${group.id}-${student.id} clearfix">

                            ${student.firstName} <span style='padding-left: 10px;'> </span>
                            ${student.lastName}
                        <!-- 	<button id="${group.id}-${student.id}"
							class="btn  delete-student pull-right">
							<span class="glyphicon glyphicon-remove"></span>
						</button>
					-->
                        <div id="${group.id}-${student.id}" class="btn rmv-cr-btn col-md-1 pull-right delete-student"
                             type='button'>
                            <span class="glyphicon glyphicon-remove delete"></span>
                        </div>

                        <br/>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <br/>
</div>

<div id="studentDeleteError" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove"></span>
                </button>
                <h4 class="modal-title">Student has reviews, removing is
                    forbidden</h4>
            </div>
        </div>
    </div>
</div>

<div class="mentors_template">
    <h2>Mentors</h2>
    <div class="panel panel-primary"
         style="background-color:<%=type%>;border: 1px solid <%=border%>; border-radius: 7px;">
        <div class="panel-heading clearfix">
            <button type="button" class="btn btn-default btn-xs pull-right">Add</button>
            <div data-toggle="collapse" data-target="#collapseMentors"
                 class="arrow col-md-1" style="color:black">
                <span class="glyphicon glyphicon-chevron-down"></span>
            </div>
        </div>

        <div id="collapseMentors" class="panel-collapse collapse">
            <ul class="list-group">
                <c:forEach items="${mentors}" var="mentor">
                    <li class="list-group-item group-${group.id}-${mentor.id} clearfix">

                            ${mentor.firstName} <span style='padding-left: 10px;'> </span>
                            ${mentor.lastName}
                        <!-- <button id="${group.id}-${mentor.id}"
							class="btn delete-mentor pull-right"><span class="glyphicon glyphicon-remove"></span>
						</button> -->
                        <div id="${group.id}-${mentor.id}" class="btn rmv-cr-btn col-md-1 pull-right delete-mentor"
                             type='button'>
                            <span class="glyphicon glyphicon-remove delete"></span>
                        </div>
                        <br/>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <br/>
</div>

<!-- <div id="attachment-title"><h2>Attachments</h2></div>-->


<div class="modal fade" id="addGroupAttachmentModal" role="dialog">
    <div class="modal-dialog">

        <div class="modal-content">
            <div class="modal-body" style="padding: 40px 50px;">
                <form role="form">
                    <div class="form-group">
                        <label for="groupAttachmentName"> Attachment name: </label><br/>
                        <input type=text class="form-control" id="groupAttachmentName"
                               placeholder="Enter attachment name" required="required">
                        <label for="groupAttachment"> Attachment: </label><br/> <input
                            type=text class="form-control" id="groupAttachment"
                            placeholder="Enter attachment" required="required">

                    </div>
                    <button type="submit"
                            class="btn btn-default btn-success pull-center"
                            id="addAttachmentSubmitButton">
                        <span class="glyphicon glyphicon-off"></span>Send
                    </button>
                </form>
            </div>
            <div class="modal-footer">

                <button type="button" class="btn btn-default btn-lg"
                        data-dismiss="modal" id="cancelButton">
                    <span class="glyphicon glyphicon-remove"></span> Cancel
                </button>
            </div>
        </div>

    </div>
</div>
<!-- -->
<div class="modal fade" id="editGroupAttachmentModal" role="dialog">
    <div class="modal-dialog">

        <div class="modal-content">
            <div class="modal-body" style="padding: 40px 50px;">
                <form role="form">
                    <div class="form-group">
                        <label for="groupAttachmentName"> Attachment name: </label><br/>
                        <input type=text class="form-control" id="editGroupAttachmentName"
                               placeholder="Enter attachment name" required="required">
                        <label for="editGroupAttachment"> Attachment: </label><br/> <input
                            type=text class="form-control" id="groupAttachment"
                            placeholder="Enter attachment" required="required">

                    </div>
                    <button type="submit"
                            class="btn btn-default btn-success pull-center"
                            id="editAttachmentSubmitButton">
                        <span class="glyphicon glyphicon-off"></span>Send
                    </button>
                </form>
            </div>
            <div class="modal-footer">

                <button type="button" class="btn btn-default btn-lg"
                        data-dismiss="modal" id="editCancelButton">
                    <span class="glyphicon glyphicon-remove"></span> Cancel
                </button>
            </div>
        </div>

    </div>
</div>

<!-- -->
<div class="container attachment-group">
    <div class="row">
        <div class="col-md-12">
            <h2>Attachments</h2>
            <div class="panel-group" id="panelGroupAttachment">
                <div class="panel panel-default">
                    <div class="panel-heading attachment-group">
                        <h4 class="panel-title row">
                            <div data-toggle="collapse" data-target="#collapseIn"
                                 class="arrow col-md-1">
                                <span class="glyphicon glyphicon-chevron-down"></span>
                            </div>
                            <div role="button" class="btn btn-default add-attachment-btn btn-sm pull-right"
                                 id="addGroupAttachmentButton" data-toggle="modal"
                                 data-target="#addGroupAttachmentModal">
                                <b>Add Attachment</b>
                            </div>
                        </h4>
                    </div>
                    <div id="collapseIn" class="panel-collapse collapse">
                        <c:forEach items="${attachments}" var="attachment">
                            <div class="panel-body row" id="attachmentId-${attachment.id}">
                                <div class="col-md-10">${attachment.name}</div>

                                <div class="btn rmv-cr-btn col-md-1 pull-right" type='button'
                                     data-button='{"id":"d${attachment.id}","title": "${attachment.name}"}'>
                                    <span class="glyphicon glyphicon-remove delete"></span>
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


<%@include file="footer.jsp" %>