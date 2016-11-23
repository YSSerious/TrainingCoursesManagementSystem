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
                $('#categoryDeleteError').modal('show');

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
                $('#criteriaDeleteError').modal('show');
            }
        });
    });

    $('#saveCategory').attr('disabled', true);
    var namePattern = new RegExp('^[a-z0-9_-]{3,15}$');
    var descriptionPattern = new RegExp('^[a-z0-9_-]{3,25}$');

    $('#categoryName, #categoryDescription').on('keyup', function () {
        if (namePattern.test($("#categoryName").val()) && descriptionPattern.test($("#categoryDescription").val())){
            $('#saveCategory').attr('disabled', false);
        } else {
            $('#saveCategory').attr('disabled', true);
        }
    });

    $('#saveCriteria').attr('disabled', true);
    $('#criteriaName').on('keyup', function () {
        if (namePattern.test($("#criteriaName").val())){
            $('#saveCriteria').attr('disabled', false);
        } else {
            $('#saveCriteria').attr('disabled', true);
        }
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
    newCriteria="<div class='panel-body' id='criteriaId-"+criteriaId+"'>" +
                "<div class='col-md-2'>"+criteriaTitle+"</div>" +
                "<button  class='btn btn-collapse'" +
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
                "<div data-toggle='collapse' class='diver col-md-3 text-primary' id='aEditId-"+categoryId+"' " +
                "data-target='#collapseIn-"+categoryId+"'><b>"+categoryTitle+"</b>" +
                "</div>" +
                "<div id='divEditId-"+categoryId+"' class='col-md-3'>"+categoryDescription+"</div>" +
                "<button class='btn btn-lg pull-right-btn'" +
                " type='button'" +
                " data-toggle='modal'" +
                " data-target='#deleteCategory'" +
                " onclick='setCategory("+categoryId+")'>" +
                "<span class='glyphicon glyphicon-remove'></span>" +
                "</button>" +
                "<button class='btn btn-lg pull-right-btn'" +
                " type='button'" +
                " data-toggle='modal'" +
                " data-target='#editCategory'" +
                " onclick='setCategory("+categoryId+")'>" +
                "<span class='glyphicon glyphicon-edit'></span>" +
                "</button>" +
                "<button class='btn btn-lg pull-right-btn'" +
                " type='button'" +
                " data-toggle='modal'" +
                " data-target='#addCriteria'" +
                " onclick='setCategory("+categoryId+")'>" +
                "<span class='glyphicon glyphicon-plus'></span>" +
                "</button>" +
                "</h4>" +
                "</div>" +
                "<div id='collapseIn-"+categoryId+"' class='panel-collapse collapse'>" +
                "</div></div>";
}
