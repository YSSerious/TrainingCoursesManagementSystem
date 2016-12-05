<%@include file="header.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
<div class="col-sm-11 col-sm-offset-1">
    <div class="col-sm-11">

        <c:if test="${not empty success}">
            <div class="alert alert-success">
                <strong>Success!</strong> ${success}
            </div>
            <br/>
        </c:if>

        <div class="row">
            <div class="col-sm-4">
                <img style="height:70%;width:70%;"
                     src='<c:url value="/presentation/resources/imgs/profile_picture.png" />'>
            </div>

            <div class="col-sm-4">
                <b><spring:message code="user.email"/>:</b> ${user.email} <br/>
                <b><spring:message code="user.first.name"/>:</b> ${user.firstName} <br/>
                <b><spring:message code="user.last.name"/>:</b> ${user.lastName} <br/>
                <b><spring:message code="user.second.name"/>:</b> ${user.secondName} <br/>
                <sec:authorize access="hasRole('ADMIN')">
                    <b><spring:message code="user.roles"/>:</b> <c:forEach var="role" items="${user.roles}"
                                                                           varStatus="loop">${role}<c:if
                        test="${!loop.last}">,</c:if> </c:forEach> <br/>
                </sec:authorize>
                <c:if test="${user.active}">
                    <b><spring:message code="user.active"/></b><br/>
                </c:if>
                <c:if test="${user.active eq false}">
                    <b><spring:message code="user.not.active"/></b><br/>
                </c:if>
            </div>

            <div class="col-sm-4">

                <c:forEach var="item" items="${user.roles}">
                    <c:if test="${item eq 'Student'}">

                        <b><spring:message code="user.status"/>:</b> ${user.statusTitle} <br/>

                        <sec:authorize access="hasAnyRole('ADMIN', 'HR')">
                            <font class="clickable" color="blue" data-toggle="modal"
                                  data-target="#projects-report-modal">
                                <b onclick="report(${user.id})"><spring:message code="user.generate.report"/></b>
                            </font>
                            <br/>

                            <div id="projects-report-modal" class="modal fade" role="dialog">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="modal-title"><spring:message code="user.generate.report"/></h4>
                                        </div>
                                        <div id="project-report-back" class="modal-body">

                                        </div>
                                        <div id="criteria-report-back"
                                             style="display:none;padding-top:0px;margin-top:0px;" class="modal-body">

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </sec:authorize>

                        <sec:authorize access="hasRole('HR')">
                            <c:if test="${user.statusId eq 1}">
                                <font class="clickable" color="blue" data-toggle="modal" data-target="#switchStatus">
                                    <b><spring:message code="user.switch.status"/></b>
                                </font>

                                <div id="switchStatus" class="modal fade" role="dialog">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close"
                                                        data-dismiss="modal">&times;</button>
                                                <h4 class="modal-title"><spring:message code="user.switch.status"/></h4>
                                            </div>
                                            <div class="modal-body">
                                                <div class="showactions"></div>
                                                <div class="actions">
                                                    <form method="post">
                                                        <br/>
                                                        <input type="radio" name="status" value="3" checked>
                                                        <spring:message code="user.interview.offer"/>
                                                        <button type="submit" style="margin-top: -5px;"
                                                                class="btn btn-primary pull-right">
                                                            <spring:message code="user.submit"/>
                                                        </button>
                                                        <br/>
                                                        <br/>
                                                        <br/>
                                                        <textarea maxlength="500" name="commentary" class="form-control"
                                                                  rows="5" id="comment" required></textarea>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </sec:authorize>

                        <c:if test="${user.statusId eq 2}">
                            <sec:authorize access="hasRole('MENTOR')">

                                <font class="clickable" color="blue" data-toggle="modal" data-target="#switchStatus">
                                    <b><spring:message code="user.switch.status"/></b>
                                </font>

                                <div id="switchStatus" class="modal fade" role="dialog">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close"
                                                        data-dismiss="modal">&times;</button>
                                                <h4 class="modal-title"><spring:message code="user.switch.status"/></h4>
                                            </div>
                                            <div class="modal-body">
                                                <div class="showactions">
                                                </div>
                                                <div class="actions">
                                                    <form method="post">
                                                        <br/>
                                                        <input type="radio" name="status" value="1" checked>
                                                        <spring:message code="user.inactive"/>
                                                        <button type="submit" style="margin-top: -5px;"
                                                                class="btn btn-primary pull-right">
                                                            <spring:message code="user.submit"/>
                                                        </button>
                                                        <br/>
                                                        <br/>
                                                        <br/>
                                                        <textarea maxlength="500" name="commentary" class="form-control"
                                                                  rows="5" id="comment" required></textarea>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                            </sec:authorize>
                            <sec:authorize access="hasRole('HR')">
                                <font class="clickable" color="blue" data-toggle="modal" data-target="#switchStatus">
                                    <b><spring:message code="user.switch.status"/></b>
                                </font>

                                <div id="switchStatus" class="modal fade" role="dialog">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close"
                                                        data-dismiss="modal">&times;</button>
                                                <h4 class="modal-title"><spring:message code="user.switch.status"/></h4>
                                            </div>
                                            <div class="modal-body">
                                                <div class="actions">
                                                    <div class="showactions"></div>
                                                    <form method="post">
                                                        <br/>
                                                        <div class="row">
                                                            <div class="col-sm-6">
                                                                <input type="radio" name="status" value="1" checked>
                                                                <spring:message code="user.inactive"/>
                                                                <br/>
                                                                <input type="radio" name="status" value="3">
                                                                <spring:message code="user.interview.offer"/>
                                                                <br/>
                                                            </div>
                                                            <div class="col-sm-6">
                                                                <button type="submit"
                                                                        class="btn btn-primary pull-right">
                                                                    <spring:message code="user.submit"/></button>
                                                            </div>
                                                        </div>
                                                        <br/>
                                                        <textarea maxlength="500" name="commentary" class="form-control"
                                                                  rows="5" id="comment" required></textarea>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </sec:authorize>
                        </c:if>

                        <sec:authorize access="hasRole('HR')">
                            <c:if test="${user.statusId eq 3}">
                                <sec:authorize access="hasRole('HR')">
                                    <font class="clickable" color="blue" data-toggle="modal"
                                          data-target="#switchStatus">
                                        <b><spring:message code="user.switch.status"/></b>
                                    </font>

                                    <div id="switchStatus" class="modal fade" role="dialog">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close"
                                                            data-dismiss="modal">&times;</button>
                                                    <h4 class="modal-title">
                                                        <spring:message code="user.switch.status"/>
                                                    </h4>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="showactions"></div>
                                                    <div class="actions">
                                                        <form method="post">
                                                            <br/>
                                                            <div class="row">
                                                                <div class="col-sm-6">
                                                                    <input type="radio" name="status" value="1" checked>
                                                                    <spring:message code="user.inactive"/>
                                                                    <br/>
                                                                    <input type="radio" name="status" value="4">
                                                                    <spring:message code="user.job.offer"/>
                                                                </div>
                                                                <div class="col-sm-6">
                                                                    <button type="submit"
                                                                            class="btn btn-primary pull-right">
                                                                        <spring:message code="user.submit"/>
                                                                    </button>
                                                                </div>
                                                            </div>
                                                            <br/>

                                                            <textarea maxlength="500" name="commentary"
                                                                      class="form-control" rows="5" id="comment"
                                                                      required></textarea>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </sec:authorize>
                            </c:if>
                        </sec:authorize>
                    </c:if>
                </c:forEach>

                <sec:authorize access="hasRole('HR')">

                    <br/>
                    <div id="createHRreviewBtn" class="clickable">
                        <b onclick="getHRReviewProjects(${user.id})">
                            <spring:message code="create.review"/>
                        </b>
                    </div>

                </sec:authorize>

                <sec:authorize access="hasRole('ADMIN')">

                    <font class="clickable" data-toggle="modal" data-target="#chooseRoles"
                          color="blue"><b><spring:message code="user.choose.roles"/></b></font>

                    <div id="chooseRoles" class="modal fade" role="dialog">
                        <div class="modal-dialog modal-sm">

                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title"><spring:message code="user.choose.roles"/></h4>
                                </div>
                                <div class="modal-body">
                                    <div class="row">
                                        <form id="manageRolesForm" action="/manageRoles" method="post">
                                            <div class="col-sm-12">
                                            	<div style="color:red;" id="error-roles-text">
                                            		
                                            	</div>
                                                <c:forEach var="role" items="${roles}">
                                                    <div class="checkbox">
                                                        <label>

                                                            <c:if test="${role.have eq true}">
                                                                <c:if test="${role.active eq true}">
                                                                    <input type="checkbox" name="roles" disabled checked
                                                                           value="${role.id}">
                                                                    <input type="hidden" name="roles"
                                                                           value="${role.id}">
                                                                </c:if>
                                                                <c:if test="${!(role.active eq true)}">
                                                                    <input type="checkbox" name="roles" checked
                                                                           value="${role.id}">
                                                                </c:if>
                                                            </c:if>

                                                            <c:if test="${role.have eq false}">
                                                                <input type="checkbox" name="roles" value="${role.id}">
                                                            </c:if>
                                                                ${role.title}</label>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                            <br/>
                                            <input type="hidden" name="student" value="${user.id}">
                                            <div class="text-center">
                                                <button type="button" onclick="check()" class="btn btn-primary"><spring:message
                                                        code="user.submit"/></button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>

                </sec:authorize>

                <sec:authorize access="hasRole('MENTOR')">
                    <%--<c:if test="${user.statusId eq 2}">--%>
                    <br/><br/>
                    <div class="clickable" data-toggle="modal" data-target="#finReviewProject"
                         onClick="getMentorStudentProjects(${user.id});"><b><spring:message
                            code="final_Review.label.clckable"/></b>
                    </div>
                    <%--</c:if>--%>
                </sec:authorize>

            </div>
        </div>
        <br/>

        <c:forEach var="item" items="${user.roles}">
            <c:if test="${item eq 'Student'}">
                <div class="student-projects">
                    Loading projects(student role)...
                    <br/>
                </div>
                <script>
                    $(document).ready(function () {
                        createStudentProjectsInfo("${user.id}", '.student-projects');
                    });
                </script>
            </c:if>
            <c:if test="${item eq 'Mentor'}">
                <div class="mentor-projects">
                    Loading projects(mentor role)...
                    <br/>
                </div>
                <script>
                    $(document).ready(function () {
                        createMentorProjectsInfo("${user.id}", '.mentor-projects');
                    });
                </script>
            </c:if>
        </c:forEach>
    </div>
