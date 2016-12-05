$(document).ready(function () {

    var namePattern = new RegExp('^[\\w\\s-]{2,20}$');
    var descriptionPattern = new RegExp('^[\\w\\s-]{5,35}$');

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
        });
    });

    $("#editCategoryModal").click(function () {

        if(!namePattern.test($("#editCategoryName").val())){
            $('#editCategoryNameErrorId').html("name must be 2-20 symbols, under/dash or space.");
            $('#form-edit-categoryName-id').addClass('has-error');
            $('#editCategoryNameErrorId').removeClass('hidden');
        }

        if($("#editCategoryName").val()==''){
            $('#editCategoryNameErrorId').html("required field");
            $('#editCategoryNameErrorId').removeClass('hidden');
            $('#form-edit-categoryName-id').addClass('has-error');
        }

        if(!namePattern.test($("#editCategoryDescription").val())){
            $('#editCategoryDescriptionErrorId').html("description must be 5-35 symbols, under/dash or space.");
            $('#form-edit-categoryDescription-id').addClass('has-error');
            $('#editCategoryDescriptionErrorId').removeClass('hidden');
        }

        if($("#editCategoryDescription").val()==''){
            $('#editCategoryDescriptionErrorId').html("required field");
            $('#form-edit-categoryDescription-id').addClass('has-error');
            $('#editCategoryDescriptionErrorId').removeClass('hidden');
        }
        if (namePattern.test($("#editCategoryName").val()) && descriptionPattern.test($("#editCategoryDescription").val())) {
            $("#editCategoryModal").attr('disabled', true);
            $.ajax({
                url: "/editCategory",
                type: "POST",
                data: {
                    id: categoryId,
                    name: $("#editCategoryName").val(),
                    description: $("#editCategoryDescription").val()
                },
                statusCode: {
                    200: function (data) {
                        console.log(data);
                        $('#aEditId-' + categoryId).html("<b>" + data.name + "</b>");
                        $('#divEditId-' + categoryId).html(data.description);
                        //Validation Success
                        $('#form-edit-categoryName-id').removeClass('has-error');
                        $('#form-edit-categoryDescription-id').removeClass('has-error');
                        $('#editCategoryNameErrorId').addClass('hidden');
                        $('#editCategoryDescriptionErrorId').addClass('hidden');
                        $("#editCategoryModal").attr('disabled', false);
                        $('#editCategory').modal('toggle');
                    },
                    400: function (textStatus) {
                        console.log(textStatus);
                    },
                    409: function (textStatus) {
                        console.log(textStatus);
                    }
                }
            });
        }
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


    $("#saveCategory").click(function () {

        if(!namePattern.test($("#categoryName").val())){
            $('#categoryNameErrorId').html("name must be 2-20 symbols, under/dash or space.");
            $('#form-group-name-id').addClass('has-error');
            $('#categoryNameErrorId').removeClass('hidden');
        }

        if($("#categoryName").val()==''){
            $('#categoryNameErrorId').html("required field");
            $('#categoryNameErrorId').removeClass('hidden');
            $('#form-group-name-id').addClass('has-error');
        }

        if(!namePattern.test($("#categoryDescription").val())){
            $('#categoryDescriptionErrorId').html("description must be 5-35 symbols, under/dash or space.");
            $('#form-group-description-id').addClass('has-error');
            $('#categoryDescriptionErrorId').removeClass('hidden');
        }

        if($("#categoryDescription").val()==''){
            $('#categoryDescriptionErrorId').html("required field");
            $('#form-group-description-id').addClass('has-error');
            $('#categoryDescriptionErrorId').removeClass('hidden');
        }


        if (namePattern.test($("#categoryName").val()) && descriptionPattern.test($("#categoryDescription").val())) {
            $.ajax({
                url: "/addCategory",
                type: "POST",
                data: {name: $("#categoryName").val(), "description": $("#categoryDescription").val()},
                statusCode: {
                    200: function (data) {
                        console.log(data);
                        //Validation Success
                        $('#form-group-name-id').removeClass('has-error');
                        $('#form-group-description-id').removeClass('has-error');
                        $('#categoryNameErrorId').addClass('hidden');
                        $('#categoryDescriptionErrorId').addClass('hidden');
                        $('#addCategory').modal('toggle');

                        buildCategory(data.id, data.name, data.description);
                        $('#panelGroupId').append(newCategory);
                    },
                    400: function (textStatus) {
                        console.log(textStatus);
                    },
                    409: function (textStatus) {
                        console.log(textStatus);
                        $('#responseErrorModal').html(textStatus.responseText);
                        $('#errorModal').modal('show');
                    }
                }
            });
        }

    });


    $("#saveCriteria").click(function () {

        if(!namePattern.test($("#criteriaName").val())){
            $('#criteriaNameErrorId').html("name must be 2-20 symbols, under/dash or space.");
            $('#form-criteria-name-id').addClass('has-error');
            $('#criteriaNameErrorId').removeClass('hidden');
        }

        if($("#criteriaName").val()==''){
            $('#criteriaNameErrorId').html("required field");
            $('#criteriaNameErrorId').removeClass('hidden');
            $('#form-criteria-name-id').addClass('has-error');
        }

        if (namePattern.test($("#criteriaName").val())) {
            $.ajax({
                url: "/saveCriteria",
                type: "POST",
                data: {categoryId: categoryId, name: $("#criteriaName").val()},
                statusCode: {
                    200: function (data) {
                        console.log(data);
                        //Validation Success
                        $('#form-criteria-name-id').removeClass('has-error');
                        $('#criteriaNameErrorId').addClass('hidden');
                        $('#addCriteria').modal('toggle');

                        buildCriteria(data.id, data.title);
                        $('#collapseIn-' + data.categoryId).append(newCriteria);
                    },
                    400: function (textStatus) {
                        console.log(textStatus);
                    },
                    409: function (textStatus) {
                        console.log(textStatus);
                        $('#responseErrorModal').html(textStatus.responseText);
                        $('#errorModal').modal('show');
                    }
                }
            });
        }
    });

});

