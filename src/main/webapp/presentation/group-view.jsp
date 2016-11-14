<%@include file="header.jsp"%>

<jsp:useBean id="type" class="java.lang.String" />
<jsp:useBean id="border" class="java.lang.String" />
<%
                  type="#fff";
                  border="#7FFF00";
				%>
<h1>Project: ${projectName}</h1>
<h2>Group:${group.name}</h2> 
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
	
	 <div class="modal fade" id="addGroupAttachmentModal" role="dialog">
    <div class="modal-dialog">
   
      <div class="modal-content">
        <div class="modal-body" style="padding:40px 50px;">
          <form role="form"   > 
            <div class="form-group">
            <label for="groupAttachmentName"> Attachment name: </label><br/>
	<input type=text class="form-control" id="groupAttachmentName"
	 placeholder="Enter attachment name"  required="required">  
	<label for="groupAttachment"> Attachment: </label><br/>
	<input type=text class="form-control" id="groupAttachment" 
	placeholder="Enter attachment"  required="required">  
               
            </div>
            <button type="submit" class="btn btn-default btn-success pull-center" 
            id="addSubmitButton"><span class="glyphicon glyphicon-off"
             
            ></span>Send</button>
          </form>
        </div>
        <div class="modal-footer">

          <button type="button" class="btn btn-default btn-lg"
           data-dismiss="modal" id="cancelButton"><span 
           class="glyphicon glyphicon-remove"></span> Cancel</button>
        </div>
      </div>
      
    </div>
  </div>
   
   <!--  -->
	<button type="button" class="btn btn-default btn-xs" value="addAttachment" 
           id="addGroupAttachmentButton">Upload</button>
	 
	<br />
	
	<div
		style="background-color:<%=type%>;border: 2px solid <%=border %>; border-radius: 7px;">
		<!--Data from DB to be inserted here -->
	<!--  test value-->	
 
  <div id="attachment-title">Attachments</div>
     <div id="attachment" style="display:none">
<c:forEach items="${attachments}" var="attachment" >
  <a href="${attachment.attachmentScope}" >   ${attachment.name}</a>
  <div class="btn-group">
    <button id="e${attachment.id}"class="btn btn-xs btn-warning edit ${attachment.id}">Edit </button>
    <button id="d${attachment.id}" class="btn btn-xs btn-danger delete ${attachment.id}">Delete </button>
    </div>
    <br/>
    </c:forEach>
      </div>
	</div>
	<br />
</div>
<%@include file="footer.jsp"%>