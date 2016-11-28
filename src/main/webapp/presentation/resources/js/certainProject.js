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
                    $('#CriteriaCheckBoxId').
                            append("<label class='checkbox-inline'><input type='checkbox' class='isCriteriaChecked'>" +
                            value.title + "</label>");
                });
                $("#GroupsCheckBoxId").children().remove();
                $.each(data.groupList, function (key, value) {
                    $('#GroupsCheckBoxId').
                            append("<label class='checkbox-inline'><input type='checkbox' class='isGroupChecked'>" +
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
    var meetingName = new RegExp('^[a-zA-Z0-9_-\\s]{3,15}$');
    var meetingPlace = new RegExp('^[a-zA-Z0-9_-\\s]{3,25}$');
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

    $('#newMeetingFormId').change(function () {
        if (meetingName.test($("#inputName").val()) &&
                meetingPlace.test($("#inputPlace").val()) && $("#inputDate").
                val() != "" && groupRE() && criteriaRE()) {
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
            data: {projectId: projectId, criteriaTitle: a.closest('tr').
                        find('td:first').text()},
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

        $("table#criterionTable").find("tr").each(function (index) {
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

function changeSpan(el) {
    var chevron = jQuery(el);
    if(chevron.children('.glyphicon').hasClass("glyphicon-chevron-down")){
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
            "<c:if test='" + data.rated + "'>" +
            "<div class='btn rmv-cr-btn col-md-1' type='button'" +
            " data-button='{\"id\":\"" + data.id + "\", \"title\": \"" +
            data.title + "\"}'>" +
            "<span class='glyphicon glyphicon-remove'></span>" +
            "</div>" +
            "</c:if>" +
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

/*
 * @summary On button click the data about modal form input creates and
 * the modal updates
 */
function turnEditModeOn(button) {
    button.on('click', function () {
        // If there are group of editable wrappers, get all of the headers
        var thisEditableWrapper = button.closest('.editable-wrapper');
        var editableGroup = thisEditableWrapper.closest('.editable-group');
        var headers = [];
        if (editableGroup.length) {
            var editableWrappers = editableGroup.children('.editable-wrapper');
            var editables = editableWrappers.children('.editable');
            headers = editables.map(function () {
                return $(this).find(':header').first().text();
            }).get();
        } else {
            headers.push(thisEditableWrapper.children('.editable').first()
                    .find(':header').first().text());
        }
        //Depending on modal purpose, change the modal
        switch (thisEditableWrapper.attr('id')) {
            case 'project-name':
                updateDataEditingModal({
                    title: 'Edit project\'s name',
                    target: 'project-name',
                    inputs: [
                        {
                            class: "col-sm-12",
                            label: 'Project name',
                            input: {
                                tag: 'input',
                                paired: false,
                                val: headers[0],
                                attrs: {
                                    type: 'text',
                                    id: 'project-name',
                                    class: 'form-control'
                                }
                            }
                        }]
                }
                );
                break;
            case 'project-startdate':
            case 'project-finishdate':
                updateDataEditingModal({
                    title: 'Edit project\'s start and finish dates',
                    target: 'project-dates',
                    inputs: [
                        {
                            wrapper: "col-sm-6",
                            class: "col-sm-11",
                            label: 'Start date',
                            input: {
                                tag: 'input',
                                paired: false,
                                val: headers[0],
                                attrs: {
                                    type: 'date',
                                    id: 'start-date',
                                    class: 'form-control'
                                }
                            }
                        },
                        {
                            wrapper: "col-sm-6",
                            class: "col-sm-11 col-sm-offset-1",
                            label: 'Finish date',
                            input: {
                                tag: 'input',
                                paired: false,
                                val: headers[1],
                                attrs: {
                                    type: 'date',
                                    id: 'finish-date',
                                    class: 'form-control'
                                }
                            }
                        }]
                });
                break;
            case 'project-description':
                updateDataEditingModal({
                    title: 'Edit project\'s description',
                    target: 'project-description',
                    inputs: [
                        {
                            class: "col-sm-12",
                            label: 'Description',
                            input: {
                                tag: 'textarea',
                                paired: true,
                                val: headers[0],
                                attrs: {
                                    id: 'project-description',
                                    class: 'form-control'
                                }
                            }
                        }]
                });
                break;
        }
        $('#editProjectDataModal').modal();
    });
}

/*
 * Update modal with received information
 */
function updateDataEditingModal(inputs) {
    var modal = $('#editProjectDataModal');
    //Set title
    modal.find('.modal-title').first().text(inputs.title);
    var modalForm = modal.find('form').first();
    modalForm.attr('data-target', inputs.target);
    var firstDivBeforeInputs = $(modalForm).children('div:last-child');
    //Create nesseccary elements based on received information
    $.each(inputs.inputs, function (i, currentInput) {
        var formGroup = $('<div></div')
                .attr('class', 'form-group')
                .addClass(currentInput.class);
        //Create wrappers with provided classes (for instance, col-sm-6)
        if ('wrapper' in currentInput) {
            var wrapper = $('<div></div>').insertBefore(firstDivBeforeInputs);
            wrapper.addClass('form-group-wrapper');
            wrapper.addClass(currentInput.wrapper);
            wrapper.append(formGroup);
        } else {
            formGroup.insertBefore(firstDivBeforeInputs);
        }
        //Create labels for inputs
        $('<label></label>').text(currentInput.label)
                .appendTo(formGroup);
        //Create inputs or textarea or whatever
        var input = $((currentInput.input.paired === true ? '<' +
                currentInput.input.tag + '>' : '') + '<' +
                currentInput.input.tag + '/>').
                appendTo($(formGroup));
        $.each(currentInput.input.attrs, function (i, d) {
            input.attr(i, d);
        });
        input.val(currentInput.input.val);
    });
    //Set datepickers to the date inputs if exists
    setStartAndFinishDatePickers();
}

//Empty modal with little timeout for nice hiding
$('#editProjectDataModal').on('hide.bs.modal', function () {
    setTimeout(function () {
        $.each($('#editProjectDataModal').find('form').first().
                children('div:not(:last)'), function () {
            $(this).remove();
        });
    }, 200);
});

$('#editProjectDataModal form').on('submit', function (e) {
    e.preventDefault();
    ajaxUpdateProject();
});

/*
 * Create ajax properties and call ajax sender function 
 * if new information was provided with the inputs
 */
function ajaxUpdateProject() {
    var form = $('#editProjectDataModal form');
    var target = form.attr('data-target');
    var ajaxParams = {};
    switch (target) {
        case 'project-name':
            var newName = form.find('#project-name').val();
            var nameHeader = $('.certain-project #project-name')
                    .find('.editable').first()
                    .find(':header').first();
            if (newName !== nameHeader.text()) {
                ajaxParams['url'] = '/updateProjectName';
                ajaxParams['data'] = {
                    projectId: form.attr('data-project-id'),
                    projectName: newName
                };
                ajaxParams['statusCode'] = {
                    200: function () {
                        nameHeader.text(newName);
                        $('#editProjectDataModal').modal('hide');
                    },
                    405: function (response) {
                        console.log(response);
                    }
                };
                sendProjectAjax(ajaxParams);
            }
            break;
        case 'project-dates':
            var newStartDate = form.find('#start-date').val();
            var startDateHeader = $('.certain-project #project-startdate')
                    .find('.editable').first()
                    .find(':header').first();
            if (newStartDate !== startDateHeader.text()) {
                ajaxParams['url'] = '/updateProjectStartDate';
                ajaxParams['data'] = {
                    projectId: form.attr('data-project-id'),
                    projectStartDate: newStartDate
                };
                ajaxParams['statusCode'] = {
                    200: function () {
                        startDateHeader.text(newStartDate);
                        $('#editProjectDataModal').modal('hide');
                    },
                    405: function (response) {
                        console.log(response);
                    }
                };
                sendProjectAjax(ajaxParams);
            }
            var newFinishDate = form.find('#finish-date').val();
            var finishDateHeader = $('.certain-project #project-finishdate')
                    .find('.editable').first()
                    .find(':header').first();
            if (newFinishDate !== finishDateHeader.text()) {
                ajaxParams['url'] = '/updateProjectFinishDate';
                ajaxParams['data'] = {
                    projectId: form.attr('data-project-id'),
                    projectFinishDate: newFinishDate
                };
                ajaxParams['statusCode'] = {
                    200: function () {
                        finishDateHeader.text(newFinishDate);
                        $('#editProjectDataModal').modal('hide');
                    },
                    405: function (response) {
                        console.log(response);
                    }
                };
                sendProjectAjax(ajaxParams);
            }
            break;
        case 'project-description':
            var newDescription = form.find('#project-description').val();
            var descriptionHeader = $('.certain-project #project-description')
                    .find('.editable').first()
                    .find(':header').first();
            if (newDescription !== descriptionHeader.text()) {
                ajaxParams['url'] = '/updateProjectDescription';
                ajaxParams['data'] = {
                    projectId: form.attr('data-project-id'),
                    projectDescription: newDescription
                };
                ajaxParams['statusCode'] = {
                    200: function () {
                        descriptionHeader.text(newDescription);
                        $('#editProjectDataModal').modal('hide');
                    },
                    405: function (response) {
                        console.log(response);
                    }
                };
                sendProjectAjax(ajaxParams);
            }
            break;
    }
}

/*
 * Apppend standart ajax properties to received one and send request
 */
function sendProjectAjax(ajaxParams) {
    $.extend(ajaxParams, {
        type: 'POST',
        dataType: 'json',
        timeout: 100000,
        beforeSend: function () {
            $('#editProjectDataModal').find('.loading').
                    css('display', 'inline-block');
        },
        complete: function () {
            $('#editProjectDataModal').find('.loading').css('display', 'none');
        }
    });
    $.ajax(ajaxParams);
}

// GROUPS EDITING AND DELETION
$('button.edit-group').each(function () {
    $(this).on('click', function (e) {
        var id = $(this).closest('tr').attr('id');
        var groupIdSpan = $('<span id="group-id"></span>').
                appendTo('#editGroupModal .modal-body');
        groupIdSpan.css("display", "none");
        groupIdSpan.attr('data-group-id', id);
        $('#editGroupModal').modal('show');
    });
});

$('#editGroupModal form').on('submit', function (e) {
    e.preventDefault();
    var groupId = $('#editGroupModal .modal-body #group-id').
            attr('data-group-id');
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
                $('#project-groups table tr#' + id + ' td:first-child a').
                        text(name);
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
            var groupIdSpan = $('<span id="group-id"></span>').
                    appendTo('#deleteGroupModal .modal-body');
            groupIdSpan.css("display", "none");
            groupIdSpan.attr('data-group-id', groupId);
            $('#deleteGroupModal').modal('show');
        }
    });
});

$('#deleteGroupModal #delete-group').on('click', function (e) {
    var groupId = $('#deleteGroupModal .modal-body #group-id').
            attr('data-group-id');
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
