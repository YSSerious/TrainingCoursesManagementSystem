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
    $.ajax($.extend({
        type: 'POST',
        contentType: "application/json",
        url: '/projects/add',
        data: JSON.stringify(project),
        dataType: 'json',
        timeout: 100000,
        statusCode: {
            200: function (response) {
                switch (response.code) {
                    case '200':
                        $('#createProjectModal').modal('hide');
                        cleanModalForm('#createProjectModal');
                        window.location.reload();
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
};
