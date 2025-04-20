<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="utils.MessageUtil" %>
<%@ page import="java.util.Locale" %>

<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="head.jsp" />
    <script src="../js/login.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/loginStyles.css">
  </head>
  <body>
    <div class="login-container">
      <h1><%= MessageUtil.getMessage(new Locale("es", "ES"), "title.inicio_sesion")%></h1>
      <form action="login" method="post" autocomplete="on">
        <label for="username"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.usuario")%>:</label>
        <input type="text" name="username" id="username" title="<%= MessageUtil.getMessage(new Locale("es", "ES"), "label.username_title")%>" autocomplete="username" required>
        <label for="password"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.contrasena")%>:</label>
        <input type="password" name="password" id="password" autocomplete="current-password" required>
        <input type="submit" value="<%= MessageUtil.getMessage(new Locale("es", "ES"), "label.iniciar_sesion")%>">
        <span style="color:#B00C0C">
			<%
				if(request.getAttribute("error") != null){
					out.print(request.getAttribute("error"));
				}
			%>
		</span>
      </form>
      <p><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.no_tienes_cuenta")%> <a href="register"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.registrate_aqui")%></a></p>
      <p><a href="forgotPassword"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.olvidaste_contrasena")%></a></p>
    </div>
    <script>
    	autocompletarLogin();
    </script>
  </body>
</html>