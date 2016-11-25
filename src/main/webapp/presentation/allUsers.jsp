<%@include file="header.jsp"%>
 <form action="" onsubmit="return validate();">
  Search for:<br>
  <input type="text" name="value" id="submitted">
  <input type="submit" value="Submit">
  <span id="rev-err" class="text-danger hidden">Unknown	error</span>
  <br>
  <input type="radio" name="type" value="name" checked> Name
  <input type="radio" name="type" value="role"> Role
  <input type="radio" name="type" value="project"> Project
  <input type="radio" name="type" value="group"> Group
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

<div class="pagination">
	<a href="#" class="first" data-action="first">&laquo;</a> <a href="#"
		class="previous" data-action="previous">&lsaquo;</a> <input
		type="text" readonly="readonly" data-current-page="${currentPage}"
		data-max-page="${noOfPages}" /> <a href="#" class="next"
		data-action="next">&rsaquo;</a> <a href="#" class="last"
		data-action="last">&raquo;</a>
</div>

<script>
	$('.pagination').jqPagination({
		link_string : '?page={page_number}',
		max_page : "${noOfPages}",
		paged : function(page) {
			window.location.href = "?page=" + page;

		}
	});
</script>



<%@include file="footer.jsp"%>