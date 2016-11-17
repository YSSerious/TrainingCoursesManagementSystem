<%@include file="header.jsp" %>
<script
        src="<c:url value="/presentation/resources/js/certainProject.js"/>"
        type="text/javascript">
</script>
${criterions}
<div class="container certain-project">
    <!-- Example row of columns -->
    <div class="row">
        <div class="col-md-12">
            <div class="page-header">
                <h3>
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
                <a href="/create-meeting?project=${project.id}" class="btn btn-default btn-xs pull-right-btn">Set
                    meetings schedule</a>
                <a href="" class="btn btn-default btn-xs pull-right-btn">Remove</a>
                <a href="" class="btn btn-default btn-xs pull-right-btn">Edit</a>
                <a href="" class="btn btn-default btn-xs pull-right-btn" id="createGroupButton"> Add</a>
            </div>
            <%@include file="createGroup.jsp" %>
            <ul class="list-group">
                <c:forEach items="${groups}" var="group">
                    <li class="list-group-item">
                        <a href="groups/group?id=${group.id}">${group.name}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <h2>
                Criteria List
            </h2>
            <div class="panel-group" id="panelGroupId">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title row">
                            <div data-toggle="collapse" data-target="#collapseIn" class="arrow col-md-1">
                                <span class="glyphicon glyphicon-chevron-down"></span>
                            </div>
                            <div role="button" class="btn add-criteria-btn btn-sm"
                                 id="showAvailableCriteria"
                                 data-toggle="modal"
                                 data-target="#showAvailableCriteriaModal">
                                <b>Add criteria</b>
                            </div>
                        </h4>
                    </div>
                    <div id="collapseIn" class="panel-collapse collapse">
                        <c:forEach items="${criterions}" var="criterion">
                            <div class="panel-body row" id="criteriaId1">
                                <div class="col-md-11">${criterion.title}</div>
                                <c:if test="${!criterion.rated}">
                                    <div class="btn rmv-cr-btn col-md-1 pull-right" type='button'
                                         data-button='{"title": "${criterion.title}"}'>
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </div>
                                </c:if>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div class="row">
        <div class="col-md-12">
            <h2>
                Attachments
            </h2>
            <div class="panel panel-btn">
                <div role="button" class="btn btn-default btn-xs pull-right-btn" id="rmv-att-main-btn">Remove</div>
                <div role="button" class="btn btn-default btn-xs pull-right-btn">Edit</div>
                <div role="button" class="btn btn-default btn-xs pull-right-btn" data-toggle="collapse"
                     data-target="#add-attachment-panel,#save-att-btn" id="add-att-btn">Add
                </div>
                <div role="button" class="btn btn-default btn-xs pull-right-btn btn-save collapse" id="save-att-btn">
                    Save
                </div>
            </div>
            <div class="panel add-panel collapse" id="add-attachment-panel">
                <div class="form-group">
                    <label for="usr">Name:</label>
                    <input type="text" class="form-control" id="att-name">
                </div>
                <div class="form-group">
                    <label for="usr">Link:</label>
                    <input type="text" class="form-control" id="att-link">
                </div>
            </div>
        </div>
    </div>
    <ul class="list-group" id="attachment-group">
        <c:forEach items="${attachments}" var="attachment">
            <li class="list-group-item">
                <a href="${attachment.attachmentScope}">${attachment.name}</a>
                <div class="btn rmv-btn" type='button' data-button='{"id_attachment": "${attachment.id}"}'>
                    <span class="glyphicon glyphicon-remove"></span>
                </div>
            </li>
        </c:forEach>
    </ul>
</div>
</div>
<div class="row">
    <div class="col-md-12">
        <h2>
            Attachments
        </h2>
        <div class="panel panel-btn">
            <div role="button" class="btn btn-default btn-xs pull-right-btn" id="rmv-att-main-btn">Remove</div>
            <div role="button" class="btn btn-default btn-xs pull-right-btn">Edit</div>
            <div role="button" class="btn btn-default btn-xs pull-right-btn" data-toggle="collapse"
                 data-target="#add-attachment-panel,#save-att-btn" id="add-att-btn">Add
            </div>
            <div role="button" class="btn btn-default btn-xs pull-right-btn btn-save collapse" id="save-att-btn">Save
            </div>
        </div>
        <div class="panel add-panel collapse" id="add-attachment-panel">
            <div class="form-group">
                <label for="usr">Name:</label>
                <input type="text" class="form-control" id="att-name">
            </div>
            <div class="form-group">
                <label for="usr">Link:</label>
                <input type="text" class="form-control" id="att-link">
            </div>
        </div>
    </div>
</div>
<ul class="list-group" id="attachment-group">
    <c:forEach items="${attachments}" var="attachment">
        <li class="list-group-item">
            <a href="${attachment.attachmentScope}">${attachment.name}</a>
            <div class="btn rmv-btn" type='button' data-button='{"id_attachment": "${attachment.id}"}'>
                <span class="glyphicon glyphicon-remove"></span>
            </div>
        </li>
    </c:forEach>
</ul>
</div>
</div>
<hr>
<!-- start showAvailableCriteria modal -->
<div id="showAvailableCriteriaModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal"><span
                        class="glyphicon glyphicon-remove"></span></button>
                <h4 class="modal-title">Add some criteria</h4>
                <hr>
                <div class="row">
                    <input type="text" id="search" placeholder="type search" class="col-md-offset-4">
                </div>
                <table id="criterionTable" class="table table-condensed table-hover table-responsive">
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
<!-- start categoryDeleteError modal -->
<div id="criteriaDeleteModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal"><span
                        class="glyphicon glyphicon-remove"></span></button>
                <h4 class="modal-title">This criteria was rated, and cannot be deleted.</h4>
                <button data-dismiss="modal" class="btn btn-link">Close</button>
            </div>
        </div>
    </div>
</div>
<!-- finish categoryDeleteError modal -->
</div>

<script>
    $(document).ready(function () {

        $("#save-att-btn").click(function (event) {
            var name = $("#att-name").val();
            var link = $("#att-link").val();
            if (name && link) {
                $("#attachment-group").prepend('<li class="list-group-item" style="color:#d9534f;">Attachment "' + name + '" is added</a></li>');

                $("#add-att-btn").click();
                $("#att-name").val('');
                $("#att-link").val('');

                $.ajax({
                    url: "/trainingCoursesManagementSystem/addProjectAttachment",
                    type: "POST",
                    data: {
                        "attachmentName": name,
                        "attachmentLink": link
                    },
                    success: function (savingStatus) {
                        console.log("Sent");
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        console.log("Failed");
                    }

                });
            }
        });

        $("#rmv-att-main-btn").click(function (event) {

            if ($('.rmv-btn').css('display') == 'none') {
                console.log("Hello");
                $('.rmv-btn').each(function () {
                    $(this).css("display", "inline-block");
                });
            } else {
                $('.rmv-btn').each(function () {
                    $(this).css("display", "none");
                });
            }
        });

        $('.rmv-btn').click(function () {
            //console.log("Hello");
            $(this).parent().remove();
            var id = $(this).data('button').id_attachment;
            $.ajax({
                url: "/trainingCoursesManagementSystem/removeProjectAttachment",
                type: "POST",
                data: {"id_attachment": id},
                success: function (savingStatus) {
                    console.log("att deleted");
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    console.log("att delete failed");
                }

            });
        });
    });
</script>
<script>
    var projectId = "${project.id}"
</script>
<%@include file="footer.jsp" %>