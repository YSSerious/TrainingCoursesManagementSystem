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
	var count = 2;
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
		$('#rev-err' + userId).addClass('hidden');
		$('#evaluateModal' + userId).modal('toggle');
		sendAjax(data, comment, meetingId);
	} else {
		//$("table tr:nth-child(2) td:nth-child("+count+")").html($(value).find('select').val());
		$('#rev-err' + userId).text(error);
		$('#rev-err' + userId).removeClass('hidden');
	}
}

function absent(userId, meeting) {
	function sendAjax(meetingId) {
		console.log(comment);
		$.ajax({
			url : '/ajax/post/absent/' + userId,
			type : 'POST',
			contentType : "application/json",
			dataType : 'json',
			data : JSON.stringify({
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
	var meetingId = meeting;

	$('#rev-err' + userId).addClass('hidden');
	$('#evaluateModal' + userId).modal('toggle');
	sendAjax(meetingId);
	alert(meetingId);
}

function app_handle_listing_horisontal_scroll(listing_obj) {
	table_obj = $('.table', listing_obj);
	count_fixed_collumns = table_obj.attr('data-count-fixed-columns')

	if (count_fixed_collumns > 0) {
		wrapper_obj = $('.meeting', listing_obj);

		wrapper_left_margin = 0;

		table_collumns_width = new Array();
		table_collumns_margin = new Array();
		$('th', table_obj).each(function(index) {
			if (index < count_fixed_collumns) {
				wrapper_left_margin += $(this).outerWidth();
				table_collumns_width[index] = $(this).outerWidth();
			}
		})
		$.each(table_collumns_width, function(key, value) {
			if (key == 0) {
				table_collumns_margin[key] = wrapper_left_margin;
			} else {
				next_margin = 0;
				$.each(table_collumns_width, function(key_next, value_next) {
					if (key_next < key) {
						next_margin += value_next;
					}
				});

				table_collumns_margin[key] = wrapper_left_margin - next_margin;
			}
		});
		if (wrapper_left_margin > 0) {
			wrapper_obj.css('cssText', 'margin-left:' + wrapper_left_margin
					+ 'px !important; width: auto')
		}
		$('tr', table_obj).each(
				function() {
					current_row_height = $(this).outerHeight();

					$('th,td', $(this)).each(
							function(index) {
								$(this).css('height', current_row_height)
								if (index < count_fixed_collumns) {
									$(this).css('position', 'absolute').css(
											'margin-left',
											'-' + table_collumns_margin[index]
													+ 'px').css('width',
											table_collumns_width[index])

									$(this).addClass('table-fixed-cell')
								}
							})
				})
	}
}

$(function() {
	app_handle_listing_horisontal_scroll($('#table-listing'))
})
