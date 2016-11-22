$(document).ready(function () {
 
	$("#addAttachmentSubmitButton").click(function(event) {
		
		var formData = new FormData(); 
		formData.append("id_group",groupId);
		formData.append("name",$("#groupAttachmentName").val())
		formData.append('attachment_scope', $("input[type=file]")[0].files[0]);
		console.log($("#groupAttachmentName").val());
		console.log($("groupId"));
	//	console.log( $("input[type=file]")[0].files[0])
		$.ajax({
			//url: "/groups/addAttachment",
		//	type: "POST",
			data: formData,
				 contentType: false,
				    processData: false,
				success: function (data) {
	                console.log(data);
	             
	            },
	            error: function (textStatus) {
	                console.log(textStatus);
	            }
		});
	});

	$('.delete').click(function(event){
	 
		var idOfAttachment = this.id;
		console.log(idOfAttachment);
		//idOfAttachment = idOfAttachment.substring(1,idOfAttachment.length);
		$.ajax({
			url: "/groups/deleteAttachment",
			type: "POST",
			data:{ "id":idOfAttachment},
			 success: function (data) {
	                console.log(data);
	               $('#attachment-'+idOfAttachment).remove();
	            },
	            error: function (textStatus) {
	                console.log(textStatus);
	            }

		});
	});
	 


	$('.delete-mentor').click(function(event){
		var Id= this.id;
		var ids = Id.split("-");
		$.ajax({
			url: "/groups/removeMentor",
			type: "POST",
			data:{ "groupId":ids[0], "userId":ids[1]},
			success: function (data) {
				console.log(data);
				$(".group-"+Id).remove();
			}
		});
	});
	
	$('.delete-student').click(function(event){
		var Id= this.id;
		var ids = Id.split("-");
		$.ajax({
			url: "/groups/removeStudent",
			type: "POST",
			data:{ "groupId":ids[0], "userId":ids[1]},
			success: function (data) {
				console.log(data);
				$(".group-"+Id).remove();
			},
			error: function(errorText){
				console.log(errorText);
				$('#studentDeleteError').modal('show');
			}
		});
	});
	
	
});

