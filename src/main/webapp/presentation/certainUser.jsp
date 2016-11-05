 
    <%@include file="header.jsp"%>
    
   First name:   ${user.firstName}<br/>
   Last name:${user.lastName}<br/>
     <!-- 
   Email:   $(user.email)<br/>
    <c:if test="${user.roles.size()==1}">
                     <td ><font size="3"><b>${user.roles.get(0).getTitle()}</b></font></td>
                     </c:if>
                     <c:if test="${user.roles.size()==2}">
                     <td ><font size="3"><b>${user.roles.get(0).getTitle()}</b><b>, ${user.roles.get(1).getTitle()}</b></font></td>
                     </c:if>
  --> 
    
   
   <%@include file="footer.jsp"%>
