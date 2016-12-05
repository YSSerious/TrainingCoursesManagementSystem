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
                    $('#CriteriaCheckBoxId').append("<label class='checkbox-inline'><input type='checkbox' class='isCriteriaChecked'>" +
                        value.title + "</label>");
                });
                $("#GroupsCheckBoxId").children().remove();
                $.each(data.groupList, function (key, value) {
                    $('#GroupsCheckBoxId').append("<label class='checkbox-inline'><input type='checkbox' class='isGroupChecked'>" +
                        value.name + "</label>");
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
            } else {
                $(".isCriteriaChecked").prop("checked", "checked");

            }
        });

    });

    $(function () {
        $("#checkAllGroupsId").click(function () {
            if (!$("#checkAllGroupsId").is(":checked")) {
                $(".isGroupChecked").removeAttr("checked");
            } else {
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
    }
    ;

    var meetingNamePattern = new RegExp('^[\\w\\s-]{2,20}$');
    var meetingPlacePattern = new RegExp('^[\\w\\s-]{5,35}$');

    var groupRE = function () {
        var check = false;
        $.each($('.isGroupChecked'), function (key, value) {
            if (value.checked)
                check = true;
        });
        return check;
    };
    var criteriaRE = function () {
        var check = false;
        $.each($('.isCriteriaChecked'), function (key, value) {
            if (value.checked)
                check = true;
        });
        return check;
    };

    $("#saveMeeting").click(function () {

        if (!meetingNamePattern.test($("#inputName").val())) {
            $('#inputNameErrorId').html("name must be 2-20 symbols, under/dash or space.");
            $('#formMeetingNameId').addClass('has-error');
            $('#inputNameErrorId').removeClass('hidden');
        }
        
        if ($("#inputName").val() == '') {
            $('#inputNameErrorId').html("required field");
            $('#inputNameErrorId').removeClass('hidden');
            $('#formMeetingNameId').addClass('has-error');
        }
        
        if (!meetingPlacePattern.test($("#inputPlace").val())) {
            $('#inputPlaceErrorId').html("name must be 2-20 symbols, under/dash or space.");
            $('#formMeetingPlaceId').addClass('has-error');
            $('#inputPlaceErrorId').removeClass('hidden');
        }
        
        if ($("#inputPlace").val() == '') {
            $('#inputPlaceErrorId').html("required field");
            $('#inputPlaceErrorId').removeClass('hidden');
            $('#formMeetingPlaceId').addClass('has-error');
        }
        
        if ($("#inputDate").val() == '') {
            $('#inputDateErrorId').html("required field");
            $('#inputDateErrorId').removeClass('hidden');
            $('#formMeetingDateId').addClass('has-error');
        }

        if (meetingNamePattern.test($("#inputName").val()) &&
            meetingPlacePattern.test($("#inputPlace").val()) && $("#inputDate").val() != "" && groupRE() && criteriaRE()) {
            $("#saveMeeting").attr('disabled', true);
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
                statusCode: {
                    200: function (data) {
                        console.log(data);
                        //Validation Success
                        $('#formMeetingNameId').removeClass('has-error');
                        $('#formMeetingPlaceId').removeClass('has-error');
                        $('#formMeetingDateId').removeClass('has-error');
                        $('#inputNameErrorId').addClass('hidden');
                        $('#inputPlaceErrorId').addClass('hidden');
                        $('#inputDateErrorId').addClass('hidden');
                        $("#saveMeeting").attr('disabled', false);
                        $('#meetingCreateModal').modal('toggle');
                    },
                    400: function (textStatus) {
                        console.log(textStatus);
                    },
                    409: function (textStatus) {
                        console.log(textStatus);
                        $('#projectErrorModal').html(textStatus.responseText);
                        $('#ErrorModal').modal('show');
                    }
                }
            });
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
                $('#collapseUL').append(buildResponseCriteria(data));
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
            statusCode: {
                200: function (data) {
                    console.log(data);
                    $('#criteriaId-' + criteria.id).remove();
                },
                409: function (textStatus) {
                    console.log(textStatus);
                    $('#projectErrorModal').html(textStatus.responseText);
                    $('#ErrorModal').modal('show');
                }
            }
        });
    });

    function appendTableRows(data) {
        $("#criterionTable > tbody:last").children().remove();
        $.each(data, function (key, value) {
            $('#criterionTable > tbody:last-child').append("<tr>" +
                "<td>" + value.title + "</td>" +
                "<td><button class='addButton btn-primary btn-xs'><span class='glyphicon glyphicon-plus'></span></button></td>" +
                "</tr>");
        });
    }

    $("#search").keyup(function () {
        var value = this.value.toLowerCase();

        $("table#criterionTable").find("tr").each(function (index) {
            if (!index)
                return;
            var id = $(this).find("td").first().text().toLowerCase();
            $(this).toggle(id.indexOf(value) !== -1);
        });
    });

})
;

