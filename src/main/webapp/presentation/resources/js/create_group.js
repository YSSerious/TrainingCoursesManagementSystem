/**
 * Created by Nastasia on 05.11.2016.
 */

$("#create-group").click(function () {
    $("#createGroupModal").modal();
});


$("#createGroupModal form").on('submit', function (event) {
    event.preventDefault();
    var groupName = $('#createGroupModal form #group-name').val();
    var projectId = $('.certain-project').first().attr('data-project-id');
    $.ajax({
        type: "POST",
        url: "/groups/add.ajax",
        data: {"groupName": groupName, "projectId": projectId},
        dataType: 'json',
        timeout: 100000,
        statusCode: {
            200: function () {
                $('#createGroupModal').modal('hide');
            }
        }
    });
});

