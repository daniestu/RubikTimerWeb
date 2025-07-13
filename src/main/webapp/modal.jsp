<%@ page import="utils.MessageUtil" %>
<%@ page import="java.util.Locale" %>
<%@ page import="models.Conf" %>

<%
    Conf conf = (Conf) request.getAttribute("conf");
    Locale locale = (Locale) request.getAttribute("locale");
    int idiomaNavegador = (int) request.getAttribute("idiomaNavegador");
%>

<%--MODALES --%>
<div id="nuevaSesion-modal" class="modal">
    <div id="nuevaSesion-modal-content" class="modal-content modal-23">
        <h2><%= MessageUtil.getMessage(locale, "title.crear_sesion")%></h2>
        <form class="modalForm" onsubmit="event.preventDefault();crearSesion(document.getElementById('nombre_sesion').value)">
            <label for="nombre_sesion"><%= MessageUtil.getMessage(locale, "label.nombre_sesion")%></label>
            <input type="text" class="input-100" id="nombre_sesion" autocomplete="off" onchange="document.getElementById('nuevaSesion-modal-error').style.display = 'none'" required>
            <button type="submit" class="btn btn-guardar"><%= MessageUtil.getMessage(locale, "forms.save")%></button>
            <span id="nuevaSesion-modal-error" style="color:#B00C0C; display:none;"><%= MessageUtil.getMessage(locale, "error.session.already_exist")%></span>
        </form>
    </div>
</div>
<div id="borrarSesion-modal" class=modal>
    <div id="borrarSesion-modal-content" class="modal-content">
        <h2><%= MessageUtil.getMessage(locale, "title.eliminar_sesion")%></h2>
        <p>
            <%= MessageUtil.getMessage(locale, "confirm.session.delete")%>
        </p>
        <div id="btn-container">
            <button id="sesionBtn-aceptar" onclick="borrarSesion(originalSelectedOption)"><%= MessageUtil.getMessage(locale, "forms.aceptar")%></button>
            <button id="sesionBtn-cancelar" onclick="ocultarBorrarSesionModal()"><%= MessageUtil.getMessage(locale, "forms.cancelar")%></button>
        </div>
        <span id="borrarSesion-modal-error" style="color:#B00C0C; display:none;"><%= MessageUtil.getMessage(locale, "error.session.delete")%></span>
    </div>
</div>
<div id="scramble-personalizado-modal" class="modal">
    <div id="scramble-personalizado-modal-content" class="modal-content modal-30">
        <h2><%= MessageUtil.getMessage(locale, "title.scramble_personalizado")%></h2>
        <form class="modalForm" onsubmit="event.preventDefault();scramblePersonalizado(document.getElementById('scramble-text').value);">
            <label for="scramble-text"><%= MessageUtil.getMessage(locale, "label.scramble")%></label>
            <input type="text" id="scramble-text" class="input-100" autocomplete="off" required>
            <button type="submit" class=" btn btn-guardar"><%= MessageUtil.getMessage(locale, "forms.save")%></button>
            <span id="scramble-personalizado-modal-error" style="color:#B00C0C; display:none;"><%= MessageUtil.getMessage(locale, "error.scramble_personalizado")%></span>
        </form>
    </div>
</div>
<div id="add-solve-modal" class="modal">
    <div id="add-solve-modal-content" class="modal-content modal-30">
        <h2><%= MessageUtil.getMessage(locale, "title.agregar_tiempo")%></h2>
        <form id="addSolve-form" class="modalForm" onsubmit="event.preventDefault();validarTiempo(document.getElementById('addSolve-tiempo').value, document.getElementById('addSolve-scramble').value);">
            <label><%= MessageUtil.getMessage(locale, "label.scramble")%></label>
            <input type="text" id="addSolve-scramble" class="input-100" autocomplete="off" required>
            <label><%= MessageUtil.getMessage(locale, "label.tiempo")%></label>
            <input type="text" id="addSolve-tiempo" autocomplete="off" required>
            <button type="submit" class="btn btn-guardar"><%= MessageUtil.getMessage(locale, "forms.save")%></button>
            <span id="add-solve-modal-error" style="color:#B00C0C; display:none;"><%= MessageUtil.getMessage(locale, "error.tiempo")%></span>
        </form>
    </div>
