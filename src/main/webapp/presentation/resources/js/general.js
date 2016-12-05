function getBeforeAndAfterAjaxEvent(modalId) {
    return {
        beforeSend: function () {
            $(modalId).find('form').first()
                    .find('button[type="submit"]').first()
                    .attr('disabled', 'disabled');
            $(modalId).find('.form-error').empty();
            $(modalId).find('.loading').
                    css('display', 'inline-block');
        },
        complete: function () {
            $(modalId).find('.loading').css('display', 'none');
            $(modalId).find('form').first()
                    .find('button[type="submit"]').first()
                    .removeAttr('disabled');
        }
    };
}

function showModalErrors(errors, modalId) {
    if (!$.isEmptyObject(errors)) {
        $.each(errors, function (field, message) {
            $(modalId + ' .form-error[path="' + field + '"]').first().text(message + '\n');
            console.log(message);
        });
    }
}

function cleanModalForm(modalId) {
    $(modalId + ' input').val('');
    $(modalId + ' .form-error').empty();
}