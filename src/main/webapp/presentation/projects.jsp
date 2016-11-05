<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <script type="text/javascript"
      src="<c:url value="/presentation/resources/third-party/jquery/jquery.min.js" />"></script>
      <script type="text/javascript"
      src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.min.js"></script>
      <script type="text/javascript"
      src="
      <c:url value="/presentation/resources/third-party/bootstrap/js/bootstrap.min.js" />
      "></script>
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
      <div class="search-field">

         <form:form modelAttribute="projectSearch" id="searchForm" style="margin:0 auto; width:500px;">
            <form:hidden path="searchRequest" id="searchRequest" value="true"/>
            <div class="row">
               <div class="form-group col-xs-6">
                  <form:input path="text" class="form-control" type="text" name="text-search" /> 
               </div>
               <div class="form-group col-xs-6" style="padding-top:5px;paddint-left:15px;">
               		<form:select path="statuses" class="selectpicker">
            			<option value="active" ${activeValue}>Active</option>
            			<option value="completed" ${completedValue}>Completed</option>
            			<option value="upcoming" ${upcomingValue}>Upcoming</option>
            		</form:select>

               </div>
            </div>
            <div class="row">
               <div class="form-group col-xs-6">
               <form:input type="date" class="form-control" path="start"/>
                 
               </div>
               <div class="form-group col-xs-6">
                  <form:input path="end" class="form-control" type="date"/>
               </div>
            </div>
            <form:select path="dateType" class="form-control" name="date-type">
            	<option value="startType" ${defaultDateTypeValue}>Default</option>
            	<option value="startType" ${startTypeValue}>Start</option>
            	<option value="endType" ${endTypeValue}>End</option>
            	<option value="startAndEndType" ${startAndEndTypeValue}>Start and end</option>
            </form:select>
            <br/>
            <form:select path="order" class="form-control" name="order">
            	<option value="" ${defaultOrderValue}>Default order</option>
            	<option value="nameasc" ${nameascValue}>by Name (ASC)</option>
            	<option value="namedesc" ${namedescValue}>by Name (DESC)</option>
            	<option value="startasc" ${startascValue}>by Start (ASC)</option>
            	<option value="startdesc" ${startdescValue}>by Start (DESC)</option>
            	<option value="finishasc" ${finishascValue}>by Finish (ASC)</option>
            	<option value="finishdesc" ${finishdescValue}>by Finish (DESC)</option>
            </form:select>
            <form:hidden path="page" id="hidden-page" />
            <br/>
            <center>
               <font class="show-select">ENABLE CRITERION SEARCH</font>
               
               <form:select path="criteria" multiple="true" name="criteria" class="criteria-select" style="width: 200px;">
               </form:select> 
               
               <br/>
               <br/>
               <input type="submit"/>
            </center>
         </form:form>
         <br/>
         <br/>
         <br/>
         <jsp:useBean id="today" class="java.util.Date" />
         <jsp:useBean id="type" class="java.lang.String" />
         <jsp:useBean id="border" class="java.lang.String" />
         <c:forEach items="${projects}" var="value">
            <c:if test="${today.time gt value.startDate.time && value.finishDate.time gt today.time}">
               <%
                  type="#DCEEB7";
                  border="#7FFF00";
                  %>
            </c:if>
            <c:if test="${value.startDate.time gt today.time}">
               <%
                  type="#D9F9F6";
                  border="#40E0D0";
                  %>
            </c:if>
            <c:if test="${today.time gt value.finishDate.time}">
               <%
                  type="#FFE0DA";
                  border="#DC143C";
                  %>
            </c:if>
            <div style="background-color:<%=type%>;margin-left:250px;margin-right:250px; border: 2px solid <%=border %>; border-radius: 7px;">
               <table style="margin: 0 auto;min-width:700px;">
                  <tr>
                     <td style="width:75%;"><font size="3"><b>${value.name}</b></font></td>
                     <td>
                        <font color="blue">Start:</font> ${value.startDate}
                        <br/><font color="blue">Finish:</font> ${value.finishDate}
                     </td>
                  </tr>
               </table>
            </div>
            <br/>
         </c:forEach>
         <br/>
         <center>
            <div class="pagination">
               <a href="#" class="first" data-action="first">&laquo;</a> <a
                  href="#" class="previous" data-action="previous">&lsaquo;</a> <input
                  type="text" readonly="readonly" data-current-page="${currPage}"
                  data-max-page="${maxPage}" /> <a href="#" class="next"
                  data-action="next">&rsaquo;</a> <a href="#" class="last"
                  data-action="last">&raquo;</a>
            </div>
         </center>
      </div>
      <script>
         $('.pagination').jqPagination({
         	link_string : '?page={page_number}',
         	max_page:'${maxPage}',
         	paged: function(page) {
         		$("#hidden-page").val(page);
         		$("#searchRequest").val('false');
         		$("#searchForm").submit();

         	}
         });
      </script>
      <script type="text/javascript">
         $(document).ready(function() {
         	var jsCriteria =[]
         	var select = '';
         	
         	<c:forEach items="${criteria}" var="value">
         		jsCriteria.push("${value}");
         	</c:forEach>
         	
         	if(jsCriteria.length > 0){ 
         
         		$.get("/ajaxcategories",function(data,status){
         			jQuery.each(data, function(i, category) {
         				  select+='<optgroup label="'+category.name+'">\n';
         				  
         				  jQuery.each(category.criteria, function(j, criterion) {
         					  
         					  select+='<option value="'+criterion.id+'">'+criterion.title+'</option>\n';
         					});
         				  
         				  select+='</optgroup>\n';
         				});
         			
         			
         			$(".criteria-select").append(select);
         			$(".criteria-select").select2();
         			$('.show-select').hide();
         			
         			$(".criteria-select").val(jsCriteria).trigger("change");
                    });
         		
         	}else{
         		
         		$(".criteria-select").hide();
         
         		$('.show-select').one('click', function(evt) {
         
         				$.get("/ajaxcategories",function(data,status){
         					
         					jQuery.each(data, function(i, category) {
         						  select+='<optgroup label="'+category.name+'">\n';
         						  
         						  jQuery.each(category.criteria, function(j, criterion) {
         							  
         							  select+='<option value="'+criterion.id+'">'+criterion.title+'</option>\n';
         							});
         						  
         						  select+='</optgroup>\n';
         						});
         					
         					$(".criteria-select").append(select);
         					$(".criteria-select").select2();
         					$(".criteria-select").show();
         					$('.show-select').hide();
         	            });
         		});
         	}
         });
      </script>
   </body>
</html>
