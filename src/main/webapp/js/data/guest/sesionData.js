const sesionData = {
    guardarTiempo(tiempo, scramble, sesion) {
        return conStore('solves', 'readwrite', (store, resolve, reject) => {
            const request = store.add({
                tiempo: tiempo,
                scramble: scramble,
                sesion: sesion,
                fecha: new Date().toISOString(),
                dnf: false,
                mas_2: false
            });

            request.onsuccess = () => resolve();
            request.onerror = () => reject(request.error);
        });
    },

    actualizarSesionPorDefecto(sesion) {
        return conStore('sesiones', 'readwrite', (store, resolve, reject) => {
            marcarSesionComoDefault(store, sesion)
                .then(resolve)
                .catch(reject);
        });
    },

    getEstadisticas(tiempos) {
        return Promise.resolve(calcularEstadisticas(tiempos));
    },

    getSolves(sesion) {
        return conStore('solves', 'readonly', (store, resolve, reject) => {
            const request = store.index('por_sesion').getAll(sesion);

            request.onsuccess = () => resolve(request.result);
            request.onerror = () => reject(request.error);
        });
    },

    borrarTiempo(id) {
        return conStore('solves', 'readwrite', (store, resolve, reject) => {
            id = Number(id);
            const getRequest = store.get(id);
            getRequest.onsuccess = () => {
                if (!getRequest.result) {
                    resolve({ eliminado: false });
                    return;
                }

                const deleteRequest = store.delete(id);
                deleteRequest.onsuccess = () => resolve({ eliminado: true });
                deleteRequest.onerror = () => reject(deleteRequest.error);
            };
            getRequest.onerror = () => reject(getRequest.error);
        });
    },

    borrarUltimoTiempoMobile(sesion) {
        return conStore('solves', 'readwrite', (store, resolve, reject) => {
            const request = store.index('por_sesion').openCursor(IDBKeyRange.only(sesion), 'prev');

            request.onsuccess = (event) => {
                const cursor = event.target.result;
                if (cursor) {
                    cursor.delete();
                    resolve({ eliminado: true });
                } else {
                    resolve({ eliminado: false });
                }
            };
            request.onerror = () => reject(getRequest.error);
        });
    },

    getSesiones() {
        return conStore('sesiones', 'readonly', (store, resolve, reject) => {
            const request = store.getAll();
            request.onsuccess = () => resolve(request.result.sort((a, b) => a.orden - b.orden));
            request.onerror = () => reject(request.error);
        });
    },

    crearSesion(nombreSesion) {
        return conStore('sesiones', 'readwrite', (store, resolve, reject) => {
            const nuevaSesion = {
                nombre: nombreSesion,
                default_sesion: false,
                orden: Date.now()
            };

            const addRequest = store.add(nuevaSesion);
            addRequest.onerror = () => reject(addRequest.error);
            addRequest.onsuccess = () => {
                marcarSesionComoDefault(store, nombreSesion)
                    .then(() => resolve(nuevaSesion))
                    .catch(reject);
            };
        });
    },

    borrarSesion(nombreSesion) {
        return abrirDB().then(db => {
            return new Promise((resolve, reject) => {
                const tx = db.transaction(['solves', 'sesiones'], 'readwrite');
                const solvesStore = tx.objectStore('solves');
                const sesionesStore = tx.objectStore('sesiones');

                const getRequest = sesionesStore.get(nombreSesion);
                getRequest.onsuccess = () => {
                    if (!getRequest.result) {
                        resolve({ eliminado: false }); // no existe esa sesión
                        return;
                    }

                    sesionesStore.delete(nombreSesion);

                    const cursorRequest = solvesStore.index('por_sesion').openCursor(IDBKeyRange.only(nombreSesion));
                    cursorRequest.onsuccess = (event) => {
                        const cursor = event.target.result;
                        if (cursor) {
                            cursor.delete();
                            cursor.continue();
                        }
                    };
                    cursorRequest.onerror = () => reject(cursorRequest.error);
                };
                getRequest.onerror = () => reject(getRequest.error);

                tx.oncomplete = () => resolve({ eliminado: true });
                tx.onerror = () => reject(tx.error);
            });
        });
    },

    renombrarSesion(name, newName) {
        return abrirDB().then(db => {
            return new Promise((resolve, reject) => {
                const tx = db.transaction(['sesiones', 'solves'], 'readwrite');
                const sesionesStore = tx.objectStore('sesiones');
                const solvesStore = tx.objectStore('solves');

                const getRequest = sesionesStore.get(name);
                getRequest.onsuccess = () => {
                    const sesionActual = getRequest.result;
                    if (!sesionActual) {
                        resolve(false);
                        return;
                    }

                    sesionesStore.delete(name);
                    sesionesStore.add({ ...sesionActual, nombre: newName });

                    const cursorRequest = solvesStore.index('por_sesion').openCursor(IDBKeyRange.only(name));
                    cursorRequest.onsuccess = (event) => {
                        const cursor = event.target.result;
                        if (cursor) {
                            const solve = cursor.value;
                            solve.sesion = newName;
                            cursor.update(solve);
                            cursor.continue();
                        }
                    };
                    cursorRequest.onerror = () => reject(cursorRequest.error);
                };
                getRequest.onerror = () => reject(getRequest.error);

                tx.oncomplete = () => resolve(true);
                tx.onerror = () => reject(tx.error);
            });
        });
    },

    updateMas2(id, action) {
        return conStore('solves', 'readwrite', (store, resolve, reject) => {
            id = Number(id);
            const getRequest = store.get(id);
            getRequest.onsuccess = () => {
                if (!getRequest.result) {
                    resolve({ actualizado: false });
                    return;
                }
                guardarFlagSolve(store, getRequest.result, 'mas_2', action).then(resolve).catch(reject);
            };
            getRequest.onerror = () => reject(getRequest.error);
        });
    },

    updateDnf(id, action) {
        return conStore('solves', 'readwrite', (store, resolve, reject) => {
            id = Number(id);
            const getRequest = store.get(id);
            getRequest.onsuccess = () => {
                if (!getRequest.result) {
                    resolve({ actualizado: false });
                    return;
                }
                guardarFlagSolve(store, getRequest.result, 'dnf', action).then(resolve).catch(reject);
            };
            getRequest.onerror = () => reject(getRequest.error);
        });
    },

    updateMas2Last(sesion, action) {
        return conStore('solves', 'readwrite', (store, resolve, reject) => {
            const cursorRequest = store.index('por_sesion').openCursor(IDBKeyRange.only(sesion), 'prev');
            cursorRequest.onsuccess = (event) => {
                const cursor = event.target.result;
                if (!cursor) {
                    resolve({ actualizado: false }); // esta sesión no tiene solves
                    return;
                }
                guardarFlagSolve(store, cursor.value, 'mas_2', action).then(resolve).catch(reject);
            };
            cursorRequest.onerror = () => reject(cursorRequest.error);
        });
    },

    updateDnfLast(sesion, action) {
        return conStore('solves', 'readwrite', (store, resolve, reject) => {
            const cursorRequest = store.index('por_sesion').openCursor(IDBKeyRange.only(sesion), 'prev');
            cursorRequest.onsuccess = (event) => {
                const cursor = event.target.result;
                if (!cursor) {
                    resolve({ actualizado: false });
                    return;
                }
                const tiempoOriginal = cursor.value.tiempo;

                guardarFlagSolve(store, cursor.value, 'dnf', action)
                    .then(resultado => resolve({ ...resultado, tiempo_original: tiempoOriginal }))
                    .catch(reject);
            };
            cursorRequest.onerror = () => reject(cursorRequest.error);
        });
    },

    exportSolves(sesion) {
        return sesionData.getSolves(sesion).then(solves => {
            const solvesOrdenados = [...solves].sort((a, b) => a.id - b.id);

            const csv = solvesOrdenados.map((solve, index) => [
                index + 1,
                solve.scramble,
                formatearFechaCSV(solve.fecha),
                solve.tiempo,
                solve.mas_2 ? '1' : '0',
                solve.dnf ? '1' : '0'
            ].join(';')).join('\n') + '\n';

            const blob = new Blob([csv], { type: 'text/csv' });
            const url = URL.createObjectURL(blob);

            const enlace = document.createElement('a');
            enlace.href = url;
            enlace.download = sesion + '.csv';
            enlace.click();

            URL.revokeObjectURL(url);
        });
    },

    importSolves(formData) {
        const sesion = formData.get('sesion');
        const file = formData.get('importFile');

        return sesionExiste(sesion).then(existe => {
            if (!existe) {
                return { importado: false };
            }

            return file.text().then(texto => {
                const lineas = texto
                    .replace(/\r\n/g, '\n')
                    .replace(/\r/g, '\n')
                    .replace(/\n$/, '')
                    .split('\n');

                if (!lineas.every(verificarLineaImportacion)) {
                    return { importado: false };
                }

                const solves = lineas.map(linea => {
                    const parts = linea.split(';');
                    return {
                        scramble: parts[1],
                        fecha: parsearFechaCSV(parts[2]).toISOString(),
                        tiempo: parts[3],
                        mas_2: parts[4] === '1',
                        dnf: parts[5] === '1',
                        sesion: sesion
                    };
                });

                return abrirDB().then(db => {
                    return new Promise((resolve, reject) => {
                        const tx = db.transaction('solves', 'readwrite');
                        const store = tx.objectStore('solves');

                        solves.forEach(solve => store.add(solve));

                        tx.oncomplete = () => resolve({ importado: true });
                        tx.onerror = () => reject(tx.error);
                    });
                });
            });
        });
    },

    guardarPreferencias(form) {
        const formData = new FormData(form);

        const preferenciasLocales = {
            ocultarElementos: formData.get('config-hide-elements') !== null ? 1 : 0,
            ocultarVisualizacion: formData.get('config-hide-preview') !== null ? 1 : 0,
            pulsacionLarga: formData.get('config-long-pulse') !== null ? 1 : 0,
            cronometroRaton: formData.get('config-mouse-timer') !== null ? 1 : 0,
            tiempoInspeccion: formData.get('config-inspect-time') !== null ? 1 : 0,
            segundosInspeccion: formData.get('config-inspect-sec') ? parseInt(formData.get('config-inspect-sec'), 10) : 0
        };

        localStorage.setItem('rubikTimerPreferencias', JSON.stringify(preferenciasLocales));

        const params = new URLSearchParams(formData);
        return fetch('conf/save', { method: 'POST', body: params })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al guardar las preferencias');
                }
            });
    }
}

