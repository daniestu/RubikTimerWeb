<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="head.jsp" />
    <script src="../js/login.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/loginStyles.css">
  </head>
  <body>
    <div class="login-container">
      <h1>Inicio de sesi�n</h1>
      <form action="login" method="post" autocomplete="on">
        <label for="username">Usuario:</label>
        <input type="text" name="username" id="username" title="Nombre de usuario o direcci�n de correo electr�nico" autocomplete="username" required>
        <label for="password">Contrase�a:</label>
        <input type="password" name="password" id="password" autocomplete="current-password" required>
        <input type="submit" value="Iniciar sesi�n">
        <span style="color:red">
			<% 
				if(request.getAttribute("error") != null){
					out.print(request.getAttribute("error"));  
				}
			%>
		</span>
      </form>
      <p>�No tienes una cuenta? <a href="register">Reg�strate aqu�</a></p>
      <p><a href="forgotPassword">�Olvidaste tu contrase�a?</a></p>
    </div>
    <script>
    	autocompletarLogin();
    </script>
  </body>
</html>