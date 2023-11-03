var originalSelectedOption;

function sesionChanged(sesion){
	if(sesion == "selectOptionNew") {
		document.getElementById("sesion_select").value = originalSelectedOption;
		document.getElementById("nombre_sesion").value = "";
		document.getElementById("nuevaSesion-modal-error").style.display = "none";
		document.getElementById("nuevaSesion-modal").style.display = "flex";
	}else if(sesion == "selectOptionDelete") {
		document.getElementById("sesion_select").value = originalSelectedOption;
		document.getElementById("borrarSesion-modal-error").style.display = "none";
		document.getElementById("borrarSesion-modal").style.display = "flex";
	}else {
		getTiemposSesion(sesion);
	}
	document.getElementById("sesion_select").blur();
}

function getEstadisticasSesion(tiempos) {
	const options = {
	  method: 'POST',
	  headers: { 'Content-Type': 'application/json' },
	  body: JSON.stringify(tiempos)
	};

	fetch('session/getData', options)
		.then(response => response.json())
		.then(estadisticas => {
			
			document.getElementById("total").textContent = estadisticas.total;
			document.getElementById("info-total").value = estadisticas.total;
			
			if (estadisticas.hasOwnProperty("mejor")) {
				const mejor = estadisticas.mejor;
				
				if (mejor.dnf == 1) {
					document.getElementById("mejor").textContent = "DNF";
					document.getElementById("info-best").value = "DNF(" + mejor.tiempo + ")";
				}else {
					document.getElementById("mejor").textContent = (mejor.mas_2 == 0) ? mejor.tiempo : (sumarMas2(mejor.tiempo) + "+");
					document.getElementById("info-best").value = (mejor.mas_2 == 0) ? mejor.tiempo : (sumarMas2(mejor.tiempo) + "+");
				}
				document.getElementById("mejor").onclick = function() {
					mostrarTiempo(mejor);
				}
				document.getElementById("info-best").onclick = function() {
					mostrarTiempo(mejor);
				}
			}else {
				document.getElementById("mejor").textContent = "";
				document.getElementById("mejor").onclick = null;
				document.getElementById("info-best").value = "";
				document.getElementById("info-best").onclick = null;
			}
			
			if (estadisticas.hasOwnProperty("peor")) {
				const peor = estadisticas.peor;
				
				if (peor.dnf == 1) {
					document.getElementById("peor").textContent = "DNF";
					document.getElementById("info-worst").value = "DNF(" + peor.tiempo + ")";
				}else {
					document.getElementById("peor").textContent = (peor.mas_2 == 0) ? peor.tiempo : (sumarMas2(peor.tiempo) + "+");
					document.getElementById("info-worst").value = (peor.mas_2 == 0) ? peor.tiempo : (sumarMas2(peor.tiempo) + "+");
				}
				
				document.getElementById("peor").onclick = function() {
					mostrarTiempo(peor);
				}
				document.getElementById("info-worst").onclick = function() {
					mostrarTiempo(peor);
				}
			}else {
				document.getElementById("peor").textContent = "";
				document.getElementById("peor").onclick = null;
				document.getElementById("info-worst").value = "";
				document.getElementById("info-worst").onclick = null;
			}
			
			if (estadisticas.hasOwnProperty("ao5")) {
				const ao5 = estadisticas.ao5;
				document.getElementById("ao5").textContent = ao5.tiempo;
				document.getElementById("ao5").onclick = function() {
					mostrarAvg(ao5);
				}
				document.getElementById("info-ao5").value = ao5.tiempo;
				document.getElementById("info-ao5").onclick = function() {
					mostrarAvg(ao5);
				}
			}else {
				document.getElementById("ao5").textContent = "";
				document.getElementById("ao5").onclick = null;
				document.getElementById("info-ao5").value = "";
				document.getElementById("info-ao5").onclick = null;
			}
			
			if (estadisticas.hasOwnProperty("ao12")) {
				const ao12 = estadisticas.ao12;
				document.getElementById("ao12").textContent = ao12.tiempo;
				document.getElementById("ao12").onclick = function() {
					mostrarAvg(ao12);
				}
				document.getElementById("info-ao12").value = ao12.tiempo;
				document.getElementById("info-ao12").onclick = function() {
					mostrarAvg(ao12);
				}
			}else {
				document.getElementById("ao12").textContent = "";
				document.getElementById("ao12").onclick = null;
				document.getElementById("info-ao12").value = "";
				document.getElementById("info-ao12").onclick = null;
			}
			
			if (estadisticas.hasOwnProperty("ao100")) {
				const ao100 = estadisticas.ao100;
				document.getElementById("ao100").textContent = ao100.tiempo;
				document.getElementById("ao100").onclick = function() {
					mostrarAvg(ao100);
				}
				document.getElementById("info-ao100").value = ao100.tiempo;
				document.getElementById("info-ao100").onclick = function() {
					mostrarAvg(ao100);
				}
			}else {
				document.getElementById("ao100").textContent = "";
				document.getElementById("ao100").onclick = null;
				document.getElementById("info-ao100").value = "";
				document.getElementById("info-ao100").onclick = null;
			}
			
			if (estadisticas.hasOwnProperty("bestao5")) {
				
				const bestao5 = estadisticas.bestao5;
				document.getElementById("info-bestao5").value = bestao5.tiempo;
				document.getElementById("info-bestao5").onclick = function() {
					mostrarAvg(bestao5);
				}
			}else {
				document.getElementById("info-bestao5").value = "";
				document.getElementById("info-bestao5").onclick = null;
			}
			
			if (estadisticas.hasOwnProperty("bestao12")) {
				const bestao12 = estadisticas.bestao12;
				document.getElementById("info-bestao12").value = bestao12.tiempo;
				document.getElementById("info-bestao12").onclick = function() {
					mostrarAvg(bestao12);
				}
			}else {
				document.getElementById("info-bestao12").value = "";
				document.getElementById("info-bestao12").onclick = null;
			}
			
			if (estadisticas.hasOwnProperty("bestao100")) {
				const bestao100 = estadisticas.bestao100;
				document.getElementById("info-bestao100").value = bestao100.tiempo;
				document.getElementById("info-bestao100").onclick = function() {
					mostrarAvg(bestao100);
				}
			}else {
				document.getElementById("info-bestao100").value = "";
				document.getElementById("info-bestao100").onclick = null;
			}
			
			document.getElementById("info-desv").value = estadisticas.desv;
			
			document.getElementById("media").textContent = estadisticas.media;
			document.getElementById("info-avg").value = estadisticas.media;
		})
}

