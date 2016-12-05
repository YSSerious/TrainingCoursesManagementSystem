/**
 * Created by Nastasia on 05.11.2016.
 */

$("#create-group").click(function () {
    $("#createGroupModal").modal();
});


$("#createGroupModal form").on('submit', function (event) {
    event.preventDefault();
    var group = {};
    group['name'] = $('#createGroupModal form #group-name').val();
    group['projectId'] = $('.certain-project').first().attr('data-project-id');
    console.log('group: ', group);
    $.ajax($.extend({
        type: "POST",
        contentType: "application/json",
        url: "/groups/add",
        data: JSON.stringify(group),
        dataType: 'json',
        timeout: 100000,
        success:
                function (response) {
                    console.log(response);
                    switch (response.code) {
                        case '200':
                            $('#createGroupModal').modal('hide');
                            cleanModalForm('#createGroupModal');
                            showNewGroup(response.messages.groupId, group.name);
                            break;
                        case '204':
                            showModalErrors(response.messages, '#createGroupModal');
                    }
                }
    }, getBeforeAndAfterAjaxEvent('#createGroupModal')));
});

function showNewGroup(groupId, groupName) {
    var tr = $('<tr></tr>').insertAfter('#project-groups table tr:last');
    tr.attr("id", groupId);
    tr.attr("data-students-amount", 0);
    var name = $('<td></td>').appendTo(tr);
    var linkToGroup = $('<a>' + groupName + '</a>').appendTo(name);
    linkToGroup.attr("link", "groups/group?id=" + groupId);
    tr.append('<td>0</td>');
    tr.append('<td>No upcoming meetings</td>');
    var editButtonTd = $("<td></td>").appendTo(tr);
    var deleteButtonTd = $("<td></td>").appendTo(tr);
    var editButton = $("<button></button>").appendTo(editButtonTd);
    editButton.attr('class', 'btn btn-collapse edit-group glyphicon-button');
    $("<span></span>").attr('class', 'glyphicon glyphicon-edit').appendTo(editButton);
    addEditGroupClickListener(editButton);
    var deleteButton = $("<button></button>").appendTo(deleteButtonTd);
    deleteButton.attr('class', 'btn btn-collapse delete-group glyphicon-button');
    $("<span></span>").attr('class', 'glyphicon glyphicon-remove').appendTo(deleteButton);
    addDeleteGroupClickListener(deleteButton);
}