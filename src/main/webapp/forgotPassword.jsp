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
      <h1>Olvido de contraseña</h1>
      <form action="forgotPassword" method="post">
        <label for="correo">Correo:</label>
        <input type="email" name="correo" required>
        <input type="submit" value="Enviar solicitud">
        <span style="color:#B00C0C">
			<% 
				if(request.getAttribute("error") != null){
					out.print(request.getAttribute("error"));  
				}
			%>
		</span>
      </form>
      <p><a href="login">Volver al inicio de sesión</a></p>
	  <% if (request.getAttribute("confirmation") != null) { %>
      <p id="confirmation">Se ha enviado un correo electrónico con instrucciones para restablecer tu contraseña.</p>
      <%} %>
    </div>
  </body>
</html>