<%@include file="header.jsp"%>
<div class="col-sm-10 col-sm-offset-1">
	<div class="col-sm-10 col-sm-offset-1 form-wrapper">
		<h2>Projects</h2>
		<hr/>
        <form:form modelAttribute="projectSearch" id="searchForm">
        	<form:hidden path="searchRequest" id="searchRequest" value="true"/>
            <div class="row">
      			<div class="col-xs-6 col-md-4">
      				<form:select path="order" class="form-control" name="order">
            			<form:option value="">Ordering</form:option>
            			<form:option value="nameasc">by Name (ASC)</form:option>
            			<form:option value="namedesc">by Name (DESC)</form:option>
            			<form:option value="startasc">by Start (ASC)</form:option>
            			<form:option value="startdesc">by Start (DESC)</form:option>
            			<form:option value="finishasc">by Finish (ASC)</form:option>
            			<form:option value="finishdesc">by Finish (DESC)</form:option>
            		</form:select>
      			</div>
            	<div class="col-xs-12 col-sm-6 col-md-8">
            		<div class="input-group">
               			<form:input path="text" class="form-control" type="text" placeholder="Search for..." />
      					<span class="input-group-btn">
        					<button class="btn btn-default" type="submit">Go!</button>
      					</span>
      				</div>
      			</div>
			</div>
			<div class="row">
				<br/>
            	<div class="col-xs-6 col-sm-4">
            		<form:input type="date" class="form-control" path="start"/>
           	 	</div>
  				<div class="col-xs-6 col-sm-4">
  					<form:input path="end" class="form-control" type="date"/>
  				</div>

  				<div class="col-xs-6 col-sm-4">
  			    	<form:select path="dateType" class="form-control" name="date-type">
            			<form:option value="" disabled="true">Default</form:option>
            			<form:option value="startType">Start</form:option>
            			<form:option value="endType">End</form:option>
            			<form:option value="startAndEndType">Start and end</form:option>
            		</form:select>
  				</div>
			</div>
		
			<br/>
		
			<div class="row">
				<div class="col-xs-12 col-sm-6 col-md-8">
		
					<center>
						<div class="show-select">
							<button type="button" class="btn btn-primary">ENABLE CRITERION SEARCH</button>
						</div>
		
						<form:select path="criteria" style="min-width:100%;" multiple="true" name="criteria" class="criteria-select">
						</form:select> 
        			</center>
				</div>
            
      			<div class="col-xs-6 col-md-4">
      				<div class="btn pull-right">
      					Active
						<form:checkbox path="statuses" value="active" />
						Completed
						<form:checkbox path="statuses" value="completed" />
						Upcoming
						<form:checkbox path="statuses" value="upcoming" />
      				</div>
				</div>
				<form:hidden path="page" id="hidden-page" /> 
			</div>
		</form:form>
		<br/>
		<br/>
		<br/>
         <jsp:useBean id="today" class="java.util.Date" />
         <jsp:useBean id="type" class="java.lang.String" />
         <jsp:useBean id="border" class="java.lang.String" />
         
         <c:if test="${empty projects}">
    		<h1><center>There are no projects to display. Try again using different parameters!</center></h1>
		 </c:if>
         
         <c:forEach items="${projects}" var="value">
            <c:if test="${today.time gt value.startDate.time && value.finishDate.time gt today.time}">
				<%
                  type="#fff";
                  border="#7FFF00";
				%>
            </c:if>
            <c:if test="${value.startDate.time gt today.time}">
               <%
                  type="#fff";
                  border="#40E0D0";
               %>
            </c:if>
            <c:if test="${today.time gt value.finishDate.time}">
               <%
                  type="#fff";
                  border="#DC143C";
               %>
            </c:if>
            <div style="background-color:<%=type%>;border: 2px solid <%=border %>; border-radius: 7px;">
               <div class="row">
               		<div class="col-xs-12 col-sm-6 col-md-8">
                  		<p style="margin-left:25px;">
                  			<br/>
                  			<font size="5"><b> ${value.name}</b></font>
                  		</p>
                  	</div>
                    
                    <div class="col-xs-6 col-md-4">
                    	<p>
                     		<br/>
                        	<br/>
                        	<br/>
                        	<a href="#" class="btn pull-right" >More</a>
                        </p>
                     </div>
               </div>
            </div>
            <br/>
         </c:forEach>
         <br/>
         <center>
         	<c:if test="${not empty projects}">
            <div class="pagination">
               <a href="#" class="first" data-action="first">&laquo;</a> <a
                  href="#" class="previous" data-action="previous">&lsaquo;</a> <input
                  type="text" readonly="readonly" data-current-page="${currPage}"
                  data-max-page="${maxPage}" /> <a href="#" class="next"
                  data-action="next">&rsaquo;</a> <a href="#" class="last"
                  data-action="last">&raquo;</a>
            </div>
            </c:if>
         </center>
	</div>
</div>
<script>
    $('.pagination').jqPagination({
        link_string: '?page={page_number}',
        max_page: '${maxPage}',
        paged: function(page) {
            $("#hidden-page").val(page);
            $("#searchRequest").val('false');
            $("#searchForm").submit();

        }
    });
</script>

<script type="text/javascript">
    $(document).ready(function() {
        var jsCriteria = []
        var select = '';

        <c:forEach items = "${criteria}" var = "value" >
        	jsCriteria.push("${value}"); 
        </c:forEach>

        if (jsCriteria.length > 0) {

            $.get("/ajaxcategories", function(data, status) {
                jQuery.each(data, function(i, category) {
                    select += '<optgroup label="' + category.name + '">\n';

                    jQuery.each(category.criteria, function(j, criterion) {

                        select += '<option value="' + criterion.id + '">' + criterion.title + '</option>\n';
                    });

                    select += '</optgroup>\n';
                });


                $(".criteria-select").append(select);
                $(".criteria-select").select2();
                $('.show-select').hide();

                $(".criteria-select").val(jsCriteria).trigger("change");
            });

        } else {

            $(".criteria-select").hide();

            $('.show-select').one('click', function(evt) {

                $.get("/ajaxcategories", function(data, status) {

                    jQuery.each(data, function(i, category) {
                        select += '<optgroup label="' + category.name + '">\n';

                        jQuery.each(category.criteria, function(j, criterion) {

                            select += '<option value="' + criterion.id + '">' + criterion.title + '</option>\n';
                        });

                        select += '</optgroup>\n';
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

<%@include file="footer.jsp"%>