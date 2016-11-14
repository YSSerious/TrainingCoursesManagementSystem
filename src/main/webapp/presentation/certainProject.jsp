<%@include file="header.jsp" %>
<script
        src="<c:url value="/presentation/resources/js/certainProject.js"/>"
        type="text/javascript">
</script>
<div class="col-sm-11 col-sm-offset-1">
    <div class="col-sm-11">
        <div class="row">
            <div class="page-header">
                <h3 id="projectNameId">
                    ${project.name}
                </h3>
                <div class="row">
                    <div class="col-md-6">
                        <div class="panel panel-default panel-horizontal top-panel">
                            <div class="panel-heading">
                                <h3 class="panel-title">Start date</h3>
                            </div>
                            <div class="panel-body">${project.startDate}</div>
                        </div>
                        <div class="panel panel-default panel-horizontal bottom-panel">
                            <div class="panel-heading">
                                <h3 class="panel-title">Finish date</h3>
                            </div>
                            <div class="panel-body">${project.finishDate}</div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <h4 class="desc-label" style="margin-top: 0px;"> Description:</h4>
                        <p>${project.description}</p>
                    </div>
                </div>
            </div>

            <h2>
                Groups
            </h2>
            <div class="panel panel-btn">
                <a href="" class="btn btn-default btn-xs pull-right-btn">Set meetings schedule</a>
                <a href="" class="btn btn-default btn-xs pull-right-btn">Remove</a>
                <a href="" class="btn btn-default btn-xs pull-right-btn">Edit</a>
                <a href="" class="btn btn-default btn-xs pull-right-btn"> Add</a>
            </div>
            <ul class="list-group">
                <c:forEach items="${groups}" var="group">
                    <li class="list-group-item">
                        <a href="/groups/group?id=${group.id}">${group.name}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="row">
            <div class="top-info">
                <div class="row">
                    <h2 class="col-sm-11">Criteria List</h2>
                    <button class="btn btn-primary btn-sm" id="showAvailableCriteria"
                            data-toggle="modal"
                            data-target="#showAvailableCriteriaModal">
                        <span class="glyphicon glyphicon-plus"></span>
                    </button>
                </div>
            </div>
            <div class="panel-group">
                <c:forEach items="${criterions}" var="criterion">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title row">
                                <div class="panel-body col-sm-11">${criterion.title}</div>
                                <button class="btn btn-danger btn-sm">
                                    <span class="glyphicon glyphicon-remove"></span>
                                </button>
                            </h4>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="row">
            <h2>
                Attachments
            </h2>
            <div class="panel panel-btn">
                <a href="#" class="btn btn-default btn-xs pull-right-btn">Remove</a>
                <a href="#" class="btn btn-default btn-xs pull-right-btn">Edit</a>
                <a href="#" class="btn btn-default btn-xs pull-right-btn">Add</a>
            </div>
            <ul class="list-group">
                <c:forEach items="${attachments}" var="attachment">
                    <li class="list-group-item">
                        <a href="">${attachment.name}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <hr>
        <!-- start showAvailableCriteria modal -->
        <div id="showAvailableCriteriaModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal"><span
                                class="glyphicon glyphicon-remove"></span></button>
                        <table id="criterionTable" class="table table-condensed table-hover">
                            <thead class="table-head">
                            <tr>
                                <th>name</th>
                                <th>add</th>
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
    </div>
</div>
<%@include file="footer.jsp" %>

<script>
    var projectId = "${project.id}";
</script>