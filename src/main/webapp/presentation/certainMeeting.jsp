<%@include file="header.jsp" %>
<div class="row container certain-project">
    <div class="col-md-12">
        <div class="page-header">
            <div class="row">
                <div class="col-md-6">
                    <div class="panel panel-default panel-horizontal top-panel">
                        <div class="panel-heading">
                            <h3 class="panel-title"><spring:message code="meeting.name"/></h3>
                        </div>
                        <div class="panel-body">${meeting.name}</div>
                    </div>
                    <div class="panel panel-default panel-horizontal top-panel">
                        <div class="panel-heading">
                            <h3 class="panel-title"><spring:message code="meeting.place"/></h3>
                        </div>
                        <div class="panel-body">${meeting.place}</div>
                    </div>
                    <div class="panel panel-default panel-horizontal bottom-panel">
                        <div class="panel-heading">
                            <h3 class="panel-title"><spring:message code="meeting.time"/></h3>
                        </div>
                        <div class="panel-body">${meeting.time}</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<c:if test="${!students.isEmpty() || !marks.isEmpty() || !absent.isEmpty()}">
<div id="table-listing">
    <div class="panel panel-primary  table-responsive table-scrollable">
        <div class="panel-heading">
            <h3 class="panel-title"><spring:message code="meeting.table"/></h3>
        </div>
        <table class="table table-striped table-bordered meeting"
               id="pagination" data-count-fixed-columns="1">
            <thead class="header">

            <tr>
                <th><spring:message code="meeting.student"/></th>
                <c:forEach items="${criteria}" var="criterion">
                    <th>${criterion.title}</th>
                </c:forEach>
            </tr>
            </thead>
            <tbody class="results">
            <c:forEach items="${students}" var="user">
                <tr>
                    <td><font size="3"><b> <a href="javascript:;"
                                              data-toggle="modal" class="open-Evaluate"
                            <sec:authorize access="hasRole('MENTOR')">
                                data-target="#evaluateModal${user.id}"
                                data-user="${user.firstName}"
                            </sec:authorize>
                    > ${user.firstName}
                            ${user.secondName} ${user.lastName} </a></b> </font></td>
                    <c:forEach items="${criteria}" var="criterion">
                        <td><font size="3">
                            <a href="#" data-toggle="tooltip" data-placement="top"
                               title="No mark, yet">-</a>
                        </font></td>
                    </c:forEach>
                </tr>
                    <!-- start evaluate Student modal -->
                    <div id="evaluateModal${user.id}" class="modal fade" tabindex="-1"
                         role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">&times;</button>
                                    <h4 class="modal-title col-xs-offset-2 col-sm-6"
                                        id="myModalLabel">Evaluate ${user.firstName} ${user.lastName}</h4>


                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary col-xs-offset-1"
                                            onclick="absent(${user.id},${meeting.id})">
                                        Absent
                                    </button>
                                </div>
                                <div class="modal-body edit-content">
                                    <ul class="list-group">
                                        <c:forEach items="${criteria}" var="criterion">
                                            <li class="list-group-item container-fluid">
                                                <div class="row">
                                                    <label class="col-xs-offset-1">${criterion.title}</label>
                                                </div>
                                                <div class="row result${user.id}">
                                                    <input class="col-xs-offset-2 col-sm-6 result-comment"
                                                           type="text" id="${criterion.id}"> <select
                                                        class="col-xs-offset-1">
                                                    <option selected>-</option>
                                                    <option>0</option>
                                                    <option>1</option>
                                                    <option>2</option>
                                                    <option>3</option>
                                                    <option>4</option>
                                                    <option>5</option>
                                                </select>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                    <label>General: </label>
                                    <textarea class="form-control" id="rev-com" rows="5"></textarea>
									<span id="rev-err${user.id}" class="text-danger hidden">Unknown
										error</span>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default"
                                            data-dismiss="modal">Close
                                    </button>
                                    <button type="button" class="btn btn-primary"
                                            onclick="guf228(${user.id}, ${meeting.id})">
                                        <spring:message code="btn.submit"/>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- finish evaluate Student modal -->
                </c:forEach>
                <c:forEach items="${marks}" var="entry">
                    <tr>
                        <td><font size="3"><b> <span id="fin-rev-err"
                                                     class="text-danger hidden">Unknown error</span> <a
                                href="javascript:;"
                                data-toggle="modal"
                                class="open-Evaluate"
                                <sec:authorize access="hasRole('MENTOR')">
                                    data-target="#evaluateModal${entry.key.id}"
                                    data-user="${entry.key.firstName}"
                                </sec:authorize>
                        >${entry.key.firstName}
                                ${entry.key.secondName} ${entry.key.lastName}</a></b></font></td>


                        <c:forEach items="${criteria}" var="criterion">
                            <td><font size="3">
                                <c:set var="check" value="false"/>
                                <c:forEach items="${entry.value}" var="mark" varStatus="status">

                                    <c:if test="${criterion.id == mark.criterionId}">
                                        <a href="#" data-toggle="tooltip" data-placement="top"
                                           title="${mark.commentary}">${mark.mark}</a>
                                        <c:set var="check" value="true"/>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${check eq 'false'}">
                                    <a href="#" data-toggle="tooltip" data-placement="top"
                                       title="No mark, yet">-</a>
                                </c:if>

                            </font></td>
                        </c:forEach>
                    </tr>
                    <!-- start evaluate Student modal -->
                    <div id="evaluateModal${entry.key.id}" class="modal fade"
                         tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                         aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">&times;</button>
                                    <h4 class="modal-title" id="myModalLabel">
                                        Evaluate ${entry.key.firstName} ${entry.key.lastName}</h4>
                                </div>
                                <div class="modal-body edit-content">
                                    <ul class="list-group">
                                        <c:forEach items="${criteria}" var="criterion">
                                            <c:set var="checkus" value="false"/>
                                            <c:forEach items="${entry.value}" var="mark">
                                                <c:if test="${criterion.id == mark.criterionId}">
                                                    <li class="list-group-item container-fluid">
                                                        <div class="row">
                                                            <label class="col-xs-offset-1">${mark.criterionName}</label>
                                                        </div>
                                                        <div class="row result${entry.key.id}">
                                                            <input class="col-xs-offset-2 col-sm-6 result-comment"
                                                                   type="text" required value="${mark.commentary}"
                                                                   id="${mark.criterionId}"> <select
                                                                class="col-xs-offset-1">
                                                            <option <c:if test="${mark.mark eq 0}">selected</c:if>>0
                                                            </option>
                                                            <option <c:if test="${mark.mark eq 1}">selected</c:if>>1
                                                            </option>
                                                            <option <c:if test="${mark.mark eq 2}">selected</c:if>>2
                                                            </option>
                                                            <option <c:if test="${mark.mark eq 3}">selected</c:if>>3
                                                            </option>
                                                            <option <c:if test="${mark.mark eq 4}">selected</c:if>>4
                                                            </option>
                                                            <option <c:if test="${mark.mark eq 5}">selected</c:if>>5
                                                            </option>
                                                        </select>
                                                        </div>
                                                    </li>
                                                    <c:set var="checkus" value="true"/>
                                                </c:if>
                                            </c:forEach>


                                            <c:if test="${checkus eq 'false'}">
                                                <li class="list-group-item container-fluid">
                                                    <div class="row">
                                                        <label class="col-xs-offset-1">${criterion.title}</label>
                                                    </div>
                                                    <div class="row result${entry.key.id}">
                                                        <input class="col-xs-offset-2 col-sm-6 result-comment"
                                                               type="text" id="${criterion.id}"> <select
                                                            class="col-xs-offset-1">
                                                        <option selected>-</option>
                                                        <option>0</option>
                                                        <option>1</option>
                                                        <option>2</option>
                                                        <option>3</option>
                                                        <option>4</option>
                                                        <option>5</option>
                                                    </select>
                                                    </div>
                                                </li>
                                            </c:if>
                                        </c:forEach>

                                    </ul>
                                    <label>General: </label>
                                <textarea class="form-control" id="rev-com" rows="5">
                                </textarea>
									<span id="rev-err" class="text-danger hidden">Unknown
										error</span></div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default"
                                            data-dismiss="modal">Close
                                    </button>
                                    <button type="button" class="btn btn-primary"
                                            onclick="guf228(${entry.key.id}, ${meeting.id})">
                                        <spring:message code="btn.submit"/>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- finish evaluate Student modal -->
                </c:forEach>
                <c:forEach items="${absent}" var="user">
                    <tr id="absent">
                        <td><font size="3"><b><a href="#" data-toggle="tooltip" data-placement="top"
                                                 title="Absent">${user.firstName}
                                ${user.secondName} ${user.lastName}</a></b> </font></td>
                        <c:forEach items="${criteria}" var="criterion">
                            <td></td>
                        </c:forEach>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:if>
