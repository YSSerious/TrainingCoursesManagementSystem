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
	    		s+= '<div class="panel panel-default"><div id="mpr'+value.id+'" class="panel-heading">' + value.name;
	    		s+= '<div class="pull-right">'+new Date(value.startDate).toString().slice(3,15)+' - '+new Date(value.finishDate).toString().slice(3,15)+'</div>';
 	    		s+= '</div><div id="ms'+value.id+'"><div class="row"><div class="col-sm-12"> <div class="col-sm-12"><h4>'+value.description+'</h4></div></div></div></div></div>';
	    	
	    	});

	    	if(jQuery.isEmptyObject(data))
	    		s= '<br/>No projects (mentor)';
	    		
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
	    		s+= '<div class="panel panel-default"><div id="pr'+value.id+'" class="panel-heading">' + value.name;
	    		s+= '<div class="pull-right">'+new Date(value.startDate).toString().slice(3,15)+' - '+new Date(value.finishDate).toString().slice(3,15)+'</div>';
 	    		s+= '</div><div id="sub'+value.id+'"><div class="row"><div class="col-sm-12"> <div class="col-sm-12"><h4><br/>'+value.description+'</h4><br/><h2>Charts:</h2><div class="col-sm-8 col-sm-offset-2" id="chart'+value.id+'">Loading...</div></div></div></div></div></div>';
	    	});

	    	if(jQuery.isEmptyObject(data))
	    		s= '<br/>No projects (student)';
	    	
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
    	 	    	    	
    	 	    	    	$.each(data.chartInfo, function( key, value1 ) {
    	 	    	    		var text = '<div id="ch'+value.id+key+'"></div><br/>';
    	 	    	    		$('#chart'+value.id).append(text);
    	 	    	    	});
    	 	    	    	
    	 	    	    	$.each(data.chartInfo, function( key, value1 ) {
    	 	    	    		if(value1.length=1){
    	 	    	    			value1.unshift({ criterionName: '', averageValue: null});
    	 	    	    			value1.unshift({ criterionName: '', averageValue: null});
    	 	    	    		}
    	 	    	    		
    	 	    	    		Morris.Bar({
    	 	    	    			  element: 'ch'+value.id+key+'',
    	 	    	    			  data: value1,
    	 	    	    			  ymax: 5,
    	 	    	    			  grid: true,
    	 	    	    			  ymin: 1,
    	 	    	    			  xkey: 'criterionName',
    	 	    	    			  ykeys: ['averageValue'],
    	 	    	    			  labels: [key]
    	 	    	    			});
    	 	    	    	});
    	 	    	    	
    	 	    	    	var table = '<br/><div class="row"><div class="col-sm-12"> <div class="col-sm-12"><h2>Grades:</h2><table class="table table-bordered"><tr><th>#</th>';
    	 	    	    	$.each(data.markTableDto.meetings, function( key, value ) {
    	 	    	    		table+= '<th>' + value + '</th>';
    	 	    	    	});
    	 	    	    	table+= '</tr>';
    	 	    	    	
    	 	    	    	$.each(data.markTableDto.tableData, function( key, value ) {
    	 	    	    		table+= '<tr><td align="center" colspan="'+ data.markTableDto.meetings.length + 1 +'"><b>' + key + '</b></td></tr>';
    	 	    	    		
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
    	 	    	    	
    	 	    	    	table+= '<br/><h2>Statuses: </h2>';
    	 	    	    	table+= '<table class="table table-bordered"><tr><th>Status:</th>';
    	 	    	    	table+= '<th>Commentary</th>';
    	 	    	    	table+= '<th>Employee</th>';
    	 	    	    	table+= '<th>Time</th></tr>';
    	 	    	    	$.each(data.studentStatuses, function( key, value ) {
    	 	    	    		table+= '<tr><td>'+value.statusDescription+'</td>';
        	 	    	    	table+= '<td>'+value.commentary+'</td>';
        	 	    	    	table+= '<td>'+value.firstName+' '+value.secondName+' '+value.lastName+'</td>';
        	 	    	    	table+= '<td>'+value.date+'</td></tr>';
    	 	    	    	});
    	 	    	    	table+= '</table>';
    	 	    	    	
    	 	    	    	table+= '<br/><h2>Meeting reviews: </h2>';
    	 	    	    	table+= '<table class="table table-bordered"><tr><th>Name</th>';
    	 	    	    	table+= '<th>Type</th>';
    	 	    	    	table+= '<th>Employee</th>';
    	 	    	    	table+= '<th>Commentary</th></tr>';
    	 	    	    	$.each(data.meetingReviews, function( key, value ) {
    	 	    	    		table+= '<tr'+getRow(value.type)+'><td>'+value.meetingName+'</td>';
    	 	    	    		table+= '<td>'+value.type+'</td>';
    	 	    	    		table+= '<td>'+value.firstName+' '+value.secondName+' '+value.lastName+'</td>';
        	 	    	    	table+= '<td>'+value.commentary+'</td></tr>';
        	 	    	    	
    	 	    	    	});
    	 	    	    	table+= '</table>';
    	 	    	    	
    	 	    	    	$('#sub'+value.id).append(table);
    	 	    	    }
    	 	    	  });
    			});
	    	});
	    }
	  });
};