function getTiemposSesion(sesion) {
	originalSelectedOption = sesion;
	fetch('solve/get?sesion=' + sesion)
		.then(response => response.json())
		.then(json => {
			if(json.usuario == "nulo") {
				window.location.href = "./login.jsp";
			}else {
				json = formatJsonTiempos(json, 0);
				getEstadisticasSesion(json);
				const tbody = document.querySelector('#tablaTiempos tbody');
				tbody.innerHTML = '';
				for (let i = json.length-1; i >= 0; i--) {
					const tiempo = json[i];
					const tr = document.createElement('tr');
					
					const idTd = document.createElement('td');
					idTd.textContent = i+1;
					tr.appendChild(idTd);
					
					const tiempoTd = document.createElement('td');
					
					if (tiempo.dnf == 1) {
						tiempoTd.textContent = "DNF";
					}else {
						tiempoTd.textContent = (tiempo.mas_2 == 0) ? tiempo.tiempo : (sumarMas2(tiempo.tiempo) + "+");
					}
					
					tiempoTd.classList.add('tablaTiempos-tiempo');
					tiempoTd.onclick = function() {
						mostrarTiempo(tiempo);
					}
					tr.appendChild(tiempoTd);
					
					tbody.appendChild(tr);
				}
			}
		});
}

function mostrarTiempo(tiempo) {
	tiempo = formatJsonTiempos(tiempo, 1);
	document.getElementById("hidden-id").value = tiempo.id;
	document.getElementById("scrambleInput").value = tiempo.scramble;
	document.getElementById("fecha").value = tiempo.fecha;
	
	if (tiempo.mas_2 == 0) {
		document.getElementById("solveBtn-mas2").classList.remove("solveBtn-clicked");
	}else {
		document.getElementById("solveBtn-mas2").classList.add("solveBtn-clicked");
	}
	
	if (tiempo.dnf == 0) {
		document.getElementById("solveBtn-dnf").classList.remove("solveBtn-clicked");
		document.getElementById("tiempo").value = (tiempo.mas_2 == 0) ? tiempo.tiempo : (sumarMas2(tiempo.tiempo) + "+");
	}else {
		document.getElementById("solveBtn-dnf").classList.add("solveBtn-clicked");
		document.getElementById("tiempo").value = "DNF(" + tiempo.tiempo + ")";;
	}
	
	document.getElementById("solve-modal-error").style.display = "none";
	document.getElementById("solveModal").style.display = "flex";
}