</div>
<div id="solveModal" class="modal">
    <div id="solveModal-content" class="modal-content modal-30">
        <h2><%= MessageUtil.getMessage(locale, "title.tiempo")%></h2>
        <form class="modalForm" onsubmit="event.preventDefault();borrarTiempo(document.getElementById('hidden-id').value)">
            <input id="hidden-id" name="hidden-id" type="hidden">
            <label for="scrambleInput" class="d-none d-lg-block"><%= MessageUtil.getMessage(locale, "label.scramble")%></label>
            <input type="text" id="scrambleInput" class="input-100 d-none d-lg-block" name="scrambleInput" disabled>
            <label for="scrambleInputMobile" class="d-lg-none"><%= MessageUtil.getMessage(locale, "label.scramble")%></label>
            <textarea type="text" id="scrambleInputMobile" class="input-100 scramble-textarea d-lg-none" name="scrambleInputMobile" disabled></textarea>
            <label for="fecha"><%= MessageUtil.getMessage(locale, "label.fecha")%></label>
            <input type="text" id="fecha" name="fecha" disabled>
            <label for="tiempo"><%= MessageUtil.getMessage(locale, "label.tiempo")%></label>
            <input type="text" id="tiempo" name="tiempo" disabled>
            <div class="mt-1">
                <button id="solveBtn-mas2" class="solveBtn" onclick="addMas2(document.getElementById('hidden-id').value);"><%= MessageUtil.getMessage(locale, "label.mas2")%></button>
                <button id="solveBtn-dnf" class="solveBtn" onclick="addDnf(document.getElementById('hidden-id').value);"><%= MessageUtil.getMessage(locale, "label.dnf")%></button>
            </div>
            <div class="w-100 d-flex justify-content-end mt-3">
                <button type="submit" id="solveBtn-eliminar"><%= MessageUtil.getMessage(locale, "forms.delete")%></button>
            </div>
            <span id="solve-modal-error" style="color:#B00C0C; display:none;"><%= MessageUtil.getMessage(locale, "error.borrar_tiempo")%></span>
        </form>
    </div>
</div>
<div id="avgModal" class="modal">
    <div id="avgModal-content" class="modal-content">
        <h2 id="avgModal-title"></h2>
        <form class="modalForm">
            <label for="scrambleInput"><%= MessageUtil.getMessage(locale, "label.media")%></label>
            <input type="text" id="avg-tiempo" name="avg-tiempo" disabled>
            <label><%= MessageUtil.getMessage(locale, "label.solves")%></label>
            <div id="avgTiempos-container" class="w-100">
                <table id="avg-table"></table>
            </div>
        </form>
    </div>
</div>

