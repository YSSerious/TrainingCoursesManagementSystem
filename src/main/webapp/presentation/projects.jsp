<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="header.jsp"%>
<%@include file="createProjectModal.jsp"%>

<div class="col-sm-11 col-sm-offset-1">
	<div class="col-sm-11">
            <div class="top-info">
                <c:if test="${not empty msg}">
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3 alert alert-success fade in">
                            <a class="close" data-dismiss="alert" aria-label="close">&times;</a>
                            ${msg}
                        </div>
                    </div>
                </c:if>
                <div class="row">
                    <h2 class="col-sm-3"><spring:message code="projects.projects"/></h2>
                    <sec:authorize access="hasRole('ADMIN')">
                           <button type="button" class="btn btn-primary pull-right" id="createProject"><spring:message code="projects.createProject.createButton"/></button>
                    </sec:authorize>
                </div>
            </div>
		<hr />
		<div class="row">
			<div class="col-md-9">
				<jsp:useBean id="today" class="java.util.Date" />
				<jsp:useBean id="type" class="java.lang.String" />
				<jsp:useBean id="border" class="java.lang.String" />
				<c:if test="${empty projects}">
					<h1><spring:message code="projects.no.results"/></h1>
				</c:if>
				<c:forEach items="${projects}" var="value">
					<c:if
						test="${today.time gt value.startDate.time && value.finishDate.time gt today.time}">
						<%
									border = "current";
						%>
					</c:if>
					<c:if test="${value.startDate.time gt today.time}">
						<%
									border = "primary";
						%>
					</c:if>
					<c:if test="${today.time gt value.finishDate.time}">
						<%
									border = "finished";
						%>
					</c:if>
					<div class="panel panel-<%=border%>">
						<div class="panel-body">
							<div class="col-md-8">	
								<font size="5"><b><a href ="certainProject/${value.id}">${value.name}</a></b></font>
							</div>

							<div class="col-md-4">
								<font color="blue"><spring:message code="projects.start"/>:</font> <fmt:formatDate pattern="yyyy-MM-dd" value="${value.startDate}" /><br />
								<font color="blue"><spring:message code="projects.finish"/>:</font> <fmt:formatDate pattern="yyyy-MM-dd" value="${value.finishDate}" />
							</div>
						</div>
					</div>
				</c:forEach>
				<br />
			</div>
			<div class="col-md-3">
			<spring:message code='projects.search.for' var="searchFor"/>
				<form:form method="get" modelAttribute="projectSearch" id="searchForm">
					<form:hidden path="searchRequest" id="searchRequest" value="true" />
					<div class="row">
					<h4 style="margin-left:15px;"><spring:message code="projects.key.words.order"/>:</h4>
						<div class="col-md-12">
								<form:input path="text" class="form-control" type="text"
									placeholder="${searchFor}" />
						</div>
					</div>
					<br />
					<div class="row">
						<div class="col-md-12">
							<form:select path="order" class="form-control" name="order">
								<form:option value=""><spring:message code="projects.ordering"/></form:option>
								<form:option value="nameasc"><spring:message code="projects.byname"/> <spring:message code="projects.asc"/></form:option>
								<form:option value="namedesc"><spring:message code="projects.byname"/> <spring:message code="projects.desc"/></form:option>
								<form:option value="startasc"><spring:message code="projects.bystart"/> <spring:message code="projects.asc"/></form:option>
								<form:option value="startdesc"><spring:message code="projects.bystart"/> <spring:message code="projects.desc"/></form:option>
								<form:option value="finishasc"><spring:message code="projects.byfinish"/> <spring:message code="projects.asc"/></form:option>
								<form:option value="finishdesc"><spring:message code="projects.byfinish"/> <spring:message code="projects.desc"/></form:option>
							</form:select>
						</div>
					</div>
					<br />
					<div class="row">
					<h4 style="margin-left:15px;"><spring:message code="projects.start.finish.date"/>:</h4>
						<div class="col-md-12">

							<form:input type="date" class="form-control" path="start"
								id="start-search-date" placeholder="From..." />
						</div>
					</div>
					<br />
					<div class="row">
						<div class="col-md-12">
							<form:input type="date" class="form-control" path="end"
								id="end-search-date" placeholder="To..." />
						</div>
					</div>
					<br />
					<div class="row">
					<h4 style="margin-left:15px;"><spring:message code="projects.date.check"/>:</h4>
						<div class="col-md-12">
							<form:select path="dateType" class="form-control"
								name="date-type">
								<form:option value=""><spring:message code="projects.default"/></form:option>
								<form:option value="startType"><spring:message code="projects.start"/></form:option>
								<form:option value="endType"><spring:message code="projects.finish"/></form:option>
								<form:option value="startAndEndType"><spring:message code="projects.start.finish"/></form:option>
							</form:select>
						</div>
					</div>

					<br />

					<div class="row">
						<h4 style="margin-left:15px;"><spring:message code="projects.status"/>:</h4>
						<div class="col-md-12">
							
							<form:checkbox path="statuses" value="active" />
							<spring:message code="projects.active"/><br/>
							
							<form:checkbox path="statuses" value="completed" />
							<spring:message code="projects.completed"/><br/>
							
							<form:checkbox path="statuses" value="upcoming" />
							<spring:message code="projects.upcoming"/>
							
							<form:hidden path="page" id="hidden-page" />
						</div>
					</div>
					<br />
					<div class="row">
						<div class="col-md-12">

							<div class="show-select">
								<button type="button" style="width: 100%;"
									class="btn btn-default"><spring:message code="projects.criterion.search"/></button>
							</div>
						</div>

						<div class="col-md-12">
							<form:select path="criteria" multiple="true" name="criteria"
								class="criteria-select" style="width:100%;">
							</form:select>
						</div>
					</div>
					<br/>
					<div class="row">
						<div class="col-md-12">
							<button onclick="cleanAndSubmit()" class="form-control btn btn-primary"><spring:message code="projects.search"/></button>	
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<div class="text-center">
		
			<c:if test="${not empty projects}">
				<c:if test="${maxPage > 1}">
				<div style="min-width:100%;" id="bootstrap-pagination">

				</div>
				</c:if>
			</c:if>
		</div>
	</div>
</div>
<script>
	var max = ${maxPage};
	var curr = ${currPage};
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
		$("#hidden-page").val(num);
		$("#searchRequest").val('');
	
		cleanAndSubmit();
	}); 

	
	function cleanAndSubmit(){
		$('#searchForm').find('input, textarea, select, checkbox').each(function(_, inp) {
			  if ($(inp).val() === '' || $(inp).attr("name") === '_criteria' || $(inp).val() === 'on' ||$(inp).val() === null){
			    inp.disabled = true;
			  }
		});

		$("#searchForm").submit();
	}
</script>

<script type="text/javascript">
	$(document).ready(function() {
		var jsCriteria = []
		var select = '';

		<c:forEach items = "${criteria}" var = "value" >
			jsCriteria.push("${value}");
		</c:forEach>

		if (jsCriteria.length > 0) {
			$(".criteria-select").hide();
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
				$(".criteria-select").show();
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