</div>

<!-- start add fin review modal -->
<div id="addFinReview" class="modal fade" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove"></span>
                </button>
                <h4 class="modal-title"><spring:message
                        code="final_review.label"/>: ${user.firstName} ${user.lastName}</h4>
            </div>
            <div class="modal-body">
                <form>
                    <table class="table" id="final-review-form-list">
                        <tr>
                            <td><spring:message code="loading.label"/></td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <span id="fin-rev-err" class="text-danger hidden">Unknown error</span>
                <button class="btn btn-default hidden" onclick="doFinalReview(${user.id})" type="button">
                    <spring:message code="btn.submit"/>
                </button>
            </div>
        </div>
    </div>
</div>
<!-- end add fin review modal -->

<!-- start add fin review choose project modal -->
<div id="finReviewProject" class="modal fade" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove"></span>
                </button>
                <h4 class="modal-title"><spring:message
                        code="final_review.label.project"/>: ${user.firstName} ${user.lastName} </h4>
            </div>
            <div class="modal-body">
                <form>
                    <table class="table" id="final-review-project-list">
                        <tr>
                            <td><spring:message code="loading.label"/></td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default hidden" onclick="getReviewForm(${user.id})" type="button">
                    <spring:message code="btn.submit"/>
                </button>
            </div>
        </div>
    </div>
