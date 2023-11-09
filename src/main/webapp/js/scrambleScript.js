var historialScrambles = [];

function generateScramble() {
	fetch('cube/generateScramble')
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
		historialScrambles.splice(historialScrambles.length - 1);
		establecerCubo(historialScrambles[historialScrambles.length - 1]);
		document.getElementById('scramble').textContent = historialScrambles[historialScrambles.length - 1].toUpperCase();
		
		if ( !(historialScrambles.length > 1) ) {
			$('#previus-icon').attr('src', 'images/previus-disabled.png');
			$("#previus-scramble").removeClass("list-item");
			$("#previus-scramble").addClass("list-item-disabled");
		}
	}
}

function scramblePersonalizado(scramble) {
	if (validarScramble(scramble)) {
		document.getElementById('scramble').textContent = scramble.toUpperCase();
		document.getElementById('scramble-personalizado-modal').style.display = "none";
		historialScrambles.push(scramble);
		establecerCubo(scramble);
		
		if(historialScrambles.length > 1) {
			$('#previus-icon').attr('src', 'images/previus.png');
			$("#previus-scramble").removeClass("list-item-disabled");
			$("#previus-scramble").addClass("list-item");
		}
	}else {
		document.getElementById("scramble-personalizado-modal-error").style.display = "block";
	}
}

function validarScramble(scramble) {
	const regex = /^([UDLRFB]('?2?)\s?)+$/i;
	
	if (regex.test(scramble)) {
		return true;
	} else {
		return false;
	}
}


function establecerCubo(scramble) {
	fetch('user/checkAuthentication')
		.then(response => response.json())
		.then(data => {
			if(!data.authenticated) {
				window.location.href = "/rubikTimerWeb/user/login";
			}else {
				fetch('cube/generateCube?scramble=' + scramble.toUpperCase())
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
					
					/*******************/
					/* CUBO DEL MODAL */
					/*******************/
					
					$("#modal-u1").css("background-color", json.u1);
					$("#modal-u2").css("background-color", json.u2);
					$("#modal-u3").css("background-color", json.u3);
					$("#modal-u4").css("background-color", json.u4);
					$("#modal-u5").css("background-color", json.u5);
					$("#modal-u6").css("background-color", json.u6);
					$("#modal-u7").css("background-color", json.u7);
					$("#modal-u8").css("background-color", json.u8);
					$("#modal-u9").css("background-color", json.u9);
					
					$("#modal-l1").css("background-color", json.l1);
					$("#modal-l2").css("background-color", json.l2);
					$("#modal-l3").css("background-color", json.l3);
					$("#modal-l4").css("background-color", json.l4);
					$("#modal-l5").css("background-color", json.l5);
					$("#modal-l6").css("background-color", json.l6);
					$("#modal-l7").css("background-color", json.l7);
					$("#modal-l8").css("background-color", json.l8);
					$("#modal-l9").css("background-color", json.l9);
					
					$("#modal-f1").css("background-color", json.f1);
					$("#modal-f2").css("background-color", json.f2);
					$("#modal-f3").css("background-color", json.f3);
					$("#modal-f4").css("background-color", json.f4);
					$("#modal-f5").css("background-color", json.f5);
					$("#modal-f6").css("background-color", json.f6);
					$("#modal-f7").css("background-color", json.f7);
					$("#modal-f8").css("background-color", json.f8);
					$("#modal-f9").css("background-color", json.f9);
					
					$("#modal-r1").css("background-color", json.r1);
					$("#modal-r2").css("background-color", json.r2);
					$("#modal-r3").css("background-color", json.r3);
					$("#modal-r4").css("background-color", json.r4);
					$("#modal-r5").css("background-color", json.r5);
					$("#modal-r6").css("background-color", json.r6);
					$("#modal-r7").css("background-color", json.r7);
					$("#modal-r8").css("background-color", json.r8);
					$("#modal-r9").css("background-color", json.r9);
					
					$("#modal-b1").css("background-color", json.b1);
					$("#modal-b2").css("background-color", json.b2);
					$("#modal-b3").css("background-color", json.b3);
					$("#modal-b4").css("background-color", json.b4);
					$("#modal-b5").css("background-color", json.b5);
					$("#modal-b6").css("background-color", json.b6);
					$("#modal-b7").css("background-color", json.b7);
					$("#modal-b8").css("background-color", json.b8);
					$("#modal-b9").css("background-color", json.b9);
					
					$("#modal-d1").css("background-color", json.d1);
					$("#modal-d2").css("background-color", json.d2);
					$("#modal-d3").css("background-color", json.d3);
					$("#modal-d4").css("background-color", json.d4);
					$("#modal-d5").css("background-color", json.d5);
					$("#modal-d6").css("background-color", json.d6);
					$("#modal-d7").css("background-color", json.d7);
					$("#modal-d8").css("background-color", json.d8);
					$("#modal-d9").css("background-color", json.d9);
				});
			}
		});
	
	
	
}