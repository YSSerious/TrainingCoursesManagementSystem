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
            200: function (data) {
                console.log('data: ', data);
                afterGroupCreate(data, groupName);
            }
        }
    });
});

function afterGroupCreate(groupId, groupName) {
    $('#createGroupModal').modal('hide');
    var tr = $('<tr></tr>').appendTo('#project-groups table');
    tr.attr("id", groupId);
    tr.attr("data-students-amount", 0);
    var name = $('<td></td>').appendTo(tr);
    var linkToGroup = $('<a>' + groupName + '</a>').appendTo(name);
    linkToGroup.attr("link", "groups/group?id=" + groupId);
    tr.append('<td>0</td>');
    tr.append('<td>No upcoming meetings</td>');
    var buttons = $("<td></td>").appendTo(tr);
    buttons.append('<button class="btn btn-collapse edit-group" class="edit-group">'
            + '<span class="glyphicon glyphicon-edit"></span>'
            + '</button>');
    buttons.append('<button class="btn btn-collapse delete-group" class="edit-group">'
            + '<span class="glyphicon glyphicon-remove"></span>'
            + '</button>');
}

