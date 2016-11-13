<%@include file="header.jsp"%>
<script src="<c:url value="/presentation/resources/js/user.js"/>"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>

<script src="http://cdn.oesmith.co.uk/morris-0.4.1.min.js"></script>

<div class="col-sm-11 col-sm-offset-1">
	<div class="col-sm-11">
	
		<c:if test="${not empty error}">
			<div class="alert alert-danger">
  				<strong>Danger!</strong> ${error}
			</div>
			<br/>
		</c:if>
	
		<c:if test="${not empty success}">
			<div class="alert alert-success">
  				<strong>Success!</strong> ${success}
			</div>
			<br/>
		</c:if>
	<div class="row">
	<div class="col-sm-4">
	<img style="height:70%;width:70%;" src='<c:url value="/presentation/resources/imgs/profile_picture.png" />'>
	</div>
	<div class="col-sm-4">
		Email: ${user.email} <br/>
		First name: ${user.firstName} <br/>
		Last name: ${user.lastName} <br/>
		Second name: ${user.secondName} <br/>
		Roles: ${user.roles} <br/>
		Active: ${user.active} <br/>
	</div>
		<div class="col-sm-4">
		<sec:authorize access="hasRole('ADMIN')">
		<div class="row">
		
	<form action="/manageRoles" method="post">
	<div class="col-sm-6">
	<c:forEach var="role" items="${roles}">
	<div class="checkbox">
  		<label><input type="checkbox" name="roles" 
  		
  		<c:forEach var="userRole" items="${user.roles}">
			<c:if test="${role.title eq userRole}">
				checked
			</c:if>
		</c:forEach>
		
  		value="${role.id}">${role.title}</label>
	</div>
	</c:forEach>
	</div>
	<div class="col-sm-6">
	<br/>
	<br/>
	<input type="hidden" name="student" value="${user.id}">
	<center><button type="submit" class="btn btn-primary">Submit</button></center>
	</div>
	</form>
	</div>
	</sec:authorize>
		<c:forEach var="item" items="${user.roles}">
			<c:if test="${item eq 'Student'}">
			
				Status: ${user.statusTitle} <br/>
				
				<sec:authorize access="hasRole('HR')">
					<c:if test="${user.statusId eq 1}">
						<div class="showactions">
							<button type="submit" class="btn btn-primary">Switch status</button>
						</div>
						<div class="actions">
							<form method="post">
							<br/>
								<input type="radio" name="status" value="3" checked>Interview offer  <button type="submit" class="btn btn-primary">Submit</button><br/><br/>
								<textarea name="commentary" class="form-control" rows="2" id="comment" required></textarea>
								
							</form>
						</div>
					</c:if>
				</sec:authorize>
				
				<c:if test="${user.statusId eq 2}">
					<sec:authorize access="hasRole('MENTOR')">
					<div class="showactions">
						<button type="submit" class="btn btn-primary">Switch status</button>
					</div>
					<div class="actions">
						<form method="post">
						<br/>
							<input type="radio" name="status" value="1" checked>Inactive  <button type="submit" class="btn btn-primary">Submit</button><br/><br/>
							<textarea name="commentary" class="form-control" rows="2" id="comment" required></textarea>
						</form>
						</div>
					</sec:authorize>
					<sec:authorize access="hasRole('HR')">
					<div class="actions">
						<div class="showactions">
							<button type="submit" class="btn btn-primary">Switch status</button>
						</div>
						<form method="post">
							<br/>
							<input type="radio" name="status" value="1" checked>Inactive 
							<input type="radio" name="status" value="3">Interview offer <button type="submit" class="btn btn-primary">Submit</button><br/><br/>
							<textarea name="commentary" class="form-control" rows="2" id="comment" required></textarea>
							
						</form>
						</div>
					</sec:authorize>
				</c:if>
				
				<sec:authorize access="hasRole('HR')">
					<c:if test="${user.statusId eq 3}">
						<sec:authorize access="hasRole('HR')">
						<div class="showactions">
							<button type="submit" class="btn btn-primary">Switch status</button>
						</div>
						<div class="actions">
						<form method="post">
						<br/>
							<input type="radio" name="status" value="1" checked>Inactive 
							<input type="radio" name="status" value="4">Job offer  <button type="submit" class="btn btn-primary">Submit</button><br/><br/>
							<textarea name="commentary" class="form-control" rows="2" id="comment" required></textarea>
							
						</form>
						</div>
						</sec:authorize>
					</c:if>
				</sec:authorize>
				
				
			</c:if>
		</c:forEach>
		</div>
		</div>
		<br/>
			
		<c:forEach var="item" items="${user.roles}">
			<c:if test="${item eq 'Student'}">
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
   			<c:if test="${item eq 'Mentor'}">
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

<script>
	$(document).ready(function() {
    	$(".actions").hide();
    	$(".showactions").click(function() {
    		$(".actions").show();
    		$(".showactions").hide();
    	});
    });
</script>
<%@include file="footer.jsp"%>