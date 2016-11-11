<%@include file="header.jsp"%>

<jsp:useBean id="type" class="java.lang.String" />
<jsp:useBean id="border" class="java.lang.String" />
<%
                  type="#fff";
                  border="#7FFF00";
				%>
<h1>Projects' name</h1>${group-project}
<h2>Groups' name</h2>
${group-name}
<br />

<div class="meetings_template">
	<label>Meetings</label>
	<button type="button" class="btn btn-default btn-xs">Edit</button>
	<br />
	<div
		style="background-color:<%=type%>;border: 2px solid <%=border %>; border-radius: 7px;">

		<!--Data from DB to be inserted here -->
		test value
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
			${student.firstName}
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
			${mentor.firstName}
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
            id="submitButton"><span class="glyphicon glyphicon-off"
             
            ></span>Submit</button>
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
	<button type="button" class="btn btn-default btn-xs">Edit</button>
	<br />
	
	<div
		style="background-color:<%=type%>;border: 2px solid <%=border %>; border-radius: 7px;">
		<!--Data from DB to be inserted here -->
		test value
		<c:forEach items="attachments" var="attachment">
     ${attachment.getName()}<br/> 
    ${attachment.getAttachmentScope()}<br/>
    
    </c:forEach>
		
	<script type="text/javascript">	$(document).ready(function(){
     $("#addGroupAttachmentButton").click(function(){
         $("#addGroupAttachmentModal").modal();
     });
 });
 
 
 $(document).ready(function() {
     $("#addGroupAttachmentButton").click(function(event) {
         $.ajax({
             url: "/groups/addAttachment",
             type: "GET",
             data: {"id_group" : $(group-id), 
            	 "name" : $("#groupAttachmentName").val(),
            	 "attachment_scope": $("#groupAttachment").val()}
             
         });
     });
 });
 </script>
	
	</div>
	<br />
</div>
<%@include file="footer.jsp"%>