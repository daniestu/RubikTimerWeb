$(document).ready(function() {
    var configContainer = $("#config-container");
    var configBtn = $("#config-btn");
    var nuevaSesionModal = $("#nuevaSesion-modal-content");
    var borrarSesionModal = $("#borrarSesion-modal-content");
    var solveModal = $("#solveModal-content");
    var avgModal = $("#avgModal-content");
    var avgTable = $(".average");
    var solveTd = $(".solve");
    var tablaTiempos = $("#tablaTiempos");
    var scramblePersonalizadoModal = $("#scramble-personalizado-modal-content");
    var addSolve = $("#add-solve-modal-content");
    var sesionSelect = $("#sesion_select");
    var sessionInfo = $("#session-info-modal-content");
    var mas2Btn = $("#solveBtn-mas2");
    var dnfBtn = $("#solveBtn-dnf");
	var previewModal = $("#preview-modal-content");
	var previewMain = $("#preview-container");
	var importModal = $("#importModal-content");

    function toggleConfigContainer() {
        configContainer.toggle();
        if (configContainer.is(":visible")) {
            configBtn.css("background-color", "#4A6572");
        } else {
            configBtn.removeAttr("style");
        }
    }

    configBtn.click(function() {
        toggleConfigContainer();
    });

	$(document).click(function(event) {
		if (!configContainer.is(event.target) && configContainer.has(event.target).length === 0 
			&& !configBtn.is(event.target) && configBtn.has(event.target).length === 0 
			&& !configBtn.find('img').is(event.target) && configBtn.find('img').has(event.target).length === 0) {
				configContainer.hide();
				configBtn.removeAttr("style");
		}
		if (!nuevaSesionModal.is(event.target) && nuevaSesionModal.has(event.target).length === 0
			&& !sesionSelect.is(event.target) && sesionSelect.has(event.target).length === 0) {
				$("#nuevaSesion-modal").hide();
				$("#nombre_sesion").val("");
		}
		if (!borrarSesionModal.is(event.target) && borrarSesionModal.has(event.target).length === 0
			&& !sesionSelect.is(event.target) && sesionSelect.has(event.target).length === 0) {
				$("#borrarSesion-modal").hide();
		}
		if (!solveModal.is(event.target) && solveModal.has(event.target).length === 0
			&& !solveTd.is(event.target) && solveTd.has(event.target).length === 0
			&& !tablaTiempos.is(event.target) && tablaTiempos.has(event.target).length === 0
			&& !mas2Btn.is(event.target) && mas2Btn.has(event.target).length === 0
			&& !dnfBtn.is(event.target) && dnfBtn.has(event.target).length === 0) {
				$("#solveModal").hide();
		}
		if (!avgModal.is(event.target) && avgModal.has(event.target).length === 0
			&& !avgTable.is(event.target) && avgTable.has(event.target).length === 0) {
				$("#avgModal").hide();
		}
		if (!scramblePersonalizadoModal.is(event.target) && scramblePersonalizadoModal.has(event.target).length === 0
			&& !configContainer.is(event.target) && configContainer.has(event.target).length === 0) {
				$("#scramble-personalizado-modal").hide();
		}
		if (!addSolve.is(event.target) && addSolve.has(event.target).length === 0
			&& !configContainer.is(event.target) && configContainer.has(event.target).length === 0) {
				$("#add-solve-modal").hide();
		}
		if (!sessionInfo.is(event.target) && sessionInfo.has(event.target).length === 0
			&& !configContainer.is(event.target) && configContainer.has(event.target).length === 0) {
				$("#session-info-modal").hide();
		}
		if (!previewModal.is(event.target) && previewModal.has(event.target).length === 0
			&& !previewMain.is(event.target) && previewMain.has(event.target).length === 0) {
				$("#preview-modal").hide();
		}
		if (!importModal.is(event.target) && importModal.has(event.target).length === 0
			&& !configContainer.is(event.target) && configContainer.has(event.target).length === 0) {
				$("#importModal").hide();
		}
	});
	
	function logout() {
		fetch('user/logout')
			.then(response => {
				if (response.redirected) {
					window.location.href = response.url;
				} else {
					
				}
			})
			.catch(error => {
				console.error('Error:', error);
			});
	}
	
	$("#logout").click(function(event) {
	  	logout();
	});
	
	$("#next-scramble").click(function(event) {
	  	generateScramble();
	});
	
	$("#previus-scramble").click(function(event) {
	  	scrambleAnterior();
	});
	
	$("#custom-scramble").click(function(event) {
		$("#scramble-text").val("");
		document.getElementById("scramble-personalizado-modal-error").style.display = "none";
		document.getElementById("scramble-personalizado-modal").style.display = "flex";
		toggleConfigContainer();
	});
	
	$("#add-solve").click(function(event) {
		$('#addSolve-form').find(':input').each(function() {
			$(this).val('');
		});
		document.getElementById("add-solve-modal-error").style.display = "none";
		document.getElementById("add-solve-modal").style.display = "flex";
		toggleConfigContainer();
	});
	
	$("#session-info").click(function(event) {
		document.getElementById("session-info-modal-error").style.display = "none";
		document.getElementById("info-name").value = document.getElementById("sesion_select").value;
		document.getElementById("session-info-modal").style.display = "flex";
		toggleConfigContainer();
	});
	
	
	
	previewMain.click(function(event) {
		document.getElementById("preview-modal").style.display = "flex";
	});
	
	$("#export-solves").click(function(event) {
		if ($('#export-solves').hasClass('list-item')) {
            exportSolves(document.getElementById("sesion_select").value);
        }
	});
	
	$("#import-solves").click(function(event) {
		document.getElementById("importSesion").value = document.getElementById("sesion_select").value;
		document.getElementById("importFile").value = "";
		document.getElementById("import-modal-error").style.display = "none";
		document.getElementById("importModal").style.display = "flex";
		toggleConfigContainer();
	});
	
});