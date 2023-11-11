<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="head.jsp" />
    <link rel="stylesheet" type="text/css" href="../css/loginStyles.css">
  </head>
  <body>
    <div class="login-container">
      <h1>Registro de usuario</h1>
      <form action="register" method="post">
      	<label for="correo">Correo:</label>
        <input type="email" name="correo" id="correo" required>
        <label for="username">Usuario:</label>
        <input type="text" name="username" id="username" required>
        <label for="password">Contraseña:</label>
        <input type="password" name="password" id="password" required>
        <label for="confirm-password">Confirmar contraseña:</label>
        <input type="password" name="confirm-password" id="confirm-password" required>
        <input type="submit" value="Registrarse">
        <span style="color:#B00C0C">
			<% 
				if(request.getAttribute("error") != null){
					out.print(request.getAttribute("error"));  
				}
			%>
		</span>
      </form>
      <p>¿Ya tienes una cuenta? <a href="login">Inicia sesión aquí</a></p>
    </div>
  </body>
</html>