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
    	 	    	    	
    	 	    	    	table+= '<div class="row"><div class = "col-sm-10"><h2>Grades:</h2></div><div class = "col-sm-2"><h2>';
    	 	    	    	//table+=	'<a href="/studentMarks/'+value.id+'/'+userId+'.xls" class="btn btn-primary pull-right">Report</a>';
    	 	    	    	table+=	'</h2></div></div>';
                            
    	 	    	    	table+= '<div class="panel panel-default"><div class="panel-heading"><div class="row">';
    	 	    	    	table+= '<div class = "col-sm-5">';
    	 	    	    	table+= getSelectCategories(data.projectCategories, value.id);
    	 	    	    	table+= '</div>';
    	 	    	    	table+= '<div class = "col-sm-5">';
    	 	    	    	table+= getSelectCriteria(data.projectCriteria, value.id);
    	 	    	    	table+= '</div>';
    	 	    	    	table+= '<div class = "col-sm-2">';
    	 	    	    	table+= '<button onclick="search('+value.id+')" class="form-control btn btn-default">Search</button>';
    	 	    	    	table+= '</div>';
    	 	    	    	table+= '</div></div>';
    	 	    	    	
    	 	    	    	table+= getMarksTable(data.markTableDto, value.id);
                            
    	 	    	    	table+= '</div>';
                            table+= '<br/><h2>Statuses: </h2>';
                            table+= getStatusesTable(data.studentStatuses);
                            
                            table+= '<br/><h2>Meeting reviews: </h2>';
                            table+= getMeetingReviewsTable(data.meetingReviews);
    	 	    	    	
                            table+= '<br/><h2>Final reviews: </h2>';
                            table+= getFinalReviews(data);
                            
                            table+= '</div></div></div>';
    	 	    	    	$('#sub'+value.id).append(table);
    	 	    	    	
    	 	    	    	$('.criteria-select-'+value.id).select2({
    	 	    	    		  placeholder: "Select criteria"
    	 	    	    	});
    	 	    	    	$('.categories-select-'+value.id).select2({
    	 	    	    		  placeholder: "Select categories"
    	 	    	    	});
    	 	    	    }
    	 	    	  });
    			});
	    	});
	    }
	  });
};