<c:if test="${students.isEmpty() && marks.isEmpty() && absent.isEmpty()}">
<h1><spring:message code="meeting.noStudents"/></h1>
</c:if>
<br>


<div class="row">
    <div class="col-md-12">
        <h2><spring:message code="meeting.criteria"/></h2>
        <div class="panel-group" id="panelGroupId">
            <div class="panel panel-primary">
                <div class="panel-heading clearfix">
                    <div data-toggle="collapse" data-target="#collapseMIn" style="margin-top:5px; color:black"
                         class="arrow col-md-1 blc" onclick="changeSpan(this)">
                        <span id="spanId" class="glyphicon glyphicon-chevron-down"></span>
                    </div>
                    <sec:authorize access="hasRole('ADMIN')">
                        <button type="button" class="btn btn-default btn-sm pull-right"
                                id="showAvailableMeetingCriteria"
                                data-toggle="modal"
                                data-target="#showAvailableCriteriaModal">
                            <b>Add criteria</b>
                        </button>
                    </sec:authorize>
                </div>
                <div id="collapseMIn" class="panel-collapse collapse clearfix">
                    <ul class="list-group" id="collapseUL">
                        <c:forEach items="${criteria}" var="criterion">
                            <li class="list-group-item  clearfix" id="criteriaaMId-${criterion.id}">
                                <div class="col-md-11">${criterion.title}</div>
                                <sec:authorize access="hasRole('ADMIN')">
                                    <c:if test="${!criterion.rated}">
                                        <div class="btn rmv-crt-btn col-md-1" type='button'
                                             data-button='{"id":"${criterion.id}","title": "${criterion.title}"}'>
                                            <span class="glyphicon glyphicon-remove"></span>
                                        </div>
                                    </c:if>
                                </sec:authorize>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- start showAvailableCriteria modal -->
