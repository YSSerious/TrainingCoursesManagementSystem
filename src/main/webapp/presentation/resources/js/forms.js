/**
 * Get date plus number of days in yy-mm-dd format
 * @param date
 * @param plus_days
 * @returns {string}
 */
function getFormattedDate(date, plus_days){
    date = date == undefined ? new Date() : date;
    plus_days = plus_days == undefined ? 0 : plus_days;
    date.setDate(date.getDate() + plus_days);
    var day = date.getDate();
    var month = date.getMonth() + 1;
    day = day > 9 ? day : '0' + day;
    month = month > 9 ? month : '0' + month;
    return date.getFullYear() + '-' + month + '-' + day;
};

$(document).ready(function () {
    $('#start-date').val(getFormattedDate());
    $('#finish-date').val(getFormattedDate());
    // Case: if finish date must be at least tomorrow
    // $('#finish-date').val(getFormattedDate(new Date(), 1));
});

/**
 * Set jquery datepickers
 */
$(document).ready(function () {
    $('#start-date').datepicker({
        dateFormat: "yy-mm-dd",
        firstDay: 1,
        minDate: new Date(),
        onSelect: function () {
            var next_date = $('#start-date').val();
            // Case: if finish date must be at least tomorrow
            // var next_date = getFormattedDate($.datepicker.parseDate("yy-mm-dd", $('#start-date').val()), 1);
            $('#finish-date').val(next_date);
            $('#finish-date').datepicker("option", "minDate", next_date);
        }
    });
    $('#finish-date').datepicker({
        dateFormat: "yy-mm-dd",
        firstDay: 1,
        minDate: "+1d"
    });
});
