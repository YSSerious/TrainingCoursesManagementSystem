function guf228(userId, meeting) {
	function sendAjax(data, comment, meetingId) {
		console.log(comment);
		$.ajax({
			url : '/ajax/post/evaluate/' + userId,
			type : 'POST',
			contentType : "application/json",
			dataType : 'json',
			data : JSON.stringify({
				data : data,
				comment : comment,
				meetingId : meetingId
			}),
			success : function(data) {
				console.log('success');
			},
			error : function(error) {
				console.log(error.responseText);
			}
		});
	}
	;

	var error = null;
	var meetingId = meeting;
	var data = [];
	var comment = $('#rev-com').val();
	$('.result' + userId).each(function(key, value) {

		var review = {
			"value" : $(value).find('select').val(),
			"criterionId" : $(value).find('input').attr('id'),
			"commentary" : $(value).find('input').val()
		};
		if (!$(this).find('input').val()) {
			error = 'All comments are required!';
			return false;
		}
		data.push(review);
	});
	if (!error) {
		$('#rev-err').addClass('hidden');
		$('#evaluateModal' + userId).modal('toggle');
		sendAjax(data, comment, meetingId);
	} else {
		$('#rev-err').text(error);
		$('#rev-err').removeClass('hidden');
	}
};

