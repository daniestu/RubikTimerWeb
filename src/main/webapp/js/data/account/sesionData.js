const sesionData = {
    guardarTiempo(tiempo, scramble, sesion) {
        return fetch('/solve/save?tiempo=' + tiempo + '&scramble=' + scramble + '&sesion=' + sesion)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al guardar el tiempo');
                }
            });
    },

    actualizarSesionPorDefecto(sesion) {
        return fetch('/session/updateDefault?sesion=' + sesion);
    },

    getEstadisticas(tiempos) {
        const options = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(tiempos)
        };

        return fetch('/session/getData', options)
            .then(response => response.json());
    },

    getSolves(sesion) {
        return fetch('/solve/get?sesion=' + sesion)
            .then(response => response.json());
    },

    borrarTiempo(id) {
        return fetch('/solve/delete?id=' + id)
            .then(response => response.json());
    },

    borrarUltimoTiempoMobile(sesion) {
        return fetch('/solve/delete_last?sesion=' + sesion)
            .then(response => response.json());
    },

    getSesiones() {
        return fetch('/session/get')
            .then(response => response.json());
    },

    crearSesion(nombreSesion) {
    	return fetch('/session/add?sesion=' + nombreSesion)
    		.then(response => response.json());
    },

    borrarSesion(nombreSesion) {
        return fetch('/session/delete?sesion=' + nombreSesion)
            .then(response => response.json());
    },

    renombrarSesion(name, newName) {
        return fetch('/session/update?name=' + name + '&newName=' + newName)
            .then(response => response.text())
            .then(data => data === "true"); // normalizamos aquí a un booleano real
    },

    updateMas2(id, action) {
        return fetch('/solve/updateMas2?id=' + id + '&action=' + action)
            .then(response => response.json());
    },

    updateDnf(id, action) {
        return fetch('/solve/updateDnf?id=' + id + '&action=' + action)
            .then(response => response.json());
    },

    updateMas2Last(sesion, action) {
        return fetch('/solve/updateMas2_last?&action=' + action + '&sesion=' + sesion)
            .then(response => response.json());
    },

    updateDnfLast(sesion, action) {
        return fetch('/solve/updateDnf_last?action=' + action + '&sesion=' + sesion)
            .then(response => response.json());
    },

    exportSolves(sesion) {
        window.location.href = '/session/export?sesion=' + sesion;
    },

    importSolves(formData) {
        return fetch('/session/import', {
            method: 'POST',
            body: formData
        }).then(response => response.json());
    },

    guardarPreferencias(form) {
        const formData = new URLSearchParams(new FormData(form));

        const options = {
            method: "POST",
            body: formData
        };

        return fetch("/conf/save", options)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al guardar las preferencias');
                }
            });
    }
};