<%@include file="header.jsp" %>
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
                <b>Email:</b> ${user.email} <br/>
                <b>First name:</b> ${user.firstName} <br/>
                <b>Last name:</b> ${user.lastName} <br/>
                <b>Second name:</b> ${user.secondName} <br/>
                <b>Roles:</b> ${user.roles} <br/>
                <b>Active:</b> ${user.active} <br/>
            </div>

            <div class="col-sm-4">

                <c:forEach var="item" items="${user.roles}">
                    <c:if test="${item eq 'Student'}">

                        <b>Status:</b> ${user.statusTitle} <br/><br/>

                        <sec:authorize access="hasRole('HR')">
                            <c:if test="${user.statusId eq 1}">
                                <div class="showactions">
                                    <button type="submit" class="btn btn-primary">Switch status</button>
                                </div>
                                <div class="actions">
                                    <form method="post">
                                        <br/>
                                        <input type="radio" name="status" value="3" checked>Interview offer
                                        <button type="submit" style="margin-top:-5px;"
                                                class="btn btn-primary pull-right">Submit
                                        </button>
                                        <br/><br/>
                                        <textarea name="commentary" class="form-control" rows="2" id="comment"
                                                  required></textarea>
                                    </form>
                                </div>
                            </c:if>
                        </sec:authorize>

                        <c:if test="${user.statusId eq 2}">
                            <sec:authorize access="hasRole('MENTOR')">
                                <div class="showactions">
                                    <button type="submit" class="btn btn-primary">Switch status</button>
                                </div>
                                <div class="actions">
                                    <form method="post">
                                        <br/>
                                        <input type="radio" name="status" value="1" checked>Inactive
                                        <button type="submit" style="margin-top:-5px;"
                                                class="btn btn-primary pull-right">Submit
                                        </button>
                                        <br/><br/>
                                        <textarea name="commentary" class="form-control" rows="2" id="comment"
                                                  required></textarea>
                                    </form>
                                </div>
                            </sec:authorize>
                            <sec:authorize access="hasRole('HR')">
                                <div class="actions">
                                    <div class="showactions">
                                        <button type="submit" class="btn btn-primary">Switch status</button>
                                    </div>
                                    <form method="post">
                                        <br/>
                                        <input type="radio" name="status" value="1" checked>Inactive
                                        <input type="radio" name="status" value="3">Interview offer
                                        <button type="submit" class="btn btn-primary pull-right">Submit</button>
                                        <br/><br/>
                                        <textarea name="commentary" class="form-control" rows="2" id="comment"
                                                  required></textarea>
                                    </form>
                                </div>
                            </sec:authorize>
                        </c:if>

                        <sec:authorize access="hasRole('HR')">
                            <c:if test="${user.statusId eq 3}">
                                <sec:authorize access="hasRole('HR')">
                                    <div class="showactions">
                                        <button type="submit" class="btn btn-primary">Switch status</button>
                                    </div>
                                    <div class="actions">
                                        <form method="post">
                                            <br/>
                                            <input type="radio" name="status" value="1" checked>Inactive
                                            <input type="radio" name="status" value="4">Job offer
                                            <button type="submit" class="btn btn-primary pull-right">Submit</button>
                                            <br/><br/>
                                            <textarea name="commentary" class="form-control" rows="2" id="comment"
                                                      required></textarea>
                                        </form>
                                    </div>
                                </sec:authorize>
                            </c:if>
                        </sec:authorize>
                    </c:if>
                </c:forEach>

                <sec:authorize access="hasRole('ADMIN')">
                    <b>Choose role:</b>
                    <div class="row">
                        <form action="/manageRoles" method="post">
                            <div class="col-sm-6">
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
                            <div class="col-sm-6">
                                <br/>
                                <br/>
                                <input type="hidden" name="student" value="${user.id}">
                                <button type="submit" class="btn btn-primary">Submit</button>
                            </div>
                        </form>
                    </div>
                </sec:authorize>
                <sec:authorize access="hasRole('MENTOR')">
                    <%--<c:if test="${user.statusId eq 2}">--%>
                        <br/><br/>
                        <button class="btn btn-lg btn-primary" data-toggle="modal" data-target="#addFinReview"
                                id="mentorFinalReviewBtn" onclick="getReviewForm(${user.id})">
                            Create final review
                        </button>
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
                <br/>
                <div>
                    <form>
                        <table class="table" id="final-review-form-list">
                            <tr>
                                <td><spring:message code="loading.label"/> </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <span id="fin-rev-err" class="text-danger hidden">Unknown error</span>
                <button class="btn btn-default" onclick="doFinalReview(${user.id})" type="button">
                <spring:message code="btn.submit"/>
                </button>
            </div>
        </div>
    </div>
</div>
<!-- end add fin review modal -->

<script>
    $(document).ready(function () {
        $(".actions").hide();
        $(".showactions").click(function () {
            $(".actions").show();
            $(".showactions").hide();
        });
    });
</script>
<%@include file="footer.jsp" %>