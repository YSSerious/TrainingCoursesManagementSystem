/**
 * Created by Alex_Frankiv on 14.11.2016.
 */
var setAttendance = function (meetingId, studentId) {
    $.ajax({
        type: 'POST',
        data: {meetingId: meetingId, studentId: studentId},
        url: '/meeting/set_no_attendance',
        statusCode: {
            404: function (response) {
                console.log('404');
            },
            200: function (response) {
                console.log(response);
            }
        }
    });
};

var do_evaluate = function (reviewId) {
    var reviewComment = $('#review-comment').val();
    var results = [];
    $('ul').find('li').each(function (index) {
        if ($(this).find('.result-criterion-id').val())
            results.push({
                mark: {value: $(this).find('.result-mark').val()}, commentary: $(this).find('.result-comment').val(),
                criterion: {id: $(this).find('.result-criterion-id').val()}
            });
    });
    console.log(results);
    $.ajax({
        type: 'POST',
        data: JSON.stringify({results: results, reviewId: reviewId, reviewComment: reviewComment}),
        url: '/meeting/do_evaluate',
        dataType: 'json',
        contentType:'application/json',
        statusCode: {
            500: function (response) {
                console.log(response.responseText);
            },
            400: function (response) {
                console.log(response);
            },
            404: function (response) {
                console.log('404');
            },
            200: function (response) {
                console.log(response);
            }
        }
    });
};