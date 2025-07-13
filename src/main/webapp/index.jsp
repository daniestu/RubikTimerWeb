<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="utils.MessageUtil" %>
<%@ page import="java.util.Locale" %>
<%@ page import="models.Conf" %>
<%@ page import="models.TemaConfig" %>

<%
    Conf conf = (Conf) request.getAttribute("conf");
    TemaConfig temaConfig = (TemaConfig) request.getAttribute("temaConfig");
    Locale locale = (Locale) request.getAttribute("locale");
%>

<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="head.jsp"/>
    <meta charset="UTF-8">
    <script type="text/javascript">
        window.config = {
            tema: <%= conf.getTema() %>,
            idioma: <%= conf.getIdioma() %>,
            ocultarElementos: <%= conf.getOcultarElementos() %>,
            ocultarVisualizacion: <%= conf.getOcultarVisualizacion() %>,
            pulsacionLarga: <%= conf.getPulsacionLarga() %>,
            cronometroRaton: <%= conf.getCronometroRaton() %>,
            tiempoInspeccion: <%= conf.getTiempoInspeccion() %>,
            segundosInspeccion: <%= conf.getSegundosInspeccion() %>,
            colorTexto: "<%= temaConfig.getColorTextoJS() %>",
            colorTerciario: "<%= temaConfig.getColorTerciarioJS() %>",
            imageExport: "<%= temaConfig.getImageExport() %>",
            imagePrevius: "<%= temaConfig.getImagePrevius() %>"
        };
    </script>
    <script src="js/scrambleScript.js" charset="UTF-8"></script>
    <script src="js/sesionScript.js" charset="UTF-8"></script>
    <script src="js/configuracionScript.js" charset="UTF-8"></script>
    <script src="js/preferenciasScript.js" charset="UTF-8"></script>
    <link rel="stylesheet" type="text/css" href="css/mainStyles.css">
    <link rel="stylesheet" type="text/css" href="css/asideStyles.css">
    <link rel="stylesheet" type="text/css" href="css/scrambleStyles.css">
    <link rel="stylesheet" type="text/css" href="css/configuracionStyles.css">
    <link rel="stylesheet" type="text/css" href="css/modalStyles.css">
    <link rel="stylesheet" type="text/css" href="css/previewStyles.css">
    <style>
        body {
            background-color: var(<%=temaConfig.getColorPrimario()%>);
            color: var(<%=temaConfig.getColorTexto()%>);
        }

        .aside-container, .side-panel, .scramble-container {
            background-color: var(<%=temaConfig.getColorSecundario()%>);
        }

        #sesion_select, #sesion_select_mobile {
            background-color: var(<%=temaConfig.getColorTerciario()%>);
            color: var(<%=temaConfig.getColorTexto()%>);
        }

        .tablaTiempos th, .list-item, .modal-content h2 {
            color: var(<%= temaConfig.getColorTexto() %>);
        }

        .toggle-box {
            background-color: var(<%= temaConfig.getColorSecundario() %>);
            color: var(<%= temaConfig.getColorTexto() %>);
            border-color: var(<%= temaConfig.getColorPrimario() %>);
        }

        #config-btn:hover, #config-container, .list-item-disabled:hover, .modal-content, .modal-content-large, .tablaTiempos-tiempo:hover {
        	background-color: var(<%=temaConfig.getColorTerciario()%>);
        }

        .list-item:hover {
            background-color: var(<%=temaConfig.getColorCuaternario()%>);
        }

        #avgTiempos-container {
            background-color: var(<%=temaConfig.getColorDisabled()%>);
        }
    </style>
  </head>
  <body>
    <div class="scramble-container" id="scramble-container">
        <div id="sesion_container_mobile">
            <label id="sesion_label_mobile" class="mb-0" for="sesion_select_mobile"><%= MessageUtil.getMessage(locale, "label.sesion")%>:</label>
            <select id="sesion_select_mobile" onchange="sesionChanged(this.value)"></select>
        </div>
        <p id="scramble" onclick="generateScramble()"></p>
    </div>

    <jsp:include page="modal.jsp" />

	<img id="config-btn" src="<%=temaConfig.getImageConfigIcon()%>"/>
	<div id="config-container" style="display:none;">
    	<ul id="config-menu">
    		<li id="custom-scramble" class="list-item"><img class="config-icon" src="<%=temaConfig.getImagePersonalizar()%>"/><%= MessageUtil.getMessage(locale, "option.mezcla_personalizada")%></li>
    		<li id="previus-scramble" class="list-item-disabled"><img id="previus-icon" class="config-icon" src="images/previus-disabled.png"/><%= MessageUtil.getMessage(locale, "option.mezcla_anterior")%></li>
    		<li id="next-scramble" class="list-item"><img class="config-icon" src="<%=temaConfig.getImageNext()%>"/><%= MessageUtil.getMessage(locale, "option.mezcla_siguiente")%></li>
    		<li id="add-solve" class="list-item"><img class="config-icon" src="<%=temaConfig.getImageAdd()%>"/><%= MessageUtil.getMessage(locale, "option.agregar_tiempo")%></li>
    		<li id="session-info" class="list-item"><img class="config-icon" src="<%=temaConfig.getImageInfo()%>"/><%= MessageUtil.getMessage(locale, "option.informacion_sesion")%></li>
    		<li id="export-solves" class="list-item-disabled"><img id="export-icon" class="config-icon" src="images/export-disabled.png"/><%= MessageUtil.getMessage(locale, "option.exportar_tiempos")%></li>
    		<li id="import-solves" class="list-item"><img class="config-icon" src="<%=temaConfig.getImageImport()%>"/><%= MessageUtil.getMessage(locale, "option.importar_tiempos")%></li>
    		<li id="preferences" class="list-item"><img class="config-icon" src="<%=temaConfig.getImagePreferences()%>"/><%= MessageUtil.getMessage(locale, "option.configuracion")%></li>
    		<hr>
    		<li id="logout" class="list-item"><img id="logout-icon" class="config-icon" src="<%=temaConfig.getImageLogout()%>"/><%= MessageUtil.getMessage(locale, "option.cerrar_sesion")%></li>
    	</ul>
    </div>
    <% if (conf.getOcultarVisualizacion() != 1) { %>
   	    <jsp:include page="preview.jsp" />
   	<%}%>
    <aside id="aside-container" class="aside-container">
    	<div id="logo_container" class="logo_container">
    		<img id="logo" class="logo" src="images/logo.png" alt="Rubik timer">
    	</div>
    	<div id="sesion_container">
    		<label id="sesion_label" class="mb-0" for="sesion_select"><%= MessageUtil.getMessage(locale, "label.sesion")%>:</label>
    		<select id="sesion_select" onchange="sesionChanged(this.value)"></select>
    	</div>
    	<div id="estadisticas_container" class="estadisticas_container">
    		<table id="tablaEstadisticas" class="tablaEstadisticas">
    			<tr>
    				<th><%= MessageUtil.getMessage(locale, "label.total")%></th>
    				<td id="total"></td>
    			</tr>
    			<tr>
    				<th><%= MessageUtil.getMessage(locale, "label.mejor")%></th>
    				<td id="mejor" class="solve"></td>
    			</tr>
    			<tr>
    				<th><%= MessageUtil.getMessage(locale, "label.peor")%></th>
    				<td id="peor" class="solve"></td>
    			</tr>
    			<tr>
    				<th><%= MessageUtil.getMessage(locale, "label.ao5")%></th>
    				<td id="ao5" class="average"></td>
    			</tr>
    			<tr>
    				<th><%= MessageUtil.getMessage(locale, "label.ao12")%></th>
    				<td id="ao12" class="average"></td>
    			</tr>
    			<tr>
    				<th><%= MessageUtil.getMessage(locale, "label.ao100")%></th>
    				<td id="ao100" class="average"></td>
    			</tr>
    			<tr>
    				<th><%= MessageUtil.getMessage(locale, "label.media")%></th>
    				<td id="media"></td>
    			</tr>
    		</table>
    	</div>
    	<div id="tiempos_container" class="tiempos_container">
    		<table id="tablaTiempos" class="tablaTiempos">
    			<thead>
    				<tr>
    					<th><%= MessageUtil.getMessage(locale, "label.id")%></th>
    					<th><%= MessageUtil.getMessage(locale, "label.tiempo_mayus")%></th>
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
                    <th><%= MessageUtil.getMessage(locale, "label.total")%></th>
                    <td id="total_mobile_side"></td>
                </tr>
                <tr>
                    <th><%= MessageUtil.getMessage(locale, "label.mejor")%></th>
                    <td id="mejor_mobile_side" class="solve"></td>
                </tr>
                <tr>
                    <th><%= MessageUtil.getMessage(locale, "label.peor")%></th>
                    <td id="peor_mobile_side" class="solve"></td>
                </tr>
                <tr>
                    <th><%= MessageUtil.getMessage(locale, "label.ao5")%></th>
                    <td id="ao5_mobile_side" class="average"></td>
                </tr>
                <tr>
                    <th><%= MessageUtil.getMessage(locale, "label.ao12")%></th>
                    <td id="ao12_mobile_side" class="average"></td>
                </tr>
                <tr>
                    <th><%= MessageUtil.getMessage(locale, "label.ao100")%></th>
                    <td id="ao100_mobile_side" class="average"></td>
                </tr>
                <tr>
                    <th><%= MessageUtil.getMessage(locale, "label.media")%></th>
                    <td id="media_mobile_side"></td>
                </tr>
            </table>
        </div>
        <div id="tiempos_container_mobile" class="tiempos_container">
            <table id="tablaTiempos_mobile" class="tablaTiempos">
                <thead>
                    <tr>
                        <th><%= MessageUtil.getMessage(locale, "label.id")%></th>
                        <th><%= MessageUtil.getMessage(locale, "label.tiempo_mayus")%></th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>

    <div id="cronometro-container" class="cronometro-container">
		<p id="cronometro">00:00:00</p>
		<div id="mobile-icons-container" class="d-lg-none invisible">
            <button type="button" id="btn-mobile-delete" class="mobile-action-btn" data-bs-toggle="modal" data-bs-target="#confirmDeleteModal">
                <img class="mobile-action-icon" src="<%=temaConfig.getImageDeleteSolve()%>"/>
            </button>
            <button type="button" id="btn-mobile-dnf" class="mobile-action-btn" onclick="addDnfUltimoSolveMobile(1)"><img class="mobile-action-icon" src="<%=temaConfig.getImageDnf()%>"/></button>
            <button type="button" id="btn-mobile-mas_dos" class="mobile-action-btn" onclick="addMas2UltimoSolveMobile(1)"><img class="mobile-action-icon" src="<%=temaConfig.getImageMasDos()%>"/></button>

		    <button type="button" id="btn-mobile-restart_dnf" class="mobile-action-btn" onclick="addDnfUltimoSolveMobile(0)"><img class="mobile-action-icon" src="<%=temaConfig.getImageRestart()%>"/></button>
		    <button type="button" id="btn-mobile-restart_mas_dos" class="mobile-action-btn" onclick="addMas2UltimoSolveMobile(0)"><img class="mobile-action-icon" src="<%=temaConfig.getImageRestart()%>"/></button>
		</div>
    </div>

    <!-- Modal de confirmacion para el borrado de un solve en mobile -->
    <div class="modal fade" id="confirmDeleteModal" tabindex="-1" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content p-0">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmDeleteModalLabel"><%= MessageUtil.getMessage(locale, "title.confirm_borrado")%></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                </div>
                <div class="modal-body">
                    <%= MessageUtil.getMessage(locale, "confirm.solve_delete")%>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><%= MessageUtil.getMessage(locale, "forms.cancelar")%></button>
                    <button type="button" class="btn btn-danger" id="confirmDeleteButton" onclick="borrarUltimoTiempoMobile()"><%= MessageUtil.getMessage(locale, "label.confirm_borrado_button")%></button>
                    <span id="delete-solve-mobile-modal-error" style="color:#B00C0C;" class="d-none"><%= MessageUtil.getMessage(locale, "error.borrar_tiempo")%></span>
                </div>
            </div>
        </div>
    </div>

    <div id="estadisticas_container_mobile" class="d-lg-none row w-100 m-0">
    	<div class="col-4">
    		<dl>
    			<div class="d-flex">
    				<dt><%= MessageUtil.getMessage(locale, "label.desviacion")%>:</dt>
    				<dd><span id="desviacion_mobile"></span></dd>
    			</div>
    			<div class="d-flex">
    				<dt><%= MessageUtil.getMessage(locale, "label.media")%>:</dt>
    				<dd><span id="media_mobile"></span></dd>
    			</div>
    			<div class="d-flex">
    				<dt><%= MessageUtil.getMessage(locale, "label.mejor")%>:</dt>
    				<dd><span id="mejor_mobile"></span></dd>
    			</div>
    			<div class="d-flex">
    				<dt><%= MessageUtil.getMessage(locale, "label.total")%>:</dt>
    				<dd><span id="total_mobile"></span></dd>
    			</div>
    		</dl>
    	</div>
    	<div class="col-4"></div>
    	<div class="col-4 d-flex justify-content-end align-items-end">
    		<dl>
    			<div class="d-flex">
    				<dt><%= MessageUtil.getMessage(locale, "label.ao5")%>:</dt>
    				<dd><span id="ao5_mobile"></span></dd>
    			</div>
    			<div class="d-flex">
    				<dt><%= MessageUtil.getMessage(locale, "label.ao12")%>:</dt>
    				<dd><span id="ao12_mobile"></span></dd>
    			</div>
    			<div class="d-flex">
    				<dt><%= MessageUtil.getMessage(locale, "label.ao100")%>:</dt>
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