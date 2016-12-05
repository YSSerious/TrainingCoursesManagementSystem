function validate() {
	var value = document.getElementById('submitted').value;
	if (value.length < 2) {
		$('#rev-err').text("Search value can't be less than 2 symbols");
		$('#rev-err').removeClass('hidden');
		return false;
	}
	return true;
};



