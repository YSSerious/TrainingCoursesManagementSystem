function validate() {
	var value = document.getElementById('submitted').value;
	if (value.length < 1 && !document.getElementById('roles').checked) {
		$('#rev-err').text(lang.users_validation);
		$('#rev-err').removeClass('hidden');
		return false;
	}
	return true;
};