$('#project-groups .panel-heading').first().on('click', function (e) {
    if ((e.target.tagName !== "BUTTON") && (e.target.tagName !== "B")) {
        $('#collapse-group').collapse('toggle');
    }
});

$(function () {
    var hiddenClass = 'glyphicon-chevron-down';
    var shownClass = 'glyphicon-chevron-up';

    $('#collapse-group').on('show.bs.collapse', function () {
        $(this).siblings('.panel-heading').first()
                .find('.' + hiddenClass).first()
                .removeClass(hiddenClass).
                addClass(shownClass);
    });
    $('#collapse-group').on('hide.bs.collapse', function () {
        $(this).siblings('.panel-heading').first()
                .find('.' + shownClass).first()
                .removeClass(shownClass).
                addClass(hiddenClass);
    });
});


function changeSpan(el) {
    var chevron = jQuery(el);
    if (chevron.children('.glyphicon').hasClass("glyphicon-chevron-down")) {
        chevron.children('.glyphicon').removeClass('glyphicon-chevron-down');
        chevron.children('.glyphicon').addClass('glyphicon-chevron-up');
    } else {
        chevron.children('.glyphicon').removeClass('glyphicon-chevron-up');
        chevron.children('.glyphicon').addClass('glyphicon-chevron-down');
    }
}

function buildResponseCriteria(data) {
    return "<li class='list-group-item  clearfix' id='criteriaId-" + data.id + "'>" +
        "<div class='col-md-11'>" + data.title + "</div>" +
        "<sec:authorize access=\"hasRole(\" ADMIN \")\">" +
        "<c:if test='" + data.rated + "'>" +
        "<div class='btn rmv-cr-btn col-md-1' type='button'" +
        " data-button='{\"id\":\"" + data.id + "\", \"title\": \"" +
        data.title + "\"}'>" +
        "<span class='glyphicon glyphicon-remove'></span>" +
        "</div>" +
        "</c:if>" +
        "</sec:authorize>" +
        "</li>";
};


$(document).ready(function () {
    if ($('#can-edit').length) {
        $('.editable-wrapper').each(function () {
            setEditableWrapperMouseEnter($(this));
        });
    }
});

/*
 * @summary On mouse enter wrapper edit button appears, on leave - disappears
 */
function setEditableWrapperMouseEnter(editableWrapper) {
    editableWrapper.on('mouseenter', function () {
        var button = $('<button class="glyphicon-button"></button>')
            .css('margin-left', '5px')
            .appendTo($(this).find('.editable-label').first());
        button.append('<span class="glyphicon glyphicon-edit"></span>');
        turnEditModeOn(button);
    }).on('mouseleave', function () {
        $(this).find('.editable-label').first()
            .find('.glyphicon-button').first().remove();
    });
}

function turnEditModeOn(button) {
    button.on('click', function () {
        fillEditProjectModalField("#project-name", "#project-name");
        fillEditProjectModalField("#project-startdate", "#start-date");
        fillEditProjectModalField("#project-finishdate", "#finish-date");
        fillEditProjectModalField("#project-description", "#description");
        setStartAndFinishDatePickers();
        $('#createProjectModal').modal();
    });
}

function fillEditProjectModalField(headerId, inputId) {
    var value = $('.certain-project ' + headerId)
            .find('.editable').first()
            .children(':header').first().text();
    $('#createProjectModal ' + inputId).val(value);
}

$('#createProjectModal form').on('submit', function (e) {
    e.preventDefault();
    ajaxUpdateProject();
});

function ajaxUpdateProject() {
    var project = {};
    project['id'] = window.location.pathname.split('/').pop();
    project['name'] = $('#createProjectModal #project-name').val();
    project['description'] = $('#createProjectModal #description').val();
    project['startDate'] = $('#createProjectModal #start-date').val();
    project['finishDate'] = $('#createProjectModal #finish-date').val();
    console.log(project);
    $.ajax($.extend({
        type: 'POST',
        contentType: "application/json",
        url: '/projects/update',
        data: JSON.stringify(project),
        dataType: 'json',
        timeout: 100000,
        statusCode: {
            200: function (response) {
                console.log(response);
                switch (response.code) {
                    case '200':
                        updateProjectPageInfo(project);
                        $('#createProjectModal').modal('hide');
                        cleanModalForm('#createProjectModal');
                        break;
                    case '204':
                        showModalErrors(response.messages, '#createProjectModal');
                        break;
                }
            },
            405: function (response) {
                console.log(response);
            }
        }
    }, getBeforeAndAfterAjaxEvent('#createProjectModal')));
}

