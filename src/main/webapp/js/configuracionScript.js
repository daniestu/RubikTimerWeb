$(document).ready(function() {
    var configContainer = $("#config-container");
    var configBtn = $("#config-btn");

    // Muestra u oculta el contenedor y cambia el color del botón
    function toggleConfigContainer() {
        configContainer.toggle();
        if (configContainer.is(":visible")) {
            configBtn.css("background-color", "#4A6572");
        } else {
            configBtn.css("background-color", "#1c2833");
        }
    }

    // Cuando se hace clic en el botón, se muestra u oculta el contenedor
    configBtn.click(function() {
        toggleConfigContainer();
    });

    // Cuando se hace clic en el documento, si no es el contenedor ni alguno de sus hijos, se oculta el contenedor
	$(document).click(function(event) {
		if (!configContainer.is(event.target) && configContainer.has(event.target).length === 0 
			&& !configBtn.is(event.target) && configBtn.has(event.target).length === 0 
			&& !configBtn.find('img').is(event.target) && configBtn.find('img').has(event.target).length === 0) {
				configContainer.hide();
				configBtn.css("background-color", "#1c2833");
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

