<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <script type="text/javascript"
      src="<c:url value="/presentation/resources/third-party/jquery/jquery.min.js" />"></script>
<script src=<c:url value="/resources/js/bootstrap.min.js" />></script>
      <script type="text/javascript"
      src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.min.js"></script>
      <script src="
      <c:url value="/presentation/resources/third-party/jqpagination/js/jquery.jqpagination.js" />
      "
      type="text/javascript"></script>
      <link rel="stylesheet"
      href="
      <c:url value="/presentation/resources/third-party/jqpagination/css/jqpagination.css" />
      "
      type="text/css">
      <link href="
      <c:url value="/presentation/resources/third-party/bootstrap/css/bootstrap.css" />
      "
      type="text/css" rel="stylesheet" />
      <link href="
      <c:url value="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/css/select2.min.css" />
      "
      type="text/css" rel="stylesheet" />
      <title>Insert title here</title>
   </head>
   <body>
         <br/>
         <jsp:useBean id="today" class="java.util.Date" />
         <jsp:useBean id="type" class="java.lang.String" />
         <jsp:useBean id="border" class="java.lang.String" />
        
           
            <div class="panel panel-success">
	<div class="panel-heading">
		<h3 class="panel-title">Users</h3>
</div>
               <table class="table table-striped table-bordered" id="pagination">
				<thead bgcolor="#8FBC8F">
				
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>User role</th>
				  </tr>
				  </thead>
				   <c:forEach items="${users}" var="user">
                  <tr>
                     <td ><font size="3"><b>${user.firstName}</b></font></td>
                     <td ><font size="3"><b>${user.lastName}</b></font></td>
                     <c:if test="${user.roles.size()==1}">
                     <td ><font size="3"><b>${user.roles.get(0).getTitle()}</b></font></td>
                     </c:if>
                     <c:if test="${user.roles.size()==2}">
                     <td ><font size="3"><b>${user.roles.get(0).getTitle()}</b><b>, ${user.roles.get(1).getTitle()}</b></font></td>
                     </c:if>
                  </tr>
                  </c:forEach>
               </table>
            </div>
            <br/>
         
   </body>
</html>
