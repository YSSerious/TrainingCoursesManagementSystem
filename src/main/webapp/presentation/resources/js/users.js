function validate() {
	var value = document.getElementById('submitted').value;
	if (value.length < 1 && !document.getElementById('roles').checked) {
		$('#rev-err').text("Search value can't be less than 1 symbol");
		$('#rev-err').removeClass('hidden');
		return false;
	}
	return true;
};