var categoryId;
var categoryName;
var categoryDescription;
var criteriaId;
var newCriteria;
var newCategory;

function setCriteria(id) {
    criteriaId = id;
}

function setCategory(id, name, description) {
    categoryId = id;
    categoryName = name;
    categoryDescription = description;

    console.log(id, name, description);
    $('#editCategoryName').val(categoryName);
    $('#editCategoryDescription').val(categoryDescription);
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

function buildCategory(id, name, desc) {
    newCategory = "<div class='panel panel-default' id='categoryPanelId-" + id + "'>" +
        "<div class='panel-heading'>" +
        "<h4 class='panel-title row'>" +
        "<div data-toggle='collapse' class='diver col-md-3 text-primary' id='aEditId-" + id + "' " +
        "data-target='#collapseIn-" + id + "'><b>" + name + "</b>" +
        "</div>" +
        "<div id='divEditId-" + id + "' class='col-md-3'>" + desc + "</div>" +
        "<button class='btn btn-lg pull-right-btn'" +
        " type='button'" +
        " data-toggle='modal'" +
        " data-target='#deleteCategory'" +
        " onclick='setCategory(\"" + id + "\", \"" + name + "\", \"" + desc + "\")'  >" +
        "<span class='glyphicon glyphicon-remove'></span>" +
        "</button>" +
        "<button class='btn btn-lg pull-right-btn'" +
        " type='button'" +
        " data-toggle='modal'" +
        " data-target='#editCategory'" +
        " onclick='setCategory(\"" + id + "\", \"" + name + "\", \"" + desc + "\")'>" +
        "<span class='glyphicon glyphicon-edit'></span>" +
        "</button>" +
        "<button class='btn btn-lg pull-right-btn'" +
        " type='button'" +
        " data-toggle='modal'" +
        " data-target='#addCriteria'" +
        " onclick='setCategory(\"" + id + "\", \"" + name + "\", \"" + desc + "\")'>" +
        "<span class='glyphicon glyphicon-plus'></span>" +
        "</button>" +
        "</h4>" +
        "</div>" +
        "<div id='collapseIn-" + id + "' class='panel-collapse collapse'>" +
        "</div></div>";
}