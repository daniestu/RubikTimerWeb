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
	fetch('GetTiemposServlet?sesion=' + sesion)
		.then(response => response.json())
		.then(json => {
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
		});
}

function getSesiones() {
	fetch('GetSesionesServlet')
		.then(response => response.json())
		.then(sesiones => {
			console.log(sesiones.lenth);
			if (sesiones.length == 0) {
				crearSesion("Default");
			}else {
				document.getElementById("sesion_select").innerHTML = "";
				for (var i = 0; i < sesiones.length; i++) {
					var option = document.createElement("option");
					option.text = sesiones[i].nombre;
					option.value = sesiones[i].nombre;
					document.getElementById("sesion_select").add(option);
				}
				getTiemposSesion(document.getElementById("sesion_select").value);
			}
		});
}

function crearSesion(nombreSesion) {
	fetch('CrearSesionServlet?sesion=' + nombreSesion)
		.then(response => response.json())
		.then(sesion => {
			document.getElementById("sesion_select").innerHTML = "";
			var option = document.createElement("option");
			option.text = sesion.nombre;
			option.value = sesion.nombre;
			document.getElementById("sesion_select").add(option);
		});
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
