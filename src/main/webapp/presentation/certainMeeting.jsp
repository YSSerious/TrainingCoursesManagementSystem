<%@include file="header.jsp"%>
This is meeting ${meeting.name}
<br>
In ${meeting.place}
<br>
At ${meeting.time}

<br>
<br>
<br>
<br>
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">Unevaluated students</h3>
	</div>
	<table class="table table-striped table-bordered" id="pagination">
		<thead>

			<tr>
				<th>Student</th>
				<th>Evaluate</th>
				<th>Mark as absent</th>
			</tr>
		</thead>
		<c:forEach items="${students}" var="user">
			<tr>
				<td><font size="3"><b> <a
							href="/certainUser/${user.id}"> ${user.firstName}
								${user.secondName} ${user.lastName} </a></b></font></td>
				<td><button type="button" class="btn btn-primary">
						<span class="glyphicon glyphicon-edit"></span> Evaluate
					</button></td>
				<td><button type="button" class="btn btn-primary">
						<span class="glyphicon glyphicon-trash"></span> Absent
					</button></td>
			</tr>
		</c:forEach>
	</table>
</div>
<br>
<br>
<br>
<br>
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">Evaluated students</h3>
	</div>
	<table class="table table-striped table-bordered" id="pagination">
		<thead>

			<tr>
				<th>Student</th>
				<c:forEach items="${criteria}" var="criterion">
					<th>${criterion.title}</th>
				</c:forEach>
			</tr>
		</thead>
		<c:forEach items="${marks}" var="entry">
			<tr>
				<td><font size="3"><b> <a
							href="/certainUser/${entry.key.id}">${entry.key.firstName}
								${entry.key.secondName} ${entry.key.lastName}</a></b></font></td>
				<c:forEach items="${entry.value}" var="mark">
					<td><font size="3"><a href="#" data-toggle="tooltip"
							data-placement="top" title="${mark.commentary}">${mark.mark}</a></font></td>
				</c:forEach>
			</tr>
		</c:forEach>
	</table>
</div>
<br>
<br>
<br>
<br>
<h2>Criteria List</h2>

<dl>
	<c:forEach items="${categories}" var="category">
		<dt>${category.name}</dt>
		<c:forEach items="${criteria}" var="criterion">
			<c:if test="${category.id == criterion.category.id}">
				<dd>&nbsp;&nbsp; -${criterion.title}</dd>
			</c:if>
		</c:forEach>
	</c:forEach>
</dl>


<%@include file="footer.jsp"%>