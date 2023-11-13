<%--MODALES --%>
    <div id="nuevaSesion-modal" class="modal">
		<div id="nuevaSesion-modal-content" class="modal-content modal-23">
			<h2>Crear nueva sesión</h2>
			<form class="modalForm" onsubmit="event.preventDefault();crearSesion(document.getElementById('nombre_sesion').value)">
				<label for="nombre_sesion">Nombre de la sesión</label>
				<input type="text" class="input-100" id="nombre_sesion" autocomplete="off" onchange="document.getElementById('nuevaSesion-modal-error').style.display = 'none'" required>
				<button type="submit" class="btn btn-guardar">Guardar</button>
				<span id="nuevaSesion-modal-error" style="color:#B00C0C; display:none;">Ya existe una sesión con ese nombre.</span>
			</form>
		</div>
	</div>
	<div id="borrarSesion-modal" class=modal>
		<div id="borrarSesion-modal-content" class="modal-content">
			<h2>Eliminar sesión</h2>
			<p>
				Se eliminará la sesión y todos los tiempos asociados. ¿Seguro que desea continuar?
			</p>
			<div id="btn-container">
				<button id="sesionBtn-aceptar" onclick="borrarSesion(originalSelectedOption)">Aceptar</button>
            	<button id="sesionBtn-cancelar" onclick="ocultarBorrarSesionModal()">Cancelar</button>
	        </div>
	        <span id="borrarSesion-modal-error" style="color:#B00C0C; display:none;">Ha ocurrido un error al borrar la sesión.</span>
		</div>
	</div>
	<div id="scramble-personalizado-modal" class="modal">
		<div id="scramble-personalizado-modal-content" class="modal-content modal-30">
			<h2>Mezcla personalizada</h2>
			<form class="modalForm" onsubmit="event.preventDefault();scramblePersonalizado(document.getElementById('scramble-text').value);">
				<label for="scramble-text">Scramble</label>
				<input type="text" id="scramble-text" class="input-100" autocomplete="off" required>
				<button type="submit" class=" btn btn-guardar">Guardar</button>
				<span id="scramble-personalizado-modal-error" style="color:#B00C0C; display:none;">El scramble introducido no es válido.</span>
			</form>
		</div>
	</div>
	<div id="add-solve-modal" class="modal">
		<div id="add-solve-modal-content" class="modal-content modal-30">
			<h2>Agregar tiempo</h2>
			<form id="addSolve-form" class="modalForm" onsubmit="event.preventDefault();validarTiempo(document.getElementById('addSolve-tiempo').value, document.getElementById('addSolve-scramble').value);">
				<label>Scramble</label>
				<input type="text" id="addSolve-scramble" class="input-100" autocomplete="off" required>
				<label>Tiempo</label>
				<input type="text" id="addSolve-tiempo" autocomplete="off" required>
				<button type="submit" class="btn btn-guardar">Guardar</button>
				<span id="add-solve-modal-error" style="color:#B00C0C; display:none;">El tiempo introducido no es válido.</span>
			</form>
		</div>
	</div>
	<div id="solveModal" class="modal">
		<div id="solveModal-content" class="modal-content modal-30">
			<h2>Tiempo</h2>
			<form class="modalForm" onsubmit="event.preventDefault();borrarTiempo(document.getElementById('hidden-id').value)">
				<input id="hidden-id" name="hidden-id" type="hidden">
				<label for="scrambleInput">Scramble</label>
				<input type="text" id="scrambleInput" class="input-100" name="scrambleInput" disabled>
				<label for="fecha">Fecha</label>
				<input type="text" id="fecha" name="fecha" disabled>
				<label for="tiempo">Tiempo</label>
				<input type="text" id="tiempo" name="tiempo" disabled>
				<div class="mt-1">
					<button id="solveBtn-mas2" class="solveBtn" onclick="addMas2(document.getElementById('hidden-id').value);">+2</button>
					<button id="solveBtn-dnf" class="solveBtn" onclick="addDnf(document.getElementById('hidden-id').value);">DNF</button>
				</div>
				<div class="w-100 d-flex justify-content-end mt-3">
					<button type="submit" id="solveBtn-eliminar">Eliminar</button>
				</div>
				<span id="solve-modal-error" style="color:#B00C0C; display:none;">Ha ocurrido un error al borrar el tiempo.</span>
			</form>
		</div>
	</div>
	<div id="avgModal" class="modal">
		<div id="avgModal-content" class="modal-content">
			<h2 id="avgModal-title"></h2>
			<form class="modalForm">
				<label for="scrambleInput">Media</label>
				<input type="text" id="avg-tiempo" name="avg-tiempo" disabled>
				<label>Tiempos</label>
				<div id="avgTiempos-container" class="w-100">
					<table id="avg-table"></table>
				</div>
			</form>
		</div>
	</div>
	
	<div id="session-info-modal" class="modal">
		<div id="session-info-modal-content" class="modal-content">
			<h2>Información de la sesión</h2>
			<form id="sessionInfo-form" class="verticalModalForm" onsubmit="event.preventDefault();actualizarSesion(document.getElementById('info-name').value);">
				<div id="session-info-data">
					<div class="form-group">
		                <label for="info-name">Nombre</label>
		                <input type="text" class="form-control" name="info-name" id="info-name">
		            </div>
		            <div class="form-group">
		                <label for="info-total">Total</label>
		                <input type="text" class="form-control" id="info-total" disabled>
		            </div>
		            <div class="form-group">
		                <label for="info-best">Mejor</label>
		                <input type="email" class="form-control" id="info-best" disabled>
		            </div>
		            <div class="form-group">
		                <label for="info-worst">Peor</label>
		                <input type="tel" class="form-control" id="info-worst" disabled>
	            	</div>
	            	<div class="form-group">
		                <label for="info-avg">Media</label>
		                <input type="tel" class="form-control" id="info-avg" disabled>
	            	</div>
	            	<div class="form-group">
		                <label for="info-desv">Desviación</label>
		                <input type="tel" class="form-control" id="info-desv" disabled>
	            	</div>
	            	<div class="form-group">
		                <label for="info-ao5">Ao5 Actual</label>
		                <input type="tel" class="form-control" id="info-ao5" disabled>
	            	</div>
	            	<div class="form-group">
		                <label for="info-ao12">Ao12 Actual</label>
		                <input type="tel" class="form-control" id="info-ao12" disabled>
	            	</div>
	            	<div class="form-group">
		                <label for="info-ao100">Ao100 Actual</label>
		                <input type="tel" class="form-control" id="info-ao100" disabled>
	            	</div>
	            	<div class="form-group">
		                <label for="info-bestao5">Mejor Ao5</label>
		                <input type="tel" class="form-control" id="info-bestao5" disabled>
	            	</div>
	            	<div class="form-group">
		                <label for="info-bestao12">Mejor Ao12</label>
		                <input type="tel" class="form-control" id="info-bestao12" disabled>
	            	</div>
	            	<div class="form-group">
		                <label for="info-bestao100">Mejor Ao100</label>
		                <input type="tel" class="form-control" id="info-bestao100" disabled>
	            	</div>
            	</div>
				<button type="submit" class="btn btn-guardar">Aceptar</button>
				<button type="submit" class="btn btn-danger ml-1" onclick="event.preventDefault();confirmDelete(document.getElementById('sesion_select').value);">Eliminar</button>
				<span id="session-info-modal-error" style="color:#B00C0C; display:none; margin-top:2%;">Ha ocurrido un error al actualizar la sesión.</span>
			</form>
		</div>
	</div>
	
	<div id="preview-modal" class=modal>
		<div id="preview-modal-content" class="preview-modal modal-content modal-content-large">
			<div id="modal-u-container" class="modal-layout-container">
				<div id="modal-u1" class="modal-layout"></div>
				<div id="modal-u2" class="modal-layout"></div>
				<div id="modal-u3" class="modal-layout"></div>
				<div id="modal-u4" class="modal-layout"></div>
				<div id="modal-u5" class="modal-layout"></div>
				<div id="modal-u6" class="modal-layout"></div>
				<div id="modal-u7" class="modal-layout"></div>
				<div id="modal-u8" class="modal-layout"></div>
				<div id="modal-u9" class="modal-layout"></div>
			</div>
			
			<div id="modal-l-container" class="modal-layout-container">
				<div id="modal-l1" class="modal-layout"></div>
				<div id="modal-l2" class="modal-layout"></div>
				<div id="modal-l3" class="modal-layout"></div>
				<div id="modal-l4" class="modal-layout"></div>
				<div id="modal-l5" class="modal-layout"></div>
				<div id="modal-l6" class="modal-layout"></div>
				<div id="modal-l7" class="modal-layout"></div>
				<div id="modal-l8" class="modal-layout"></div>
				<div id="modal-l9" class="modal-layout"></div>
			</div>
			
			<div id="modal-f-container" class="modal-layout-container">
				<div id="modal-f1" class="modal-layout"></div>
				<div id="modal-f2" class="modal-layout"></div>
				<div id="modal-f3" class="modal-layout"></div>
				<div id="modal-f4" class="modal-layout"></div>
				<div id="modal-f5" class="modal-layout"></div>
				<div id="modal-f6" class="modal-layout"></div>
				<div id="modal-f7" class="modal-layout"></div>
				<div id="modal-f8" class="modal-layout"></div>
				<div id="modal-f9" class="modal-layout"></div>
			</div>
			
			<div id="modal-r-container" class="modal-layout-container">
				<div id="modal-r1" class="modal-layout"></div>
				<div id="modal-r2" class="modal-layout"></div>
				<div id="modal-r3" class="modal-layout"></div>
				<div id="modal-r4" class="modal-layout"></div>
				<div id="modal-r5" class="modal-layout"></div>
				<div id="modal-r6" class="modal-layout"></div>
				<div id="modal-r7" class="modal-layout"></div>
				<div id="modal-r8" class="modal-layout"></div>
				<div id="modal-r9" class="modal-layout"></div>
			</div>
			
			<div id="modal-b-container" class="modal-layout-container">
				<div id="modal-b1" class="modal-layout"></div>
				<div id="modal-b2" class="modal-layout"></div>
				<div id="modal-b3" class="modal-layout"></div>
				<div id="modal-b4" class="modal-layout"></div>
				<div id="modal-b5" class="modal-layout"></div>
				<div id="modal-b6" class="modal-layout"></div>
				<div id="modal-b7" class="modal-layout"></div>
				<div id="modal-b8" class="modal-layout"></div>
				<div id="modal-b9" class="modal-layout"></div>
			</div>
			
			<div id="modal-d-container" class="modal-layout-container">
				<div id="modal-d1" class="modal-layout"></div>
				<div id="modal-d2" class="modal-layout"></div>
				<div id="modal-d3" class="modal-layout"></div>
				<div id="modal-d4" class="modal-layout"></div>
				<div id="modal-d5" class="modal-layout"></div>
				<div id="modal-d6" class="modal-layout"></div>
				<div id="modal-d7" class="modal-layout"></div>
				<div id="modal-d8" class="modal-layout"></div>
				<div id="modal-d9" class="modal-layout"></div>
			</div>
		</div>
	</div>
	
	<div id="importModal" class="modal">
	    <div id="importModal-content" class="modal-content modal-30">
	        <h2>Importar tiempos</h2>
	        <form id="importForm" class="modalForm" enctype="multipart/form-data">
	            <input type="hidden" name="sesion" id="importSesion" />
	            <input type="file" id="importFile" name="importFile" required />
	            <button type="button" onclick="importSolves()" class="btn btn-guardar mt-3">Importar</button>
	            <span id="import-modal-error" class="mt-2" style="color:#B00C0C; display:none;">Ha ocurrido un error al importar los tiempos.</span>
	        </form>
	    </div>
	</div>
	
	<div id="configModal" class="modal">
	    <div id="configModal-content" class="modal-content modal-30">
	        <h2>Configuración</h2>
	        <form id="configForm" class="configForm">
	            <div class="form-group">
	                <label class="config-label" for="config-theme">Tema</label>
	                <select class="form-control config-select" name="config-theme" id="config-theme">
	                	<option>Opcion 1</option>
	                	<option>Opcion 2</option>
	                	<option>Opcion 3</option>
	                </select>
	            </div>
	            <div class="form-group">
	                <label class="config-label" for="config-lang">Idioma</label>
	                <select class="form-control config-select" name="config-lang" id="config-lang">
	                	<option>Opcion 1</option>
	                	<option>Opcion 2</option>
	                	<option>Opcion 3</option>
	                </select>
	            </div>
	            <div class="form-group">
	            	<input class="config-check" type="checkbox" name="config-hide-elements" id="config-hide-elements"/>
	            	<label class="check-label" for="config-hide-elements">Ocultar todos los elementos cuando se cronometra</label>
	            </div>
	            <div class="form-group">
	            	<input class="config-check" type="checkbox" name="config-hide-preview" id="config-hide-preview"/>
	            	<label class="check-label" for="config-hide-preview">Ocultar la visualización del cubo</label>
	            </div>
	            <div class="form-group">
	            	<input class="config-check" type="checkbox" name="config-long-pulse" id="config-long-pulse"/>
	            	<label class="check-label" for="config-long-pulse">Pulsación larga</label>
	            </div>
	            <div class="form-group">
	            	<input class="config-check" type="checkbox" name="config-mouse-timer" id="config-mouse-timer"/>
	            	<label class="check-label" for="config-mouse-timer">Usar cronómetro del ratón</label>
	            </div>
	            <div class="form-group">
	            	<input class="config-check" type="checkbox" name="config-inspect-time" id="config-inspect-time"/>
	            	<label class="check-label" for="config-inspect-time">Usar tiempo de inspección</label>
	            </div>
	            <div class="form-group">
	                <label class="config-label-large" for="config-inspect-sec">Segundos de inspección</label>
	                <input type="number" class="config-number" name="config-inspect-sec" id="config-inspect-sec">
	            </div>
	            <div class="config-buttons">
	            	<button type="button" class="btn btn-guardar config-btn mt-3">Aceptar</button>
	            	<button type="button" class="btn btn-guardar config-btn mt-3">Restablecer</button>
	            	<button type="button" class="btn btn-guardar config-btn mt-3">Cancelar</button>
	            </div>
	            
	            <span id="config-modal-error" class="mt-2" style="color:#B00C0C; display:none;">Ha ocurrido un error al guardar la configuración.</span>
	        </form>
	    </div>
	</div>
	<%--FIN MODALES --%>