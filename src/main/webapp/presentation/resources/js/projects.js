$('#createProject').on('click', function () {
    $('#createProjectModal').modal();
});

$('#create-project-form').submit(function (e) {
    e.preventDefault();
    createProjectAjax();
});

function createProjectAjax() {
    var project = {};
    project['name'] = $('#project-name').val();
    project['description'] = $('#description').val();
    project['startDate'] = $('#start-date').val();
    project['finishDate'] = $('#finish-date').val();
    console.log(project);
    $.ajax({
        type: 'POST',
        contentType: "application/json",
        url: '/projects/add',
        data: JSON.stringify(project),
        dataType: 'json',
        timeout: 100000,
        beforeSend: function () {
            $('#createProjectModal .form-error').empty();
            $('#createProjectModal').find('.loading').
                    css('display', 'inline-block');
        },
        complete: function () {
            $('#createProjectModal').find('.loading').css('display', 'none');
        },
        statusCode: {
            200: function (response) {
                switch (response.code) {
                    case '200':
                        $('#createProjectModal').modal('hide');
                        cleanForm();
                        break;
                    case '204':
                        showErrors(response.messages);
                        break;
                }
            },
            405: function (response) {
                console.log(response);
            }
        }
    });
}
;

function showErrors(errors) {
    if (!$.isEmptyObject(errors)) {
        $.each(errors, function (field, message) {
            $('.form-error[path="' + field + '"]').first().text(message);
        });
    }
}

function cleanForm() {
    $('#createProjectModal input').val('');
    $('#createProjectModal .form-error').empty();
}
