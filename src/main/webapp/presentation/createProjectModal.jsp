<div id="createProjectModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal"><span
                        class="glyphicon glyphicon-remove"></span></button>
                <h4 class="modal-title">Create project</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <!--                <form class="default-form col-sm-12" id='create-project-form' method="post"
                                               modelAttribute="projectForm" action="${projectsUrl}" novalidate="">-->
                    <form class="default-form" id='create-project-form' method="post">
                        <div class="form-group col-sm-12 required">
                            <div class="col-sm-12">
                                <label for="project-name" path="name">Name</label>
                                <!--<errors path="name" class="form-error"/>-->
                            </div>
                            <div class="col-sm-12">
                                <input type="text" class="form-control" id="project-name"
                                       path="name" />
                                <label id="name-message"></label>
                            </div>
                        </div>
                        <div class="form-group col-sm-12 required">
                            <div class="col-sm-12">
                                <label for="start-date" path="startDate">Start date</label>
                                <!--                            <errors path="startDate" class="form-error"/>-->
                            </div>
                            <div class="col-sm-6">
                                <input type="date" class="form-control" id="start-date"
                                       path="startDate"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-12 required">
                            <div class="col-sm-12">
                                <label for="finish-date" path="finishDate">Finish date</label>
                                <!--<errors path="finishDate" class="form-error"/>-->
                            </div>
                            <div class="col-sm-6">
                                <input type="date" class="form-control" id="finish-date"
                                       path="finishDate" />	
                            </div>
                        </div>
                        <div class="form-group col-sm-12 required">
                            <div class="col-sm-12">
                                <label for="description" path="description">Description</label>
                                <!--<errors path="description" class="form-error"/>-->
                            </div>
                            <div class="col-sm-12">
                                <textarea class="form-control" id="description"
                                          path="description"></textarea>
                            </div>
                        </div>
                        <div class="col-sm-12">
                            <button type="submit" class="btn btn-primary">
                                Create
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>