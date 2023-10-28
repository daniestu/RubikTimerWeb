<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="head.jsp" />
    <meta charset="UTF-8">
    <script src="js/scrambleScript.js" charset="UTF-8"></script>
    <script src="js/sesionScript.js" charset="UTF-8"></script>
    <script src="js/configuracionScript.js" charset="UTF-8"></script>
    <link rel="stylesheet" type="text/css" href="css/mainStyles.css">
    <link rel="stylesheet" type="text/css" href="css/asideStyles.css">
    <link rel="stylesheet" type="text/css" href="css/scrambleStyles.css">
    <link rel="stylesheet" type="text/css" href="css/configuracionStyles.css">
    <link rel="stylesheet" type="text/css" href="css/modalStyles.css">
    <link rel="stylesheet" type="text/css" href="css/previewStyles.css">
  </head>
  <body>
    <div class="scramble-container">
      <p id="scramble" onclick="generateScramble()"></p>
    </div>
    
    <%--MODALES --%>
    <div id="nuevaSesion-modal" class="modal">
		<div id="nuevaSesion-modal-content" class="modal-content modal-23">
			<h2>Crear nueva sesión</h2>
			<form class="modalForm" onsubmit="event.preventDefault();crearSesion(document.getElementById('nombre_sesion').value)">
				<label for="nombre_sesion">Nombre de la sesión</label>
				<input type="text" class="input-100" id="nombre_sesion" autocomplete="off" onchange="document.getElementById('nuevaSesion-modal-error').style.display = 'none'" required>
				<button type="submit" class="btn btn-guardar">Guardar</button>
				<span id="nuevaSesion-modal-error" style="color:red; display:none;">Ya existe una sesión con ese nombre.</span>
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
	        <span id="borrarSesion-modal-error" style="color:red; display:none;">Ha ocurrido un error al borrar la sesión.</span>
		</div>
	</div>
	<div id="scramble-personalizado-modal" class="modal">
		<div id="scramble-personalizado-modal-content" class="modal-content modal-30">
			<h2>Mezcla personalizada</h2>
			<form class="modalForm" onsubmit="event.preventDefault();scramblePersonalizado(document.getElementById('scramble-text').value);">
				<label for="scramble-text">Scramble</label>
				<input type="text" id="scramble-text" class="input-100" autocomplete="off" required>
				<button type="submit" class=" btn btn-guardar">Guardar</button>
				<span id="scramble-personalizado-modal-error" style="color:red; display:none;">El scramble introducido no es válido.</span>
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
				<span id="add-solve-modal-error" style="color:red; display:none;">El tiempo introducido no es válido.</span>
			</form>
		</div>
	</div>
	<div id="solveModal" class="modal">
		<div class="modal-content modal-30">
			<h2>Tiempo</h2>
			<form class="modalForm" onsubmit="event.preventDefault();borrarTiempo(document.getElementById('hidden-id').value)">
				<input id="hidden-id" name="hidden-id" type="hidden">
				<label for="scrambleInput">Scramble</label>
				<input type="text" id="scrambleInput" class="input-100" name="scrambleInput" disabled>
				<label for="fecha">Fecha</label>
				<input type="text" id="fecha" name="fecha" disabled>
				<label for="tiempo">Tiempo</label>
				<input type="text" id="tiempo" name="tiempo" disabled>
				<div class="w-100 d-flex justify-content-end">
					<button type="submit" id="solveBtn-eliminar">Eliminar</button>
				</div>
				<span id="solve-modal-error" style="color:red; display:none;">Ha ocurrido un error al borrar el tiempo.</span>
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
				<span id="session-info-modal-error" style="color:red; display:none; margin-top:2%;">Ha ocurrido un error al actualizar la sesión.</span>
			</form>
		</div>
	</div>
	
	<%--FIN MODALES --%>
	
	<img id="config-btn" src="images/config-icon.png"/>
	<div id="config-container" style="display:none;">
   		<ul id="config-menu">
   			<li id="custom-scramble" class="list-item"><img class="config-icon" src="images/personalizar.png"/>Mezcla personalizada</li>
			<li id="previus-scramble" class="list-item-disabled"><img id="previus-icon" class="config-icon" src="images/previus-disabled.png"/>Mezcla anterior</li>
			<li id="next-scramble" class="list-item"><img class="config-icon" src="images/next.png"/>Mezcla siguiente</li>
			<li id="add-solve" class="list-item"><img class="config-icon" src="images/add.png"/>Agregar tiempo</li>
			<li id="session-info" class="list-item"><img class="config-icon" src="images/info.png"/>Información de la sesión</li>
			<hr>
			<li id="logout" class="list-item"><img id="logout-icon" class="config-icon" src="images/logout.png"/>Cerrar sesión</li>
		</ul>
   	</div>
   	<jsp:include page="preview.jsp" />
    <aside class="aside-container">
    	<div id="logo_container">
    		<img id="logo" src="images/logo.png" alt="Rubik timer">
    	</div>
      	<div id="sesion_container">
			<label id="sesion_label" class="mb-0" for="sesion_select">Sesión:</label>
		 	<select id="sesion_select" onchange="sesionChanged(this.value)"></select>
      	</div>
      	<div id="estadisticas_container">
      		<table id="tablaEstadisticas">
				<tr>
					<th>Total</th>
					<td id="total"></td>
				</tr>
				<tr>
					<th>Mejor</th>
					<td id="mejor" class="solve"></td>
				</tr>
				<tr>
					<th>Peor</th>
					<td id="peor" class="solve"></td>
				</tr>
				<tr>
					<th>Ao5</th>
					<td id="ao5" class="average"></td>
				</tr>
				<tr>
					<th>Ao12</th>
					<td id="ao12" class="average"></td>
				</tr>
				<tr>
					<th>Ao100</th>
					<td id="ao100" class="average"></td>
				</tr>
				<tr>
					<th>Media</th>
					<td id="media"></td>
				</tr>
			</table>
      	</div>
      	<div id="tiempos_container">
			<table id="tablaTiempos">
				<thead>
					<tr>
						<th>ID</th>
						<th>TIEMPO</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
    </aside>
    <div class="cronometro-container">
		<p id="cronometro">00:00:00</p>
    </div>
    <script src="js/cronometroScript.js"></script>
    <script>
	    generateScramble();
	    getSesiones();
    </script>
  </body>
</html>