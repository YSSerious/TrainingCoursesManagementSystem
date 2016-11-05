/**
 * Created by Nastasia on 05.11.2016.
 */
$(document).ready(function(){
    $("#createGroupButton").click(function(){
        $("#createGroupModal").modal();
    });
});


$(document).ready(function() {
    $("#submitButton").click(function(event) {
        $.ajax({
            url: "/groups/add",
            type: "POST",
            data: {"group_name" : $("#groupname").val(), "project_id" : 1}
        });
    });
});
