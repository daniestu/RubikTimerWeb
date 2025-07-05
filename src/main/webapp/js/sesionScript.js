var originalSelectedOption;
let isOpen = false;

function sesionChanged(sesion){
    const select = document.getElementById("sesion_select");
    const select_mobile = document.getElementById("sesion_select_mobile");
	if(sesion == "selectOptionNew") {
		select.value = originalSelectedOption;
		select_mobile.value = originalSelectedOption;
		document.getElementById("nombre_sesion").value = "";
		document.getElementById("nuevaSesion-modal-error").style.display = "none";
		document.getElementById("nuevaSesion-modal").style.display = "flex";
	}else if(sesion == "selectOptionDelete") {
		select.value = originalSelectedOption;
		select_mobile.value = originalSelectedOption;
		document.getElementById("borrarSesion-modal-error").style.display = "none";
		document.getElementById("borrarSesion-modal").style.display = "flex";
	}else {
	    select.value = sesion;
	    select_mobile.value = sesion;
		fetch('session/updateDefault?sesion=' + sesion)
		.catch(function() {
			console.error("Ha ocurrido un error al actualizar la sesión");
		});
		getTiemposSesion(sesion);
	}
	select.blur();
	select_mobile.blur();
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
			document.getElementById("total_mobile").textContent = estadisticas.total;
			document.getElementById("total_mobile_side").textContent = estadisticas.total;
			document.getElementById("info-total").value = estadisticas.total;
			
			if (estadisticas.hasOwnProperty("mejor")) {
				const mejor = estadisticas.mejor;
				
				if (mejor.dnf == 1) {
					document.getElementById("mejor").textContent = "DNF";
					document.getElementById("mejor_mobile_side").textContent = "DNF";
					document.getElementById("mejor_mobile").textContent = "DNF";
					document.getElementById("info-best").value = "DNF(" + mejor.tiempo + ")";
				}else {
					document.getElementById("mejor").textContent = (mejor.mas_2 == 0) ? mejor.tiempo : (sumarMas2(mejor.tiempo) + "+");
					document.getElementById("mejor_mobile_side").textContent = (mejor.mas_2 == 0) ? mejor.tiempo : (sumarMas2(mejor.tiempo) + "+");
					document.getElementById("mejor_mobile").textContent = (mejor.mas_2 == 0) ? mejor.tiempo : (sumarMas2(mejor.tiempo) + "+");
					document.getElementById("info-best").value = (mejor.mas_2 == 0) ? mejor.tiempo : (sumarMas2(mejor.tiempo) + "+");
				}
				document.getElementById("mejor").onclick = function() {
					mostrarTiempo(mejor);
				}
				document.getElementById("mejor_mobile_side").onclick = function() {
					mostrarTiempo(mejor);
				}
				document.getElementById("info-best").onclick = function() {
					mostrarTiempo(mejor);
				}
			}else {
				document.getElementById("mejor").textContent = "";
				document.getElementById("mejor_mobile_side").textContent = "";
				document.getElementById("mejor_mobile").textContent = "--";
				document.getElementById("mejor").onclick = null;
				document.getElementById("mejor_mobile_side").onclick = null;
				document.getElementById("info-best").value = "";
				document.getElementById("info-best").onclick = null;
			}
			
			if (estadisticas.hasOwnProperty("peor")) {
				const peor = estadisticas.peor;
				
				if (peor.dnf == 1) {
					document.getElementById("peor").textContent = "DNF";
					document.getElementById("peor_mobile_side").textContent = "DNF";
					document.getElementById("info-worst").value = "DNF(" + peor.tiempo + ")";
				}else {
					document.getElementById("peor").textContent = (peor.mas_2 == 0) ? peor.tiempo : (sumarMas2(peor.tiempo) + "+");
					document.getElementById("peor_mobile_side").textContent = (peor.mas_2 == 0) ? peor.tiempo : (sumarMas2(peor.tiempo) + "+");
					document.getElementById("info-worst").value = (peor.mas_2 == 0) ? peor.tiempo : (sumarMas2(peor.tiempo) + "+");
				}
				
				document.getElementById("peor").onclick = function() {
					mostrarTiempo(peor);
				}
				document.getElementById("peor_mobile_side").onclick = function() {
					mostrarTiempo(peor);
				}
				document.getElementById("info-worst").onclick = function() {
					mostrarTiempo(peor);
				}
			}else {
				document.getElementById("peor").textContent = "";
				document.getElementById("peor_mobile_side").textContent = "";
				document.getElementById("peor").onclick = null;
				document.getElementById("peor_mobile_side").onclick = null;
				document.getElementById("info-worst").value = "";
				document.getElementById("info-worst").onclick = null;
			}
			
			if (estadisticas.hasOwnProperty("ao5")) {
				const ao5 = estadisticas.ao5;
				document.getElementById("ao5").textContent = ao5.tiempo;
				document.getElementById("ao5_mobile_side").textContent = ao5.tiempo;
				document.getElementById("ao5_mobile").textContent = ao5.tiempo;
				document.getElementById("ao5").onclick = function() {
					mostrarAvg(ao5);
				}
				document.getElementById("ao5_mobile_side").onclick = function() {
					mostrarAvg(ao5);
				}
				document.getElementById("info-ao5").value = ao5.tiempo;
				document.getElementById("info-ao5").onclick = function() {
					mostrarAvg(ao5);
				}
			}else {
				document.getElementById("ao5").textContent = "";
				document.getElementById("ao5_mobile_side").textContent = "";
				document.getElementById("ao5_mobile").textContent = "--";
				document.getElementById("ao5").onclick = null;
				document.getElementById("ao5_mobile_side").onclick = null;
				document.getElementById("info-ao5").value = "";
				document.getElementById("info-ao5").onclick = null;
			}
			
			if (estadisticas.hasOwnProperty("ao12")) {
				const ao12 = estadisticas.ao12;
				document.getElementById("ao12").textContent = ao12.tiempo;
				document.getElementById("ao12_mobile_side").textContent = ao12.tiempo;
				document.getElementById("ao12_mobile").textContent = ao12.tiempo;
				document.getElementById("ao12").onclick = function() {
					mostrarAvg(ao12);
				}
				document.getElementById("ao12_mobile_side").onclick = function() {
					mostrarAvg(ao12);
				}
				document.getElementById("info-ao12").value = ao12.tiempo;
				document.getElementById("info-ao12").onclick = function() {
					mostrarAvg(ao12);
				}
			}else {
				document.getElementById("ao12").textContent = "";
				document.getElementById("ao12_mobile_side").textContent = "";
				document.getElementById("ao12_mobile").textContent = "--";
				document.getElementById("ao12").onclick = null;
				document.getElementById("ao12_mobile_side").onclick = null;
				document.getElementById("info-ao12").value = "";
				document.getElementById("info-ao12").onclick = null;
			}
			
			if (estadisticas.hasOwnProperty("ao100")) {
				const ao100 = estadisticas.ao100;
				document.getElementById("ao100").textContent = ao100.tiempo;
				document.getElementById("ao100_mobile_side").textContent = ao100.tiempo;
				document.getElementById("ao100_mobile").textContent = ao100.tiempo;
				document.getElementById("ao100").onclick = function() {
					mostrarAvg(ao100);
				}
				document.getElementById("ao100_mobile_side").onclick = function() {
					mostrarAvg(ao100);
				}
				document.getElementById("info-ao100").value = ao100.tiempo;
				document.getElementById("info-ao100").onclick = function() {
					mostrarAvg(ao100);
				}
			}else {
				document.getElementById("ao100").textContent = "";
				document.getElementById("ao100_mobile_side").textContent = "";
				document.getElementById("ao100_mobile").textContent = "--";
				document.getElementById("ao100").onclick = null;
				document.getElementById("ao100_mobile_side").onclick = null;
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

			if (estadisticas.hasOwnProperty("desv")) {
			    document.getElementById("desviacion_mobile").textContent = estadisticas.desv;
			}else {
			    document.getElementById("desviacion_mobile").textContent = "--";
			}
			document.getElementById("info-desv").value = estadisticas.desv;

			if (estadisticas.hasOwnProperty("media") && estadisticas.media) {
			    document.getElementById("media_mobile").textContent = estadisticas.media;
			}else {
			    document.getElementById("media_mobile").textContent = "--";
			}
			document.getElementById("media").textContent = estadisticas.media;
			document.getElementById("media_mobile_side").textContent = estadisticas.media;

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
				
				$('#export-icon').attr('src', (json.length != 0) ? 'images/export.png' : 'images/export-disabled.png');
				$("#export-solves").removeClass((json.length != 0) ? "list-item-disabled" : "list-item");
				$("#export-solves").addClass((json.length != 0) ? "list-item" : "list-item-disabled");
				
				json = formatJsonTiempos(json, 0);
				getEstadisticasSesion(json);
				const tbody = document.querySelector('#tablaTiempos tbody');
				const tbody_mobile = document.querySelector('#tablaTiempos_mobile tbody');
				tbody.innerHTML = '';
				tbody_mobile.innerHTML = '';
				for (let i = json.length-1; i >= 0; i--) {
					const tiempo = json[i];
					const tr = document.createElement('tr');
					const tr_mobile = document.createElement('tr');

					const idTd = document.createElement('td');
					const idTd_mobile = document.createElement('td');
					idTd.textContent = i+1;
					idTd_mobile.textContent = i+1;
					tr.appendChild(idTd);
					tr_mobile.appendChild(idTd_mobile);

					const tiempoTd = document.createElement('td');
					const tiempoTd_mobile = document.createElement('td');

					if (tiempo.dnf == 1) {
						tiempoTd.textContent = "DNF";
						tiempoTd_mobile.textContent = "DNF";
					}else {
						tiempoTd.textContent = (tiempo.mas_2 == 0) ? tiempo.tiempo : (sumarMas2(tiempo.tiempo) + "+");
						tiempoTd_mobile.textContent = (tiempo.mas_2 == 0) ? tiempo.tiempo : (sumarMas2(tiempo.tiempo) + "+");
					}
					
					tiempoTd.classList.add('tablaTiempos-tiempo');
					tiempoTd_mobile.classList.add('tablaTiempos-tiempo');
					tiempoTd.onclick = function() {
						mostrarTiempo(tiempo);
					}
					tiempoTd_mobile.onclick = function() {
					    console.log('Click en móvil', tiempo);
						mostrarTiempo(tiempo);
					}
					tr.appendChild(tiempoTd);
					tr_mobile.appendChild(tiempoTd_mobile);

					tbody.appendChild(tr);
					tbody_mobile.appendChild(tr_mobile);
				}
			}
		});
}