function marcarSesionComoDefault(store, nombre) {
    return new Promise((resolve, reject) => {
        const cursorRequest = store.openCursor();
        cursorRequest.onsuccess = (event) => {
            const cursor = event.target.result;
            if (cursor) {
                const esEstaSesion = cursor.value.nombre === nombre;
                if (cursor.value.default_sesion !== esEstaSesion) {
                    const registro = cursor.value;
                    registro.default_sesion = esEstaSesion;
                    cursor.update(registro);
                }
                cursor.continue();
            } else {
                resolve();
            }
        };
        cursorRequest.onerror = () => reject(cursorRequest.error);
    });
}

function guardarFlagSolve(store, solve, campo, action) {
    return new Promise((resolve, reject) => {
        solve.mas_2 = false;
        solve.dnf = false;
        solve[campo] = action == 1;

        const putRequest = store.put(solve);
        putRequest.onsuccess = () => resolve({ actualizado: true });
        putRequest.onerror = () => reject(putRequest.error);
    });
}

function formatearFechaCSV(fechaIso) {
    const fecha = new Date(fechaIso);
    const dia = String(fecha.getDate()).padStart(2, '0');
    const mes = String(fecha.getMonth() + 1).padStart(2, '0');
    return `${dia}/${mes}/${fecha.getFullYear()}`;
}

