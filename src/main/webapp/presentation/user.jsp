<%@include file="header.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
<div class="col-sm-11 col-sm-offset-1">
    <div class="col-sm-11">

        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                <strong>Danger!</strong> ${error}
            </div>
            <br/>
        </c:if>

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
                <b><spring:message code="user.roles"/>:</b> ${user.roles} <br/>
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
                            <font color="blue" data-toggle="modal"
                                  data-target="#projects-report-modal"><b onclick="report(${user.id})"><spring:message
                                    code="user.generate.report"/></b></font>
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
                                <font color="blue" data-toggle="modal"
                                      data-target="#switchStatus"><b><spring:message
                                        code="user.switch.status"/></b></font>

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
                                                        <br/> <input type="radio" name="status" value="3"
                                                                     checked><spring:message
                                                            code="user.interview.offer"/>
                                                        <button type="submit" style="margin-top: -5px;"
                                                                class="btn btn-primary pull-right"><spring:message
                                                                code="user.submit"/></button>
                                                        <br/>
                                                        <br/>
                                                        <br/>
                                                        <textarea name="commentary" class="form-control" rows="5"
                                                                  id="comment" required></textarea>
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

                                <font color="blue" data-toggle="modal"
                                      data-target="#switchStatus"><b><spring:message
                                        code="user.switch.status"/></b></font>

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
                                                        <br/> <input type="radio" name="status" value="1"
                                                                     checked><spring:message code="user.inactive"/>
                                                        <button type="submit" style="margin-top: -5px;"
                                                                class="btn btn-primary pull-right"><spring:message
                                                                code="user.submit"/></button>
                                                        <br/>
                                                        <br/>
                                                        <br/>
                                                        <textarea name="commentary" class="form-control" rows="5"
                                                                  id="comment" required></textarea>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                            </sec:authorize>
                            <sec:authorize access="hasRole('HR')">
                                <font color="blue" data-toggle="modal"
                                      data-target="#switchStatus"><b><spring:message
                                        code="user.switch.status"/></b></font>

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
                                                        <br/> <input type="radio" name="status" value="1"
                                                                     checked><spring:message code="user.inactive"/>
                                                        <input type="radio" name="status" value="3"><spring:message
                                                            code="user.interview.offer"/>
                                                        <button type="submit" class="btn btn-primary pull-right">
                                                            <spring:message code="user.submit"/></button>
                                                        <br/>
                                                        <br/>
                                                        <br/>
                                                        <textarea name="commentary" class="form-control" rows="5"
                                                                  id="comment" required></textarea>
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
                                    <font color="blue" data-toggle="modal"
                                          data-target="#switchStatus"><b><spring:message code="user.switch.status"/></b></font>

                                    <div id="switchStatus" class="modal fade" role="dialog">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close"
                                                            data-dismiss="modal">&times;</button>
                                                    <h4 class="modal-title"><spring:message
                                                            code="user.switch.status"/></h4>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="showactions"></div>
                                                    <div class="actions">
                                                        <form method="post">
                                                            <br/> <input type="radio" name="status" value="1"
                                                                         checked><spring:message code="user.inactive"/>
                                                            <input type="radio"
                                                                   name="status" value="4"><spring:message
                                                                code="user.job.offer"/>
                                                            <button type="submit" class="btn btn-primary pull-right">
                                                                <spring:message code="user.submit"/></button>
                                                            <br/>
                                                            <br/>
                                                            <br/>
                                                            <textarea name="commentary" class="form-control" rows="5"
                                                                      id="comment" required></textarea>
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

                    <br/><br/>
                    <button class="btn btn-lg btn-primary" data-toggle="modal" data-target="#addHRreview"
                            id="createHRreviewBtn" onclick="createHRreviewForm(${user.id})"><spring:message
                            code="create.review"/>
                    </button>
                    <%@include file="reviewHR.jsp" %>
                </sec:authorize>

                <sec:authorize access="hasRole('ADMIN')">

                    <font data-toggle="modal" data-target="#chooseRoles" color="blue"><b><spring:message
                            code="user.choose.roles"/></b></font>

                    <div id="chooseRoles" class="modal fade" role="dialog">
                        <div class="modal-dialog modal-sm">

                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title"><spring:message code="user.choose.roles"/></h4>
                                </div>
                                <div class="modal-body">
                                    <div class="row">
                                        <form action="/manageRoles" method="post">
                                            <div class="col-sm-12">
                                                <c:forEach var="role" items="${roles}">
                                                    <div class="checkbox">
                                                        <label><input type="checkbox" name="roles"
                                                        <c:forEach var="userRole" items="${user.roles}">
                                                                      <c:if test="${role.title eq userRole}">checked</c:if>
                                                        </c:forEach>
                                                                      value="${role.id}">${role.title}</label>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                            <br/>
                                            <input type="hidden" name="student" value="${user.id}">
                                            <center>
                                                <button type="submit" class="btn btn-primary"><spring:message
                                                        code="user.submit"/></button>
                                            </center>
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
                    <div class="clickable" data-toggle="modal"
                         data-target="#finReviewProject" onClick="getMentorStudentProjects(${user.id});"><b>Create final review</b>
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
                <h4 class="modal-title">Final Review</h4>
                <div>
                    <form>
                        <table class="table" id="final-review-form-list">
                            <tr>
                                <td><spring:message code="loading.label"/></td>
                            </tr>
                        </table>
                    </form>
                </div>
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
                <h4 class="modal-title">Choose project...</h4>
                <div>
                    <form>
                        <table class="table" id="final-review-project-list">
                            <tr>
                                <td><spring:message code="loading.label"/></td>
                            </tr>
                        </table>
                    </form>
                </div>
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

<div id="createHRreviewModal" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Create review</h4>
            </div>
            <div class="modal-body">
                <div class="showactions"></div>
                <br class="actions">
                <br method="post">
                <br><label class="col-md-4 control-label">Select type</label>
                <br class="col-lg-6">
                <select class="form-control" name="reviewtype" id="reviewtype" onchange="">
                    <option selected disabled></option>
                    <option value="G">General</option>
                    <option value="T">Technical</option>
                </select>

                </br>
                <br><label class="col-md-4 control-label">Write review</label>
                <textarea name="commentary" class="form-control" rows="5"
                          id="commentary" required></textarea>
                </br>
                <button type="submit" class="btn btn-primary pull-right" id="submitreviewBtn"><spring:message
                        code="user.submit"/></button>
                </br>
                </br>
            </div>


            </form>
        </div>
    </div>
</div>
</div>
</div>

<%@include file="footer.jsp" %>