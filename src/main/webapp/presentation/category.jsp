<%--
  Created by IntelliJ IDEA.
  User: Алексей
  Date: 07.11.2016
  Time: 18:37
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp" %>
<script
        src="<c:url value="/presentation/resources/js/category.js"/>"
        type="text/javascript">
</script>
<style>
    .diver {
        cursor: pointer;
    }
    .pull-right-btn {
        float: right!important;
        margin-right: 10px;
        margin-top: 5px;
        margin-bottom: 5px;
        background-color: #f5f5f5;
        color: rgb(166, 166, 166);
        font-weight: 500;
        border-color: #f5f5f5;
    }
    .btn-collapse{
        /*float: right!important;*/
        /*margin-right: 10px;*/
        margin-top: 5px;
        margin-bottom: 5px;
        background-color: white;
        color: rgb(166, 166, 166);
        /*font-weight: 500;*/
        border-color: white;
    }
</style>

<div class="col-md-12">
    <div class="col-md-12">
        <div class="top-info">
            <div class="row">
                <h2 class="col-md-3">Categories</h2>
                <button class="btn btn-primary pull-right"
                        id="newCategoryId"
                        data-toggle="modal"
                        data-target="#addCategory">New Category
                </button>
            </div>
        </div>
        <hr/>
        <div class="panel-group" id="panelGroupId">
            <c:forEach items="${categories}" var="category" varStatus="loop">
                <div class="panel panel-default" id="categoryPanelId-${category.id}">
                    <div class="panel-heading">
                        <h4 class="panel-title row">
                            <div data-toggle="collapse" class="diver col-md-3 text-primary" id="aEditId-${category.id}"
                                 data-target="#collapseIn-${category.id}"><b>${category.name}</b>
                            </div>
                            <div id="divEditId-${category.id}" class="col-md-3">${category.description}</div>
                            <button class="btn btn-lg pull-right-btn"
                                    type="button"
                                    data-toggle="modal"
                                    data-target="#deleteCategory"
                                    onclick="setCategory('${category.id}','${category.name}','${category.description}')">
                                <span class="glyphicon glyphicon-remove"></span>
                            </button>
                            <button class="btn btn-lg pull-right-btn"
                                    type="button"
                                    data-toggle="modal"
                                    data-target="#editCategory"
                                    onclick="setCategory('${category.id}','${category.name}','${category.description}')">
                                <span class="glyphicon glyphicon-edit"></span>
                            </button>
                            <button class="btn btn-lg pull-right-btn"
                                    type="button"
                                    data-toggle="modal"
                                    data-target="#addCriteria"
                                    onclick="setCategory('${category.id}','${category.name}','${category.description}')">
                                <span class="glyphicon glyphicon-plus"></span>
                            </button>
                        </h4>
                    </div>
                    <div id="collapseIn-${category.id}" class="panel-collapse collapse">
                        <c:forEach items="${category.criteria}" var="criteria" varStatus="loop">
                            <div class="panel-body" id="criteriaId-${criteria.id}">
                                <div class="col-md-2">${criteria.title}</div>
                                <button class="btn btn-collapse"
                                        data-toggle="modal"
                                        data-target="#deleteCriteria"
                                        onclick="setCriteria(${criteria.id})">
                                    <span class="glyphicon glyphicon-remove"></span>
                                </button>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- start add category modal -->
        <div id="addCategory" class="modal fade" data-backdrop="static">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal">
                            <span class="glyphicon glyphicon-remove"></span>
                        </button>
                        <h4 class="modal-title">New Category</h4>
                        <div>
                            <form name="addCategoryForm" id="addCategoryFormId" class="form-horizontal">
                                    <div class="form-group" id="form-group-name-id">
                                        <label class="control-label col-sm-2" for="categoryName">Name</label>
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control" id="categoryName" placeholder="Enter name">
                                        </div>
                                        <h5 id="categoryNameErrorId" class="text-danger hidden"></h5>
                                    </div>
                                    <div class="form-group" id="form-group-description-id">
                                        <label class="control-label col-sm-2" for="categoryDescription">Description</label>
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control" id="categoryDescription" placeholder="Enter description">
                                        </div>
                                        <h5 id="categoryDescriptionErrorId" class="text-danger hidden"></h5>
                                    </div>
                            </form>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary" id="saveCategory" type="button" >
                            Add
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <!-- end add category modal -->
        <!-- start add criteria modal -->
        <div id="addCriteria" class="modal fade" data-backdrop="static">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal">
                            <span class="glyphicon glyphicon-remove"></span>
                        </button>
                        <h4 class="modal-title">New Criteria</h4>
                        <div>
                            <form name="addCriteriaForm" id="addCriteriaFormId" class="form-horizontal">
                                    <div class="form-group" id="form-criteria-name-id">
                                        <label class="control-label col-sm-2" for="criteriaName">Name</label>
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control" id="criteriaName" placeholder="Enter name">
                                        </div>
                                        <h5 id="criteriaNameErrorId" class="text-danger hidden"></h5>
                                    </div>
                            </form>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary" id="saveCriteria" type="button">
                            Add
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <!-- end add criteria modal -->
        <!-- start delete category modal -->
        <div id="deleteCategory" class="modal fade" data-backdrop="static">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal"><span
                                class="glyphicon glyphicon-remove"></span></button>
                        <h4 class="modal-title">Are you sure?</h4>
                        <button data-dismiss="modal" id="deleteCategoryModal" class="btn btn-link">Yes</button>
                        <button data-dismiss="modal" class="btn btn-link">NO</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- finish delete category modal -->
        <!-- start delete criteria modal -->
        <div id="deleteCriteria" class="modal fade" data-backdrop="static">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal"><span
                                class="glyphicon glyphicon-remove"></span></button>
                        <h4 class="modal-title">Are you sure?</h4>
                        <button data-dismiss="modal" id="deleteCriteriaModal" class="btn btn-link">Yes</button>
                        <button data-dismiss="modal" class="btn btn-link">NO</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- finish delete criteria modal -->
        <!-- start edit category modal -->
        <div id="editCategory" class="modal fade" data-backdrop="static">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal"><span
                                class="glyphicon glyphicon-remove"></span></button>
                        <h4 class="modal-title">Edit category</h4>
                        <%--<div>--%>
                            <%--<table class="table">--%>
                                <%--<tr>--%>
                                    <%--<td>Name</td>--%>
                                    <%--<td><input id="editCategoryName" type="text"></td>--%>
                                <%--</tr>--%>
                                <%--<tr>--%>
                                    <%--<td>Description</td>--%>
                                    <%--<td><input id="editCategoryDescription" type="text"></td>--%>
                                <%--</tr>--%>
                            <%--</table>--%>
                        <%--</div>--%>
                        <div>
                            <form name="editCategoryForm" id="editCategoryFormId" class="form-horizontal">
                                <div class="form-group" id="form-edit-categoryName-id">
                                    <label class="control-label col-sm-2" for="editCategoryName">Name</label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control" id="editCategoryName" placeholder="Enter name">
                                    </div>
                                    <h5 id="editCategoryNameErrorId" class="text-danger hidden"></h5>
                                </div>
                                <div class="form-group" id="form-edit-categoryDescription-id">
                                    <label class="control-label col-sm-2" for="editCategoryDescription">Name</label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control" id="editCategoryDescription" placeholder="Enter description">
                                    </div>
                                    <h5 id="editCategoryDescriptionErrorId" class="text-danger hidden"></h5>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary" type="button" id="editCategoryModal">Save
                            changes
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <!-- finish edit category modal -->
        <!-- start Error modal -->
        <div id="errorModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal"><span
                                class="glyphicon glyphicon-remove"></span></button>
                        <h4 class="modal-title" id="responseErrorModal"></h4>
                        <button data-dismiss="modal" class="btn btn-link">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- finish Error modal -->
    </div>
</div>


<%@include file="footer.jsp" %>