<div id="session-info-modal" class="modal">
    <div id="session-info-modal-content" class="modal-content">
        <h2><%= MessageUtil.getMessage(locale, "title.informacion_sesion")%></h2>
        <form id="sessionInfo-form" class="verticalModalForm" onsubmit="event.preventDefault();actualizarSesion(document.getElementById('info-name').value);">
            <div id="session-info-data">
                <div class="form-group">
                    <label for="info-name"><%= MessageUtil.getMessage(locale, "label.nombre")%></label>
                    <input type="text" class="form-control" name="info-name" id="info-name">
                </div>
                <div class="form-group">
                    <label for="info-total"><%= MessageUtil.getMessage(locale, "label.total")%></label>
                    <input type="text" class="form-control" id="info-total" disabled>
                </div>
                <div class="form-group">
                    <label for="info-best"><%= MessageUtil.getMessage(locale, "label.mejor")%></label>
                    <input type="email" class="form-control" id="info-best" disabled>
                </div>
                <div class="form-group">
                    <label for="info-worst"><%= MessageUtil.getMessage(locale, "label.peor")%></label>
                    <input type="tel" class="form-control" id="info-worst" disabled>
                </div>
                <div class="form-group">
                    <label for="info-avg"><%= MessageUtil.getMessage(locale, "label.media")%></label>
                    <input type="tel" class="form-control" id="info-avg" disabled>
                </div>
                <div class="form-group">
                    <label for="info-desv"><%= MessageUtil.getMessage(locale, "label.desviacion")%></label>
                    <input type="tel" class="form-control" id="info-desv" disabled>
                </div>
                <div class="form-group">
                    <label for="info-ao5"><%= MessageUtil.getMessage(locale, "label.ao5_actual")%></label>
                    <input type="tel" class="form-control" id="info-ao5" disabled>
                </div>
                <div class="form-group">
                    <label for="info-ao12"><%= MessageUtil.getMessage(locale, "label.ao12_actual")%></label>
                    <input type="tel" class="form-control" id="info-ao12" disabled>
                </div>
                <div class="form-group">
                    <label for="info-ao100"><%= MessageUtil.getMessage(locale, "label.ao100_actual")%></label>
                    <input type="tel" class="form-control" id="info-ao100" disabled>
                </div>
                <div class="form-group">
                    <label for="info-bestao5"><%= MessageUtil.getMessage(locale, "label.mejor_ao5")%></label>
                    <input type="tel" class="form-control" id="info-bestao5" disabled>
                </div>
                <div class="form-group">
                    <label for="info-bestao12"><%= MessageUtil.getMessage(locale, "label.mejor_ao12")%></label>
                    <input type="tel" class="form-control" id="info-bestao12" disabled>
                </div>
                <div class="form-group">
                    <label for="info-bestao100"><%= MessageUtil.getMessage(locale, "label.mejor_ao100")%></label>
                    <input type="tel" class="form-control" id="info-bestao100" disabled>
                </div>
            </div>
            <button type="submit" class="btn btn-guardar"><%= MessageUtil.getMessage(locale, "forms.aceptar")%></button>
            <button type="submit" class="btn btn-danger ml-1" onclick="event.preventDefault();confirmDelete(document.getElementById('sesion_select').value);"><%= MessageUtil.getMessage(locale, "forms.delete")%></button>
            <span id="session-info-modal-error" style="color:#B00C0C; display:none; margin-top:2%;"><%= MessageUtil.getMessage(locale, "error.actualizar_sesion")%></span>
        </form>
    </div>
</div>

<div id="preview-modal" class=modal>
    <jsp:include page="modalPreview.jsp" />
</div>

<div id="importModal" class="modal">
    <div id="importModal-content" class="modal-content modal-30">
        <h2><%= MessageUtil.getMessage(locale, "title.importar_tiempos")%></h2>
        <form id="importForm" class="modalForm" enctype="multipart/form-data">
            <input type="hidden" name="sesion" id="importSesion" />
            <input type="file" id="importFile" name="importFile" required />
            <button type="button" onclick="importSolves()" class="btn btn-guardar mt-3"><%= MessageUtil.getMessage(locale, "forms.importar")%></button>
            <span id="import-modal-error" class="mt-2" style="color:#B00C0C; display:none;"><%= MessageUtil.getMessage(locale, "error.importar_tiempos")%></span>
        </form>
    </div>
</div>

