$(function(){
     $("#addGroupAttachmentButton").click(function(){
         $("#addGroupAttachmentModal").modal();
     });
 });
	$(function(){
		 
	     $("#editGroupAttachmentButton").click(function(){
	         $("#editGroupAttachmentModal").modal();
	     });
	 });
 
 $(function() {
     $("#addSubmitButton").click(function(event) {
    	// event.preventDefault();
         $.ajax({
        	 contentType : "application/json",
				dataType: 'json',
        	 url: "/groups/addAttachment",
             type: "POST",
             data: {"id_group" : "${groupId}", 
            	 "name" : $("#groupAttachmentName").val(),
            	 "attachment_scope": $("#groupAttachment").val(),
            	 
                 error:function (xhr, ajaxOptions, thrownError){
             	    if(xhr.status==404) {
             	        alert(thrownError);
             	    }
             	},
             	succes: function(){
             		alert("succes");
             	}
             }
            		  
            	  });
      
         });
     });
 $(function() {

     $("#editGroupAttachmentButton").click(function(event) {
    	 event.preventDefault()
         $.ajax({
             url: "/groups/group/editAttachment",
             type: "POST",
             data: JSON.stringify( {"id_group" : $(group-id), 
            	 "name" : $("#groupAttachmentName").val(),
            	 "attachment_scope": $("#groupAttachment").val()
             }),
             error:function (xhr, ajaxOptions, thrownError){
         	    if(xhr.status==404) {
         	        alert(thrownError);
         	    }
         	},
         	succes: function(){
         		alert("succes");
         	}
         	  });
      
         });
     });
 $('.delete').click(function(event){
	   var idOfAttachment = event.target.id;
	   idOfAttachment = idOfAttachment.substring(1,idOfAttachment.length);
	   console.log("Lol");
	   $.ajax({
		   url: "/groups/deleteAttachment",
	   		data:{ "id":idOfAttachment}
	   ,
       error:function (xhr, ajaxOptions, thrownError){
   	    if(xhr.status==404) {
   	        alert(thrownError);
   	    }
   	},
   	succes: function(){
   		alert("succes");
   	}
	   });
 });
 $(function(){
	    $("#attachment-title").click(function(){
	        $("#attachment").slideToggle();
	    });
	});