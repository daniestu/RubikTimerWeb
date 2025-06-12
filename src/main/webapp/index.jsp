<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="utils.MessageUtil" %>
<%@ page import="java.util.Locale" %>

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
    <div class="scramble-container" id="scramble-container">
        <div id="sesion_container_mobile">
            <label id="sesion_label_mobile" class="mb-0" for="sesion_select_mobile"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.sesion")%>:</label>
            <select id="sesion_select_mobile" onchange="sesionChanged(this.value)"></select>
        </div>
        <p id="scramble" onclick="generateScramble()"></p>
    </div>
    
    <%--MODALES --%>
    <div id="nuevaSesion-modal" class="modal">
    	<div id="nuevaSesion-modal-content" class="modal-content modal-23">
    		<h2><%= MessageUtil.getMessage(new Locale("es", "ES"), "title.crear_sesion")%></h2>
    		<form class="modalForm" onsubmit="event.preventDefault();crearSesion(document.getElementById('nombre_sesion').value)">
    			<label for="nombre_sesion"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.nombre_sesion")%></label>
    			<input type="text" class="input-100" id="nombre_sesion" autocomplete="off" onchange="document.getElementById('nuevaSesion-modal-error').style.display = 'none'" required>
    			<button type="submit" class="btn btn-guardar"><%= MessageUtil.getMessage(new Locale("es", "ES"), "forms.save")%></button>
    			<span id="nuevaSesion-modal-error" style="color:#B00C0C; display:none;"><%= MessageUtil.getMessage(new Locale("es", "ES"), "error.session.already_exist")%></span>
    		</form>
    	</div>
    </div>
	<div id="borrarSesion-modal" class=modal>
    	<div id="borrarSesion-modal-content" class="modal-content">
    		<h2><%= MessageUtil.getMessage(new Locale("es", "ES"), "title.eliminar_sesion")%></h2>
    		<p>
    			<%= MessageUtil.getMessage(new Locale("es", "ES"), "confirm.session.delete")%>
    		</p>
    		<div id="btn-container">
    			<button id="sesionBtn-aceptar" onclick="borrarSesion(originalSelectedOption)"><%= MessageUtil.getMessage(new Locale("es", "ES"), "forms.aceptar")%></button>
    			<button id="sesionBtn-cancelar" onclick="ocultarBorrarSesionModal()"><%= MessageUtil.getMessage(new Locale("es", "ES"), "forms.cancelar")%></button>
    		</div>
    		<span id="borrarSesion-modal-error" style="color:#B00C0C; display:none;"><%= MessageUtil.getMessage(new Locale("es", "ES"), "error.session.delete")%></span>
    	</div>
    </div>
	<div id="scramble-personalizado-modal" class="modal">
    	<div id="scramble-personalizado-modal-content" class="modal-content modal-30">
    		<h2><%= MessageUtil.getMessage(new Locale("es", "ES"), "title.scramble_personalizado")%></h2>
    		<form class="modalForm" onsubmit="event.preventDefault();scramblePersonalizado(document.getElementById('scramble-text').value);">
    			<label for="scramble-text"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.scramble")%></label>
    			<input type="text" id="scramble-text" class="input-100" autocomplete="off" required>
    			<button type="submit" class=" btn btn-guardar"><%= MessageUtil.getMessage(new Locale("es", "ES"), "forms.save")%></button>
    			<span id="scramble-personalizado-modal-error" style="color:#B00C0C; display:none;"><%= MessageUtil.getMessage(new Locale("es", "ES"), "error.scramble_personalizado")%></span>
    		</form>
    	</div>
    </div>
	<div id="add-solve-modal" class="modal">
    	<div id="add-solve-modal-content" class="modal-content modal-30">
    		<h2><%= MessageUtil.getMessage(new Locale("es", "ES"), "title.agregar_tiempo")%></h2>
    		<form id="addSolve-form" class="modalForm" onsubmit="event.preventDefault();validarTiempo(document.getElementById('addSolve-tiempo').value, document.getElementById('addSolve-scramble').value);">
    			<label><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.scramble")%></label>
    			<input type="text" id="addSolve-scramble" class="input-100" autocomplete="off" required>
    			<label><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.tiempo")%></label>
    			<input type="text" id="addSolve-tiempo" autocomplete="off" required>
    			<button type="submit" class="btn btn-guardar"><%= MessageUtil.getMessage(new Locale("es", "ES"), "forms.save")%></button>
    			<span id="add-solve-modal-error" style="color:#B00C0C; display:none;"><%= MessageUtil.getMessage(new Locale("es", "ES"), "error.tiempo")%></span>
    		</form>
    	</div>
    </div>
	<div id="solveModal" class="modal">
    	<div id="solveModal-content" class="modal-content modal-30">
    		<h2><%= MessageUtil.getMessage(new Locale("es", "ES"), "title.tiempo")%></h2>
    		<form class="modalForm" onsubmit="event.preventDefault();borrarTiempo(document.getElementById('hidden-id').value)">
    			<input id="hidden-id" name="hidden-id" type="hidden">
    			<label for="scrambleInput" class="d-none d-lg-block"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.scramble")%></label>
    			<input type="text" id="scrambleInput" class="input-100 d-none d-lg-block" name="scrambleInput" disabled>
    			<label for="scrambleInputMobile" class="d-lg-none"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.scramble")%></label>
                <textarea type="text" id="scrambleInputMobile" class="input-100 scramble-textarea d-lg-none" name="scrambleInputMobile" disabled></textarea>
    			<label for="fecha"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.fecha")%></label>
    			<input type="text" id="fecha" name="fecha" disabled>
    			<label for="tiempo"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.tiempo")%></label>
    			<input type="text" id="tiempo" name="tiempo" disabled>
    			<div class="mt-1">
    				<button id="solveBtn-mas2" class="solveBtn" onclick="addMas2(document.getElementById('hidden-id').value);"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.mas2")%></button>
    				<button id="solveBtn-dnf" class="solveBtn" onclick="addDnf(document.getElementById('hidden-id').value);"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.dnf")%></button>
    			</div>
    			<div class="w-100 d-flex justify-content-end mt-3">
    				<button type="submit" id="solveBtn-eliminar"><%= MessageUtil.getMessage(new Locale("es", "ES"), "forms.delete")%></button>
    			</div>
    			<span id="solve-modal-error" style="color:#B00C0C; display:none;"><%= MessageUtil.getMessage(new Locale("es", "ES"), "error.borrar_tiempo")%></span>
    		</form>
    	</div>
    </div>
	<div id="avgModal" class="modal">
    	<div id="avgModal-content" class="modal-content">
    		<h2 id="avgModal-title"></h2>
    		<form class="modalForm">
    			<label for="scrambleInput"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.media")%></label>
    			<input type="text" id="avg-tiempo" name="avg-tiempo" disabled>
    			<label><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.solves")%></label>
    			<div id="avgTiempos-container" class="w-100">
    				<table id="avg-table"></table>
    			</div>
    		</form>
    	</div>
    </div>
	
	<div id="session-info-modal" class="modal">
    	<div id="session-info-modal-content" class="modal-content">
    		<h2><%= MessageUtil.getMessage(new Locale("es", "ES"), "title.informacion_sesion")%></h2>
    		<form id="sessionInfo-form" class="verticalModalForm" onsubmit="event.preventDefault();actualizarSesion(document.getElementById('info-name').value);">
    			<div id="session-info-data">
    				<div class="form-group">
    					<label for="info-name"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.nombre")%></label>
    					<input type="text" class="form-control" name="info-name" id="info-name">
    				</div>
    				<div class="form-group">
    					<label for="info-total"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.total")%></label>
    					<input type="text" class="form-control" id="info-total" disabled>
    				</div>
    				<div class="form-group">
    					<label for="info-best"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.mejor")%></label>
    					<input type="email" class="form-control" id="info-best" disabled>
    				</div>
    				<div class="form-group">
    					<label for="info-worst"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.peor")%></label>
    					<input type="tel" class="form-control" id="info-worst" disabled>
    				</div>
    				<div class="form-group">
    					<label for="info-avg"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.media")%></label>
    					<input type="tel" class="form-control" id="info-avg" disabled>
    				</div>
    				<div class="form-group">
    					<label for="info-desv"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.desviacion")%></label>
    					<input type="tel" class="form-control" id="info-desv" disabled>
    				</div>
    				<div class="form-group">
    					<label for="info-ao5"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.ao5_actual")%></label>
    					<input type="tel" class="form-control" id="info-ao5" disabled>
    				</div>
    				<div class="form-group">
    					<label for="info-ao12"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.ao12_actual")%></label>
    					<input type="tel" class="form-control" id="info-ao12" disabled>
    				</div>
    				<div class="form-group">
    					<label for="info-ao100"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.ao100_actual")%></label>
    					<input type="tel" class="form-control" id="info-ao100" disabled>
    				</div>
    				<div class="form-group">
    					<label for="info-bestao5"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.mejor_ao5")%></label>
    					<input type="tel" class="form-control" id="info-bestao5" disabled>
    				</div>
    				<div class="form-group">
    					<label for="info-bestao12"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.mejor_ao12")%></label>
    					<input type="tel" class="form-control" id="info-bestao12" disabled>
    				</div>
    				<div class="form-group">
    					<label for="info-bestao100"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.mejor_ao100")%></label>
    					<input type="tel" class="form-control" id="info-bestao100" disabled>
    				</div>
    			</div>
    			<button type="submit" class="btn btn-guardar"><%= MessageUtil.getMessage(new Locale("es", "ES"), "forms.aceptar")%></button>
    			<button type="submit" class="btn btn-danger ml-1" onclick="event.preventDefault();confirmDelete(document.getElementById('sesion_select').value);"><%= MessageUtil.getMessage(new Locale("es", "ES"), "forms.delete")%></button>
    			<span id="session-info-modal-error" style="color:#B00C0C; display:none; margin-top:2%;"><%= MessageUtil.getMessage(new Locale("es", "ES"), "error.actualizar_sesion")%></span>
    		</form>
    	</div>
    </div>
	
	<div id="preview-modal" class=modal>
		<jsp:include page="modalPreview.jsp" />
	</div>
	
	<div id="importModal" class="modal">
    	<div id="importModal-content" class="modal-content modal-30">
    		<h2><%= MessageUtil.getMessage(new Locale("es", "ES"), "title.importar_tiempos")%></h2>
    		<form id="importForm" class="modalForm" enctype="multipart/form-data">
    			<input type="hidden" name="sesion" id="importSesion" />
    			<input type="file" id="importFile" name="importFile" required />
    			<button type="button" onclick="importSolves()" class="btn btn-guardar mt-3"><%= MessageUtil.getMessage(new Locale("es", "ES"), "forms.importar")%></button>
    			<span id="import-modal-error" class="mt-2" style="color:#B00C0C; display:none;"><%= MessageUtil.getMessage(new Locale("es", "ES"), "error.importar_tiempos")%></span>
    		</form>
    	</div>
    </div>
	<%--FIN MODALES --%>
	<img id="config-btn" src="images/config-icon.png"/>
	<div id="config-container" style="display:none;">
    	<ul id="config-menu">
    		<li id="custom-scramble" class="list-item"><img class="config-icon" src="images/personalizar.png"/><%= MessageUtil.getMessage(new Locale("es", "ES"), "option.mezcla_personalizada")%></li>
    		<li id="previus-scramble" class="list-item-disabled"><img id="previus-icon" class="config-icon" src="images/previus-disabled.png"/><%= MessageUtil.getMessage(new Locale("es", "ES"), "option.mezcla_anterior")%></li>
    		<li id="next-scramble" class="list-item"><img class="config-icon" src="images/next.png"/><%= MessageUtil.getMessage(new Locale("es", "ES"), "option.mezcla_siguiente")%></li>
    		<li id="add-solve" class="list-item"><img class="config-icon" src="images/add.png"/><%= MessageUtil.getMessage(new Locale("es", "ES"), "option.agregar_tiempo")%></li>
    		<li id="session-info" class="list-item"><img class="config-icon" src="images/info.png"/><%= MessageUtil.getMessage(new Locale("es", "ES"), "option.informacion_sesion")%></li>
    		<li id="export-solves" class="list-item-disabled"><img id="export-icon" class="config-icon" src="images/export-disabled.png"/><%= MessageUtil.getMessage(new Locale("es", "ES"), "option.exportar_tiempos")%></li>
    		<li id="import-solves" class="list-item"><img class="config-icon" src="images/import.png"/><%= MessageUtil.getMessage(new Locale("es", "ES"), "option.importar_tiempos")%></li>
    		<hr>
    		<li id="logout" class="list-item"><img id="logout-icon" class="config-icon" src="images/logout.png"/><%= MessageUtil.getMessage(new Locale("es", "ES"), "option.cerrar_sesion")%></li>
    	</ul>
    </div>
   	<jsp:include page="preview.jsp" />
    <aside class="aside-container">
    	<div id="logo_container" class="logo_container">
    		<img id="logo" class="logo" src="images/logo.png" alt="Rubik timer">
    	</div>
    	<div id="sesion_container">
    		<label id="sesion_label" class="mb-0" for="sesion_select"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.sesion")%>:</label>
    		<select id="sesion_select" onchange="sesionChanged(this.value)"></select>
    	</div>
    	<div id="estadisticas_container" class="estadisticas_container">
    		<table id="tablaEstadisticas" class="tablaEstadisticas">
    			<tr>
    				<th><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.total")%></th>
    				<td id="total"></td>
    			</tr>
    			<tr>
    				<th><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.mejor")%></th>
    				<td id="mejor" class="solve"></td>
    			</tr>
    			<tr>
    				<th><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.peor")%></th>
    				<td id="peor" class="solve"></td>
    			</tr>
    			<tr>
    				<th><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.ao5")%></th>
    				<td id="ao5" class="average"></td>
    			</tr>
    			<tr>
    				<th><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.ao12")%></th>
    				<td id="ao12" class="average"></td>
    			</tr>
    			<tr>
    				<th><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.ao100")%></th>
    				<td id="ao100" class="average"></td>
    			</tr>
    			<tr>
    				<th><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.media")%></th>
    				<td id="media"></td>
    			</tr>
    		</table>
    	</div>
    	<div id="tiempos_container" class="tiempos_container">
    		<table id="tablaTiempos" class="tablaTiempos">
    			<thead>
    				<tr>
    					<th><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.id")%></th>
    					<th><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.tiempo_mayus")%></th>
    				</tr>
    			</thead>
    			<tbody>
    			</tbody>
    		</table>
    	</div>
    </aside>

    <div class="toggle-box rounded-end d-lg-none" id="toggleBox">
        <span class="arrow" id="toggleArrow">➤</span>
    </div>

    <div class="side-panel d-lg-none" id="sidePanel">
        <div id="logo_container_mobile" class="logo_container">
            <img id="logo_mobile" class="logo" src="images/logo.png" alt="Rubik timer">
        </div>
        <div id="estadisticas_container_mobile_side" class="estadisticas_container">
            <table id="tablaEstadisticas_mobile" class="tablaEstadisticas">
                <tr>
                    <th><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.total")%></th>
                    <td id="total_mobile_side"></td>
                </tr>
                <tr>
                    <th><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.mejor")%></th>
                    <td id="mejor_mobile_side" class="solve"></td>
                </tr>
                <tr>
                    <th><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.peor")%></th>
                    <td id="peor_mobile_side" class="solve"></td>
                </tr>
                <tr>
                    <th><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.ao5")%></th>
                    <td id="ao5_mobile_side" class="average"></td>
                </tr>
                <tr>
                    <th><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.ao12")%></th>
                    <td id="ao12_mobile_side" class="average"></td>
                </tr>
                <tr>
                    <th><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.ao100")%></th>
                    <td id="ao100_mobile_side" class="average"></td>
                </tr>
                <tr>
                    <th><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.media")%></th>
                    <td id="media_mobile_side"></td>
                </tr>
            </table>
        </div>
        <div id="tiempos_container_mobile" class="tiempos_container">
            <table id="tablaTiempos_mobile" class="tablaTiempos">
                <thead>
                    <tr>
                        <th><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.id")%></th>
                        <th><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.tiempo_mayus")%></th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>

    <div class="cronometro-container">
		<p id="cronometro">00:00:00</p>
		<div id="mobile-icons-container" class="d-lg-none invisible">
            <button type="button" id="btn-mobile-delete" class="mobile-action-btn" data-bs-toggle="modal" data-bs-target="#confirmDeleteModal">
                <img class="mobile-action-icon" src="images/delete-solve.png"/>
            </button>
            <button type="button" id="btn-mobile-dnf" class="mobile-action-btn" onclick="addDnfUltimoSolveMobile(1)"><img class="mobile-action-icon" src="images/dnf.png"/></button>
            <button type="button" id="btn-mobile-mas_dos" class="mobile-action-btn" onclick="addMas2UltimoSolveMobile(1)"><img class="mobile-action-icon" src="images/mas_dos.png"/></button>

		    <button type="button" id="btn-mobile-restart_dnf" class="mobile-action-btn" onclick="addDnfUltimoSolveMobile(0)"><img class="mobile-action-icon" src="images/restart.png"/></button>
		    <button type="button" id="btn-mobile-restart_mas_dos" class="mobile-action-btn" onclick="addMas2UltimoSolveMobile(0)"><img class="mobile-action-icon" src="images/restart.png"/></button>
		</div>
    </div>

    <!-- Modal de confirmacion para el borrado de un solve en mobile -->
    <div class="modal fade" id="confirmDeleteModal" tabindex="-1" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content p-0">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmDeleteModalLabel"><%= MessageUtil.getMessage(new Locale("es", "ES"), "title.confirm_borrado")%></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                </div>
                <div class="modal-body">
                    <%= MessageUtil.getMessage(new Locale("es", "ES"), "confirm.solve_delete")%>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><%= MessageUtil.getMessage(new Locale("es", "ES"), "forms.cancelar")%></button>
                    <button type="button" class="btn btn-danger" id="confirmDeleteButton" onclick="borrarUltimoTiempoMobile()"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.confirm_borrado_button")%></button>
                    <span id="delete-solve-mobile-modal-error" style="color:#B00C0C;" class="d-none"><%= MessageUtil.getMessage(new Locale("es", "ES"), "error.borrar_tiempo")%></span>
                </div>
            </div>
        </div>
    </div>

    <div id="estadisticas_container_mobile" class="d-lg-none row w-100 m-0">
    	<div class="col-4">
    		<dl>
    			<div class="d-flex">
    				<dt><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.desviacion")%>:</dt>
    				<dd><span id="desviacion_mobile"></span></dd>
    			</div>
    			<div class="d-flex">
    				<dt><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.media")%>:</dt>
    				<dd><span id="media_mobile"></span></dd>
    			</div>
    			<div class="d-flex">
    				<dt><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.mejor")%>:</dt>
    				<dd><span id="mejor_mobile"></span></dd>
    			</div>
    			<div class="d-flex">
    				<dt><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.total")%>:</dt>
    				<dd><span id="total_mobile"></span></dd>
    			</div>
    		</dl>
    	</div>
    	<div class="col-4"></div>
    	<div class="col-4 d-flex justify-content-end align-items-end">
    		<dl>
    			<div class="d-flex">
    				<dt><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.ao5")%>:</dt>
    				<dd><span id="ao5_mobile"></span></dd>
    			</div>
    			<div class="d-flex">
    				<dt><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.ao12")%>:</dt>
    				<dd><span id="ao12_mobile"></span></dd>
    			</div>
    			<div class="d-flex">
    				<dt><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.ao100")%>:</dt>
    				<dd><span id="ao100_mobile"></span></dd>
    			</div>
    		</dl>
    	</div>
    </div>

    <script src="js/cronometroScript.js"></script>
    <script>
	    generateScramble();
	    getSesiones();
    </script>
  </body>
</html>