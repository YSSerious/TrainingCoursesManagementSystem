	function validate() {
	 var value = document.getElementById('submitted').value;
	 if (value.length < 2) {
		$('#rev-err').text("Search value can't be less than 2 symbols");
		$('#rev-err').removeClass('hidden');
	   return false; 
	 }
	 return true;
	};


	var max = ${noOfPages};
	var curr = ${currentPage};

	var value = getParameterByName('value');
	var type = getParameterByName('type');
	if(max!=1){
	$('#bootstrap-pagination').bootpag({
		total: max,
	    page: curr,
	    maxVisible: 5,
	    leaps: true,
	    firstLastUse: true,
	    first: '←',
	    last: '→',
	    wrapClass: 'pagination',
	    activeClass: 'active',
	    disabledClass: 'disabled',
	    nextClass: 'next',
	    prevClass: 'prev',
	    lastClass: 'last',
	    firstClass: 'first'
	}).on("page", function(event, num){

		if(value!=null && type!=null)
			window.location.href = "?page="+num+"&value="+value+"&type="+type;
		else
			window.location.href = "?page="+num;
		
	});
	};

	function getParameterByName(name, url) {
	    if (!url) {
	      url = window.location.href;
	    }
	    name = name.replace(/[\[\]]/g, "\\$&");
	    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
	        results = regex.exec(url);
	    if (!results) return null;
	    if (!results[2]) return '';
	    return decodeURIComponent(results[2].replace(/\+/g, " "));
	};

	$(document).on("click", ".btn-danger", function () {
		window.location.href = "/allUsers";
	});

	function yesnoCheck() {
	    if (document.getElementById('roles').checked) {
	        $('#check').removeClass('hidden');
	        $('#submitted').addClass('hidden');
	        document.getElementById('submitted').disabled = true;
	        $('#check').children('input').each(function () {
	    	    this.disabled = false;
	    	});
	    }
	    else{ 
			$('#check').addClass('hidden');
	    	$('#submitted').removeClass('hidden');
	    	document.getElementById('submitted').disabled = false;
	    	$('#check').children('input').each(function () {
	    	    this.disabled = true;
	    	});
	    	document.getElementById('check').disabled = true;
	    }
	};
	$( document ).ready(yesnoCheck()); 


	var clicked = false;
	document.getElementById('button').onclick = function() {
		if(!clicked){
		 $('#table').removeClass('col-md-11');
		 $('#search').removeClass('col-md-1');
	     $('#table').addClass('col-md-9');
	     $('#search').addClass('col-md-3');
	     clicked = true;
		}
		else{
			setTimeout(function(){
			$('#table').removeClass('col-md-9');
			 $('#search').removeClass('col-md-3');
		     $('#table').addClass('col-md-10');
		     $('#search').addClass('col-md-1');
		     $('#table').removeClass('col-md-10');
		     $('#table').addClass('col-md-11');
		     clicked = false;
			}, 250);}
	};
