<%@include file="header.jsp"%>
<c:url var="projectsUrl" value="/projects/create-project" />
<div class="col-sm-6 col-sm-offset-3">
    <div class="col-sm-10 col-sm-offset-1 form-wrapper">
        <h2>Create new project</h2>
        <form:form class="default-form col-sm-12" id='create-project-form' method="post"
                   modelAttribute="projectForm" action="${projectsUrl}">
            <div class="form-group col-sm-12 required">
                <div class="col-sm-12">
                    <form:label for="project-name" path="name">Name</form:label>
                    <form:errors path="name" class="form-error"/>
                </div>
                <div class="col-sm-12">
                    <form:input type="text" class="form-control" id="project-name"
                                path="name" />
                    <label id="name-message"></label>
                </div>
            </div>
            <div class="form-group col-sm-12 required">
                <div class="col-sm-12">
                    <form:label for="start-date" path="startDate">Start date</form:label>
                    <form:errors path="startDate" class="form-error"/>
                </div>
                <div class="col-sm-6">
                    <form:input type="date" class="form-control" id="start-date"
                                path="startDate" />
                </div>
            </div>
            <div class="form-group col-sm-12 required">
                <div class="col-sm-12">
                    <form:label for="finish-date" path="finishDate">Finish date</form:label>
                    <form:errors path="finishDate" class="form-error"/>
                </div>
                <div class="col-sm-6">
                    <form:input type="date" class="form-control" id="finish-date"
                                path="finishDate" />	
                </div>
            </div>
            <div class="form-group col-sm-12 required">
                <div class="col-sm-12">
                    <form:label for="description" path="description">Description</form:label>
                    <form:errors path="description" class="form-error"/>
                </div>
                <div class="col-sm-12">
                    <form:textarea class="form-control" id="description"
                                   path="description"></form:textarea>
                    </div>
                </div>
                <div class="col-sm-12">
                    <button type="submit" class="btn btn-primary">Create</button>
                </div>
        </form:form>
    </div>
</div>
<%@include file="footer.jsp"%>