function mostrarAvg(avg) {
	const total = avg.solves.length;
	document.getElementById("avgModal-title").textContent = "Ao" + total;
	document.getElementById("avg-tiempo").value = avg.tiempo;
	
	const tabla = document.querySelector('#avg-table');
	tabla.innerHTML = "";
	for (let i = avg.solves.length-1; i >= 0; i--) {
		const solve = avg.solves[i];
		
		const tr = document.createElement('tr');
		const solveTd = document.createElement('td');
		solveTd.textContent = (solve.mas_2 == 0) ? solve.tiempo : (sumarMas2(solve.tiempo) + "+");
		
		tr.appendChild(solveTd);
		tabla.appendChild(tr);
	}
	document.getElementById("avgModal").style.display = "flex";
}

function borrarTiempo(id) {
	fetch('solve/delete?id=' + id)
		.then(response => response.json())
		.then(data => {
			if(data.eliminado) {
				getSesiones();
				document.getElementById("solveModal").style.display = "none";
			}else {
				document.getElementById("solve-modal-error").style.display = "block";
			}
	})
	.catch(function() {
		document.getElementById("solve-modal-error").style.display = "block";
	});
}

function getSesiones() {
  fetch('session/get')
    .then(response => response.json())
    .then(sesiones => {
      if (sesiones.length == 0) {
        crearSesion("Default");
      } else {
        const select = document.getElementById("sesion_select");
        select.innerHTML = "";

        for (var i = 0; i < sesiones.length; i++) {
          const option = document.createElement("option");
          option.text = sesiones[i].nombre;
          option.value = sesiones[i].nombre;

          select.add(option);
        }

        const separator = document.createElement("optgroup");
        separator.label = "\u2014\u2014\u2014\u2014\u2014\u2014\u2014";
        select.add(separator);

        const nuevaSesion = document.createElement("option");
        nuevaSesion.text = "Nueva sesión";
        nuevaSesion.value = "selectOptionNew";
        select.add(nuevaSesion);
        
        const borrarSesion = document.createElement("option");
        borrarSesion.text = "Eliminar sesión";
        borrarSesion.value = "selectOptionDelete";
        select.add(borrarSesion);

		if ( !(originalSelectedOption === undefined) ) {
			select.value = originalSelectedOption;
		}
        getTiemposSesion(select.value);
      }
    });
}

function crearSesion(nombreSesion) {
	if(validarNombreSesion(nombreSesion)) {
		fetch('session/add?sesion=' + nombreSesion)
			.then(response => response.json())
			.then(sesion => {
				originalSelectedOption = sesion.nombre;
				getSesiones();
				document.getElementById("nuevaSesion-modal").style.display = "none";
		});
	}else {
		document.getElementById("nuevaSesion-modal-error").style.display = "block";
	}
}

function borrarSesion(nombreSesion) {
	fetch('session/delete?sesion=' + nombreSesion)
		.then(response => response.json())
		.then(data => {
			if(data.eliminado) {
				originalSelectedOption = undefined;
				getSesiones();
				ocultarBorrarSesionModal();
			}else {
				document.getElementById("borrarSesion-modal-error").style.display = "block";
			}
		})
		.catch(function() {
			document.getElementById("borrarSesion-modal-error").style.display = "block";
		});
}

function actualizarSesion(newName) {
	var name = document.getElementById("sesion_select").value;
	
	fetch('session/update?name=' + name + '&newName=' + newName)
		.then(response => response.text())
		.then(data => {
			if(data == "true") {
				originalSelectedOption = newName;
				getSesiones();
				$("#session-info-modal").hide();
				
			}else {
				document.getElementById("session-info-modal-error").style.display = "block";
			}
		})
		.catch(function() {
			document.getElementById("session-info-modal-error").style.display = "block";
		});
}

function validarNombreSesion(nombreSesion) {
	var opciones = $('#sesion_select option');
	for (var i = 0; i < opciones.length; i++) {
		if (opciones[i].value === nombreSesion) {
			return false;
		}
	}
	return true;
}

function validarTiempo(tiempo, scramble) {
	let ok = false;
	const regex = /^(0[0-9]|[1-5][0-9]):(0[0-9]|[1-5][0-9]):([0-9][0-9])$/;
	
	if (regex.test(tiempo)) {
		if (validarScramble(scramble)) {
			ok = true;
		}
	}
	
	if (ok) {
		guardarTiempo(tiempo, scramble);
		document.getElementById("add-solve-modal").style.display = "none";
	}else {
		document.getElementById("add-solve-modal-error").style.display = "block";
	}
}

