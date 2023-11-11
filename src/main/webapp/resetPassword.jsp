<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="head.jsp" />
    <link rel="stylesheet" type="text/css" href="../css/loginStyles.css">
  </head>
  <body>
  	<%
  		String token = (String) request.getAttribute("token");
  		boolean caducado = (boolean) request.getAttribute("caducado");
  	%>
    <div class="login-container">
      <%if (!caducado) { %>
      <h1>Restablecimiento de contraseña</h1>
      <form action="resetPassword" method="post">
      	<input type="hidden" name="token" value="<%= token %>"/>
        <label for="password">Nueva contraseña:</label>
        <input type="password" name="password" required>
        <label for="confirm-password">Confirmar contraseña:</label>
        <input type="password" name="confirm-password" required>
        <input type="submit" value="Restablecer contraseña">
        <span style="color:#B00C0C">
			<% 
				if(request.getAttribute("error") != null){
					out.print(request.getAttribute("error"));  
				}
			%>
		</span>
      </form>
      <%}else { %>
      <h1>El enlace ha caducado</h1>
      <%}%>
      <p><a href="login">Volver al formulario de inicio de sesión</a></p>
      <% if (request.getAttribute("confirmation") != null) { %>
      <p id="confirmation">Se ha restablecido la contraseña.</p>
      <%} %>
    </div>
  </body>
</html>