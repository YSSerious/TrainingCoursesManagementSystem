function getMark(mark){
	if(mark == ' ')
		return ' ';
	else if(mark == '-')
		return '<p style="padding:0px; margin:0px;">-</p>';
	return '<p style="padding:0px; margin:0px;">'+mark+'</p>';
}

function getRow(type){
	if(type == 'A')
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
	    		var currentDate = new Date();
	    		var startDate = new Date(value.startDate);
	    		var finishDate = new Date(value.finishDate);
	    		var divClass = 'default';
	    		
	    		if(startDate.getTime() < currentDate.getTime() && currentDate.getTime() < finishDate.getTime()){
	    			divClass = 'current';
	    		}else if(startDate.getTime() > currentDate.getTime()){
	    			divClass = 'primary';
	    		}else{
	    			divClass = 'finished';
	    		}
	    		
	    		s+= '<h2>Mentor projects: </h2><hr/><div class="panel panel-'+divClass+'"><div id="mpr'+value.id+'" class="panel-body">' + value.name;
	    		s+= '<div class="pull-right">'+new Date(value.startDate).toString().slice(3,15)+' - '+new Date(value.finishDate).toString().slice(3,15)+'</div>';
 	    		s+= '</div></div>';
	    	
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
	    	var s = '<h2>Student projects: </h2><hr/>';
	    	$.each( data, function( key, value ) {
	    		var currentDate = new Date();
	    		var startDate = new Date(value.startDate);
	    		var finishDate = new Date(value.finishDate);
	    		var divClass = 'default';
	    		
	    		if(startDate.getTime() < currentDate.getTime() && currentDate.getTime() < finishDate.getTime()){
	    			divClass = 'current';
	    		}else if(startDate.getTime() > currentDate.getTime()){
	    			divClass = 'primary';
	    		}else{
	    			divClass = 'finished';
	    		}
	    		s+= '<div class="panel panel-'+divClass+'"><div id="pr'+value.id+'" class="panel-body">' + value.name;
	    		s+= '<div class="pull-right">'+new Date(value.startDate).toString().slice(3,15)+' - '+new Date(value.finishDate).toString().slice(3,15)+'</div>';
 	    		s+= '</div></div><div id="sub'+value.id+'"><div class="row"><div class="col-sm-12"> <div class="col-sm-12"><div id="stinf-'+value.id+'"></div><br/><h4>'+value.description+'</h4><div class="col-sm-8 col-sm-offset-2 charts-wrapper" id="chart'+value.id+'">Loading...</div></div></div></div></div>';
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
    	 	    	    	
    	 	    	    	$('#stinf-'+value.id).html('<h4><b>Status: </b>'+data.studentStatuses[data.studentStatuses.length - 1].statusDescription+'</h4>');
                            createCharts(data.chartInfo, value.id);
    	 	    	    	
    	 	    	    	var table = '<br/><div class="row"><div class="col-sm-12"> <div class="col-sm-12">';
    	 	    	    	
    	 	    	    	if(data.fullMeetingReviews.length > 0){
    	 	    	    		table+= '<br/><h2>Meeting reviews: </h2>';
    	 	    	    		table+= getFullMeetingReviews(data.fullMeetingReviews);
    	 	    	    	}
    	 	    	    	
    	 	    	    	if(data.markTableDto.tableData.length>0){
    	 	    	    	table+= '<br/>';
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
    	 	    	    	}
                            //table+= '<br/><h2>Statuses: </h2>';
                            //table+= getStatusesTable(data.studentStatuses);
                            
                            //table+= '<br/><h2>Meeting reviews: </h2>';
                            //table+= getMeetingReviewsTable(data.meetingReviews);
    	 	    	    	
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

    	 	    	       var $table = $('#marks-'+value.id);
    	 	    	       var $fixedColumn = $table.clone().insertBefore($table).addClass('fixed-column');

    	 	    	       $fixedColumn.find('th:not(:first-child),td:not(:first-child)').remove();

    	 	    	       $fixedColumn.find('tr').each(function (i, elem) {
    	 	    	           $(this).height($table.find('tr:eq(' + i + ')').height());
    	 	    	           $(this).width($table.find('tr:eq(' + i + ')').width());
    	 	    	       });
    	 	    	       
    	 	    	      $.each(data.fullMeetingReviews, function(key, meetingReview) {
    	 	    			$('#m'+meetingReview.id).click(function() {
    	 	    				$('#sm'+meetingReview.id).slideToggle();
    	 	    			});
    	 	    			
    	 	    			if(key != 0){
    	 	    				$('#sm'+meetingReview.id).hide();
    	 	    			}
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
 	table+= '<div class="table-responsive"><table class="table table-bordered user-table" id="marks-'+projectId+'"><tr><th>#</th>';
 	
 	for(var i =0; i<15; i++){
 	$.each(markTableDto.meetings, function( key, value ) {
 		if(key<markTableDto.meetings.length-1)
 			table+= '<th>M' + (key+1) + '</th>';
 		else
 			table+= '<th>FR</th>';
 	});
 	}
 	table+= '</tr>';

 	$.each(markTableDto.tableData, function(key, categoryResult ) {
 		table+= '<tr id="row-'+projectId+'-'+categoryResult.categoryDto.id+'"><td><b>' + categoryResult.categoryDto.name + '</b></td><td colspan="'+ markTableDto.meetings.length*15+'"></td></tr>';
 		
 		$.each(categoryResult.criteriaResults, function( i, criterionResult ) {
 			table+= '<tr class="active" id="row-'+projectId+'-'+categoryResult.categoryDto.id+'-'+criterionResult.criterionId+'"><td style="padding-left:30px;">' + criterionResult.criterionName + '</td>';
 			for(var i =0; i<15; i++){
 			$.each(criterionResult.marks, function(index, mark) {
 					if(mark.commentary == '')
 						table+= '<td><span title="'+mark.description+''+mark.commentary+'">' + getMark(mark.value) + '</span></td>';
 					else
 						table+= '<td><span title="'+mark.description+': '+mark.commentary+'">' + getMark(mark.value) + '</span></td>';
	    		});
 			}
 			table+= '</tr>';
 		});
 		
 		
 	});
 	
 	table+= '</table></div>';
 	
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
    
	
		$('#marks-'+projectId+' > tbody  > tr').each(function(index, value) {
			if($(value).css('display') == 'none'){
				$(value).show();
			}
		});
	if(selectedCriteria.length != 0 || selectedCategories.length != 0){
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
		'success' : function(resp) {
			var s = '';
			console.warn(resp);
			$.each( resp.data, function( key, value ) {
				s+='<tr class="fin-rev-res-item"><td>'+value.criterion.title+'</td><td><select id="sel'
				+value.criterion.id+'">';
				for(var i=1; i<6; ++i){
					s+='<option val='+i;
					if(value.mark&&i==value.mark.value)
						s+=' selected';
					s+='>'+i+'</option>';
				}
				s+='</select></td><td><input required type="text" id="'
					+value.criterion.id+'"';
				if(value.commentary)
					s+=' value="'+value.commentary+'"';
				s+='></td></tr>';
			});
			s+='<tr><td colspan="3"><label>General: </label><textarea class="form-control" id="fin-rev-com" rows="5">';
			if(resp.comment)
				s+=resp.comment;
			s+='</textarea></td></tr>';
			if(jQuery.isEmptyObject(resp.data))
				s= '<br/><h4>No criteria for this project!</h4>';

			$('#final-review-form-list').html(s);
			$('#addFinReview').find('.btn').removeClass('hidden');
		},
		'error': function (data) {
			console.warn(data);
		}
	});
};

function doFinalReview(userId) {
	function sendAjax(data, comment) {
		console.log(comment);
		$.ajax({
			url: '/ajax/post/final_review_form/'+userId,
			type: 'POST',
			contentType: "application/json",
			dataType: 'json',
			data: JSON.stringify({data:data,comment:comment}),
			success: function (data) {
				console.log('success');
			},
			error: function (error) {
				console.log(error.responseText);
			}
		});
	};
	var error = null;
	var data = [];
	var comment = $('#fin-rev-com').val();
	$('#final-review-form-list').find($('.fin-rev-res-item')).each(function () {
		var finReview = {"mark": {"value": $(this).find('select').val()}, "criterion": {"id": $(this).find('input').attr('id')}, "commentary": $(this).find('input').val()};
		if(!$(this).find('input').val()) {
			error = 'All comments are required!';
			return false;
		}
		data.push(finReview);
	});
	if(!error){
		$('#fin-rev-err').addClass('hidden');
		$('#addFinReview').modal('toggle');
		sendAjax(data, comment);
	}
	else{
		$('#fin-rev-err').text(error);
		$('#fin-rev-err').removeClass('hidden');
	}
};

function getFullMeetingReviews(fullMeetingReviews){
	var div = '';
	
	$.each(fullMeetingReviews, function(key, meetingReview) {
		
		var type = '';
		
		if(meetingReview.marks.length == 0){
			type = 'default';
		}else{
			type = 'primary';
		}
		
		div  += '<div class="panel panel-'+type+'"><div id=m'+meetingReview.id+' class="panel-heading">';
		div += meetingReview.name + ' <div class="pull-right">' + meetingReview.date + '</div></div><div id=sm'+meetingReview.id+' class="panel-body">';
			  
		
		div += '<h4>' + meetingReview.commentary + '</h4>';
		
		if(meetingReview.marks.length != 0){
			div += '<hr/>';
		}
		
		div += '<div style="max-height:350px; overflow: auto;"><table class="table table-bordered">'
		div += '<thead style="background-color:#e6e6e6"><tr><td>Criterion</td><td>Mark</td></tr></thead><tbody>';
		$.each(meetingReview.marks, function(key, mark) {
			div += '<tr>';
			div += '<td>' + mark.criterionName + '</td>';
			div += '<td>' + mark.value + '</td>';
			div += '</tr>';
		});
		
		div += '</tbody></table></div></div></div>';
		
	});
	
	return div;
}

function report(studentId){
	$.ajax({
	    'url' : '/ajaxstudentprojects',
	    'type' : 'GET',
	    'data' : {'user' : studentId},
	    
	    'success' : function(data) {
	    	var select = '<div class="row"><div class="col-sm-8">';
	    	select += '<select style="width:100%;" multiple="true" class="select-project-report">';
	    	
	    	$.each(data, function(key, value ) {
	    		select += '<option value="' + value.id + '">' + value.name + '</option>\n';
	    	});
	    	
	    	select += '</select></div><div class="col-sm-4">';
	    	select += '<button onclick="loadCriteria('+studentId+')" class="btn btn-primary pull-right">Next step</button></div></div>';
	    	
	    	if(data.length == 0){
	    		$('#project-report-back').html('There are no projects!');
	    	}else{
	    		$('#project-report-back').html(select);
	 	    	$('.select-project-report').select2({
		    		  placeholder: "Select project or leave it empty to select all"
		    	});
	    	}

	    }
	  });
}

function loadCriteria(studentId){
	var projects = $('.select-project-report').val();
	$.ajax({
	    'url' : '/ajaxcriteria',
	    'type' : 'GET',
	    'data' : {
	    	'student' : studentId,
	    	'projects' : JSON.stringify(projects)},
	    'success' : function(data) {
	    	
	    	var select = '<br/><div class="row"><div class="col-sm-8">';
	    	select += '<select style="width:100%;" multiple="true" class="select-category-report">';
	    	
	    	$.each(data.categories, function(key, value ) {
	    		select += '<option value="' + value.id + '">' + value.name + '</option>\n';
	    	});
	    	
	    	select += '</select></div><div class="col-sm-4"></div></div>';
	    	$('#criteria-report-back').html(select);
 	    	$('.select-category-report').select2({
	    		  placeholder: "Select categories"
	    	});
 	    	
 	    	select = '<br/><div class="row"><div class="col-sm-8">';
	    	select += '<select style="width:100%;" multiple="true" class="select-criterion-report">';
	    	
	    	$.each(data.criteria, function(key, value ) {
	    		select += '<option value="' + value.id + '">' + value.title + '</option>\n';
	    	});
	    	
	    	select += '</select></div><div class="col-sm-4"><button onclick="sendRequest('+studentId+')" class="btn btn-primary pull-right">Download</button></div></div>';
	    	$('#criteria-report-back').append(select);
 	    	$('.select-criterion-report').select2({
	    		  placeholder: "Select criteria"
	    	});
	    }
	});
}

function sendRequest(studentId){
	var projects = $('.select-project-report').val();
	var categories = $('.select-category-report').val();
	var criteria = $('.select-criterion-report').val();
	
	var url = '/studentMarks/'+studentId+'/';
	url +=JSON.stringify(projects)+'/'+JSON.stringify(categories);
	url += '/'+JSON.stringify(criteria)+'.xls';
	window.location = url;
}