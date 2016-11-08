
<%@include file="header.jsp"%>
<div style="width:30%;height:100%;margin-left:30%;">
     <img style="height:250px;width:100%;" src='<c:url value="/presentation/resources/imgs/profile_picture.png" />'>  
 
 <br/> 
 
First name: ${user.firstName}
<br/>
Second name: ${user.secondName}
<br/>
Last name:${user.lastName}
<br/>

Email: ${user.email}
<br/>
Roles: 
<c:forEach items="${user.roles}" var="role">
${role.getTitle()}
</c:forEach>
 <c:forEach items="${user.roles}" var="role"> 
<c:if test='${role.getTitle()}==="ROLE_STUDENT"'> 
 Status: ${user.isActive()}
</c:if>

</c:forEach>

 

</div>
 
<%@include file="footer.jsp"%>
