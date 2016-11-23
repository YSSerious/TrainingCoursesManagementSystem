/**
 * Created by Алексей on 13.11.2016.
 */

$(document).ready(function () {

    $("#showGroupsAndCriteria").click(function () {
        clearMeetingTable();
        $.ajax({
            url: "/getCriteriaAndGroups",
            type: "GET",
            data: {projectId: projectId},
            success: function (data) {
                console.log(data);
                $("#CriteriaCheckBoxId").children().remove();
                $.each(data.criterions, function (key, value) {
                    $('#CriteriaCheckBoxId').append("<label class='checkbox-inline'><input type='checkbox' class='isCriteriaChecked'>" + value.title + "</label>");
                });
                $("#GroupsCheckBoxId").children().remove();
                $.each(data.groupList, function (key, value) {
                    $('#GroupsCheckBoxId').append("<label class='checkbox-inline'><input type='checkbox' class='isGroupChecked'>" + value.name + "</label>");
                });
            },
            error: function (textStatus) {
                console.log(textStatus);
            }
        });
    });

    $(function () {
        $("#checkAllCriteriaId").click(function () {
            if (!$("#checkAllCriteriaId").is(":checked")) {
                $(".isCriteriaChecked").removeAttr("checked");
            }
            else {
                $(".isCriteriaChecked").prop("checked", "checked");

            }
        });

    });

    $(function () {
        $("#checkAllGroupsId").click(function () {
            if (!$("#checkAllGroupsId").is(":checked")) {
                $(".isGroupChecked").removeAttr("checked");
            }
            else {
                $(".isGroupChecked").prop("checked", "checked");

            }
        });

    });

    function clearMeetingTable() {
        $("#checkAllCriteriaId").removeAttr("checked");
        $("#checkAllGroupsId").removeAttr("checked");
        $(".isGroupChecked").removeAttr("checked");
        $(".isCriteriaChecked").removeAttr("checked");
        $("#inputName").val("");
        $("#inputPlace").val("");
        $("#inputDate").val("");
    };

    $("#saveMeeting").click(function () {

        var dto = {
            name: '',
            place: '',
            date: '',
            criterions: [],
            groups: []
        };

        dto.name = $("#inputName").val();
        dto.place = $("#inputPlace").val();
        dto.date = $("#inputDate").val();

        $.each($('.isCriteriaChecked'), function (key, value) {
            if (value.checked) {
                dto.criterions.push(value.closest('label').textContent);
            }
        });

        $.each($('.isGroupChecked'), function (key, value) {
            if (value.checked) {
                dto.groups.push(value.closest('label').textContent);
            }
        });
        console.log(dto);

        $.ajax({
            url: "/saveMeeting",
            type: "POST",
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify({
                name: dto.name,
                place: dto.place,
                date: dto.date,
                crit: dto.criterions,
                gr: dto.groups
            }),
            success: function (data) {
                if (data == 1)
                    console.log("success");
                if (data == 0)
                    $('#meetingAddError').modal('show');
            },
            error: function (textStatus) {
                console.log(textStatus);
            }
        });
    });

    $('#saveMeeting').attr('disabled', true);
    var meetingName = new RegExp('^[a-z0-9_-]{3,15}$');
    var meetingPlace = new RegExp('^[a-z0-9_-]{3,25}$');
    var groupRE = function () {
        var check=false;
        $.each($('.isGroupChecked'), function (key, value) {
            if (value.checked)
                check=true;
        });
        return check;
    };
    var criteriaRE = function () {
        var check=false;
        $.each($('.isCriteriaChecked'), function (key, value) {
            if (value.checked)
                check=true;
        });
        return check;
    };

    $('input[type="text"]').on('keyup', function () {
        if (meetingName.test($("#inputName").val()) && meetingPlace.test($("#inputPlace").val()) && $("#inputDate").val()!="" && groupRE() && criteriaRE()){
            $('#saveMeeting').attr('disabled', false);
        } else {
            $('#saveMeeting').attr('disabled', true);
        }
    });


    $("#showAvailableCriteria").click(function () {
        $.ajax({
            url: "/getAvailableCriteria",
            type: "GET",
            data: {projectId: projectId},
            success: function (data) {
                console.log(data);
                appendTableRows(data);
            },
            error: function (textStatus) {
                console.log(textStatus);
            }
        });
    });

    $(document).on('click', '.addButton', function () {
        var a = $(this);
        $.ajax({
            url: "/addCriteria",
            type: "POST",
            data: {projectId: projectId, criteriaTitle: a.closest('tr').find('td:first').text()},
            success: function (data) {
                console.log(data);
                $('#collapseIn').append(buildResponseCriteria(data));
                a.parent().parent().remove();
            },
            error: function (textStatus) {
                console.log(textStatus);
            }
        });
    });

    $(document).on('click', '.rmv-cr-btn', function () {
        var criteria = $.parseJSON($(this).attr('data-button'));
        console.log(criteria.id);
        $.ajax({
            url: "/deleteProjectCriteria",
            type: "POST",
            data: {projectId: projectId, criteriaTitle: criteria.title},
            success: function (data) {
                console.log(data);
                $('#criteriaId-' + criteria.id).remove();
            },
            error: function (textStatus) {
                console.log(textStatus);
                $('#criteriaDeleteErrorModal').modal('show');

            }
        });
    });

    function appendTableRows(data) {
        $("#criterionTable > tbody:last").children().remove();
        $.each(data, function (key, value) {
            $('#criterionTable > tbody:last-child').append("<tr>" +
                "<td>" + value.title + "</td>" +
                "<td><button class='addButton btn-primary btn-sm'><span class='glyphicon glyphicon-plus'></span></button></td>" +
                "</tr>");
        });
    }

    $("#search").keyup(function () {
        var value = this.value.toLowerCase();

        $("table").find("tr").each(function (index) {
            if (!index)
                return;
            var id = $(this).find("td").first().text().toLowerCase();
            $(this).toggle(id.indexOf(value) !== -1);
        });
    });

});

