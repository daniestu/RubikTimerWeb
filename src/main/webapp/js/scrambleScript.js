var historialScrambles = [];

function generateScramble() {
	fetch('GenerarScrambleServlet')
		.then(response => response.text())
		.then(scramble => {
			document.getElementById('scramble').textContent = scramble;
			historialScrambles.push(scramble);
			
			if(historialScrambles.length > 1) {
				$('#previus-icon').attr('src', 'images/previus.png');
				$("#previus-scramble").removeClass("list-item-disabled");
				$("#previus-scramble").addClass("list-item");
			}
		});
}

function scrambleAnterior() {
	if (historialScrambles.length > 1) {
		historialScrambles.splice(historialScrambles.length - 1);
		document.getElementById('scramble').textContent = historialScrambles[historialScrambles.length - 1];
		
		if ( !(historialScrambles.length > 1) ) {
			$('#previus-icon').attr('src', 'images/previus-disabled.png');
			$("#previus-scramble").removeClass("list-item");
			$("#previus-scramble").addClass("list-item-disabled");
		}
	}
}