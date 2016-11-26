$(document).ready(function () {
 
	$("#addAttachmentSubmitButton").click(function(event) {
		
		if($("#groupAttachmentName").val().length==0 ||$("#groupAttachment").val().length==0)
			return
	 
	 
		$.ajax({
			url: "/groups/addAttachment",
			type: "POST",
			data: {"id_group" : $("#groupId").val(), 
			"name" : $("#groupAttachmentName").val(),
			"attachment_scope": $("#groupAttachment").val()},
		
			success: function (data) {
	                console.log(data);
	                $('#listAttachments').append(buildAttachment($("#groupAttachmentName").val(),
	                		$("#groupAttachment").val()));
	                $('#addGroupAttachmentModal').modal("hide");
	            },
	            error: function (textStatus) {
	                console.log(textStatus);
	            }
		});
		
	});

	function buildAttachment(name,scope) {
		 
	    return "<li   class='list-group-item  clearfix'>"+
        
        "<a href="+scope+">"+name+" </a>"+
        
        
          // "<br/>"+
       "<li/>";
	    
	}

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

	$("#editMeetingButton").click(function () {
		$.ajax({
			url: "/groups/editMeeting",
			type: "POST",
			data: {id: chosenMeetingId, name: $("#editMeetingName").val(), date: $("#editMeetingDate").val(), place: $("#editMeetingPlace").val()},
			success: function (data) {
				console.log(data);
				$('#editMeetingNameId-'+data.id).html(data.name);
				$('#editMeetingDateId-'+data.id).html(convertTimestamp(data.time));
				$('#editMeetingPlaceId-'+data.id).html(data.place);
			},
			error: function (textStatus) {
				console.log(textStatus);
			}
		});
	});

	$('#editMeetingButton').attr('disabled', true);
	var meetingName = new RegExp('^[a-zA-Z0-9_-\\s]{3,15}$');
	var meetingPlace = new RegExp('^[a-zA-Z0-9_-\\s]{3,25}$');

	$('#editMeetingFormId').change(function () {
		if (meetingName.test($("#editMeetingName").val()) && meetingPlace.test($("#editMeetingPlace").val()) && $("#editMeetingDate").val()!=""){
			$('#editMeetingButton').attr('disabled', false);
		} else {
			$('#editMeetingButton').attr('disabled', true);
		}
	});

	function convertTimestamp(timestamp) {
		var d = new Date(timestamp);
		return d.toLocaleDateString()+", "+d.getHours()+":"+d.getMinutes();
	}
	
});

function changeSpan() {
	if ($("#spanId").hasClass("glyphicon-chevron-down")) {
		$("#spanId").removeClass('glyphicon-chevron-down');
		$("#spanId").addClass('glyphicon-chevron-up');
	} else {
		$("#spanId").removeClass('glyphicon-chevron-up');
		$("#spanId").addClass('glyphicon-chevron-down');
	}
}
var chosenMeetingId;

function setMeeting(id) {
	chosenMeetingId = id;
}