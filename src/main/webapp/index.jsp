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
    <div id="nuevaSesion-modal" class="sesionModal">
		<div id="nuevaSesion-modal-content" class="sesionModal-content">
			<h2>Crear nueva sesión</h2>
			<form onsubmit="event.preventDefault();crearSesion(document.getElementById('nombre_sesion').value)">
				<label for="nombre_sesion">Nombre de la sesión</label>
				<input type="text" id="nombre_sesion" autocomplete="off" onchange="document.getElementById('nuevaSesion-modal-error').style.display = 'none'" required>
				<br>
				<button type="submit" id="sesionBtn-guardar">Guardar</button>
				<span id="nuevaSesion-modal-error" style="color:red; display:none;">Ya existe una sesión con ese nombre.</span>
			</form>
		</div>
	</div>
	<div id="borrarSesion-modal" class="sesionModal">
		<div id="borrarSesion-modal-content" class="sesionModal-content">
			<h2>Eliminar sesión</h2>
			<p>
				Se eliminará la sesión y todos los tiempos asociados. ¿Seguro que desea continuar?
			</p>
			<div id="sesionBtn-container">
				<button id="sesionBtn-aceptar" onclick="borrarSesion()">Aceptar</button>
            	<button id="sesionBtn-cancelar" onclick="ocultarBorrarSesionModal()">Cancelar</button>
	        </div>
	        <span id="borrarSesion-modal-error" style="color:red; display:none;">Ha ocurrido un error al borrar la sesión.</span>
		</div>
	</div>
	<img id="config-btn" src="images/config-icon.png"/>
	<div id="config-container" style="display:none;">
   		<ul id="config-menu">
			<li><a href="#">Opción 1</a></li>
			<li><a href="#">Opción 2</a></li>
			<li><a href="#">Opción 3</a></li>
			<hr>
			<li><a href="#" id="logout">Cerrar sesión</a></li>
		</ul>
   	</div>
    <aside class="aside-container">
    	<div id="logo_container">
    		<img id="logo" src="images/logo.png" alt="Rubik timer">
    	</div>
      	<div id="sesion_container">
			<label id="sesion_label" for="sesion_select">Sesión:</label>
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
						<th>+2</th>
						<th>DNF</th>
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