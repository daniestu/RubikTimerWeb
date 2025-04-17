var intervalo_cronometro;
var intervalo_manteniendo_espacio;

var tiempo_inicial_cronometro;
var tiempo_actual_cronometro;
var tiempo_inicial_espacio;
var tiempo_actual_espacio;

var cronometrando = false;
var detenido = false;
var spacePressedForOneSecond = false;
var presionando_espacio = false;

const elementosNoPermitidos = ['aside', 'nav', 'section', 'footer', 'ol', 'ul', 'li', 'a', 'i', 'img', 'input', 'textarea', 'button', 'select', 'optgroup', 'option'];
const idsElementosNoPermitidos = ['config-container', 'scramble-container'];

function iniciarCronometro() {
	tiempo_inicial_cronometro = new Date().getTime();
	intervalo_cronometro = setInterval(actualizarCronometro, 10);
}

function detenerCronometro() {
	clearInterval(intervalo_cronometro);
	var tiempoTranscurrido = document.getElementById("cronometro").textContent;
	var scramble = document.getElementById("scramble").textContent;
	guardarTiempo(tiempoTranscurrido, scramble);
	generateScramble();
}

function actualizarCronometro() {
	tiempo_actual_cronometro = new Date().getTime() - tiempo_inicial_cronometro;
	actualizarTextoCronometro(tiempo_actual_cronometro);
}

function actualizarTextoCronometro(tiempoTranscurrido) {
	let minutos = Math.floor(tiempoTranscurrido / (60 * 1000));
	let segundos = Math.floor((tiempoTranscurrido - (minutos * 60 * 1000)) / 1000);
	let milisegundos = tiempoTranscurrido % 1000;
	minutos = minutos < 10 ? '0' + minutos : minutos;
	segundos = segundos < 10 ? '0' + segundos : segundos;
	milisegundos = milisegundos < 10 ? '0' + milisegundos : milisegundos;
	const textoCronometro = `${pad(minutos, 2)}:${pad(segundos, 2)}:${pad(milisegundos, 2)}`;
	const cronometro = document.getElementById('cronometro');
	cronometro.textContent = textoCronometro;
}

function pad(numero, longitud) {
	let str = '' + numero;
	while (str.length < longitud) {
		str = '0' + str;
	}
	return str.substr(0, longitud);
}

function guardarTiempo(tiempo, scramble) {
	fetch('solve/save?tiempo=' + tiempo + '&scramble=' + scramble + '&sesion=' + document.getElementById("sesion_select").value)
		.then(response => {
			getTiemposSesion(document.getElementById("sesion_select").value)
			console.log('El tiempo se ha guardado correctamente.');
		})
		.catch(error => {
			console.error('Error al guardar el tiempo:', error);
		});
}

document.body.onkeyup = function(e) {
	if (e.code === "Space") {
		if (!cronometrando && !detenido && spacePressedForOneSecond) {
			iniciarCronometro();
			cronometrando = true;
			detenido = false;
		} else if (!cronometrando && detenido) {
			detenido = false;
		} else if (!spacePressedForOneSecond) {
			clearInterval(intervalo_manteniendo_espacio);
		}
		spacePressedForOneSecond = false;
		presionando_espacio = false;
		document.getElementById("cronometro").style.color = "white";
	}
}

document.body.onkeydown = function(e) {
	if (e.code === "Space") {
		if (!presionando_espacio) {
			presionando_espacio = true;
			if (cronometrando) {
				detenerCronometro();
				cronometrando = false;
				detenido = true;
			} else {
				tiempo_inicial_espacio = new Date().getTime();
				intervalo_manteniendo_espacio = setInterval(validar_manteniendo_espacio, 100);
				document.getElementById("cronometro").textContent = "00:00:00";
				document.getElementById("cronometro").style.color = "red";
			}
		}
	}
}

// Evento para dispositivos móviles (touchstart)
document.addEventListener('touchstart', function(e) {
    const targetElement = e.target;
    let permitido = !elementosNoPermitidos.includes(targetElement.tagName.toLowerCase()) && verificarElementoPermitido(targetElement);

    if (permitido) {
        var configContainer = $("#config-container");
        var configBtn = $("#config-btn");
        if (configContainer.is(":visible")) {
            configContainer.toggle();
            configBtn.removeAttr("style");
            permitido = false;
        }
    }

    if (permitido) {
        e.preventDefault();

        if (!presionando_espacio) {
            presionando_espacio = true;
            if (cronometrando) {
                detenerCronometro();
                cronometrando = false;
                detenido = true;
            } else {
                tiempo_inicial_espacio = new Date().getTime();
                intervalo_manteniendo_espacio = setInterval(validar_manteniendo_espacio, 100);
                document.getElementById("cronometro").textContent = "00:00:00";
                document.getElementById("cronometro").style.color = "red";
            }
        }
    }
}, { passive: false });

// Evento para dispositivos móviles (touchend)
document.addEventListener('touchend', function(e) {
    const targetElement = e.target;
    let permitido = !elementosNoPermitidos.includes(targetElement.tagName.toLowerCase()) && verificarElementoPermitido(targetElement);

    if (permitido) {
        if (!cronometrando && !detenido && spacePressedForOneSecond) {
            iniciarCronometro();
            cronometrando = true;
            detenido = false;
        } else if (!cronometrando && detenido) {
            detenido = false;
        } else if (!spacePressedForOneSecond) {
            clearInterval(intervalo_manteniendo_espacio);
        }
        spacePressedForOneSecond = false;
        presionando_espacio = false;
        document.getElementById("cronometro").style.color = "white";
    }
}, { passive: false });

function validar_manteniendo_espacio() {
	if (presionando_espacio) {
		tiempo_actual_espacio = new Date().getTime();
		if ((tiempo_actual_espacio - tiempo_inicial_espacio) >= 1000) {
			spacePressedForOneSecond = true;
			document.getElementById("cronometro").style.color = "green";
			clearInterval(intervalo_manteniendo_espacio);
		}
	}
}

function verificarElementoPermitido(elemento) {
    console.log("entra");
    if (!elemento.id) {
        return true;
    }

    const elementoId = elemento.id;

    if (idsElementosNoPermitidos.includes(elementoId)) {
        return false;
    }

    for (const idPadre of idsElementosNoPermitidos) {
        const elementoPadre = document.getElementById(idPadre);
        if (elementoPadre) {
            function verificarEnHijosRecursivo(nodo, idABuscar) {
                if (nodo.id === idABuscar) {
                    return true;
                }
                if (nodo.children) {
                    for (const hijo of nodo.children) {
                        if (verificarEnHijosRecursivo(hijo, idABuscar)) {
                            return true;
                        }
                    }
                }
                return false;
            }

            if (verificarEnHijosRecursivo(elementoPadre, elementoId)) {
                return false;
            }
        }
    }

    return true;
}