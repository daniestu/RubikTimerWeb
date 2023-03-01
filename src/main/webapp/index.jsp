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
    <link rel="stylesheet" type="text/css" href="css/cronometroStyles.css">
    <link rel="stylesheet" type="text/css" href="css/asideStyles.css">
    <link rel="stylesheet" type="text/css" href="css/scrambleStyles.css">
    <link rel="stylesheet" type="text/css" href="css/configuracionStyles.css">
    <link rel="stylesheet" type="text/css" href="css/sesionStyles.css">
  </head>
  <body>
    <div class="scramble-container">
      <p id="scramble"></p>
    </div>
    <div id="nuevaSesion-modal" class="modal">
		<div id="nuevaSesion-modal-content" class="modal-content">
			<h2>Crear nueva sesi�n</h2>
			<form onsubmit="event.preventDefault();crearSesion(document.getElementById('nombre_sesion').value)">
				<label for="nombre_sesion">Nombre de la sesi�n</label>
				<input type="text" id="nombre_sesion" autocomplete="off" onchange="document.getElementById('nuevaSesion-modal-error').style.display = 'none'" required>
				<br>
				<button type="submit" id="sesionBtn-guardar">Guardar</button>
				<span id="nuevaSesion-modal-error" style="color:red; display:none;">Ya existe una sesi�n con ese nombre.</span>
			</form>
		</div>
	</div>
	<div id="borrarSesion-modal" class=modal>
		<div id="borrarSesion-modal-content" class="modal-content">
			<h2>Eliminar sesi�n</h2>
			<p>
				Se eliminar� la sesi�n y todos los tiempos asociados. �Seguro que desea continuar?
			</p>
			<div id="btn-container">
				<button id="sesionBtn-aceptar" onclick="borrarSesion()">Aceptar</button>
            	<button id="sesionBtn-cancelar" onclick="ocultarBorrarSesionModal()">Cancelar</button>
	        </div>
	        <span id="borrarSesion-modal-error" style="color:red; display:none;">Ha ocurrido un error al borrar la sesi�n.</span>
		</div>
	</div>
	<div id="solveModal" class="modal">
		<div id="solveModal-content" class="modal-content">
			<h2>Crear nueva sesi�n</h2>
			<form id="solveForm" onsubmit="event.preventDefault();borrarTiempo(document.getElementById('hidden-id').value)">
				<input id="hidden-id" name="hidden-id" type="hidden">
				<label for="scrambleInput">Scramble</label>
				<input type="text" id="scrambleInput" name="scrambleInput" value="R' F L F2 U2 R' U2 F D B' F' R D2 F' D' F2 U R2 B2 U'" disabled>
				<div class="d-flex flex-column justify-content-center">
					<label for="fecha">Fecha</label>
					<input type="text" id="fecha" name="fecha" disabled>
					<label for="tiempo">Tiempo</label>
					<input type="text" id="tiempo" name="tiempo" disabled>
				</div>
				<div class="d-flex justify-content-end">
					<button type="submit" id="solveBtn-eliminar">Eliminar</button>
				</div>
				<span id="solve-modal-error" style="color:red; display:none;">Ha ocurrido un error al borrar el tiempo.</span>
			</form>
		</div>
	</div>
	<img id="config-btn" src="images/config-icon.png"/>
	<div id="config-container" style="display:none;">
   		<ul id="config-menu">
			<li><a href="#">Opci�n 1</a></li>
			<li><a href="#">Opci�n 2</a></li>
			<li><a href="#">Opci�n 3</a></li>
			<hr>
			<li><a href="#" id="logout">Cerrar sesi�n</a></li>
		</ul>
   	</div>
    <aside class="aside-container">
    	<div id="logo_container">
    		<img id="logo" src="images/logo.png" alt="Rubik timer">
    	</div>
      	<div id="sesion_container">
			<label id="sesion_label" class="mb-0" for="sesion_select">Sesi�n:</label>
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
					<td id="mejor"></td>
				</tr>
				<tr>
					<th>Peor</th>
					<td id="peor"></td>
				</tr>
				<tr>
					<th>Ao5</th>
					<td id="ao5"></td>
				</tr>
				<tr>
					<th>Ao12</th>
					<td id="ao12"></td>
				</tr>
				<tr>
					<th>Ao100</th>
					<td id="ao100"></td>
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