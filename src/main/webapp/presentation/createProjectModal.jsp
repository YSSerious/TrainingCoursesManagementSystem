<div id="createProjectModal" class="modal fade custom-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal"><span
                        class="glyphicon glyphicon-remove"></span></button>
                <h4 class="modal-title"><spring:message code="projects.createProject.title"/></h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form class="default-form" id='create-project-form' method="post">
                        <div class="form-group col-sm-12 required">
                            <div class="col-sm-12">
                                <label for="project-name" path="name"><spring:message code="projects.createProject.name"/></label>
                                <label path="name" class="form-error"></label>
                            </div>
                            <div class="col-sm-12">
                                <input type="text" class="form-control" id="project-name"
                                       path="name" />
                            </div>
                        </div>
                        <div class="form-group col-sm-12 required">
                            <div class="col-sm-12">
                                <label for="start-date" path="startDate"><spring:message code="projects.createProject.startDate"/></label>
                                <label path="startDate" class="form-error"></label>
                            </div>
                            <div class="col-sm-6">
                                <input type="date" class="form-control" id="start-date"
                                       path="startDate"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-12 required">
                            <div class="col-sm-12">
                                <label for="finish-date" path="finishDate"><spring:message code="projects.createProject.finishDate"/></label>
                                <label path="finishDate" class="form-error"></label>
                            </div>
                            <div class="col-sm-6">
                                <input type="date" class="form-control" id="finish-date"
                                       path="finishDate" />	
                            </div>
                        </div>
                        <div class="form-group col-sm-12 required">
                            <div class="col-sm-12">
                                <label for="description" path="description"><spring:message code="projects.createProject.description"/></label>
                                <label path="description" class="form-error"></label>
                            </div>
                            <div class="col-sm-12">
                                <textarea class="form-control" id="description"
                                          path="description"></textarea>
                            </div>
                        </div>
                        <div class="col-sm-12 bottom-controls">
                            <button type="submit" class="btn btn-primary pull-right"><spring:message code="btn.save"/></button>
                            <button type="button" class="btn btn-default pull-right" data-dismiss="modal"><spring:message code="btn.cancel"/></button>
                            <!--<div class="loading pull-right"></div>-->
                            <div class="loading pull-right">
                                <img src="<c:url value="/presentation/resources/imgs/ajax-loader.gif"/>"/>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>