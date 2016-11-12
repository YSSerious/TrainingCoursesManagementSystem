<%@include file="header.jsp"%>
<script src="<c:url value="/presentation/resources/js/user.js"/>"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>

<script src="http://cdn.oesmith.co.uk/morris-0.4.1.min.js"></script>

<div class="col-sm-11 col-sm-offset-1">
	<div class="col-sm-11">
		Email: ${user.email} <br/>
		First name: ${user.firstName} <br/>
		Last name: ${user.lastName} <br/>
		Second name: ${user.secondName} <br/>
		Roles: ${user.roles} <br/>
		Active: ${user.active} <br/>
		<br/>
			
		<c:forEach var="item" items="${user.roles}">
			<c:if test="${item eq 'ROLE_STUDENT'}">
    			<div class="student-projects">
    				Loading projects(student role)...
    				<br/>
    			</div>
    			<script>
    				$( document ).ready(function() {    	
    					createStudentProjectsInfo("${user.id}", '.student-projects');
    				});
    			</script>
  			</c:if>
  
   			<c:if test="${item eq 'ROLE_MENTOR'}">
    			<div class="mentor-projects">
    				Loading projects(mentor role)...
    				<br/>
    			</div>
    
   				 <script>
    				$(document).ready(function() {
    					createMentorProjectsInfo("${user.id}", '.mentor-projects');
    				});
    			</script>
  			</c:if>
		</c:forEach>
	</div>
</div>

<%@include file="footer.jsp"%>