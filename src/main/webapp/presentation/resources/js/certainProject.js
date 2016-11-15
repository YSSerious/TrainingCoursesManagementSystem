/**
 * Created by Алексей on 13.11.2016.
 */

$(document).ready(function () {

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
        $.ajax({
            url: "/addCriteria",
            type: "POST",
            data: {projectId: projectId, criteriaTitle: $(this).closest('tr').find('td:first').text()},
            success: function (data) {
                console.log(data);
                buildResponseCriteria(data)
                $('#criteriaPanelId').append(responseCriteria);
                //$($(this).closest('tr')).remove();
            },
            error: function (textStatus) {
                console.log(textStatus);
            }
        });
    });

    $(document).on('click', '.delButton', function () {
        $.ajax({
            url: "/deleteProjectCriteria",
            type: "POST",
            data: {projectId: projectId, criteriaTitle: $(this).closest('div').text()},
            success: function (data) {
                console.log(data);
                //$(this).closest('.del').remove();
            },
            error: function (textStatus) {
                console.log(textStatus);
            }
        });
    });
    
    function appendTableRows(data){
        $("#criterionTable > tbody:last").children().remove();
        $.each(data, function(key, value){
            $('#criterionTable > tbody:last-child').append("<tr>" +
                "<td>"+value.title+"</td>" +
                "<td><button class='addButton btn-primary btn-sm'><span class='glyphicon glyphicon-plus'></span></button></td>" +
                "</tr>");
        });
    }
    
});
var responseCriteria;
function buildResponseCriteria(data){
    responseCriteria="<div class='panel panel-default'>" +
    "<div class='panel-heading'>" +
    "<h4 class='panel-title row'>" +
    "<div class='panel-body col-sm-11'>"+data.title+"</div>" +
    "<button class='delButton btn-danger btn-sm'>" +
    "<span class='glyphicon glyphicon-remove'></span>" +
    "</button></h4></div></div>";
};