function createCharts(data, projectId) {
    var chart = $("#chart" + projectId);
    var typesDropdown = "<div class='dropdown chart-dropdown' id='type-menu'>" +
            "<button class='btn btn-link dropdown-toggle'" + 
            "type='button' data-toggle='dropdown'>" +
            "Show<span class='caret'></span></button>" +
            "<ul class='dropdown-menu'>" +
      "<li><a id='all-criteria'>All criteria</a></li>" +
      "<li><a id='categories'>Categories</a></li>" + 
      "<li><a id='grouped-criteria'>Grouped criteria</a></li></ul>";
    chart.append(typesDropdown);
    var sortDropdown = "<div class='dropdown chart-dropdown' id='sort-menu'>" +
            "<button class='btn btn-link dropdown-toggle'" + 
            "type='button' data-toggle='dropdown'>" +
            "Sort by<span class='caret'></span></button>" +
            "<ul class='dropdown-menu'>" +
      "<li><a id='increase'>Increase</a></li>" +
      "<li><a id='decrease'>Decrease</a></li></ul>";
    chart.append(sortDropdown);
    var criteriaValues = [];
    $.each(data, function (index, value) {
        $.each(value, function (index, value) {
            criteriaValues.push(value.averageValue);
        });
    });
    var maxYAxisValue = Math.max.apply(null, criteriaValues);
//    $('.chart-dropdown ul li').click(function(e) {
//        console.log($(this).find('a').attr('id'));
//    });
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

function getMarksTable(markTableDto, projectId){
	var table = '';
 	table+= '<table class="table table-bordered" id="marks-'+projectId+'"><tr><th>#</th>';
 	$.each(markTableDto.meetings, function( key, value ) {
 		if(key<markTableDto.meetings.length-1)
 			table+= '<th>M' + (key+1) + '</th>';
 		else
 			table+= '<th>F</th>';
 	});
 	table+= '</tr>';

 	$.each(markTableDto.tableData, function(key, categoryResult ) {
 		table+= '<tr id="row-'+projectId+'-'+categoryResult.categoryDto.id+'"><td align="center" colspan="'+ markTableDto.meetings.length + 1 +'"><b>' + categoryResult.categoryDto.name + '</b></td></tr>';
 		
 		$.each(categoryResult.criteriaResults, function( i, criterionResult ) {
 			table+= '<tr class="active" id="row-'+projectId+'-'+categoryResult.categoryDto.id+'-'+criterionResult.criterionId+'"><td>' + criterionResult.criterionName + '</td>';
 			$.each(criterionResult.marks, function(index, mark) {
 					if(mark.commentary == '')
 						table+= '<td><span title="'+mark.description+''+mark.commentary+'">' + getMark(mark.value) + '</span></td>';
 					else
 						table+= '<td><span title="'+mark.description+': '+mark.commentary+'">' + getMark(mark.value) + '</span></td>';
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

function getFinalReviews(studentProfile){
	var reviews = '';
	
	reviews += '<table class="table table-bordered"><tr><th style="width:33%">Final</th>';
	reviews += '<th style="width:33%">General</th>';
	reviews += '<th style="width:33%">Technical</th>';
	reviews += '</tr>';

	if (studentProfile.finalReview !== null) {
		reviews += '<tr><td><b>On ' + studentProfile.finalReview.date + ' by '
				+ studentProfile.finalReview.lastName + ' '
				+ studentProfile.finalReview.firstName + '</b><br/>'
				+ studentProfile.finalReview.commentary + '</td>';
	} else {
		reviews += '<tr><td>There is no final review!</td>';
	}

	if (studentProfile.technicalReview !== null) {
		reviews += '<td><b>On ' + studentProfile.technicalReview.date + ' by '
				+ studentProfile.technicalReview.lastName + ' '
				+ studentProfile.technicalReview.firstName + '</b><br/>'
				+ studentProfile.technicalReview.commentary + '</td>';
	} else {
		reviews += '<td>There is no technical review!</td>';
	}

	if (studentProfile.generalReview !== null) {
		reviews += '<td><b>On ' + studentProfile.generalReview.date + ' by '
				+ studentProfile.generalReview.lastName + ' '
				+ studentProfile.generalReview.firstName + '</b><br/>'
				+ studentProfile.generalReview.commentary + '</td>';

		reviews += '</tr>';
	} else {
		reviews += '<td>There is no general review!</td>';
	}
	
	return reviews;
}

function getSelectCategories(projectCategories, projectId){
	var select = '<select style="width:90%;" multiple="true" class="categories-select-'+projectId+'">';
	
	$.each(projectCategories, function(key, value ) {
		select += '<option value="' + value.id + '">' + value.name + '</option>\n';
	});
	
	select += '</select>';
	
	return select;
}

function search(projectId){
	var emptyCategoties = [];
	var selectedCriteria = []; 
	$('.criteria-select-'+projectId+' :selected').each(function(){
		selectedCriteria.push($(this).val()); 
    });
	
	var selectedCategories = []; 
	$('.categories-select-'+projectId+' :selected').each(function(){
        selectedCategories.push($(this).val()); 
    });
    
	if(selectedCriteria.length == 0 && selectedCategories.length == 0){
		$('#marks-'+projectId+' > tbody  > tr').each(function(index, value) {
			if($(value).css('display') == 'none'){
				$(value).show();
			}
		});
	}else{
    
		var currentCategory;
		var open = 0;
    
		$('#marks-'+projectId+' > tbody  > tr').each(function(index, value) {
			var id = value.id;
			var keys = id.split("-");
 		
			if(keys.length === 3){
				if(currentCategory != null && open === 0){
					$(currentCategory).hide();
				}
 			
				open = 0;
				currentCategory = value;	
			}
 		
			if(keys.length === 4){
				if(($.inArray(keys[2], selectedCategories)) == -1 && ($.inArray(keys[3], selectedCriteria)) == -1){
					$(value).hide();
				}else{
					if($(value).css('display') == 'none'){
						$(value).show();
						var parent = '#'+keys[0]+'-'+keys[1]+'-'+keys[2];
						if($(parent).css('display') == 'none'){
							$(parent).show();
						}
					}
					open = open + 1;
				}
			}
		});
 	
		if(currentCategory != null && open === 0){
			$(currentCategory).hide();
		}
	}
}

function getSelectCriteria(projectCriteria, projectId){
	var select = '<select style="width:90%;" multiple="true" class="criteria-select-'+projectId+'">';
	
	$.each(projectCriteria, function(key, value ) {
		select += '<option value="' + value.id + '">' + value.title + '</option>\n';
	});
	
	select += '</select>';
	
	return select;
}

function getReviewForm(userId) {
	$.ajax({
		'url' : '/ajax/get/final_review_form',
		'type' : 'GET',
		'data' : {'user' : userId},
		'success' : function(data) {
			var s = '';
			$.each( data, function( elem ) {
				s+='<tr><td>'+elem.criterion.title+'</td><td><input type="text" id="'
				+elem.criterion.id+'"></td></tr>';
			});

			if(jQuery.isEmptyObject(data))
				s= '<br/><h4>No criteria for this project!</h4>';

			$('#final-review-form-list').html(s);
		},
		'error': function (data) {
			console.warn(data);
		}
	});
};
