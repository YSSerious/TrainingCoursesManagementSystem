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
					<th>First Name</th>
					<th>Last Name</th>
					<th>User role</th>
				</tr>
			</thead>
			<c:forEach items="${users}" var="user">
				<tr>
					<td><font size="3"><b><a href ="users/${user.id}">${user.firstName}</a></b></font></td>
					<td><font size="3"><b>${user.lastName}</b></font></td>
					<c:if test="${user.roles.size()==1}">
						<td><font size="3"><b>${user.roles.get(0).getTitle()}</b></font></td>
					</c:if>
					<c:if test="${user.roles.size()==2}">
						<td><font size="3"><b>${user.roles.get(0).getTitle()}</b><b>,
									${user.roles.get(1).getTitle()}</b></font></td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
	</div>
	<br />

	<%--For displaying Previous link except for the 1st page --%>
    <c:if test="${currentPage != 1}">
        <td><a href="allUsers?page=${currentPage - 1}">Previous</a></td>
    </c:if>
 
    <%--For displaying Page numbers. 
    The when condition does not display a link for the current page--%>
    <table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="allUsers?page=${i}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
    </table>
     
    <%--For displaying Next link --%>
    <c:if test="${currentPage lt noOfPages}">
        <td><a href="allUsers?page=${currentPage + 1}">Next</a></td>
    </c:if>
	<%@include file="footer.jsp"%>