<div id="editProjectDataModal" class="modal fade custom-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal"><span
                        class="glyphicon glyphicon-remove"></span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form class="default-form" method="post" data-project-id=${project.id}>
                        <div class="col-sm-12 bottom-controls">
                            <button type="submit" class="btn btn-primary pull-right">
                                Save
                            </button>
                            <button type="button" class="btn btn-default pull-right" data-dismiss="modal">
                                Cancel
                            </button>
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