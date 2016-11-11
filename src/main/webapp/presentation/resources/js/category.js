$(document).ready(function () {

    $("#saveCategory").click(function () {
        $.ajax({
            url: "/addCategory",
            type: "POST",
            data: {name: $("#categoryName").val(), "description": $("#categoryDescription").val()},
            success: function (data) {
                console.log(data);
                buildCategory(data.id, data.name, data.description);
                $('#panelGroupId').append(newCategory);
            },
            error: function (textStatus) {
                console.log(textStatus);
            }
        });
    });

    $("#saveCriteria").click(function () {
        $.ajax({
            url: "/saveCriteria",
            type: "POST",
            data: {categoryId: categoryId, name: $("#criteriaName").val()},
            success: function (data) {
                console.log(data);
                buildCriteria(data.id, data.title);
                $('#collapseIn-'+data.categoryId).append(newCriteria);
            },
            error: function (textStatus) {
                console.log(textStatus);
            }
        });
    });

    $("#deleteCategoryModal").click(function () {
        $.ajax({
            url: "/deleteCategory",
            type: "POST",
            data: {categoryId: categoryId},
            success: function (data) {
                console.log(data);
                $('#categoryPanelId-'+categoryId).remove();
            },
            error: function (textStatus) {
                console.log(textStatus);
            }
        });
    });

    $("#editCategoryModal").click(function () {
        $.ajax({
            url: "/editCategory",
            type: "POST",
            data: {id: categoryId, name: $("#editCategoryName").val(), description: $("#editCategoryDescription").val()},
            success: function (data) {
                console.log(data);
                $('#aEditId-'+categoryId).html("<b>"+data.name+"</b>");
                $('#divEditId-'+categoryId).html(data.description);
            },
            error: function (textStatus) {
                console.log(textStatus);
            }
        });
    });

    $("#deleteCriteriaModal").click(function () {
        $.ajax({
            url: "/deleteCriteria",
            type: "POST",
            data: {criteriaId: criteriaId},
            success: function (data) {
                console.log(data);
                $('#criteriaId-'+criteriaId).remove();
            },
            error: function (textStatus) {
                console.log(textStatus);
            }
        });
    });

});

var categoryId;
var criteriaId;
var newCriteria;
var newCategory;

function setCriteria(id) {
    criteriaId = id;
}

function setCategory(id) {
    categoryId = id;
}

function buildCriteria(criteriaId, criteriaTitle){
    newCriteria="<div class='panel-body row' id='criteriaId-"+criteriaId+"'>" +
                "<div class='col-sm-2'>"+criteriaTitle+"</div>" +
                "<button  class='btn btn-danger btn-xs'" +
                " data-toggle='modal'" +
                " data-target='#deleteCriteria'" +
                " onclick='setCriteria("+criteriaId+")'>" +
                "<span class='glyphicon glyphicon-remove'></span>" +
                "</button>" +
                "</div>";
}


function buildCategory(categoryId, categoryTitle, categoryDescription){
    newCategory="<div class='panel panel-default' id='categoryPanelId-"+categoryId+"'>" +
                "<div class='panel-heading'>" +
                "<h4 class='panel-title row'>" +
                "<div data-toggle='collapse' class='diver col-sm-4' id='aEditId-"+categoryId+"' " +
                "data-target='#collapseIn-"+categoryId+"'><b>"+categoryTitle+"</b>" +
                "</div>" +
                "<div id='divEditId-"+categoryId+"' class='col-sm-7'>"+categoryDescription+"</div>" +
                "<button class='btn btn-primary btn-sm'" +
                " data-toggle='modal'" +
                " data-target='#editCategory'" +
                " onclick='setCategory("+categoryId+")'>" +
                "<span class='glyphicon glyphicon-edit'></span>" +
                "</button>" +
                "<button class='btn btn-danger btn-sm'" +
                " data-toggle='modal'" +
                " data-target='#deleteCategory'" +
                " onclick='setCategory("+categoryId+")'>" +
                "<span class='glyphicon glyphicon-remove'></span>" +
                "</button>" +
                "</h4>" +
                "</div>" +
                "<div id='collapseIn-"+categoryId+"' class='panel-collapse collapse'>" +
                "<div class='panel-body row'>" +
                "<div class='col-sm-2'><b>Add new Criteria</b></div>" +
                "<button  class='btn btn-primary btn-xs'" +
                " data-toggle='modal'" +
                " data-target='#addCriteria'" +
                " onclick='setCategory("+categoryId+")'>" +
                "<span class='glyphicon glyphicon-plus'></span>" +
                "</button></div></div></div>";
}
