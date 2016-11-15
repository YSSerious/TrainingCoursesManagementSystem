<%@include file="header.jsp"%>
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
                        <a href="/projects/create-project" class="btn btn-primary pull-right"><spring:message code="projects.create.project"/></a>
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
							type = "#fff";
									border = "#7FFF00";
						%>
					</c:if>
					<c:if test="${value.startDate.time gt today.time}">
						<%
							type = "#fff";
									border = "#40E0D0";
						%>
					</c:if>
					<c:if test="${today.time gt value.finishDate.time}">
						<%
							type = "#fff";
									border = "#DC143C";
						%>
					</c:if>
					<div
						style="background-color:<%=type%>;border: 2px solid <%=border%>; border-radius: 7px;">
						<div class="row">
							<div class="col-xs-12 col-sm-6 col-md-8">
								<p style="margin-top: -5px; margin-left: 15px;">
									<br /> <font size="5"><b><a href ="certainProject/${value.id}">${value.name}</a></b></font>
								</p>
							</div>

							<div class="col-xs-6 col-md-4">
								<p style="margin-top: -5px;">
									<br /> <font color="blue"><spring:message code="projects.start"/>:</font> ${value.startDate} <br />
									<font color="blue"><spring:message code="projects.finish"/>:</font> ${value.finishDate} <br />
								</p>
							</div>
						</div>
					</div>
					<br />
				</c:forEach>
				<br />
			</div>
			<div class="col-md-3">
			<spring:message code='projects.search.for' var="searchFor"/>
				<form:form modelAttribute="projectSearch" id="searchForm">
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
								<form:option value="" disabled="true"><spring:message code="projects.default"/></form:option>
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
								<button type="button" style="min-width: 100%;"
									class="btn btn-default"><spring:message code="projects.criterion.search"/></button>
							</div>
						</div>

						<div class="col-md-12">
							<form:select path="criteria" multiple="true" name="criteria"
								class="criteria-select" style="min-width: 88%;">
							</form:select>
						</div>
					</div>
					<br/>
					<div class="row">
						<div class="col-md-12">
							<input type=submit class="form-control btn btn-primary" value=<spring:message code="projects.search"/> />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<div style="min-width:260px;" class="col-sm-3 col-sm-offset-3">
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
		</div>
	</div>
</div>
<script>
	$('.pagination').jqPagination({
		link_string : '?page={page_number}',
		max_page : '${maxPage}',
		paged : function(page) {
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