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

<div class="col-sm-11 col-sm-offset-1">
    <div class="col-sm-11">
        <div class="top-info">
            <div class="row">
                <h2 class="col-sm-3">Categories</h2>
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
                            <a data-toggle="collapse" class="col-sm-5" id="aEditId-${category.id}"
                               data-target="#collapseIn-${category.id}"><b>${category.name}</b></a>
                            <div id="divEditId-${category.id}" class="col-sm-6">${category.description}</div>
                            <button class="btn btn-primary"
                                    data-toggle="modal"
                                    data-target="#editCategory"
                                    onclick="setCategory(${category.id})">
                                <span class="glyphicon glyphicon-edit"></span>
                            </button>
                            <button class="btn btn-danger"
                                    data-toggle="modal"
                                    data-target="#deleteCategory"
                                    onclick="setCategory(${category.id})">
                                <span class="glyphicon glyphicon-remove"></span>
                            </button>
                        </h4>
                    </div>
                    <div id="collapseIn-${category.id}" class="panel-collapse collapse">
                         <div class="panel-body row">
                             <div class="col-sm-2"><b>Add new Criteria</b></div>
                            <button  class="btn btn-primary btn-xs"
                                     data-toggle="modal"
                                     data-target="#addCriteria"
                                     onclick="setCategory(${category.id})">
                                <span class="glyphicon glyphicon-plus"></span>
                            </button>
                       </div>
                        <c:forEach items="${category.criteria}" var="criteria" varStatus="loop">
                        <div class="panel-body row" id="criteriaId-${criteria.id}">
                                <div class="col-sm-2">${criteria.title}</div>
                                <button  class="btn btn-danger btn-xs"
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
        <div id="addCategory" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal">
                            <span class="glyphicon glyphicon-remove"></span>
                        </button>
                        <h4 class="modal-title">New Category</h4>
                        <div>
                            <form name="addCategoryForm">
                                <table class="table">
                                    <tr>
                                        <td>Name</td>
                                        <td><input type="text" id="categoryName"></td>

                                    </tr>
                                    <tr>
                                        <td>Description</td>
                                        <td><input type="text" id="categoryDescription"></td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-default" id="saveCategory" type="button" data-dismiss="modal">
                            Add
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <!-- end add category modal -->
        <!-- start add criteria modal -->
        <div id="addCriteria" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal">
                            <span class="glyphicon glyphicon-remove"></span>
                        </button>
                        <h4 class="modal-title">New Criteria</h4>
                        <div>
                            <form name="addCriteriaForm">
                                <table class="table">
                                    <tr>
                                        <td>Name</td>
                                        <td><input type="text" id="criteriaName"></td>

                                    </tr>
                                </table>
                            </form>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-default" id="saveCriteria" type="button" data-dismiss="modal">
                            Add
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <!-- end add criteria modal -->
        <!-- start delete category modal -->
        <div id="deleteCategory" class="modal fade">
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
        <div id="deleteCriteria" class="modal fade">
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
        <div id="editCategory" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal"><span
                                class="glyphicon glyphicon-remove"></span></button>
                        <h4 class="modal-title">Edit category</h4>

                        <div>
                            <table class="table">
                                <tr>
                                    <td>Name</td>
                                    <td><input id="editCategoryName" type="text"></td>
                                </tr>
                                <tr>
                                    <td>Description</td>
                                    <td><input id="editCategoryDescription" type="text"></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary" type="button" data-dismiss="modal" id="editCategoryModal">Save changes</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- finish edit category modal -->
    </div>
</div>


<%@include file="footer.jsp" %>




