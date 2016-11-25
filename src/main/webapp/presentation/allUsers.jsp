<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
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
					<a href ="/users/${user.id}">
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
<div class="text-center">
	<div style="min-width:100%;" id="bootstrap-pagination">
	</div>
</div>
	
<script>
var max = ${noOfPages};
var curr = ${currentPage};
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
	window.location.href = "?page="+num;
}); 
</script>

	<%@include file="footer.jsp"%>