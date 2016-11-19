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
                $('#criteriaId-'+criteria.id).remove();
            },
            error: function (textStatus) {
                console.log(textStatus);
                $('#criteriaDeleteErrorModal').modal('show');

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

//Disable groups collapse if any of header button clicked
$('#project-groups .panel-heading > button').each(function () {
    $(this).on('click', function (e) {
        console.log('click');
        e.stopPropagation();
    });
});


function changeSpan(){
    if ($("#spanId").hasClass("glyphicon-chevron-down") ){
        $("#spanId").removeClass('glyphicon-chevron-down');
        $("#spanId").addClass('glyphicon-chevron-up');
    }else{
        $("#spanId").removeClass('glyphicon-chevron-up');
        $("#spanId").addClass('glyphicon-chevron-down');
    }
};

function buildResponseCriteria(data){
    return "<div class='panel-body' id='criteriaId-"+data.id+"'>" +
        "<div class='col-md-11'>"+data.title+"</div>" +
        "<c:if test='"+data.rated+"'>" +
        "<div class='btn rmv-cr-btn col-md-1' type='button'" +
        " data-button='{\"id\":\""+data.id+"\", \"title\": \""+data.title+"\"}'>" +
        "<span class='glyphicon glyphicon-remove'></span>" +
        "</div>" +
        "</c:if>" +
        "</div>";
};
