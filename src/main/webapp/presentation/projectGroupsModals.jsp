<div id="createGroupModal" class="modal fade custom-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal"><span
                        class="glyphicon glyphicon-remove"></span></button>
                <h4 class="modal-title">Create group</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form class="default-form" id='create-group-form' method="post">
                        <label path="general" class="form-error"></label>
                        <div class="form-group col-sm-12 required">
                            <div class="col-sm-12">
                                <label for="group-name" path="name">Name</label>
                                <label path="name" class="form-error"></label>
                            </div>
                            <div class="col-sm-12">
                                <input type="text" class="form-control" id="group-name"
                                       path="name" />
                            </div>
                        </div>
                        <div class="col-sm-12 bottom-controls">
                            <button type="submit" class="btn btn-primary pull-right"><spring:message code="btn.save"/></button>
                            <button type="button" class="btn btn-default pull-right" data-dismiss="modal"><spring:message code="btn.cancel"/></button>
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

<div id="editGroupModal" class="modal fade custom-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal"><span
                        class="glyphicon glyphicon-remove"></span></button>
                <h4 class="modal-title">Edit group</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form class="default-form" id='edit-group-form' method="post">
                        <label path="general" class="form-error"></label>
                        <div class="form-group col-sm-12 required">
                            <div class="col-sm-12">
                                <label for="group-name" path="name">Name</label>
                                <!--<errors path="name" class="form-error"/>-->
                            </div>
                            <div class="col-sm-12">
                                <input type="text" class="form-control" id="group-name"
                                       path="name" />
                                <label id="name-message"></label>
                            </div>
                        </div> 
                        <div class="col-sm-12 bottom-controls">
                            <button type="submit" class="btn btn-primary pull-right"><spring:message code="btn.save"/></button>
                            <button type="button" class="btn btn-default pull-right" data-dismiss="modal"><spring:message code="btn.cancel"/></button>
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

<div id="deleteGroupModal" class="modal fade custom-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal"><span
                        class="glyphicon glyphicon-remove"></span></button>
                <h4 class="modal-title">Delete group</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">  
                    <div class="col-sm-12">
                        <h4>Are you sure? You can not undo this action.</h4>
                    </div>
                    <form>
                        <label path="general" class="form-error"></label>
                        <div class="col-sm-12 bottom-controls">
                            <button type="submit" class="btn btn-primary pull-right"><spring:message code="btn.delete"/></button>
                            <button type="button" class="btn btn-default pull-right" data-dismiss="modal"><spring:message code="btn.cancel"/></button>
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

<div id="cannotDeleteGroupModal" class="modal fade custom-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal"><span
                        class="glyphicon glyphicon-remove"></span></button>
                <h4 class="modal-title">Delete group</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">  
                    <h4>This group is not empty!</h4>
                </div>
            </div>
        </div>
    </div>
</div>