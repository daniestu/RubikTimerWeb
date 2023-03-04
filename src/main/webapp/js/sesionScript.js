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

	fetch('GetEstadisticasServlet', options)
		.then(response => response.json())
		.then(estadisticas => {
			document.getElementById("total").textContent = estadisticas.total;
			
			if (estadisticas.hasOwnProperty("mejor")) {
				const mejor = estadisticas.mejor;
				document.getElementById("mejor").textContent = mejor.tiempo;
				document.getElementById("mejor").onclick = function() {
					mostrarTiempo(mejor.id);
				}
			}else {
				document.getElementById("mejor").textContent = "";
				document.getElementById("mejor").onclick = null;
			}
			
			if (estadisticas.hasOwnProperty("peor")) {
				const peor = estadisticas.peor;
				document.getElementById("peor").textContent = peor.tiempo;
				document.getElementById("peor").onclick = function() {
					mostrarTiempo(peor.id);
				}
			}else {
				document.getElementById("peor").textContent = "";
				document.getElementById("peor").onclick = null;
			}
			
			if (estadisticas.hasOwnProperty("ao5")) {
				const ao5 = estadisticas.ao5;
				document.getElementById("ao5").textContent = ao5.tiempo;
				document.getElementById("ao5").onclick = function() {
					mostrarAvg(ao5);
				}
			}else {
				document.getElementById("ao5").textContent = "";
				document.getElementById("ao5").onclick = null;
			}
			
			if (estadisticas.hasOwnProperty("ao12")) {
				const ao12 = estadisticas.ao12;
				document.getElementById("ao12").textContent = ao12.tiempo;
				document.getElementById("ao12").onclick = function() {
					mostrarAvg(ao12);
				}
			}else {
				document.getElementById("ao12").textContent = "";
				document.getElementById("ao12").onclick = null;
			}
			
			if (estadisticas.hasOwnProperty("ao100")) {
				const ao100 = estadisticas.ao100;
				document.getElementById("ao100").textContent = ao100.tiempo;
				document.getElementById("ao12").onclick = function() {
					mostrarAvg(ao100);
				}
			}else {
				document.getElementById("ao100").textContent = "";
				document.getElementById("ao100").onclick = null;
			}
			
			document.getElementById("media").textContent = estadisticas.media;
		})
}

function getTiemposSesion(sesion) {
	originalSelectedOption = sesion;
	fetch('GetTiemposServlet?sesion=' + sesion)
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
					tiempoTd.textContent = tiempo.tiempo;
					tiempoTd.classList.add('tablaTiempos-tiempo');
					tiempoTd.onclick = function() {
						mostrarTiempo(tiempo.id);
					}
					tr.appendChild(tiempoTd);
					
					tbody.appendChild(tr);
				}
			}
		});
}

function mostrarTiempo(id) {
	fetch('GetSelectedSolveServlet?id=' + id)
		.then(response => response.json())
		.then(tiempo => {
			tiempo = formatJsonTiempos(tiempo, 1);
			document.getElementById("hidden-id").value = tiempo.id;
			document.getElementById("scrambleInput").value = tiempo.scramble;
			document.getElementById("fecha").value = tiempo.fecha;
			document.getElementById("tiempo").value = tiempo.tiempo;
			document.getElementById("solve-modal-error").style.display = "none";
			document.getElementById("solveModal").style.display = "flex";
	});
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
		solveTd.textContent = solve.tiempo;
		
		tr.appendChild(solveTd);
		tabla.appendChild(tr);
	}
	document.getElementById("avgModal").style.display = "flex";
}

function borrarTiempo(id) {
	fetch('EliminarSolveServlet?id=' + id)
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
  fetch('GetSesionesServlet')
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
		fetch('CrearSesionServlet?sesion=' + nombreSesion)
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

function borrarSesion() {
	fetch('BorrarSesionServlet?sesion=' + originalSelectedOption)
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

function validarNombreSesion(nombreSesion) {
	var opciones = $('#sesion_select option');
	for (var i = 0; i < opciones.length; i++) {
		if (opciones[i].value === nombreSesion) {
			return false;
		}
	}
	return true;
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