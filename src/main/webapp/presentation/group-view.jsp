<%@include file="header.jsp"%>

<jsp:useBean id="type" class="java.lang.String" />
<jsp:useBean id="border" class="java.lang.String" />
<%
                  type="#fff";
                  border="#7FFF00";
				%>
<h1>Project: </h1>${projectName}
<h2>Group:</h2> ${group.name}
<br />

<div class="meetings_template">
	<label>Meetings</label>
	<button type="button" class="btn btn-default btn-xs">Edit</button>
	<br />
	<div
		style="background-color:<%=type%>;border: 2px solid <%=border %>; border-radius: 7px;">

		<c:forEach items="${meetings}" var="meeting">
			${meeting.name} <span style='padding-left:10px;'> </span> ${meeting.time} <span style='padding-left:10px;'> </span>
			${meeting.place}
			<br/> 
		</c:forEach>
	</div>
	<br />
</div>

<div class="students_template">
	<label>Students</label>
	<button type="button" class="btn btn-default btn-xs">Add</button>
	<button type="button" class="btn btn-default btn-xs">Remove</button>
	<br />
	<div
		style="background-color:<%=type%>;border: 2px solid <%=border %>; border-radius: 7px;">
		<!--Data from DB to be inserted here -->
		<c:forEach items="${students}" var="student">
			${student.firstName} <span style='padding-left:10px;'> </span> ${student.lastName}
			<br/> 
		</c:forEach>
	</div>
	<br />
</div>

<div class="mentors_template">
	<label>Mentors</label>
	<button type="button" class="btn btn-default btn-xs">Add</button>
	<button type="button" class="btn btn-default btn-xs">Remove</button>
	<br />
	<div
		style="background-color:<%=type%>;border: 2px solid <%=border %>; border-radius: 7px;">
		<!--Data from DB to be inserted here -->
		<c:forEach items="${mentors}" var="mentor">
			${mentor.firstName} <span style='padding-left:10px;'> </span> ${mentor.lastName}
			<br/> 
		</c:forEach>
		
	</div>
	<br />
</div>

<div class="attachments_template">
	<label>Attachments</label><br/>
	<label for="groupAttachmentName"> Attachment name: </label><br/>
	<input type=text class="form-control" id="groupAttachmentName" placeholder="Enter attachment name" >  
	<label for="groupAttachment"> Attachment: </label><br/>
	<input type=text class="form-control" id="groupAttachment" placeholder="Enter attachment" >  
	<button type="button" class="btn btn-default btn-xs" value="addAttachment" id="addGroupAttachmentButton">Upload</button>
	<button type="button" class="btn btn-default btn-xs">Edit</button>
	<br />
	<div
		style="background-color:<%=type%>;border: 2px solid <%=border %>; border-radius: 7px;">
		<!--Data from DB to be inserted here -->
		test value
	<script type="text/javascript">	$(document).ready(function(){
     $("#addGroupAttachmentButton").click(function(){
         $("#addGroupAttachmentModal").modal();
     });
 });
 
 
 $(document).ready(function() {
     $("#addGroupAttachmentButton").click(function(event) {
         $.ajax({
             url: "/group/addAttachment",
             type: "POST",
             data: {"id_group" : $(group-id), "name" : $("#groupAttachmentName").val(),"attachment_scope": $("#groupAttachment").val()}
         });
     });
 });
 </script>
	
	</div>
	<br />
</div>
<%@include file="footer.jsp"%>