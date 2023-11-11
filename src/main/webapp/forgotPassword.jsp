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
      <h1>Olvido de contrase�a</h1>
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
      <p><a href="login">Volver al inicio de sesi�n</a></p>
	  <% if (request.getAttribute("confirmation") != null) { %>
      <p id="confirmation">Se ha enviado un correo electr�nico con instrucciones para restablecer tu contrase�a.</p>
      <%} %>
    </div>
  </body>
</html>