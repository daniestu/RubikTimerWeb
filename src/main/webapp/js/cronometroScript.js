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
const idsElementosNoPermitidos = ['config-container', 'scramble-container', 'toggleBox', 'sidePanel'];

let touchStartX = null;
let touchStartY = null;
const umbralDeslizamiento = 20;

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

	let mobileIconsContainer = document.getElementById("mobile-icons-container");
	if (mobileIconsContainer) {
	    mobileIconsContainer.classList.remove('invisible');
	}
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

document.addEventListener('touchstart', function(e) {
    const targetElement = e.target;
    let permitido = !elementosNoPermitidos.includes(targetElement.tagName.toLowerCase()) && !verificarElementoModal(targetElement) && verificarElementoPermitido(targetElement);

    if (permitido) {
        var configContainer = $("#config-container");
        var configBtn = $("#config-btn");
        if (configContainer.is(":visible")) {
            configContainer.toggle();
            configBtn.removeAttr("style");
            permitido = false;
        }

        const toggleBox = document.getElementById('toggleBox');
        const sidePanel = document.getElementById('sidePanel');
        const toggleArrow = document.getElementById('toggleArrow');

        if (sidePanel.classList.contains('open')) {
            sidePanel.classList.remove('open');
            toggleBox.classList.remove('open');

            toggleArrow.textContent = '➤';
            permitido = false;
            isOpen = false;
        }
    }

    if (permitido) {
        touchStartX = e.touches[0].clientX;
        touchStartY = e.touches[0].clientY;

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

                let mobileIconsContainer = document.getElementById("mobile-icons-container");
                if (mobileIconsContainer) {
                    mobileIconsContainer.classList.add('invisible');
                }

                document.getElementById("btn-mobile-delete").classList.remove("d-none");
                document.getElementById("btn-mobile-dnf").classList.remove("d-none");
                document.getElementById("btn-mobile-mas_dos").classList.remove("d-none");
                document.getElementById("btn-mobile-restart_dnf").classList.add("d-none");
                document.getElementById("btn-mobile-restart_mas_dos").classList.add("d-none");
            }
        }
    }
}, { passive: true });

document.addEventListener('touchmove', function(e) {
    if (touchStartX !== null && touchStartY !== null) {
        const touchMoveX = e.touches[0].clientX;
        const touchMoveY = e.touches[0].clientY;
        const deltaX = Math.abs(touchMoveX - touchStartX);
        const deltaY = Math.abs(touchMoveY - touchStartY);

        if (deltaX > umbralDeslizamiento || deltaY > umbralDeslizamiento) {
            presionando_espacio = false;
            spacePressedForOneSecond = false;
            clearInterval(intervalo_manteniendo_espacio);
            document.getElementById("cronometro").style.color = "white";
            touchStartX = null;
            touchStartY = null;
        }
    }
}, { passive: true });

document.addEventListener('touchend', function(e) {
    const targetElement = e.target;
    let permitido = !elementosNoPermitidos.includes(targetElement.tagName.toLowerCase()) && !verificarElementoModal(targetElement) && verificarElementoPermitido(targetElement);

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

        // Condicional para preventDefault basado en el desplazamiento
        if (touchStartX !== null && touchStartY !== null) {
            const touchEndX = e.changedTouches[0].clientX;
            const touchEndY = e.changedTouches[0].clientY;
            const deltaX = Math.abs(touchEndX - touchStartX);
            const deltaY = Math.abs(touchEndY - touchStartY);

            if (deltaX <= umbralDeslizamiento && deltaY <= umbralDeslizamiento) {
                e.preventDefault();
            }
            touchStartX = null;
            touchStartY = null;
        }
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

function verificarElementoModal(elemento) {
    if (!elemento) return false;

    let actual = elemento;

    while (actual) {
        if (actual.classList && actual.classList.contains('modal')) {
            return true;
        }
        actual = actual.parentElement;
    }

    return false;
}

function verificarElementoPermitido(elemento) {
    while (elemento) {
        if (elemento.id) {
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
        } else {
            elemento = elemento.parentElement;
        }
    }
    return true;
}