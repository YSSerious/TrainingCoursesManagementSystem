<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>
 <form action="" onsubmit="return validate();" method="get">
  Search for:<br>
  
  <input type="text" name="value" id="submitted"
  <c:if test="${param.value != null}">
   value="${param.value}"
   </c:if>
   >
  <input type="submit" value="Submit">
  <span id="rev-err" class="text-danger hidden">Unknown	error</span>
  <br>
  <input type="radio" name="type" value="name" 
  <c:if test="${param.type == 'name' || param.type == null}">
  checked
  </c:if>
  > Name
  <input type="radio" name="type" value="role" 
  <c:if test="${param.type == 'role'}">
  checked
  </c:if>
  > Role
  <input type="radio" name="type" value="project" 
  <c:if test="${param.type == 'project'}">
  checked
  </c:if>
  > Project
  <input type="radio" name="type" value="group" 
  <c:if test="${param.type == 'group'}">
  checked
  </c:if>
  > Group
</form> 
<br>
<br>
<c:if test="${!users.isEmpty()}">
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">Users</h3>
	</div>
	<table class="table table-striped table-bordered" id="pagination">
		<tr>
			<th>User</th>
			<th>User role</th>
		</tr>

		<c:forEach items="${users}" var="user">
			<tr>
				<td><font size="3"><b> <a href="/users/${user.id}">
								${user.firstName} ${user.secondName} ${user.lastName} </a></b></font></td>
				<td><font size="3"> <c:forEach items="${user.roles}"
							var="role" varStatus="loop">
							<b>${role.getTitle().substring(5)}
							<c:if test="${!loop.last}">,</c:if>
							</b>
							
						</c:forEach>
				</font></td>
			</tr>
		</c:forEach>
	</table>
</div>
</c:if>
<c:if test="${users.isEmpty()}">
	<h1>No such users</h1>
	</c:if>


	<br />
<div class="text-center">
	<div style="min-width:100%;" id="bootstrap-pagination">
	</div>
</div>
	
<script>

var max = ${noOfPages};
var curr = ${currentPage};

var value = getParameterByName('value');
var type = getParameterByName('type');
$('#bootstrap-pagination').bootpag({
	total: max,
    page: curr,
    maxVisible: 5,
    leaps: true,
    firstLastUse: true,
    first: '←',
    last: '→',
    wrapClass: 'pagination',
    activeClass: 'active',
    disabledClass: 'disabled',
    nextClass: 'next',
    prevClass: 'prev',
    lastClass: 'last',
    firstClass: 'first'
}).on("page", function(event, num){

	if(value!=null && type!=null)
		window.location.href = "?page="+num+"&value="+value+"&type="+type;
	else
		window.location.href = "?page="+num;
	
});


function getParameterByName(name, url) {
    if (!url) {
      url = window.location.href;
    }
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}
</script>

	<%@include file="footer.jsp"%>
