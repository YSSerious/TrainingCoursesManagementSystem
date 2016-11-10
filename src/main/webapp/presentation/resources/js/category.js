// Name: <input type="text"  id="nameId"/> <br/>
//     Age :<input type="text" id="ageId"/> <br/>
//     <button class="btn btn-sm btn-success" type="submit" id="submitId">Submit</button>


$(document).ready(function () {



    // $("#submitId").click(function () {
    //     $.ajax({
    //         url: "/category/post",
    //         type: "POST",
    //         data: {name: $("#nameId").val(), "age": $("#ageId").val()},
    //         success: function (data) {
    //             console.log(data);
    //             $("#label").text(data);
    //         },
    //         error: function (textStatus) {
    //             console.log(textStatus);
    //         }
    //     });
    //
    // });
    //
    // function getAllCategory() {
    //     $.ajax({
    //         url: "/category/getAllCategory",
    //         type: "GET",
    //         success: function (data) {
    //             console.log(data);
    //             categories = data;
    //             display(categories);
    //
    //         },
    //         error: function (textStatus) {
    //             console.log(textStatus);
    //         }
    //     });
    // }
    //
    // $("#submitId").click(function(){
    //     categories.splice(0,1);
    //     document.getElementById('tbody').innerHTML = '';
    //     display(categories);
    // });

});

function showId(id) {
    console.log(id);
}

function deleteCategory() {
}
