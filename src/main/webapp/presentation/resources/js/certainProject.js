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
        var a = $(this);
        $.ajax({
            url: "/addCriteria",
            type: "POST",
            data: {projectId: projectId, criteriaTitle: a.closest('tr').find('td:first').text()},
            success: function (data) {
                console.log(data);
                buildResponseCriteria(data)
                $('#collapseIn').append(responseCriteria);
                a.parent().parent().remove();
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

    $("#search").keyup(function() {
        var value = this.value.toLowerCase();

        $("table").find("tr").each(function(index) {
            if (!index) return;
            var id = $(this).find("td").first().text().toLowerCase();
            $(this).toggle(id.indexOf(value) !== -1);
        });
    });
    
});
var responseCriteria;
function buildResponseCriteria(data){
    responseCriteria="<div class='panel-body row' id='criteriaId1'>" +
        "<div class='col-md-11'>"+data.title+"</div>" +
        "<div class='btn rmv-cr-btn col-md-1 pull-right' type='button'>" +
        "<span class='glyphicon glyphicon-remove'></span>" +
        "</div>" +
        "</div>";
};


// <div class='panel-body row' id='criteriaId1'>
//     <div class='col-md-11'>${criterion.title}</div>
//     <div class='btn rmv-cr-btn col-md-1 pull-right' type='button'>
//     <span class='glyphicon glyphicon-remove'></span>
//     </div>
//     </div>