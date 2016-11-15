/**
 * Created by Alex_Frankiv on 14.11.2016.
 */
var setAttendance = function (meetingId, studentId) {
     $.ajax({
         type: 'POST',
         data: {meetingId: meetingId, studentId: studentId},
         url: 'meeting/set_no_attendance',
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