function parsearFechaCSV(fechaStr) {
    const match = fechaStr.match(/^(\d{2})\/(\d{2})\/(\d{4})$/);
    if (!match) return null;

    const dia = parseInt(match[1], 10);
    const mes = parseInt(match[2], 10);
    const anio = parseInt(match[3], 10);
    const fecha = new Date(anio, mes - 1, dia);

    const esValida = fecha.getFullYear() === anio && fecha.getMonth() === mes - 1 && fecha.getDate() === dia;
    return esValida ? fecha : null;
}

function verificarLineaImportacion(linea) {
    const parts = linea.split(';');

    if (parts.length !== 6) return false;

    const indice = parseInt(parts[0], 10);
    if (isNaN(indice) || indice < 0) return false;
    if (!/^([UDLRFB]'?2?\s?)+$/.test(parts[1])) return false;
    if (!/^([0-9]+):([0-5][0-9]):([0-9][0-9])$/.test(parts[3])) return false;
    if (parts[4] !== '0' && parts[4] !== '1') return false;
    if (parts[5] !== '0' && parts[5] !== '1') return false;

    return parsearFechaCSV(parts[2]) !== null;
}

function sesionExiste(nombre) {
    return conStore('sesiones', 'readonly', (store, resolve, reject) => {
        const request = store.get(nombre);
        request.onsuccess = () => resolve(!!request.result);
        request.onerror = () => reject(request.error);
    });
}