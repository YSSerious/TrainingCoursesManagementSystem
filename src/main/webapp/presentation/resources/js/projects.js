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
        url: '/projects/create-project.ajax',
        data: JSON.stringify(project),
        dataType: 'json',
        timeout: 100000,
        statusCode: {
            200: function () {
                console.log("Success!");
            },
            405: function (response) {
                console.log(response);
            }
        }
    });
}
;
