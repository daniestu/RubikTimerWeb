const DB_NAME = 'RubikTimerGuest';
const DB_VERSION = 1;

let dbPromise = null;

function abrirDB() {
    if (dbPromise) return dbPromise;

    dbPromise = new Promise((resolve, reject) => {
        const request = indexedDB.open(DB_NAME, DB_VERSION);

        request.onupgradeneeded = (event) => {
            const db = event.target.result;

            if (!db.objectStoreNames.contains('solves')) {
                const solvesStore = db.createObjectStore('solves', { keyPath: 'id', autoIncrement: true });
                solvesStore.createIndex('por_sesion', 'sesion', { unique: false });
            }

            if (!db.objectStoreNames.contains('sesiones')) {
                db.createObjectStore('sesiones', { keyPath: 'nombre' });
            }
        };

        request.onsuccess = (event) => resolve(event.target.result);
        request.onerror = (event) => reject(event.target.error);
    });

    return dbPromise;
}

function conStore(nombreStore, modo, callback) {
    return abrirDB().then(db => {
        return new Promise((resolve, reject) => {
            const tx = db.transaction(nombreStore, modo);
            const store = tx.objectStore(nombreStore);
            callback(store, resolve, reject);
        });
    });
}