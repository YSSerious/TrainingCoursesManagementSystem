$(document).ready(function () {

    $("#saveCategory").click(function () {
        $.ajax({
            url: "/addCategory",
            type: "POST",
            data: {name: $("#categoryName").val(), "description": $("#categoryDescription").val()},
            statusCode: {
                200: function (data) {
                    console.log(data);
                        buildCategory(data.id, data.name, data.description);
                        $('#panelGroupId').append(newCategory);
                },
                409: function (textStatus) {
                    console.log(textStatus);
                    $('#responseErrorModal').html(textStatus.responseText);
                    $('#errorModal').modal('show');
                }
            }
        });
    });

    $("#saveCriteria").click(function () {
        $.ajax({
            url: "/saveCriteria",
            type: "POST",
            data: {categoryId: categoryId, name: $("#criteriaName").val()},
            statusCode: {
                200: function (data) {
                    console.log(data);
                        buildCriteria(data.id, data.title);
                        $('#collapseIn-' + data.categoryId).append(newCriteria);
                },
                409: function (textStatus) {
                    console.log(textStatus);
                    $('#responseErrorModal').html(textStatus.responseText);
                    $('#errorModal').modal('show');
                }
            }
        });
    });

    $("#deleteCategoryModal").click(function () {
        $.ajax({
            url: "/deleteCategory",
            type: "POST",
            data: {categoryId: categoryId},
            statusCode: {
                200: function (data) {
                    console.log(data);
                    $('#categoryPanelId-' + categoryId).remove();
                },
                409: function (textStatus) {
                        console.log(textStatus);
                        $('#responseErrorModal').html(textStatus.responseText);
                        $('#errorModal').modal('show');
                }
            }
            // , success: function () {
            // }
        });
    });

    $("#editCategoryModal").click(function () {
        $.ajax({
            url: "/editCategory",
            type: "POST",
            data: {
                id: categoryId,
                name: $("#editCategoryName").val(),
                description: $("#editCategoryDescription").val()
            },
            success: function (data) {
                console.log(data);
                $('#aEditId-' + categoryId).html("<b>" + data.name + "</b>");
                $('#divEditId-' + categoryId).html(data.description);
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
            statusCode: {
                200: function (data) {
                    console.log(data);
                    $('#criteriaId-' + criteriaId).remove();
                },
                409: function (textStatus) {
                    console.log(textStatus);
                    $('#responseErrorModal').html(textStatus.responseText);
                    $('#errorModal').modal('show');
                }
            }
        });
    });

    var namePattern = new RegExp('^[a-zA-Z0-9_-\\s]{3,15}$');
    var descriptionPattern = new RegExp('^[a-zA-Z0-9_-\\s]{3,25}$');

    $('#saveCategory').attr('disabled', true);
    $('#categoryName, #categoryDescription').on('keyup', function () {
        if (namePattern.test($("#categoryName").val()) && descriptionPattern.test($("#categoryDescription").val())) {
            $('#saveCategory').attr('disabled', false);
        } else {
            $('#saveCategory').attr('disabled', true);
        }
    });

    $('#saveCriteria').attr('disabled', true);
    $('#criteriaName').on('keyup', function () {
        if (namePattern.test($("#criteriaName").val())) {
            $('#saveCriteria').attr('disabled', false);
        } else {
            $('#saveCriteria').attr('disabled', true);
        }
    });

    $('#editCategoryModal').attr('disabled', true);
    $('#editCategoryName, #editCategoryDescription').on('keyup', function () {
        if (namePattern.test($("#editCategoryName").val()) && descriptionPattern.test($("#editCategoryDescription").val())) {
            $('#editCategoryModal').attr('disabled', false);
        } else {
            $('#editCategoryModal').attr('disabled', true);
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

function buildCriteria(criteriaId, criteriaTitle) {
    newCriteria = "<div class='panel-body' id='criteriaId-" + criteriaId + "'>" +
        "<div class='col-md-2'>" + criteriaTitle + "</div>" +
        "<button  class='btn btn-collapse'" +
        " data-toggle='modal'" +
        " data-target='#deleteCriteria'" +
        " onclick='setCriteria(" + criteriaId + ")'>" +
        "<span class='glyphicon glyphicon-remove'></span>" +
        "</button>" +
        "</div>";
}

function buildCategory(categoryId, categoryTitle, categoryDescription) {
    newCategory = "<div class='panel panel-default' id='categoryPanelId-" + categoryId + "'>" +
        "<div class='panel-heading'>" +
        "<h4 class='panel-title row'>" +
        "<div data-toggle='collapse' class='diver col-md-3 text-primary' id='aEditId-" + categoryId + "' " +
        "data-target='#collapseIn-" + categoryId + "'><b>" + categoryTitle + "</b>" +
        "</div>" +
        "<div id='divEditId-" + categoryId + "' class='col-md-3'>" + categoryDescription + "</div>" +
        "<button class='btn btn-lg pull-right-btn'" +
        " type='button'" +
        " data-toggle='modal'" +
        " data-target='#deleteCategory'" +
        " onclick='setCategory(" + categoryId + ")'>" +
        "<span class='glyphicon glyphicon-remove'></span>" +
        "</button>" +
        "<button class='btn btn-lg pull-right-btn'" +
        " type='button'" +
        " data-toggle='modal'" +
        " data-target='#editCategory'" +
        " onclick='setCategory(" + categoryId + ")'>" +
        "<span class='glyphicon glyphicon-edit'></span>" +
        "</button>" +
        "<button class='btn btn-lg pull-right-btn'" +
        " type='button'" +
        " data-toggle='modal'" +
        " data-target='#addCriteria'" +
        " onclick='setCategory(" + categoryId + ")'>" +
        "<span class='glyphicon glyphicon-plus'></span>" +
        "</button>" +
        "</h4>" +
        "</div>" +
        "<div id='collapseIn-" + categoryId + "' class='panel-collapse collapse'>" +
        "</div></div>";
}