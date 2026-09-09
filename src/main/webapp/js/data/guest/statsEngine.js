function calcularEstadisticas(solves) {
    const total = solves.length;
    const solvesOrdenados = [...solves].sort((a, b) => b.id - a.id); // copia, descendente por id
    const mediaStr = media(solves);

    const estadisticas = {
        total: total,
        media: mediaStr,
        desv: calcularDesviacion(solves, mediaStr)
    };

    const mejor = mejorTiempo(solves);
    if (mejor !== null) estadisticas.mejor = mejor;

    const peor = peorTiempo(solves);
    if (peor !== null) estadisticas.peor = peor;

    const ao5 = avg(solvesOrdenados, 5);
    if (ao5 !== null) estadisticas.ao5 = ao5;

    const ao12 = avg(solvesOrdenados, 12);
    if (ao12 !== null) estadisticas.ao12 = ao12;

    const ao100 = avg(solvesOrdenados, 100);
    if (ao100 !== null) estadisticas.ao100 = ao100;

    estadisticas.bestao5 = bestavg(solvesOrdenados, 5);
    estadisticas.bestao12 = bestavg(solvesOrdenados, 12);
    estadisticas.bestao100 = bestavg(solvesOrdenados, 100);

    return estadisticas;
}

function pad2(numero) {
    return numero < 10 ? '0' + numero : '' + numero;
}

function convertirTiempoMs(tiempo) {
    const partes = tiempo.split(':');
    let m = parseInt(partes[0], 10);
    let s = parseInt(partes[1], 10);
    let ms = parseInt(partes[2], 10);

    s += m * 60;
    ms += s * 100;

    return ms;
}

function convertirMsTiempo(ms) {
    if (ms < 100) {
        return '00:00:' + pad2(ms);
    }

    const lms = ms % 100;
    let s = (ms - lms) / 100;

    if (s < 60) {
        return '00:' + pad2(s) + ':' + pad2(lms);
    }

    const m = Math.floor(s / 60);
    s = s % 60;

    return pad2(m) + ':' + pad2(s) + ':' + pad2(lms);
}

function esMejorTiempo(mejor, tiempo) {
    return convertirTiempoMs(mejor) >= convertirTiempoMs(tiempo);
}

function esPeorTiempo(peor, tiempo) {
    return convertirTiempoMs(peor) < convertirTiempoMs(tiempo);
}

function tiempoEfectivo(solve) {
    return solve.mas_2
        ? convertirMsTiempo(convertirTiempoMs(solve.tiempo) + 200)
        : solve.tiempo;
}

function mejorTiempo(solves) {
    if (solves.length === 0) {
        return null;
    }

    let mejor = solves[0];

    for (const solve of solves) {
        if (!solve.dnf) {
            const mejorEfectivo = tiempoEfectivo(mejor);
            const tiempoACalcular = tiempoEfectivo(solve);

            if (mejor.dnf || esMejorTiempo(mejorEfectivo, tiempoACalcular)) {
                mejor = solve;
            }
        }
    }

    return mejor;
}

function peorTiempo(solves) {
    if (solves.length === 0) {
        return null;
    }

    let peor = solves[0];

    for (const solve of solves) {
        if (!solve.dnf) {
            const peorEfectivo = tiempoEfectivo(peor);
            const tiempoACalcular = tiempoEfectivo(solve);

            if (peor.dnf || esPeorTiempo(peorEfectivo, tiempoACalcular)) {
                peor = solve;
            }
        }
    }

    return peor;
}

function media(solves) {
    let mediaStr = '';

    if (solves.length !== 0) {
        let suma = 0;
        let dnfCount = 0;

        for (const solve of solves) {
            if (solve.dnf) {
                dnfCount++;
            } else {
                suma += convertirTiempoMs(tiempoEfectivo(solve));
            }
        }

        const validos = solves.length - dnfCount;
        if (validos !== 0) {
            const promedio = Math.trunc(suma / validos);
            mediaStr = convertirMsTiempo(promedio);
        }
    }

    return mediaStr;
}

function calcularDesviacion(solves, avg) {
    if (!avg) {
        return 0;
    }

    const mediaMs = convertirTiempoMs(avg);
    let suma = 0;
    let size = 0;

    for (const solve of solves) {
        if (!solve.dnf) {
            size++;

            const tiempoMs = convertirTiempoMs(tiempoEfectivo(solve));
            const diferencia = Math.abs(mediaMs - tiempoMs);

            suma += diferencia * diferencia;
        }
    }

    if (size < 2) {
        return 0;
    }

    suma = Math.trunc(suma / (size - 1));
    const desviacion = Math.sqrt(suma) / 100;

    return Math.round(desviacion * 100) / 100;
}

