$(function() {
	$('#project-name').bind('focusout', function() {
		input = $('#project-name').val();
		if (input == "") {
			$('#name-message').css("display", "none").css("visibility", "hidden").empty();
		} else {
			var project_form = $('#create-project-form');
			console.log(project_form.serializeArray());
			$.ajax({
				type : 'POST',
				contentType : "application/json",
				dataType: 'json',
				data : input,
				url : 'create-project/is-name-free',
				statusCode : {
					404 : function(response) {
						console.log('404');
					},
					200 : function(response) {
						showNameMessage(response);
					}
				}
			})
		}
	});
});

function showNameMessage(response) {
	var message = "This name is available";
	if (response == false){
		message = "This name is unavailable";
	}
	$('#name-message').empty().append(message).css("visibility", "visible").css("display", "inline");
}