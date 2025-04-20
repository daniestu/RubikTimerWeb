<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="utils.MessageUtil" %>
<%@ page import="java.util.Locale" %>

<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="head.jsp" />
    <link rel="stylesheet" type="text/css" href="../css/loginStyles.css">
  </head>
  <body>
    <div class="login-container">
      <h1><%= MessageUtil.getMessage(new Locale("es", "ES"), "title.registro_usuario")%></h1>
      <form action="register" method="post">
      	<label for="correo"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.correo")%>:</label>
        <input type="email" name="correo" id="correo" required>
        <label for="username"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.usuario")%>:</label>
        <input type="text" name="username" id="username" required>
        <label for="password"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.contrasena")%>:</label>
        <input type="password" name="password" id="password" required>
        <label for="confirm-password"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.confirm_contrasena")%>:</label>
        <input type="password" name="confirm-password" id="confirm-password" required>
        <input type="submit" value="<%= MessageUtil.getMessage(new Locale("es", "ES"), "label.registrarse")%>">
        <span style="color:#B00C0C">
			<%
				if(request.getAttribute("error") != null){
					out.print(request.getAttribute("error"));
				}
			%>
		</span>
      </form>
      <p><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.ya_tienes_cuenta")%> <a href="login"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.inicia_sesion_aqui")%></a></p>
    </div>
  </body>
</html>