$('#project-groups .panel-heading').first().on('click', function (e) {
    console.log(e.target.tagName);
    if ((e.target.tagName !== "BUTTON") && (e.target.tagName !== "B")) {
        $('#collapse-group').collapse('toggle');
    }
});

function changeSpan() {
    if ($("#spanId").hasClass("glyphicon-chevron-down")) {
        $("#spanId").removeClass('glyphicon-chevron-down');
        $("#spanId").addClass('glyphicon-chevron-up');
    } else {
        $("#spanId").removeClass('glyphicon-chevron-up');
        $("#spanId").addClass('glyphicon-chevron-down');
    }
}
;

function buildResponseCriteria(data) {
    return "<div class='panel-body' id='criteriaId-" + data.id + "'>" +
        "<div class='col-md-11'>" + data.title + "</div>" +
        "<c:if test='" + data.rated + "'>" +
        "<div class='btn rmv-cr-btn col-md-1' type='button'" +
        " data-button='{\"id\":\"" + data.id + "\", \"title\": \"" + data.title + "\"}'>" +
        "<span class='glyphicon glyphicon-remove'></span>" +
        "</div>" +
        "</c:if>" +
        "</div>";
}
;

// GROUPS EDITING AND DELETION
$('button.edit-group').each(function () {
    $(this).on('click', function (e) {
        var id = $(this).closest('tr').attr('id');
        var groupIdSpan = $('<span id="group-id"></span>').appendTo('#editGroupModal .modal-body');
        groupIdSpan.css("display", "none");
        groupIdSpan.attr('data-group-id', id);
//        $('#editGroupModal .modal-title')
//                .append($('#project-groups table tr#' + id + ' td:first-child a').text);
        $('#editGroupModal').modal('show');
    });
});

$('#editGroupModal form').on('submit', function (e) {
    e.preventDefault();
    var groupId = $('#editGroupModal .modal-body #group-id').attr('data-group-id');
    var groupName = $('#editGroupModal form #group-name').val();
    editGroupViaAjax(groupId, groupName);
});

function editGroupViaAjax(id, name) {
    console.log({"groupId": id, "groupName": name});
    $.ajax({
        type: 'POST',
        url: '/groups/edit.ajax',
        data: {groupId: id, groupName: name},
        dataType: 'json',
        timeout: 100000,
        statusCode: {
            200: function () {
                $('#editGroupModal').modal('hide');
                $('#project-groups table tr#' + id + ' td:first-child a').text(name);
            }
        }
    });
}

$('button.delete-group').each(function () {
    $(this).on('click', function (e) {
        var parentTr = $(this).closest('tr');
        var groupId = parentTr.attr('id');
        var studentsAmount = parseInt(parentTr.attr('data-students-amount'));
        if (studentsAmount > 0) {
            $('#cannotDeleteGroupModal').modal('show');
        } else {
            var groupIdSpan = $('<span id="group-id"></span>').appendTo('#deleteGroupModal .modal-body');
            groupIdSpan.css("display", "none");
            groupIdSpan.attr('data-group-id', groupId);
            $('#deleteGroupModal').modal('show');
        }
    });
});

$('#deleteGroupModal #delete-group').on('click', function (e) {
    var groupId = $('#deleteGroupModal .modal-body #group-id').attr('data-group-id');
    deleteGroupViaAjax(groupId);
});

function deleteGroupViaAjax(id) {
    $.ajax({
        type: 'POST',
        url: '/groups/delete.ajax',
        data: {groupId: id},
        dataType: 'json',
        timeout: 100000,
        statusCode: {
            200: function () {
                $('#deleteGroupModal').modal('hide');
                $('#project-groups table tr#' + id).remove();
            }
        }
    });
}