function formatJsonTiempos(json, accion) {
	if(accion == 0) {
		for (let i = 0; i < json.length; i++) {
			let fecha = new Date(json[i].fecha);
			let dia = fecha.getDate().toString().padStart(2, '0');
			let mes = (fecha.getMonth() + 1).toString().padStart(2, '0');
			let anio = fecha.getFullYear().toString();
			let fechaFormateada = `${anio}-${mes}-${dia}`;
			json[i].fecha = fechaFormateada;
		}
	}else if(accion == 1) {
		let fecha = new Date(json.fecha);
		let dia = fecha.getDate().toString().padStart(2, '0');
		let mes = (fecha.getMonth() + 1).toString().padStart(2, '0');
		let anio = fecha.getFullYear().toString();
		let fechaFormateada = `${dia}/${mes}/${anio}`;
		json.fecha = fechaFormateada;
	}
	
	return json;
}

function ocultarBorrarSesionModal() {
	document.getElementById("borrarSesion-modal").style.display = "none";
}

function confirmDelete(nombreSesion) {
	var confirmacion = window.confirm("¿Estás seguro de que deseas eliminar esta sesión?");

	if (confirmacion) {
		borrarSesion(nombreSesion);
	}
	
	$("#session-info-modal").hide();
	
}

function addMas2(id) {
	event.preventDefault();
	var action;
	if (document.getElementById("solveBtn-mas2").classList.contains("solveBtn-clicked")) {
		action = 0;
	} else {
		action = 1;
	}
	fetch('solve/updateMas2?id=' + id + '&action=' + action)
	.then(response => response.json())
	.then(data => {
		if (data.actualizado) {
			if (action == 0) {
				document.getElementById("solveBtn-mas2").classList.remove("solveBtn-clicked");
				document.getElementById("tiempo").value = restarMas2(document.getElementById("tiempo").value);
			}else {
				document.getElementById("solveBtn-mas2").classList.add("solveBtn-clicked");
				document.getElementById("solveBtn-dnf").classList.remove("solveBtn-clicked");
				var tiempo = document.getElementById("tiempo").value;
				
				if (tiempo.includes('DNF')) {
					tiempo = tiempo.replace(/^DNF\((.*?)\)$/, '$1');
				}
				
				document.getElementById("tiempo").value = sumarMas2(tiempo) + "+";
				
			}
			getSesiones();
		}
	});
}

function addDnf(id) {
	event.preventDefault();
	var action;
	if (document.getElementById("solveBtn-dnf").classList.contains("solveBtn-clicked")) {
		action = 0;
	} else {
		action = 1;
	}
	fetch('solve/updateDnf?id=' + id + '&action=' + action)
	.then(response => response.json())
	.then(data => {
		if (data.actualizado) {
			if (action == 0) {
				document.getElementById("solveBtn-dnf").classList.remove("solveBtn-clicked");
				document.getElementById("tiempo").value = document.getElementById("tiempo").value.replace(/^DNF\((.*?)\)$/, '$1');
				
			}else {
				document.getElementById("solveBtn-dnf").classList.add("solveBtn-clicked");
				document.getElementById("solveBtn-mas2").classList.remove("solveBtn-clicked");
				
				var tiempo = document.getElementById("tiempo").value;
				
				if (tiempo.includes('+')) {
					tiempo = restarMas2(tiempo);
				}
				document.getElementById("tiempo").value = "DNF(" + tiempo + ")";
			}
			getSesiones();
		}
	});
}

function sumarMas2(tiempoOriginal) {
	var tiempoPartes = tiempoOriginal.split(":");
	var minutos = parseInt(tiempoPartes[0]);
  	var segundos = parseInt(tiempoPartes[1]);
  	
  	segundos += 2;
  	
  	if (segundos >= 60) {
	    minutos += Math.floor(segundos / 60);
	    segundos %= 60;
  	}
  	
  	var minutosFormateados = minutos.toString().padStart(2, "0");
  	var segundosFormateados = segundos.toString().padStart(2, "0");
  	
  	var nuevoTiempo = minutosFormateados + ":" + segundosFormateados + ":" + tiempoPartes[2];

  	return nuevoTiempo;
}

function restarMas2(tiempoOriginal) {
	var tiempoPartes = tiempoOriginal.split(":");
	var minutos = parseInt(tiempoPartes[0]);
  	var segundos = parseInt(tiempoPartes[1]);
  	
  	segundos -= 2;
  	
	if (segundos < 0) {
		minutos += Math.floor(segundos / 60);
		segundos = 60 + (segundos % 60);
	}
  	
  	var minutosFormateados = minutos.toString().padStart(2, "0");
  	var segundosFormateados = segundos.toString().padStart(2, "0");
  	
  	var nuevoTiempo = minutosFormateados + ":" + segundosFormateados + ":" + tiempoPartes[2].slice(0, -1);
  	
  	return nuevoTiempo;
}


