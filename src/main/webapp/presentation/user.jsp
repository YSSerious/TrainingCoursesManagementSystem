<%@include file="header.jsp"%>
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

                        <b>Status:</b> ${user.statusTitle} <br/>
                        
                        <sec:authorize access="hasAnyRole('ADMIN', 'HR')">
								<font color="blue" data-toggle="modal"
									data-target="#projets-report-modal"><b onclick="report(${user.id})">Generate report</b></font>
									<br/>

								<div id="projets-report-modal" class="modal fade" role="dialog">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 class="modal-title">Generate report</h4>
											</div>
											<div id="project-report-back" class="modal-body">

											</div>
											<div id="criteria-report-back" class="modal-body">

											</div>
										</div>
									</div>
								</div>
                        </sec:authorize>

                        <sec:authorize access="hasRole('HR')">
                            <c:if test="${user.statusId eq 1}">
								<font color="blue" data-toggle="modal"
									data-target="#switchStatus"><b>Switch status</b></font>

								<div id="switchStatus" class="modal fade" role="dialog">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 class="modal-title">Switch status</h4>
											</div>
											<div class="modal-body">
												<div class="showactions"></div>
												<div class="actions">
													<form method="post">
														<br /> <input type="radio" name="status" value="3" checked>Interview
														offer
														<button type="submit" style="margin-top: -5px;"
															class="btn btn-primary pull-right">Submit</button><br/>
														<br />
														<br />
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
									data-target="#switchStatus"><b>Switch status</b></font>

								<div id="switchStatus" class="modal fade" role="dialog">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 class="modal-title">Switch status</h4>
											</div>
											<div class="modal-body">
												<div class="showactions"></div>
												<div class="actions">
													<form method="post">
														<br /> <input type="radio" name="status" value="1" checked>Inactive
														<button type="submit" style="margin-top: -5px;"
															class="btn btn-primary pull-right">Submit</button><br/>
														<br />
														<br />
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
									data-target="#switchStatus"><b>Switch status</b></font>

								<div id="switchStatus" class="modal fade" role="dialog">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 class="modal-title">Switch status</h4>
											</div>
											<div class="modal-body">
												<div class="actions">
													<div class="showactions"></div>
													<form method="post">
														<br /> <input type="radio" name="status" value="1" checked>Inactive
														<input type="radio" name="status" value="3">Interview
														offer
														<button type="submit" class="btn btn-primary pull-right">Submit</button><br/>
														<br />
														<br />
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
										data-target="#switchStatus"><b>Switch status</b></font>

									<div id="switchStatus" class="modal fade" role="dialog">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal">&times;</button>
													<h4 class="modal-title">Switch status</h4>
												</div>
												<div class="modal-body">
													<div class="showactions"></div>
													<div class="actions">
														<form method="post">
															<br /> <input type="radio" name="status" value="1"
																checked>Inactive <input type="radio"
																name="status" value="4">Job offer
															<button type="submit" class="btn btn-primary pull-right">Submit</button><br/>
															<br />
															<br />
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
							id="createHRreviewBtn" onclick="createHRreviewForm(${user.id})"><spring:message code="create.review"/>
					</button>
					<%@include file="reviewHR.jsp"%>
				</sec:authorize>

                <sec:authorize access="hasRole('ADMIN')">

					<font data-toggle="modal" data-target="#chooseRoles" color="blue"><b>Choose
							roles</b></font>

					<div id="chooseRoles" class="modal fade" role="dialog">
						<div class="modal-dialog modal-sm">

							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">&times;</button>
									<h4 class="modal-title">Choose roles</h4>
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
											<br />
											<input type="hidden" name="student" value="${user.id}">
											<center>
												<button type="submit" class="btn btn-primary">Submit</button>
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
                <button class="btn btn-default hidden" onclick="doFinalReview(${user.id})" type="button">
                <spring:message code="btn.submit"/>
                </button>
            </div>
        </div>
    </div>
</div>
<!-- end add fin review modal -->

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
				<button type="submit" class="btn btn-primary pull-right" id="submitreviewBtn">Submit</button>
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