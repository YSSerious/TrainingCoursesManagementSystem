function getMark(mark){
	if(mark == 'A')
		return '<div class="btn btn-danger">A</button>';
	else if(mark == 'L')
		return '<div class="btn btn-danger">L</button>';
	else if(mark == 'U')
		return '<div class="btn btn-primary">U</button>';
	else if(mark != '-')
		return '<div class="btn btn-success">'+mark+'</button>';
	return '<div class="btn btn-default">N</button>';
}

function getRow(type){
	if(type == 'A')
		return ' class="danger"';
	else if (type == 'L')
		return ' class="danger"';
	return '';
}

function createMentorProjectsInfo(userId, divInside){

	$.ajax({
	    'url' : '/ajaxmentorprojects',
	    'type' : 'GET',
	    'data' : {'user' : userId},
	    
	    'success' : function(data) {
	    	var s = '';
	    	$.each( data, function( key, value ) {
	    		s+= '<h2>Mentor projects: </h2><hr/><div class="panel panel-default"><div id="mpr'+value.id+'" class="panel-heading">' + value.name;
	    		s+= '<div class="pull-right">'+new Date(value.startDate).toString().slice(3,15)+' - '+new Date(value.finishDate).toString().slice(3,15)+'</div>';
 	    		s+= '</div><div id="ms'+value.id+'"><div class="row"><div class="col-sm-12"> <div class="col-sm-12"><h4>'+value.description+'</h4></div></div></div></div></div>';
	    	
	    	});

	    	if(jQuery.isEmptyObject(data))
	    		s= '<br/><h2>Mentor projects: </h2><hr/><h4>No projects</h4>';
	    		
	    	$(divInside).html(s);
	    }
	  });
};

function createStudentProjectsInfo(userId, divInside){
	$.ajax({
	    'url' : '/ajaxstudentprojects',
	    'type' : 'GET',
	    'data' : {'user' : userId},
	    
	    'success' : function(data) {
	    	var s = '';
	    	$.each( data, function( key, value ) {
	    		s+= '<h2>Student projects: </h2><hr/><div class="panel panel-default"><div id="pr'+value.id+'" class="panel-heading">' + value.name;
	    		s+= '<div class="pull-right">'+new Date(value.startDate).toString().slice(3,15)+' - '+new Date(value.finishDate).toString().slice(3,15)+'</div>';
 	    		s+= '</div><div id="sub'+value.id+'"><div class="row"><div class="col-sm-12"> <div class="col-sm-12"><h4>'+value.description+'</h4><div class="col-sm-8 col-sm-offset-2 charts-wrapper" id="chart'+value.id+'">Loading...</div></div></div></div></div></div>';
	    	});

	    	if(jQuery.isEmptyObject(data))
	    		s= '<br/><h2>Student projects: </h2><hr/><h4>No projects</h4>';
	    	
	    	$(divInside).html(s);
                
	    	$.each(data, function(key, value) {
	    		$('#sub'+value.id).hide();
	    		$('#pr'+value.id).click(function() {
	    			$('#sub'+value.id).slideToggle();
	    			$('#pr'+value.id).unbind( "click" );
	    			$('#pr'+value.id).click(function() {
	    				$('#sub'+value.id).slideToggle();
	    			});
	    			
    	 			$.ajax({
    	 	    	    'url' : '/ajaxstudentprofile',
    	 	    	    'type' : 'GET',
    	 	    	    'data' : {
    	 	    	      'student' : userId,
    	 	    	      'project' : value.id
    	 	    	    },
    	 	    	    
    	 	    	    'success' : function(data) {
    	 	    	    	$('#chart'+value.id).html('');
                            createCharts(data.chartInfo, value.id);
    	 	    	    	
    	 	    	    	var table = '<br/><div class="row"><div class="col-sm-12"> <div class="col-sm-12">';
    	 	    	    	
    	 	    	    	table+= '<h2>Grades:</h2>';
                            table+= getMarksTable(data.markTableDto);
                            
                            table+= '<br/><h2>Statuses: </h2>';
                            table+= getStatusesTable(data.studentStatuses);
                            
                            
                            table+= '<br/><h2>Meeting reviews: </h2>';
                            table+= getMeetingReviewsTable(data.meetingReviews);
    	 	    	    	
                            table+= '</div></div></div>';
    	 	    	    	$('#sub'+value.id).append(table);
    	 	    	    }
    	 	    	  });
    			});
	    	});
	    }
	  });
};

function createCharts(data, projectId) {
    var criteriaValues = [];
    $.each(data, function (index, value) {
        $.each(value, function (index, value) {
            criteriaValues.push(value.averageValue);
        });
    });
    var maxYAxisValue = Math.max.apply(null, criteriaValues);
    drawCriteriaChart(data, '#chart' + projectId, 6, 'title');
}

function getMeetingReviewsTable(meetingReviews){
	var table = '';
 	table+= '<table class="table table-bordered"><tr><th>ID</th><th>Name</th>';
 	table+= '<th>Type</th>';
 	table+= '<th>Employee</th>';
 	table+= '<th>Commentary</th></tr>';
 	$.each(meetingReviews, function( key, value ) {
 		table+= '<tr'+getRow(value.type)+'><td>M'+(key+1)+'</td><td>'+value.meetingName+'</td>';
 		table+= '<td>'+value.type+'</td>';
 		table+= '<td>'+value.firstName+' '+value.secondName+' '+value.lastName+'</td>';
	    	table+= '<td>'+value.commentary+'</td></tr>';
	    	
 	});
 	table+= '</table>';
 	
 	return table;
}

function getMarksTable(markTableDto){
	var table = '';
 	table+= '<table class="table table-bordered"><tr><th>#</th>';
 	$.each(markTableDto.meetings, function( key, value ) {
 		if(key<markTableDto.meetings.length-1)
 			table+= '<th>M' + (key+1) + '</th>';
 		else
 			table+= '<th>F</th>';
 	});
 	table+= '</tr>';
 	
 	$.each(markTableDto.tableData, function( key, value ) {
 		table+= '<tr><td align="center" colspan="'+ markTableDto.meetings.length + 1 +'"><b>' + key + '</b></td></tr>';
 		
 		$.each(value, function( key1, value1 ) {
 			
 			table+= '<tr class="active"><td>' + value1[0].value + '</td>';
 			$.each(value1, function(index, mark) {
 				if(index>0){
 					if(mark.commentary == '')
 						table+= '<td><span title="'+mark.description+''+mark.commentary+'">' + getMark(mark.value) + '</span></td>';
 					else
 						table+= '<td><span title="'+mark.description+': '+mark.commentary+'">' + getMark(mark.value) + '</span></td>';
 				}
	    		});
 			table+= '</tr></div></div></div>';
 		});
 		
 		
 	});
 	
 	table+= '</table>';
 	
 	return table;
}

function getStatusesTable(studentStatuses){
	var table = '';
 	
	table+= '<table class="table table-bordered"><tr><th>Status:</th>';
 	table+= '<th>Commentary</th>';
 	table+= '<th>Employee</th>';
 	table+= '<th>Time</th></tr>';
 	$.each(studentStatuses, function( key, value ) {
 		table+= '<tr><td>'+value.statusDescription+'</td>';
	    	table+= '<td>'+value.commentary+'</td>';
	    	table+= '<td>'+value.firstName+' '+value.secondName+' '+value.lastName+'</td>';
	    	table+= '<td>'+value.date+'</td></tr>';
 	});
 	table+= '</table>';
 	
 	return table;
}

