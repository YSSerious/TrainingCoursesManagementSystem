$(document).ready(function () {

	$("#addGroupAttachmentButton").click(function(){
		$("#addGroupAttachmentModal").modal();
	});


	$("#editGroupAttachmentButton").click(function(){
		$("#editGroupAttachmentModal").modal();
	});


	$("#submitButton").click(function(event) {
		$.ajax({
			url: "/groups/addAttachment",
			type: "POST",
			data: {"id_group" : "${groupId}", 
				"name" : $("#groupAttachmentName").val(),
				"attachment_scope": $("#groupAttachment").val()}
		});
	});



	$("#editGroupAttachmentButton").click(function(event) {
		$.ajax({
			url: "/groups/editAttachment",
			type: "POST",
			data: {"id_group" : $(group-id), 
				"name" : $("#groupAttachmentName").val(),
				"attachment_scope": $("#groupAttachment").val()
			}

		});

	});

	$('.delete').click(function(event){
		var idOfAttachment = event.target.id;
		idOfAttachment = idOfAttachment.substring(1,idOfAttachment.length);
		$.ajax({
			url: "/groups/deleteAttachment",
			type: "POST",
			data:{ "id":idOfAttachment}

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
			}
		});
	});
});

