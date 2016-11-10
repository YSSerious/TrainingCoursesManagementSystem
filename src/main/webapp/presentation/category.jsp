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
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<div class="col-sm-11 col-sm-offset-1">
    <div class="col-sm-11">
        <div class="top-info">
            <div class="row">
                <h2 class="col-sm-3">Categories</h2>
                <button class="btn btn-primary pull-right"
                        data-toggle="modal"
                        data-target="#addCategory">New Category
                </button>
            </div>
        </div>
        <hr/>

        <c:forEach items="${categories}" var="category" varStatus="loop">
            <div class="panel-group">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title row">
                            <a data-toggle="collapse" class="col-sm-4"
                               data-target="#collapseIn-${category.id}">${category.name}</a>
                            <div class="col-sm-6">${category.description}</div>
                            <button class="btn btn-primary btn-sm col-sm-1">edit</button>
                            <button class="btn btn-danger btn-sm col-sm-1">delete</button>
                        </h4>
                    </div>
                    <div id="collapseIn-${category.id}" class="panel-collapse collapse">
                        <div class="panel-body row">
                            <c:forEach items="${category.criteria}" var="criteria" varStatus="loop">
                                <div class="col-sm-2">${criteria.title}</div>
                                <button class="btn btn-danger btn-xs ">delete</button>
                                <br>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>


        <div id="addCategory" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal"><span
                                class="glyphicon glyphicon-remove"></span>
                        </button>
                        <h4 class="modal-title">New Category</h4>
                        <div>
                            <form name="addCategoryForm">
                                <table class="table">
                                    <tr>
                                        <td>Name</td>
                                        <td><input type="text"></td>

                                    </tr>
                                    <tr>
                                        <td>Description</td>
                                        <td><input type="text"></td>
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

    </div>
</div>


<%@include file="footer.jsp" %>




