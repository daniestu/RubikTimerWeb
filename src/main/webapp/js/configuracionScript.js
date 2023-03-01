$(document).ready(function() {
    var configContainer = $("#config-container");
    var configBtn = $("#config-btn");
    var nuevaSesionModal = $("#nuevaSesion-modal-content");
    var borrarSesionModal = $("#borrarSesion-modal-content");
    var solveModal = $("#solveModal-content");
    var sesionSelect = $("#sesion_select");

    function toggleConfigContainer() {
        configContainer.toggle();
        if (configContainer.is(":visible")) {
            configBtn.css("background-color", "#4A6572");
        } else {
            configBtn.css("background-color", "#1c2833");
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
				configBtn.css("background-color", "#1c2833");
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
		if (!solveModal.is(event.target) && solveModal.has(event.target).length === 0) {
				$("#solveModal").hide();
		}
	});
	
	function logout() {
		fetch('LogoutServlet')
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
	  event.preventDefault();
	  logout();
	  
	});

});