<div id="showAvailableCriteriaModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove"></span>
                </button>
                <h4 class="modal-title">Add some criteria</h4>
                <hr>
                <div class="row">
                    <input type="text" id="search" placeholder="type search"
                           class="col-md-offset-4">
                </div>
                <table id="criterionMeetingTable"
                       class="table table-condensed table-hover table-responsive">
                    <thead class="table-head">
                    <tr>
                        <th><b>Name</b></th>
                        <th><b>Add</b></th>
                    </tr>
                    </thead>
                    <tbody id="criteriaTableId">
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<!-- finish showAvailableCriteria modal -->
<!-- start criteriaDeleteError modal -->
<div id="criteriaMeetingDeleteErrorModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove"></span>
                </button>
                <h4 class="modal-title" id="criteriaMeetingDeleteError"></h4>
                <button data-dismiss="modal" class="btn btn-link">Close</button>
            </div>
        </div>
    </div>
</div>
<!-- finish criteriaDeleteError modal -->
<script>
    var meetingId = "${meeting.id}"
    function isMeetingReviewed() {
        $.ajax({
            url: "/isMeetingReviewed",
            type: "GET",
            data: {meetingId: meetingId},
            success: function (data) {
                console.log(data);
                if (data)
                    $('#showAvailableMeetingCriteria').attr('disabled', true);
            }
        });
    }

    isMeetingReviewed();
</script>
<script>
    $(document).on("click", ".open-Evaluate", function () {
        var user = $(this).data('user');
        $(".modal-body #user").val(user);
    });
</script>


<%@include file="footer.jsp" %>