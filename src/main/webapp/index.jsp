<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="head.jsp" />
    <script src="js/scrambleScript.js"></script>
    <link rel="stylesheet" type="text/css" href="css/cronometroStyles.css">
    <link rel="stylesheet" type="text/css" href="css/asideStyles.css">
    <link rel="stylesheet" type="text/css" href="css/scrambleStyles.css">
  </head>
  <body>
    <div class="scramble-container">
      <p id="scramble"></p>
    </div>
    <aside class="aside-container">
    	<div id="logo_container">
    		<img id="logo" src="images/logo.png" alt="Rubik timer">
    	</div>
      	<div id="sesion_container">
			<label id="sesion_label" for="sesion_select">Sesión:</label>
		 	<select id="sesion_select">
				<option value="option1">Option 1</option>
				<option value="option2">Option 2</option>
				<option value="option3">Option 3</option>
			 </select>
      	</div>
      	<div id="estadisticas_container">
      		<table id="tablaEstadisticas">
				<tr>
					<th>Total</th>
					<td></td>
				</tr>
				<tr>
					<th>Mejor</th>
					<td></td>
				</tr>
				<tr>
					<th>Peor</th>
					<td></td>
				</tr>
				<tr>
					<th>Ao5</th>
					<td></td>
				</tr>
				<tr>
					<th>Ao12</th>
					<td></td>
				</tr>
				<tr>
					<th>Ao100</th>
					<td></td>
				</tr>
				<tr>
					<th>Media</th>
					<td></td>
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
    <script>generateScramble();</script>
  </body>
</html>