</div>
<!-- end add fin review choose project modal -->

<!-- start fin review hr choose project modal -->
<div id="finReviewHRProject" class="modal fade" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove"></span>
                </button>
                <h4 class="modal-title"><spring:message
                        code="final_review.label.project"/>: ${user.firstName} ${user.lastName} </h4>
            </div>
            <div class="modal-body">
                <form>
                    <table class="table" id="final-review-hr-project-list">
                        <tr>
                            <td><spring:message code="loading.label"/></td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default hidden" onclick="getHRReviewForm(${user.id})" type="button">
                    <spring:message code="btn.submit"/>
                </button>
            </div>
        </div>
    </div>
</div>
<!-- end fin review hr choose project modal -->

<div id="createHRreviewModal" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Create review</h4>
            </div>
            <div class="modal-body">
                <div class="showactions"></div>
                    <label class="col-md-4 control-label">Select type</label>
                    <br class="col-lg-6"/>
                    <select class="form-control" name="reviewtype" id="reviewtype">
                    </select>
                    <input type="hidden" id="project-id-hr-rev">
                    <br/>
                    <br/>
                    <label class="col-md-4 control-label">Write review</label>
                    <textarea maxlength="500" name="commentary" class="form-control" rows="5" id="hr-rev-commentary"
                              required></textarea>
                    <br/>
                <span id="hr-rev-err" class="text-danger hidden">Comment needed!</span>
                    <button class="btn btn-primary pull-right" id="submitreviewBtn" onclick="doHRRequest(${user.id})"><spring:message
                            code="user.submit"/></button>
                    <br/>
                    <br/>
                </form>
            </div>
        </div>
    </div>
</div>
<c:set var="errorTextRoles">
    <spring:message code="error.role.zero"/>
</c:set>


<script type="text/javascript">
	function check(){
		if($("input[name='roles']").is(":checked")){
			$("#manageRolesForm").submit();
		}else{
			var error = "${errorTextRoles}";
			$("#error-roles-text").html('<div class="text-center">'+error+'</div>');
		}
	}
</script>
<%@include file="footer.jsp" %>