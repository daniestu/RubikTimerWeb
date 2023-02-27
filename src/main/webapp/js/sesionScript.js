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
			document.getElementById("mejor").textContent = estadisticas.mejor;
			document.getElementById("peor").textContent = estadisticas.peor;
			document.getElementById("ao5").textContent = estadisticas.ao5;
			document.getElementById("ao12").textContent = estadisticas.ao12;
			document.getElementById("ao100").textContent = estadisticas.ao100;
			document.getElementById("media").textContent = estadisticas.media;
		});
}

function getTiemposSesion(sesion) {
	originalSelectedOption = sesion;
	fetch('GetTiemposServlet?sesion=' + sesion)
		.then(response => response.json())
		.then(json => {
			if(json.usuario == "nulo") {
				window.location.href = "./login.jsp";
			}else {
				json = formatJsonTiempos(json);
				getEstadisticasSesion(json);
				const tbody = document.querySelector('#tablaTiempos tbody');
				tbody.innerHTML = '';
				for (let i = json.length-1; i >= 0; i--) {
					const tiempo = json[i];
					const tr = document.createElement('tr');
					
					const idTd = document.createElement('td');
					const idSpan = document.createElement('span');
					idTd.textContent = i+1;
					idSpan.textContent = tiempo.id;
					idSpan.style.display = "none";
					tr.appendChild(idTd);
					tr.appendChild(idSpan);
					
					const tiempoTd = document.createElement('td');
					tiempoTd.textContent = tiempo.tiempo;
					tr.appendChild(tiempoTd);
					
					const mas2Td = document.createElement('td');
					if (tiempo.mas_2) {
						mas2Td.textContent = "x";
					}
					tr.appendChild(mas2Td);
					
					
					const dnfTd = document.createElement('td');
					
					if (tiempo.dnf) {
						dnfTd.textContent = "x";
					}
					tr.appendChild(dnfTd);
					
					tbody.appendChild(tr);
				}
			}
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

function formatJsonTiempos(json) {
	for (let i = 0; i < json.length; i++) {
		let fecha = new Date(json[i].fecha);
		let dia = fecha.getDate().toString().padStart(2, '0');
		let mes = (fecha.getMonth() + 1).toString().padStart(2, '0');
		let anio = fecha.getFullYear().toString();
		let fechaFormateada = `${anio}-${mes}-${dia}`;
		json[i].fecha = fechaFormateada;
	}
	
	return json;
}

function ocultarBorrarSesionModal() {
	document.getElementById("borrarSesion-modal").style.display = "none";
}