$('#delete-project').on('click', function () {
    console.log('delete');
    var projectId = $('.certain-project').first().attr('data-project-id');
    $.ajax({
        type: "POST",
        url: "/projects/delete",
        data: {projectId: projectId},
        dataType: 'json',
        timeout: 100000,
        success: function (response) {
            switch (response.code) {
                case '200':
                    window.location.href = "/projects";
                    break;
            }
        }
    });
});