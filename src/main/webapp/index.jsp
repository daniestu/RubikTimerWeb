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
    
    <jsp:include page="modal.jsp" />
    
	<img id="config-btn" src="images/config-icon.png"/>
	<div id="config-container" style="display:none;">
   		<ul id="config-menu">
   			<li id="custom-scramble" class="list-item"><img class="config-icon" src="images/personalizar.png"/>Mezcla personalizada</li>
			<li id="previus-scramble" class="list-item-disabled"><img id="previus-icon" class="config-icon" src="images/previus-disabled.png"/>Mezcla anterior</li>
			<li id="next-scramble" class="list-item"><img class="config-icon" src="images/next.png"/>Mezcla siguiente</li>
			<li id="add-solve" class="list-item"><img class="config-icon" src="images/add.png"/>Agregar tiempo</li>
			<li id="session-info" class="list-item"><img class="config-icon" src="images/info.png"/>Información de la sesión</li>
			<li id="export-solves" class="list-item-disabled"><img id="export-icon" class="config-icon" src="images/export-disabled.png"/>Exportar tiempos</li>
			<li id="import-solves" class="list-item"><img class="config-icon" src="images/import.png"/>Importar tiempos</li>
			<li id="preferences" class="list-item"><img class="config-icon" src="images/preferences.png"/>Configuración</li>
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