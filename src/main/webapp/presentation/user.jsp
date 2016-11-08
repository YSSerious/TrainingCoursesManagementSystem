<%@include file="header.jsp"%>
Email: ${user.email} <br/>
First name: ${user.firstName} <br/>
Last name: ${user.lastName} <br/>
Second name: ${user.secondName} <br/>
Roles: ${user.roles} <br/>
Active: ${user.active} <br/>
<br/>

<c:forEach var="item" items="${user.roles}">
  <c:if test="${item eq 'ROLE_STUDENT'}">
    <div class="student-projects">
    	Loading projects(student role)...
    	<br/>
    </div>
    <script>
    $( document ).ready(function() {    	
    $.ajax({
    	    'url' : '/ajaxstudentprojects',
    	    'type' : 'GET',
    	    'data' : {
    	    'user' : ${user.id},
    	    },
    	    'success' : function(data) {
    	    	var s = '<br/><div>';
    	    	$.each( data, function( key, value ) {
    	    		s+= '<div class="inner-project" id="pr' + value.id + '">';
    	    		s+= value.name + '<br/>' + value.description + '<br/>';
    	    		s+= value.startDate + '<br/>' + value.finishDate + '<br/>';
     	    		s+= '</div><div id="sub'+value.id+'"> </div>';
    	    	});
    	    	
    	    	s+= '</div>';

    	    	if(jQuery.isEmptyObject(data))
    	    		s= '<br/>Passive student';
    	    	
    	    	$('.student-projects').html(s);
    	    	
    	    	$.each(data, function( key, value ) {
    	    		$('#pr'+value.id).click(function() {
    	    			$('#pr'+value.id).unbind( "click" );
	    	 			$.ajax({
	    	 	    	    'url' : '/ajaxstudentprofile',
	    	 	    	    'type' : 'GET',
	    	 	    	    'data' : {
	    	 	    	      'student' : ${user.id},
	    	 	    	      'project' : value.id
	    	 	    	    },
	    	 	    	    'success' : function(data) {
								
	    	 	    	    	var marks = 'Marks: <br/>';
	    	 	    	    	$.each(data.markInformation, function( key, value ) {
	    	 	    	    		marks+= key + ': ';
	    	 	    	    		$.each(value, function(index, mark) {
		    	 	    	    		marks+= mark.mark + ' ';
		    	 	    	    	});
	    	 	    	    		marks+= '<br/>';
	    	 	    	    	});
	    	 	    	    	
	    	 	    	    	if(jQuery.isEmptyObject(data.markInformation))
	    	 	    	    		marks = '<br/>No marks...';
	    	 	    	    	
	    	 	    	    	$('#sub'+value.id).html(marks);
	    	 	    	    }
	    	 	    	  });
	    			});
    	    	});
    	    }
    	  });
    });
    </script>
  </c:if>
  
   <c:if test="${item eq 'ROLE_MENTOR'}">
    <div class="mentor-projects">
    	Loading projects(mentor role)...
    	<br/>
    </div>
    <script>
    $( document ).ready(function() {
    $.ajax({
    	    'url' : '/ajaxmentorprojects',
    	    'type' : 'GET',
    	    'data' : {
    	      'user' : ${user.id},
    	    },
    	    'success' : function(data) {
    	    	var s = '<br/><div>';
    	    	$.each( data, function( key, value ) {
    	    		s+= '<div class="inner-project" id="' + value.id + '">';
    	    		s+= value.name + '<br/>' + value.description + '<br/>';
    	    		s+= value.startDate + '<br/>' + value.finishDate + '<br/>';
     	    		s+= '</div>';
    	    	});
    	    	
    	    	s+= '</div>';
    	    	if(jQuery.isEmptyObject(data))
    	    		s= '<br/>Passive mentor';
    	    	
    	    		
    	    	$('.mentor-projects').html(s);
    	    }
    	  });
    });
    </script>
  </c:if>
</c:forEach>

<%@include file="footer.jsp"%>