<div id="configModal" class="modal">
    <div id="configModal-content" class="modal-content modal-30">
        <h2><%= MessageUtil.getMessage(locale, "option.configuracion")%></h2>
        <form id="configForm" class="configForm" action="conf/save" method="post">
            <div class="form-group">
                <label class="config-label" for="config-theme"><%= MessageUtil.getMessage(locale, "label.tema")%></label>
                <select class="form-control config-select" name="config-theme" id="config-theme">
                    <option value="1" <% out.print((conf.getTema() == 1) ? "selected" : "");%>><%= MessageUtil.getMessage(locale, "label.tema1")%></option>
                    <option value="2" <% out.print((conf.getTema() == 2) ? "selected" : "");%>><%= MessageUtil.getMessage(locale, "label.tema2")%></option>
                </select>
            </div>
            <div class="form-group">
                <label class="config-label" for="config-lang"><%= MessageUtil.getMessage(locale, "label.idioma")%></label>
                <select class="form-control config-select" name="config-lang" id="config-lang">
                    <option value="1" <% out.print((conf.getIdioma() == 1) ? "selected" : "");%>><%= MessageUtil.getMessage(locale, "label.ingles")%></option>
                    <option value="2" <% out.print((conf.getIdioma() == 2) ? "selected" : "");%>><%= MessageUtil.getMessage(locale, "label.espanol")%></option>
                    <option value="3" <% out.print((conf.getIdioma() == 3) ? "selected" : "");%>><%= MessageUtil.getMessage(locale, "label.frances")%></option>
                    <option value="4" <% out.print((conf.getIdioma() == 4) ? "selected" : "");%>><%= MessageUtil.getMessage(locale, "label.aleman")%></option>
                    <option value="5" <% out.print((conf.getIdioma() == 5) ? "selected" : "");%>><%= MessageUtil.getMessage(locale, "label.italiano")%></option>
                    <option value="6" <% out.print((conf.getIdioma() == 6) ? "selected" : "");%>><%= MessageUtil.getMessage(locale, "label.portugues")%></option>
                    <option value="7" <% out.print((conf.getIdioma() == 7) ? "selected" : "");%>><%= MessageUtil.getMessage(locale, "label.chino")%></option>
                    <option value="8" <% out.print((conf.getIdioma() == 8) ? "selected" : "");%>><%= MessageUtil.getMessage(locale, "label.arabe")%></option>
                    <option value="9" <% out.print((conf.getIdioma() == 9) ? "selected" : "");%>><%= MessageUtil.getMessage(locale, "label.ruso")%></option>
                    <option value="10" <% out.print((conf.getIdioma() == 10) ? "selected" : "");%>><%= MessageUtil.getMessage(locale, "label.japones")%></option>
                    <option value="11" <% out.print((conf.getIdioma() == 11) ? "selected" : "");%>><%= MessageUtil.getMessage(locale, "label.coreano")%></option>
                </select>
            </div>
            <div class="form-group">
                <input class="config-check" type="checkbox" name="config-hide-elements" id="config-hide-elements" <% out.print((conf.getOcultarElementos() == 1) ? "checked" : "");%>/>
                <label class="check-label" for="config-hide-elements"><%= MessageUtil.getMessage(locale, "label.ocultar_elementos")%></label>
            </div>
            <div class="form-group">
                <input class="config-check" type="checkbox" name="config-hide-preview" id="config-hide-preview" <% out.print((conf.getOcultarVisualizacion() == 1) ? "checked" : "");%>/>
                <label class="check-label" for="config-hide-preview"><%= MessageUtil.getMessage(locale, "label.ocultar_visualizacion")%></label>
            </div>
            <div class="form-group">
                <input class="config-check" type="checkbox" name="config-long-pulse" id="config-long-pulse" <% out.print((conf.getPulsacionLarga() == 1) ? "checked" : "");%>/>
                <label class="check-label" for="config-long-pulse"><%= MessageUtil.getMessage(locale, "label.pulsacion_larga")%></label>
            </div>
            <div class="form-group">
                <input class="config-check" type="checkbox" name="config-mouse-timer" id="config-mouse-timer" <% out.print((conf.getCronometroRaton() == 1) ? "checked" : "");%>/>
                <label class="check-label" for="config-mouse-timer"><%= MessageUtil.getMessage(locale, "label.cronometro_raton")%></label>
            </div>
            <div class="form-group">
                <input class="config-check" type="checkbox" name="config-inspect-time" id="config-inspect-time" onchange="checkInspectTime(this)" <% out.print((conf.getTiempoInspeccion() == 1) ? "checked" : "");%>/>
                <label class="check-label" for="config-inspect-time"><%= MessageUtil.getMessage(locale, "label.tiempo_inspeccion")%></label>
            </div>
            <div class="form-group">
                <label class="config-label-large" for="config-inspect-sec"><%= MessageUtil.getMessage(locale, "label.segundos_inspeccion")%></label>
                <input type="number" class="config-number" name="config-inspect-sec" id="config-inspect-sec"
                <% if (conf.getTiempoInspeccion() == 0) { %>disabled<% } else { %>value="<%= conf.getSegundosInspeccion() %>"<% } %>>
            </div>
            <div class="config-buttons">
                <button type="submit" class="btn btn-guardar config-btn mt-3"><%= MessageUtil.getMessage(locale, "forms.aceptar")%></button>
                <button type="button" class="btn btn-guardar config-btn mt-3" onclick="restablecerConfiguracion(<%=idiomaNavegador%>);"><%= MessageUtil.getMessage(locale, "forms.restablecer")%></button>
                <button type="button" class="btn btn-guardar config-btn mt-3"><%= MessageUtil.getMessage(locale, "forms.cancelar")%></button>
            </div>

            <span id="config-modal-error" class="mt-2" style="color:#B00C0C; display:none;"><%= MessageUtil.getMessage(locale, "error.configuracion")%></span>
        </form>
    </div>
</div>
<%--FIN MODALES --%>