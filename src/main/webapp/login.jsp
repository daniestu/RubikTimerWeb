<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="head.jsp" />
    <link rel="stylesheet" type="text/css" href="css/loginStyles.css">
  </head>
  <body>
    <div class="login-container">
      <h1>Inicio de sesión</h1>
      <form action="LoginServlet" method="post">
        <label for="username">Usuario:</label>
        <input type="text" name="username" id="username" required>
        <label for="password">Contraseña:</label>
        <input type="password" name="password" id="password" required>
        <input type="submit" value="Iniciar sesión">
        <span style="color:red">
			<% 
				if(request.getAttribute("error") != null){
					out.print(request.getAttribute("error"));  
				}
			%>
		</span>
      </form>
      <p>¿No tienes una cuenta? <a href="register.jsp">Regístrate aquí</a></p>
    </div>
  </body>
</html>