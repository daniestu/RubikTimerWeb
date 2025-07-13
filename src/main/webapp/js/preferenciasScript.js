function restablecerConfiguracion(idiomaNavegador) {
    var tema = document.getElementById("config-theme");
    var idioma = document.getElementById("config-lang");
    var ocultarElementos = document.getElementById("config-hide-elements");
    var ocultarVisualizacion = document.getElementById("config-hide-preview");
    var pulsacionLarga = document.getElementById("config-long-pulse");
    var cronometroRaton = document.getElementById("config-mouse-timer");
    var tiempoInspeccion = document.getElementById("config-inspect-time");
    var segundosInspeccion = document.getElementById("config-inspect-sec");

    tema.value = 1;
    idioma.value = idiomaNavegador;
    ocultarElementos.checked = true;
    ocultarVisualizacion.checked = false;
    pulsacionLarga.checked = true;
    cronometroRaton.checked = false;
    tiempoInspeccion.checked = false;
    segundosInspeccion.value = "";
    segundosInspeccion.disabled = true;
}

function checkInspectTime(checkbox) {
    var segundosInspeccion = document.getElementById("config-inspect-sec");

    if (checkbox.checked) {
        segundosInspeccion.disabled = false;
    } else {
        segundosInspeccion.value = "";
        segundosInspeccion.disabled = true;
    }
}