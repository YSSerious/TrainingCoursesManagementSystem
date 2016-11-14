<%@include file="header.jsp"%>
	<div class="panel panel-success">
		<div class="panel-heading">
			<h3 class="panel-title">Users</h3>
		</div>
		<table  class="table table-striped table-bordered" id="pagination">
			<thead bgcolor="#8FBC8F">

				<tr>
					<th>User</th>
					<th>User role</th>
				</tr>
			</thead>
			<c:forEach items="${users}" var="user">
				<tr>
					<td><font size="3"><b>
					<a href ="/certainUser/${user.id}">
					${user.firstName} ${user.secondName} ${user.lastName}
					</a></b></font></td>
					<td><font size="3">
					<c:forEach items="${user.roles}" var = "role">
						<b>${role.getTitle()}</b>
					</c:forEach>
					</font></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<br />
	
				<div class="pagination">
					<a href="#" class="first" data-action="first">&laquo;</a> <a
						href="#" class="previous" data-action="previous">&lsaquo;</a> <input
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
				window.location.href = "?page="+page;
				
			}
		});
	</script>

	<%@include file="footer.jsp"%>