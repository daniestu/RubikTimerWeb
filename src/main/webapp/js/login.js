function autocompletarLogin() {
	const username = getCookie('RubikTimerUsername');
	const password = getCookie('RubikTimerPassword');

	if (username && password) {
        document.getElementById("username").value = username;
        document.getElementById("password").value = password;

        document.getElementById("rememberMe").checked = true;
    }
}

function getCookie(name) {
	const cookieValue = document.cookie.match('(^|;)\\s*' + name + '\\s*=\\s*([^;]+)');
	return cookieValue ? cookieValue.pop() : '';
}