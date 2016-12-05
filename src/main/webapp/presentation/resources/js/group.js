$(document).ready(function () {
 //Modal window and adding attachment
	$("#addAttachmentSubmitButton").click(function(event) {
		var name=$("#groupAttachmentName").val();
		var value=$("#groupAttachment").val();
		// var regexp=new RegExp('/^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/');
		if(name.length==0  || value.length==0)
			return
			 
		$.ajax({
			url: "/groups/addAttachment",
			type: "POST",
			data: {"id_group" : $("#groupId").val(), 
			"name" : name,
			"attachment_scope": value},
		
			success: function (data) {
	                console.log(data);
	                $('#listAttachments').prepend(buildAttachment($("#groupAttachmentName").val(),
	                		$("#groupAttachment").val()));
	                $('#addGroupAttachmentModal').modal("hide");
	            },
	            error: function (textStatus) {
	                console.log(textStatus);
	            }
		});
		
	});
	
$("#addNoteSubmitButton").click(function(event) {
		
		if($("#noteAttachmentName").val().length==0 ||$("#noteAttachment").val().length==0)
			return
	 
	 
		$.ajax({
			url: "/groups/addAttachment",
			type: "POST",
			data: {"id_group" : $("#groupId").val(), 
			"name" : ("meeting_note_"+$("#noteAttachmentName").val()),
			"attachment_scope": $("#noteAttachment").val()},
		
			success: function (data) {
	               // console.log(data);
	                $("#noteList").prepend(buildAttachment("meeting_note_"+$("#noteAttachmentName").val(),
	                		$("#noteAttachment").val()));
	                $('#addMeetingNoteModal').modal("hide");
	            },
	            error: function (textStatus) {
	                console.log(textStatus);
	            }
		});
		
	});

	function buildAttachment(name,scope) {
		 
	     return '<li   class="list-group-item  clearfix">'+
        
        '<a href='+scope+'>'+name+'</a>'+
        
        
          // "<br/>"+
       '</li>';
	    
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
	 


	bindRemoveMentor();
	bindRemoveStudent();

	$("#deleteMeetingButton").click(function () {
		$.ajax({
			url: "/groups/deleteMeeting",
			type: "POST",
			data: {meetingId: chosenMeetingId},
			statusCode: {
				200: function (data) {
					console.log(data);
					$('#meetingId-'+chosenMeetingId).remove();
				},
				409: function (textStatus) {
					console.log(textStatus);
					$('#meetingDeleteErrorModal').html(textStatus.responseText);
					$('#meetingDeleteError').modal('show');
				}
			}
		});
	});
	
	$("#editMeetingButton").click(function () {
		$.ajax({
			url: "/groups/editMeeting",
			type: "POST",
			data: {id: chosenMeetingId, name: $("#editMeetingName").val(), date: $("#editMeetingDate").val(), place: $("#editMeetingPlace").val()},
			statusCode: {
				200: function (data) {
					console.log(data);
					$('#editMeetingNameId-'+data.id).html(data.name);
					$('#editMeetingDateId-'+data.id).html(convertTimestamp(data.time));
					$('#editMeetingPlaceId-'+data.id).html(data.place);
				},
				409: function (textStatus) {
					console.log(textStatus.responseText);
				}
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
		return d.getFullYear()+"-"+zeroPadded(d.getMonth() + 1)+"-"+zeroPadded(d.getDate())+", "+d.getHours()+":"+d.getMinutes();
	}
	
});

function changeSpan(el) {
	var chevron = jQuery(el);
	if(chevron.find('span').hasClass("glyphicon-chevron-down")){
		chevron.find('span').removeClass('glyphicon-chevron-down');
		chevron.find('span').addClass('glyphicon-chevron-up');
	} else {
		chevron.find('span').removeClass('glyphicon-chevron-up');
		chevron.find('span').addClass('glyphicon-chevron-down');
	}
}

var chosenMeetingId;
var chosenMeetingName;
var chosenMeetingPlace;
var chosenMeetingDate;

function setMeeting(id, name, place, date) {
	chosenMeetingId = id;
	chosenMeetingName = name;
	chosenMeetingPlace = place;
	chosenMeetingDate = date;

	$('#editMeetingName').val(chosenMeetingName);
	$('#editMeetingPlace').val(chosenMeetingPlace);
	$('#editMeetingDate').val(convertTimestampTwo(chosenMeetingDate));
}

function convertTimestampTwo(timestamp) {
	var d = new Date(timestamp);
	return d.getFullYear()+"-"+zeroPadded(d.getMonth() + 1)+"-"+zeroPadded(d.getDate())+"T"+zeroPadded(d.getHours())+":"+zeroPadded(d.getMinutes());
}
function zeroPadded(val) {
	if (val >= 10)
		return val;
	else
		return '0' + val;
}



function appendStudentsTableRows(data) {
	$("#studentsTable > tbody:last").children().remove();
	$.each(data, function (key, value) {
		$('#studentsTable > tbody:last-child').append("<tr>" +
				"<td>" + value.firstName + " " + value.lastName + "</td>" +
				"<td><button value='" + value.id + "' class='addStudentsButton btn-primary btn-sm'><span class='glyphicon glyphicon-plus'></span></button></td>" +
				"</tr>");
	});
	
	bindRemoveStudent();
}

$("#showAvailableStudents").click(function () {
	$.ajax({
		url: "/students/inactive",
		type: "GET",
		data: {groupId: $("#groupId").val()},
		success: function (data) {
			console.log(data);
			appendStudentsTableRows(data);
		},
		error: function (textStatus) {
			console.log(textStatus);
		}
	});
});

function buildResponseStudent(data) {
	return "<li class=\"list-group-item group-" + $("#groupId").val() + "-" + data.id + " clearfix\">"
				//+"<c:if test='" + data.key.status.id == 1'>"
				//+"<span class=\"label label-danger\"><spring:message code='group.student.expelled' /></span>
				//</c:if>
			+ "<a href=\"/users/"+ data.id +"\"> " + data.firstName +"</a>"
			+ "<span style=\"padding-left: 10px;\"> </span>"
			+ data.lastName + "<span style=\"padding-left: 10px;\"> </span>"
			+ "<div id=\""+ $("#groupId").val() + "-"+ data.id + "\" "
			+ "class=\"btn rmv-cr-btn col-md-1 pull-right delete-student\" type=\"button\">"
			+ "<span class=\"glyphicon glyphicon-remove delete\"></span>"
			+ "</div> <br />"
			+ "</li>";
}

$(document).on('click', '.addStudentsButton', function () {
	var a = $(this);
	var groupId = $("#groupId").val();
	$.ajax({
		url: "/groups/add/students",
		type: "POST",
		data: {groupId: groupId, userId: a.val()},
		success: function (data) {
			console.log(data);
			$('#collapseStudents').append(buildResponseStudent(data));
			a.parent().parent().remove();
			bindRemoveStudent();
		},
		error: function (textStatus) {
			console.log(textStatus);
		}
	});
});

//***********************************

function appendMentorsTableRows(data) {
	$("#mentorsTable > tbody:last").children().remove();
	$.each(data, function (key, value) {
		$('#mentorsTable > tbody:last-child').append("<tr>" +
				"<td>" + value.firstName + " " + value.lastName + "</td>" +
				"<td><button value='" + value.id + "' class='addMentorsButton btn-primary btn-sm'><span class='glyphicon glyphicon-plus'></span></button></td>" +
				"</tr>");
	});
}

$("#showAvailableMentors").click(function () {
	$.ajax({
		url: "/mentors/free",
		type: "GET",
		data: {groupId: $("#groupId").val()},
		success: function (data) {
			console.log(data);
			appendMentorsTableRows(data);
		},
		error: function (textStatus) {
			console.log(textStatus);
		}
	});
});

function buildResponseMentor(data) {
	var div = '';
	
	div += '<li class="list-group-item group-'+$("#groupId").val()+'-'+data.id+' clearfix">';

	div += '<a href="/users/'+data.id+'">'+data.firstName+'</a>';
	div += '<span style="padding-left: 10px;"> </span> '+data.lastName;
	                        
	div += '<div id="'+$("#groupId").val()+'-'+data.id+'" class="btn rmv-cr-btn col-md-1 pull-right delete-mentor" type="button">';
	div += '<span class="glyphicon glyphicon-remove delete"></span>';
	div += '</div>';
	div += '<br/>';
	div += '</li>';
	
	return div;
}

$(document).on('click', '.addMentorsButton', function () {
	var a = $(this);
	var groupId = $("#groupId").val();
	$.ajax({
		url: "/groups/add/mentors",
		type: "POST",
		data: {groupId: groupId, userId: a.val()},
		success: function (data) {
			console.log(data);
			$('#collapseMentors').append(buildResponseMentor(data));
			a.parent().parent().remove();
			bindRemoveMentor();
		},
		error: function (textStatus) {
			console.log(textStatus);
		}
	});
});

function bindRemoveStudent(){
	$('.delete-student').unbind('click');
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
}

function bindRemoveMentor(){
	$('.delete-mentor').unbind('click');
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
}
