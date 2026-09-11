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
<html data-tema="<%= conf.getTema() %>" data-bs-theme="<%= (conf.getTema() == 1 || conf.getTema() == 2 || conf.getTema() == 3) ? "dark" : "light" %>">
  <head>
    <% request.setAttribute("tituloClave", "seo.titulo_inicio");
      request.setAttribute("descripcionClave", "seo.descripcion_inicio");
      request.setAttribute("urlCanonica", "https://www.dtimerapp.com/"); %>
    <jsp:include page="head.jsp"/>
    <meta charset="UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono:wght@400;500&family=Space+Grotesk:wght@500;600;700&display=swap" rel="stylesheet">
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
            invitado: <%= request.getAttribute("esInvitado") %>
        };

        if (window.config.invitado) {
            try {
                const preferenciasGuardadas = JSON.parse(localStorage.getItem('rubikTimerPreferencias') || '{}');
                Object.assign(window.config, preferenciasGuardadas);
            } catch (e) {
                console.error('No se han podido cargar las preferencias del invitado', e);
            }
        }
    </script>
    <% if (session.getAttribute("usuario") != null) { %>
        <script src="js/data/account/sesionData.js" charset="UTF-8"></script>
    <% } else { %>
        <script src="js/data/guest/statsEngine.js" charset="UTF-8"></script>
        <script src="js/data/guest/db.js" charset="UTF-8"></script>
        <script src="js/data/guest/sesionData.js" charset="UTF-8"></script>
    <% } %>
    <script src="js/core/scrambleScript.js" charset="UTF-8"></script>
    <script src="js/core/preferenciasScript.js" charset="UTF-8"></script>
    <script src="js/core/configuracionScript.js" charset="UTF-8"></script>
    <script src="js/ui/sesionUI.js" charset="UTF-8"></script>
    <link rel="stylesheet" type="text/css" href="css/mainStyles.css">
    <link rel="stylesheet" type="text/css" href="css/asideStyles.css">
    <link rel="stylesheet" type="text/css" href="css/scrambleStyles.css">
    <link rel="stylesheet" type="text/css" href="css/configuracionStyles.css">
    <link rel="stylesheet" type="text/css" href="css/modalStyles.css">
    <link rel="stylesheet" type="text/css" href="css/previewStyles.css">
    <script type="application/ld+json">
        {
            "@context": "https://schema.org",
            "@type": "WebApplication",
            "name": "DTimer",
            "url": "https://www.dtimerapp.com/",
            "description": "<%= MessageUtil.getMessage(locale, "seo.descripcion_inicio")%>",
            "applicationCategory": "UtilitiesApplication",
            "operatingSystem": "Any (web-based)",
            "offers": {
                "@type": "Offer",
                "price": "0",
                "priceCurrency": "USD"
            }
        }
    </script>
  </head>
  <body>
    <h1 class="visually-hidden"><%= MessageUtil.getMessage(locale, "seo.titulo_inicio")%></h1>
    <jsp:include page="icons.jsp" />

    <div class="scramble-container" id="scramble-container">
        <div id="sesion_container_mobile">
            <label id="sesion_label_mobile" class="mb-0" for="sesion_select_mobile"><%= MessageUtil.getMessage(locale, "label.sesion")%>:</label>
            <select id="sesion_select_mobile" onchange="sesionChanged(this.value)"></select>
        </div>
        <p id="scramble" onclick="generateScramble()"></p>
    </div>

    <jsp:include page="modal.jsp" />

    <button type="button" id="config-btn" aria-label="<%= MessageUtil.getMessage(locale, "option.configuracion")%>"><svg class="icon"><use href="#icon-menu-dots"/></svg></button>
    <div id="config-container" style="display:none;">
    	<ul id="config-menu">
    		<li id="custom-scramble" class="list-item"><svg class="icon config-icon"><use href="#icon-edit"/></svg><%= MessageUtil.getMessage(locale, "option.mezcla_personalizada")%></li>
    		<li id="previus-scramble" class="list-item-disabled"><svg id="previus-icon" class="icon config-icon"><use href="#icon-chevron-left"/></svg><%= MessageUtil.getMessage(locale, "option.mezcla_anterior")%></li>
    		<li id="next-scramble" class="list-item"><svg class="icon config-icon"><use href="#icon-chevron-right-double"/></svg><%= MessageUtil.getMessage(locale, "option.mezcla_siguiente")%></li>
    		<li id="add-solve" class="list-item"><svg class="icon config-icon"><use href="#icon-plus"/></svg><%= MessageUtil.getMessage(locale, "option.agregar_tiempo")%></li>
    		<li id="session-info" class="list-item"><svg class="icon config-icon"><use href="#icon-info"/></svg><%= MessageUtil.getMessage(locale, "option.informacion_sesion")%></li>
    		<li id="export-solves" class="list-item-disabled"><svg id="export-icon" class="icon config-icon"><use href="#icon-download"/></svg><%= MessageUtil.getMessage(locale, "option.exportar_tiempos")%></li>
    		<li id="import-solves" class="list-item"><svg class="icon config-icon"><use href="#icon-upload"/></svg><%= MessageUtil.getMessage(locale, "option.importar_tiempos")%></li>
    		<li id="preferences" class="list-item"><svg class="icon config-icon"><use href="#icon-sliders"/></svg><%= MessageUtil.getMessage(locale, "option.configuracion")%></li>
    		<hr>
    		<li id="logout" class="list-item"><svg id="logout-icon" class="icon config-icon"><use href="#icon-logout"/></svg><%= MessageUtil.getMessage(locale, "option.cerrar_sesion")%></li>
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
    			<tr><th><%= MessageUtil.getMessage(locale, "label.total")%></th><td id="total"></td></tr>
    			<tr><th><%= MessageUtil.getMessage(locale, "label.mejor")%></th><td id="mejor" class="solve"></td></tr>
    			<tr><th><%= MessageUtil.getMessage(locale, "label.peor")%></th><td id="peor" class="solve"></td></tr>
    			<tr><th><%= MessageUtil.getMessage(locale, "label.ao5")%></th><td id="ao5" class="average"></td></tr>
    			<tr><th><%= MessageUtil.getMessage(locale, "label.ao12")%></th><td id="ao12" class="average"></td></tr>
    			<tr><th><%= MessageUtil.getMessage(locale, "label.ao100")%></th><td id="ao100" class="average"></td></tr>
    			<tr><th><%= MessageUtil.getMessage(locale, "label.media")%></th><td id="media"></td></tr>
    		</table>
    	</div>
    	<div id="tiempos_container" class="tiempos_container">
    		<table id="tablaTiempos" class="tablaTiempos">
    			<thead><tr><th><%= MessageUtil.getMessage(locale, "label.id")%></th><th><%= MessageUtil.getMessage(locale, "label.tiempo_mayus")%></th></tr></thead>
    			<tbody></tbody>
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
                <tr><th><%= MessageUtil.getMessage(locale, "label.total")%></th><td id="total_mobile_side"></td></tr>
                <tr><th><%= MessageUtil.getMessage(locale, "label.mejor")%></th><td id="mejor_mobile_side" class="solve"></td></tr>
                <tr><th><%= MessageUtil.getMessage(locale, "label.peor")%></th><td id="peor_mobile_side" class="solve"></td></tr>
                <tr><th><%= MessageUtil.getMessage(locale, "label.ao5")%></th><td id="ao5_mobile_side" class="average"></td></tr>
                <tr><th><%= MessageUtil.getMessage(locale, "label.ao12")%></th><td id="ao12_mobile_side" class="average"></td></tr>
                <tr><th><%= MessageUtil.getMessage(locale, "label.ao100")%></th><td id="ao100_mobile_side" class="average"></td></tr>
                <tr><th><%= MessageUtil.getMessage(locale, "label.media")%></th><td id="media_mobile_side"></td></tr>
            </table>
        </div>
        <div id="tiempos_container_mobile" class="tiempos_container">
            <table id="tablaTiempos_mobile" class="tablaTiempos">
                <thead><tr><th><%= MessageUtil.getMessage(locale, "label.id")%></th><th><%= MessageUtil.getMessage(locale, "label.tiempo_mayus")%></th></tr></thead>
                <tbody></tbody>
            </table>
        </div>
    </div>

    <div id="cronometro-container" class="cronometro-container">
		<p id="cronometro">00:00:00</p>
		<div id="mobile-icons-container" class="d-lg-none invisible">
            <button type="button" id="btn-mobile-delete" class="mobile-action-btn" data-bs-toggle="modal" data-bs-target="#confirmDeleteModal">
                <svg class="icon mobile-action-icon"><use href="#icon-trash"/></svg>
            </button>
            <button type="button" id="btn-mobile-dnf" class="mobile-action-btn" onclick="addDnfUltimoSolveMobile(1)"><svg class="icon mobile-action-icon"><use href="#icon-flag"/></svg></button>
            <button type="button" id="btn-mobile-mas_dos" class="mobile-action-btn" onclick="addMas2UltimoSolveMobile(1)"><svg class="icon mobile-action-icon"><use href="#icon-plus-two"/></svg></button>
		    <button type="button" id="btn-mobile-restart_dnf" class="mobile-action-btn" onclick="addDnfUltimoSolveMobile(0)"><svg class="icon mobile-action-icon"><use href="#icon-undo"/></svg></button>
		    <button type="button" id="btn-mobile-restart_mas_dos" class="mobile-action-btn" onclick="addMas2UltimoSolveMobile(0)"><svg class="icon mobile-action-icon"><use href="#icon-undo"/></svg></button>
		</div>
    </div>

    <div class="modal fade" id="confirmDeleteModal" tabindex="-1" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content p-0">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmDeleteModalLabel"><%= MessageUtil.getMessage(locale, "title.confirm_borrado")%></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                </div>
                <div class="modal-body"><%= MessageUtil.getMessage(locale, "confirm.solve_delete")%></div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><%= MessageUtil.getMessage(locale, "forms.cancelar")%></button>
                    <button type="button" class="btn btn-danger" id="confirmDeleteButton" onclick="borrarUltimoTiempoMobile()"><%= MessageUtil.getMessage(locale, "label.confirm_borrado_button")%></button>
                    <span id="delete-solve-mobile-modal-error" class="form-error d-none"><%= MessageUtil.getMessage(locale, "error.borrar_tiempo")%></span>
                </div>
            </div>
        </div>
    </div>

    <div id="estadisticas_container_mobile" class="d-lg-none row w-100 m-0">
    	<div class="col-4">
    		<dl>
    			<div class="d-flex"><dt><%= MessageUtil.getMessage(locale, "label.desviacion")%>:</dt><dd><span id="desviacion_mobile"></span></dd></div>
    			<div class="d-flex"><dt><%= MessageUtil.getMessage(locale, "label.media")%>:</dt><dd><span id="media_mobile"></span></dd></div>
    			<div class="d-flex"><dt><%= MessageUtil.getMessage(locale, "label.mejor")%>:</dt><dd><span id="mejor_mobile"></span></dd></div>
    			<div class="d-flex"><dt><%= MessageUtil.getMessage(locale, "label.total")%>:</dt><dd><span id="total_mobile"></span></dd></div>
    		</dl>
    	</div>
    	<div class="col-4"></div>
    	<div class="col-4 d-flex justify-content-end align-items-end">
    		<dl>
    			<div class="d-flex"><dt><%= MessageUtil.getMessage(locale, "label.ao5")%>:</dt><dd><span id="ao5_mobile"></span></dd></div>
    			<div class="d-flex"><dt><%= MessageUtil.getMessage(locale, "label.ao12")%>:</dt><dd><span id="ao12_mobile"></span></dd></div>
    			<div class="d-flex"><dt><%= MessageUtil.getMessage(locale, "label.ao100")%>:</dt><dd><span id="ao100_mobile"></span></dd></div>
    		</dl>
    	</div>
    </div>

    <script src="js/core/cronometroScript.js"></script>
    <script>
	    generateScramble();
	    getSesiones();
    </script>
  </body>
</html>