function avg(solvesOrdenados, numeroAvg) {
    if (solvesOrdenados.length < numeroAvg) {
        return null;
    }

    let dnfCount = 0;
    const avgSolves = [];
    let mejor = solvesOrdenados[0];
    let peor = solvesOrdenados[0];
    let suma = 0;

    for (let i = 0; i < numeroAvg; i++) {
        const actual = solvesOrdenados[i];
        const mejorEfectivo = tiempoEfectivo(mejor);
        const peorEfectivo = tiempoEfectivo(peor);
        const tiempoACalcular = tiempoEfectivo(actual);

        if (actual.dnf) {
            dnfCount++;
        }
        avgSolves.push(actual);
        suma += convertirTiempoMs(tiempoACalcular);

        if (!peor.dnf && (actual.dnf || esPeorTiempo(peorEfectivo, tiempoACalcular))) {
            peor = actual;
        }
        if (mejor.dnf || (!actual.dnf && esMejorTiempo(mejorEfectivo, tiempoACalcular))) {
            mejor = actual;
        }
    }

    const tiempoPeor = tiempoEfectivo(peor);
    const tiempoMejor = tiempoEfectivo(mejor);

    suma = Math.trunc((suma - convertirTiempoMs(tiempoPeor) - convertirTiempoMs(tiempoMejor)) / (numeroAvg - 2));

    return {
        solves: avgSolves,
        dnf: dnfCount >= 2,
        tiempo: dnfCount >= 2 ? 'DNF' : convertirMsTiempo(suma)
    };
}

function evaluarVentana(ventana) {
    let mejor = ventana[0];
    let peor = ventana[0];
    let dnf = false;
    let dnfCont = 0;
    let suma = 0;

    for (const j of ventana) {
        const mejorAtm = tiempoEfectivo(mejor);
        const peorAtm = tiempoEfectivo(peor);
        const tiempoACalcular = tiempoEfectivo(j);

        if (dnf) {
            if (!j.dnf) {
                if (mejor.dnf || esMejorTiempo(mejorAtm, tiempoACalcular)) {
                    mejor = j;
                }
            } else {
                dnfCont++;
            }
        } else {
            if (j.dnf) {
                dnfCont++;
                dnf = true;
                peor = j;
            } else {
                if (mejor.dnf || esMejorTiempo(mejorAtm, tiempoACalcular)) {
                    mejor = j;
                }
                if (!peor.dnf && esPeorTiempo(peorAtm, tiempoACalcular)) {
                    peor = j;
                }
            }
        }

        suma += convertirTiempoMs(tiempoACalcular);
    }

    const tiempoPeor = tiempoEfectivo(peor);
    const tiempoMejor = tiempoEfectivo(mejor);
    suma = suma - (convertirTiempoMs(tiempoMejor) + convertirTiempoMs(tiempoPeor));

    return { dnfCont, suma };
}

function bestavg(solvesOrdenados, num) {
    let ventana = [];
    let mejoresSolves = [];
    let mejorAvg = '';

    for (const solve of solvesOrdenados) {
        ventana.push(solve);
        if (ventana.length > num) {
            ventana.shift(); // equivalente a al.remove(0) en Java: mantiene la ventana en tamaño "num"
        }

        if (ventana.length === num) {
            const { dnfCont, suma } = evaluarVentana(ventana);

            if (dnfCont >= 2) {
                if (mejorAvg === '') {
                    // es la PRIMERA ventana que evaluamos en toda la sesión: se inicializa aunque sea con DNF
                    mejorAvg = 'DNF';
                    mejoresSolves = [...ventana];
                }
                // si ya había una mejorAvg previa (aunque fuera "DNF"), una ventana con 2+ DNF nunca la mejora: se ignora
            } else {
                const candidato = convertirMsTiempo(Math.trunc(suma / (num - 2)));
                if (mejorAvg === '' || mejorAvg === 'DNF' || esMejorTiempo(mejorAvg, candidato)) {
                    mejorAvg = candidato;
                    mejoresSolves = [...ventana];
                }
            }
        }
    }

    return {
        tiempo: mejorAvg,
        solves: mejoresSolves,
        dnf: mejorAvg === 'DNF'
    };
}