function mostrarTiempo(tiempo) {
    console.log("dentro del mostrar tiempo");
	tiempo = formatJsonTiempos(tiempo, 1);
	document.getElementById("hidden-id").value = tiempo.id;
	document.getElementById("scrambleInput").value = tiempo.scramble;
	document.getElementById("scrambleInputMobile").value = tiempo.scramble;
	document.getElementById("fecha").value = tiempo.fecha;
	console.log("1");
	if (tiempo.mas_2 == 0) {
		document.getElementById("solveBtn-mas2").classList.remove("solveBtn-clicked");
	}else {
		document.getElementById("solveBtn-mas2").classList.add("solveBtn-clicked");
	}
	console.log("2");
	if (tiempo.dnf == 0) {
		document.getElementById("solveBtn-dnf").classList.remove("solveBtn-clicked");
		document.getElementById("tiempo").value = (tiempo.mas_2 == 0) ? tiempo.tiempo : (sumarMas2(tiempo.tiempo) + "+");
	}else {
		document.getElementById("solveBtn-dnf").classList.add("solveBtn-clicked");
		document.getElementById("tiempo").value = "DNF(" + tiempo.tiempo + ")";;
	}
	console.log("3");
	document.getElementById("solve-modal-error").style.display = "none";
	document.getElementById("solveModal").style.display = "flex";
	console.log("4");
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

function borrarUltimoTiempoMobile() {
    const select = document.getElementById("sesion_select");
    fetch('solve/delete_last?sesion=' + select.value)
        .then(response => response.json())
        .then(data => {
            if(data.eliminado) {
                getSesiones();

                document.getElementById("cronometro").textContent = "00:00:00";

                let mobileIconsContainer = document.getElementById("mobile-icons-container");
                if (mobileIconsContainer) {
                    mobileIconsContainer.classList.add('invisible');
                }

                document.getElementById("btn-mobile-delete").classList.remove("d-none");
                document.getElementById("btn-mobile-dnf").classList.remove("d-none");
                document.getElementById("btn-mobile-mas_dos").classList.remove("d-none");
                document.getElementById("btn-mobile-restart_dnf").classList.add("d-none");
                document.getElementById("btn-mobile-restart_mas_dos").classList.add("d-none");

                var modalElement = document.getElementById('confirmDeleteModal');
                var modal = bootstrap.Modal.getInstance(modalElement);
                modal.hide();
            }else {
                document.getElementById("delete-solve-mobile-modal-error").classList.remove('d-none');
            }
    })
    .catch(function() {
        document.getElementById("delete-solve-mobile-modal-error").classList.remove('d-none');
    });
}

function getSesiones() {
  fetch('session/get')
    .then(response => response.json())
    .then(sesiones => {
		const select = document.getElementById("sesion_select");
		const select_mobile = document.getElementById("sesion_select_mobile");
		select.innerHTML = "";
		if (sesiones.length == 0) {
			crearSesion("Default");
		} else {
			for (var i = 0; i < sesiones.length; i++) {
				const option = document.createElement("option");
				const option_mobile = document.createElement("option");
				option.text = sesiones[i].nombre;
				option_mobile.value = sesiones[i].nombre;
				option_mobile.text = sesiones[i].nombre;
                				option.value = sesiones[i].nombre;
				
				if (sesiones[i].default_sesion) {
		        	option.selected = true;
		        	option_mobile.selected = true;
			    }
				
				select.add(option);
				select_mobile.add(option_mobile);
			}
			
			const separator = document.createElement("optgroup");
			const separator_mobile = document.createElement("optgroup");
			separator.label = "\u2014\u2014\u2014\u2014\u2014\u2014\u2014";
			separator_mobile.label = "\u2014\u2014\u2014\u2014\u2014\u2014\u2014";
			select.add(separator);
			select_mobile.add(separator_mobile);

			const nuevaSesion = document.createElement("option");
			const nuevaSesion_mobile = document.createElement("option");
			nuevaSesion.text = "Nueva sesión";
			nuevaSesion_mobile.text = "Nueva sesión";
			nuevaSesion.value = "selectOptionNew";
			nuevaSesion_mobile.value = "selectOptionNew";
			select.add(nuevaSesion);
			select_mobile.add(nuevaSesion_mobile);

			const borrarSesion = document.createElement("option");
			const borrarSesion_mobile = document.createElement("option");
			borrarSesion.text = "Eliminar sesión";
			borrarSesion_mobile.text = "Eliminar sesión";
			borrarSesion.value = "selectOptionDelete";
			borrarSesion_mobile.value = "selectOptionDelete";
			select.add(borrarSesion);
			select_mobile.add(borrarSesion_mobile);

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

function addMas2UltimoSolveMobile(action) {
    const select = document.getElementById("sesion_select");

	fetch('solve/updateMas2_last?&action=' + action + '&sesion=' + select.value)
	.then(response => response.json())
	.then(data => {
		if (data.actualizado) {
			if (action == 0) {
				document.getElementById("btn-mobile-delete").classList.remove("d-none");
                document.getElementById("btn-mobile-dnf").classList.remove("d-none");
                document.getElementById("btn-mobile-mas_dos").classList.remove("d-none");
                document.getElementById("btn-mobile-restart_dnf").classList.add("d-none");
                document.getElementById("btn-mobile-restart_mas_dos").classList.add("d-none");

				document.getElementById("cronometro").textContent = restarMas2(document.getElementById("cronometro").textContent);
			}else {
				document.getElementById("btn-mobile-delete").classList.add("d-none");
				document.getElementById("btn-mobile-dnf").classList.add("d-none");
				document.getElementById("btn-mobile-mas_dos").classList.add("d-none");
				document.getElementById("btn-mobile-restart_dnf").classList.add("d-none");
				document.getElementById("btn-mobile-restart_mas_dos").classList.remove("d-none");
				var tiempo = document.getElementById("cronometro").textContent;

				document.getElementById("cronometro").textContent = sumarMas2(tiempo) + "+";

			}
			getSesiones();
		}
	});
}

function addDnfUltimoSolveMobile(action) {
    const select = document.getElementById("sesion_select");

	fetch('solve/updateDnf_last?action=' + action + '&sesion=' + select.value)
	.then(response => response.json())
	.then(data => {
		if (data.actualizado) {
			if (action == 0) {
				document.getElementById("btn-mobile-delete").classList.remove("d-none");
                document.getElementById("btn-mobile-dnf").classList.remove("d-none");
                document.getElementById("btn-mobile-mas_dos").classList.remove("d-none");
                document.getElementById("btn-mobile-restart_dnf").classList.add("d-none");
                document.getElementById("btn-mobile-restart_mas_dos").classList.add("d-none");

				document.getElementById("cronometro").textContent = data.tiempo_original;
			}else {
				document.getElementById("btn-mobile-delete").classList.add("d-none");
                document.getElementById("btn-mobile-dnf").classList.add("d-none");
                document.getElementById("btn-mobile-mas_dos").classList.add("d-none");
                document.getElementById("btn-mobile-restart_dnf").classList.remove("d-none");
                document.getElementById("btn-mobile-restart_mas_dos").classList.add("d-none");

				document.getElementById("cronometro").textContent = "DNF";
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

function exportSolves (sesion) {
	window.location.href = 'session/export?sesion=' + sesion;
}

function importSolves () {
	var form = document.getElementById('importForm');
    var formData = new FormData(form);

    fetch('session/import', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        if (data.importado) {
			getSesiones();
			document.getElementById("importModal").style.display = "none";
		}else {
			document.getElementById('import-modal-error').style.display = 'block';
		}
    })
    .catch(error => {
        console.error('Error al importar el archivo:', error);
        document.getElementById('import-modal-error').style.display = 'block';
    });
}

document.addEventListener('DOMContentLoaded', function () {
    const deleteSolveMobileModal = document.getElementById('confirmDeleteModal');
    const toggleBox = document.getElementById('toggleBox');
    const sidePanel = document.getElementById('sidePanel');
    const toggleArrow = document.getElementById('toggleArrow');

    deleteSolveMobileModal.addEventListener('click', function (e) {
        if (e.target.tagName === 'BUTTON' && e.target.closest('.modal')) {
            e.target.blur();
        }
    });

    deleteSolveMobileModal.addEventListener('shown.bs.modal', function () {
        document.getElementById('delete-solve-mobile-modal-error').classList.add('d-none');
    });

    deleteSolveMobileModal.addEventListener('hidden.bs.modal', function () {
        document.getElementById('delete-solve-mobile-modal-error').classList.add('d-none');
    });

    toggleBox.addEventListener('click', () => {
        isOpen = !isOpen;
        sidePanel.classList.toggle('open', isOpen);
        toggleBox.classList.toggle('open', isOpen);

        toggleArrow.textContent = isOpen ? '◀' : '➤';
    });
});