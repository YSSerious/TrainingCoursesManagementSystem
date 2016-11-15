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

    $('.addButton').click(function () {
        console.log($(this < 'tr:first').text());
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


