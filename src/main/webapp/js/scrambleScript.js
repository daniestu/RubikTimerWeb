var historialScrambles = [];

function generateScramble() {
	fetch('GenerarScrambleServlet')
		.then(response => response.text())
		.then(scramble => {
			establecerCubo(scramble);
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
		establecerCubo(historialScrambles[historialScrambles.length - 1]);
		historialScrambles.splice(historialScrambles.length - 1);
		document.getElementById('scramble').textContent = historialScrambles[historialScrambles.length - 1];
		
		if ( !(historialScrambles.length > 1) ) {
			$('#previus-icon').attr('src', 'images/previus-disabled.png');
			$("#previus-scramble").removeClass("list-item");
			$("#previus-scramble").addClass("list-item-disabled");
		}
	}
}

function establecerCubo(scramble) {
	fetch('EstablecerCuboServlet?scramble=' + scramble)
		.then(response => response.json())
		.then(json => {
			
			$("#u1").css("background-color", json.u1);
			$("#u2").css("background-color", json.u2);
			$("#u3").css("background-color", json.u3);
			$("#u4").css("background-color", json.u4);
			$("#u5").css("background-color", json.u5);
			$("#u6").css("background-color", json.u6);
			$("#u7").css("background-color", json.u7);
			$("#u8").css("background-color", json.u8);
			$("#u9").css("background-color", json.u9);
			
			$("#l1").css("background-color", json.l1);
			$("#l2").css("background-color", json.l2);
			$("#l3").css("background-color", json.l3);
			$("#l4").css("background-color", json.l4);
			$("#l5").css("background-color", json.l5);
			$("#l6").css("background-color", json.l6);
			$("#l7").css("background-color", json.l7);
			$("#l8").css("background-color", json.l8);
			$("#l9").css("background-color", json.l9);
			
			$("#f1").css("background-color", json.f1);
			$("#f2").css("background-color", json.f2);
			$("#f3").css("background-color", json.f3);
			$("#f4").css("background-color", json.f4);
			$("#f5").css("background-color", json.f5);
			$("#f6").css("background-color", json.f6);
			$("#f7").css("background-color", json.f7);
			$("#f8").css("background-color", json.f8);
			$("#f9").css("background-color", json.f9);
			
			$("#r1").css("background-color", json.r1);
			$("#r2").css("background-color", json.r2);
			$("#r3").css("background-color", json.r3);
			$("#r4").css("background-color", json.r4);
			$("#r5").css("background-color", json.r5);
			$("#r6").css("background-color", json.r6);
			$("#r7").css("background-color", json.r7);
			$("#r8").css("background-color", json.r8);
			$("#r9").css("background-color", json.r9);
			
			$("#b1").css("background-color", json.b1);
			$("#b2").css("background-color", json.b2);
			$("#b3").css("background-color", json.b3);
			$("#b4").css("background-color", json.b4);
			$("#b5").css("background-color", json.b5);
			$("#b6").css("background-color", json.b6);
			$("#b7").css("background-color", json.b7);
			$("#b8").css("background-color", json.b8);
			$("#b9").css("background-color", json.b9);
			
			$("#d1").css("background-color", json.d1);
			$("#d2").css("background-color", json.d2);
			$("#d3").css("background-color", json.d3);
			$("#d4").css("background-color", json.d4);
			$("#d5").css("background-color", json.d5);
			$("#d6").css("background-color", json.d6);
			$("#d7").css("background-color", json.d7);
			$("#d8").css("background-color", json.d8);
			$("#d9").css("background-color", json.d9);
		});
	
}