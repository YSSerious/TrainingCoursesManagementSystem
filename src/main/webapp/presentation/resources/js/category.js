$(document).ready(function () {

    $("#saveCategory").click(function () {
        $.ajax({
            url: "/addCategory",
            type: "POST",
            data: {name: $("#categoryName").val(), "description": $("#categoryDescription").val()},
            success: function (data) {
                console.log(data);
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

    $("#saveCriteria").click(function () {
        $.ajax({
            url: "/saveCriteria",
            type: "POST",
            data: {categoryId: categoryId, name: $("#criteriaName").val()},
            success: function (data) {
                console.log(data);
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


function setCriteria(id) {
    criteriaId = id;
}

function setCategory(id) {
    categoryId = id;
}
