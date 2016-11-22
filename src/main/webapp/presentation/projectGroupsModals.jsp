<div id="createGroupModal" class="modal fade" tabindex="-1" role="dialog">
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

<div id="editGroupModal" class="modal fade" tabindex="-1" role="dialog">
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
                        <div class="col-sm-12">
                            <button type="button" class="btn btn-default" data-dismiss="modal">
                                Cancel
                            </button>
                            <button type="submit" class="btn btn-primary pull-right">
                                Submit
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>                 

<div id="deleteGroupModal" class="modal fade" tabindex="-1" role="dialog">
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
                    <button type="button" class="btn btn-default" data-dismiss="modal">
                        Cancel
                    </button>
                    <button type="button" id="delete-group" class="btn btn-primary pull-right">
                        Delete
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="cannotDeleteGroupModal" class="modal fade" tabindex="-1" role="dialog">
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