function updateProjectPageInfo(project){
    updateOneHeader(project.name, "#project-name");
    updateOneHeader(project.startDate, "#project-startdate");
    updateOneHeader(project.finishDate, "#project-finishdate");
    updateOneHeader(project.description, "#project-description");
}

function updateOneHeader(projectProperties, header) {
    $('.certain-project ' + header)
            .find('.editable').first()
            .children(':header').first().text(projectProperties);
}

$('#createProjectModal').on('hidden.bs.modal', function() {
   cleanModalForm('#createProjectModal'); 
});

// GROUPS EDITING AND DELETION
function addEditGroupClickListener(button) {
    button.on('click', function (e) {
        var tr = $(this).closest('tr');
        $('#editGroupModal').attr('data-group-id', tr.attr('id'));
        $('#editGroupModal input[path="name"]')
                .first().val(
                tr.find('td').first().find('a').first().text());
        $('#editGroupModal').modal('show');
    });
}
$('button.edit-group').each(function () {
    addEditGroupClickListener($(this));
});

$('#editGroupModal form').on('submit', function (e) {
    e.preventDefault();
    var groupId = $('#editGroupModal').attr('data-group-id');
    var groupName = $('#editGroupModal form #group-name').val();
    var groupProjectId = $('.certain-project').first().attr('data-project-id');
    editGroupViaAjax(groupId, groupName, groupProjectId);
});

$('#editGroupModal').on('hide.bs.modal', function() {
    cleanModalForm('#editGroupModal'); 
});

function editGroupViaAjax(id, name, projectId) {
    var group = {};
    group['id'] = id;
    group['name'] = name;
    group['projectId'] = projectId;
    console.log('GROUP:', group);
    $.ajax($.extend({
        type: 'POST',
        contentType: "application/json",
        url: '/groups/edit',
        data: JSON.stringify(group),
        dataType: 'json',
        timeout: 100000,
        success:
                function (response) {
                    console.log(response);
                    switch (response.code) {
                        case '200':
                            $('#editGroupModal').modal('hide');
                            cleanModalForm('#editGroupModal');
                            $('#project-groups table tr#' + id +
                                    ' td:first-child a').
                                    text(name);
                            break;
                        case '204':
                            showModalErrors(response.messages, '#editGroupModal');
                            break;
                    }
                }
    }, getBeforeAndAfterAjaxEvent('#editGroupModal')));
}

function addDeleteGroupClickListener(button) {
    button.on('click', function (e) {
        var parentTr = $(this).closest('tr');
        var groupId = parentTr.attr('id');
        var studentsAmount = parseInt(parentTr.attr('data-students-amount'));
        if (studentsAmount > 0) {
            $('#cannotDeleteGroupModal').modal('show');
        } else {
            $('#deleteGroupModal').attr('data-group-id', groupId);
            $('#deleteGroupModal').modal('show');
        }
    });
}

$('button.delete-group').each(function () {
    addDeleteGroupClickListener($(this));
});

$('#deleteGroupModal form').on('submit', function (e) {
    e.preventDefault();
    var groupId = $('#deleteGroupModal').attr('data-group-id');
    deleteGroupViaAjax(groupId);
});

function deleteGroupViaAjax(id) {
    $.ajax($.extend({
        type: 'POST',
        url: '/groups/delete',
        data: {groupId: id},
        dataType: 'json',
        timeout: 100000,
        success:
                function (response) {
                    console.log(response.code);
                    switch (response.code) {
                        case '200':
                            $('#deleteGroupModal').modal('hide');
                            deleteGroupFromPage(id);
                            cleanModalForm('#deleteGroupModal');
                            break;
                        case '204':
                            showModalErrors(response.messages, '#deleteGroupModal');
                            break;
                    }
                }
    }, getBeforeAndAfterAjaxEvent('#deleteGroupModal')));
}

function deleteGroupFromPage(groupId) {
    var groupTr = $('div#project-groups table tr#' + groupId);
    groupTr.remove();
}