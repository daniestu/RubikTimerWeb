function autocompletarLogin() {
	const username = getCookie('RubikTimerUsername');
	const password = getCookie('RubikTimerPassword');
	
	document.getElementById("username").value = username;
	document.getElementById("password").value = password;
}

function getCookie(name) {
	const cookieValue = document.cookie.match('(^|;)\\s*' + name + '\\s*=\\s*([^;]+)');
	return cookieValue ? cookieValue.pop() : '';
}