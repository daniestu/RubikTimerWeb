function generateScramble() {
	// Enviar solicitud al servidor para generar el scramble
	fetch('GenerarScrambleServlet')
		.then(response => response.text())
		.then(scramble => {
			// Actualizar el HTML con el scramble generado
			document.getElementById('scramble').textContent = scramble;
		});
}