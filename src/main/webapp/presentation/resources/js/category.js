$(document).ready(function() {

    var categories=[];

    // $("#submitId").click(function() {
    //     $.ajax({
    //         url: "/category/post",
    //         type: "POST",
    //         data: {name: $("#nameId").val(),"age" : $("#ageId").val()},
    //         success:function(data)
    //         {
    //             console.log(data);
    //             $("#label").text(data);
    //         },
    //         error: function(textStatus)
    //         {
    //           console.log(textStatus);
    //         }
    //     });
    //
    // });

    getAllCategory();

    function getAllCategory() {
        $.ajax({
            url: "/category/getAllCategory",
            type: "GET",
            success:function(data)
            {
                console.log(data);
                categories=data;
                display(categories);

            },
            error: function(textStatus)
            {
                console.log(textStatus);
            }
        });
    }

    $("#submitId").click(function(){
        categories.splice(0,1);
        document.getElementById('tbody').innerHTML = '';
        display(categories);
    });

    function display(data){
        $.each(data, function (i) {
            var eachrow = "<tr>"
                + "<td>" + data[i].id + "</td>"
                + "<td>" + data[i].name + "</td>"
                + "<td>" + data[i].description + "</td>"
                + "<td><button name=\"data[i].id\" class=\"btn btn-info\" type=\"button\"><span class=\"glyphicon glyphicon-edit\"></span></button></td>"
                + "<td><button id=\"data[i].id\" class=\"btn btn-danger\" type=\"button\"><span class=\"glyphicon glyphicon-remove\"></span></button></td>"
                + "</tr>";
            $('#tbody').append(eachrow);
        });
    }

});