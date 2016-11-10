<%@include file="header.jsp"%>
	<br />
	<jsp:useBean id="today" class="java.util.Date" />
	<jsp:useBean id="type" class="java.lang.String" />
	<jsp:useBean id="border" class="java.lang.String" />


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
					<a href ="certainUser/${user.id}">
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

	<%--For displaying Previous link except for the 1st page --%>
	<div class="pagination">
   
    <c:if test="${currentPage != 1}">
   <a class="previous" href="/allUsers/${currentPage - 1}">Previous</a>
    </c:if>
 
    <%--For displaying Page numbers. 
    The when condition does not display a link for the current page--%>
    
            <c:forEach begin="1" end="${noOfPages}" var="i">
                        <a href="/allUsers/${i}">${i}</a>
            </c:forEach>
      
    
    <%--For displaying Next link --%>
    <c:if test="${currentPage lt noOfPages}">
        <a class="next" href="/allUsers/${currentPage + 1}">Next</a>
    </c:if>

    </div>
	<%@include file="footer.jsp"%>