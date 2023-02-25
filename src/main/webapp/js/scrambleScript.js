function generateScramble() {
	fetch('GenerarScrambleServlet')
		.then(response => response.text())
		.then(scramble => {
			document.getElementById('scramble').textContent = scramble;
		});
}