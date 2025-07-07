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
  	<%
  		String token = (String) request.getAttribute("token");
  		boolean caducado = (boolean) request.getAttribute("caducado");
  	%>
    <div class="login-container">
      <%if (!caducado) { %>
      <h1><%= MessageUtil.getMessage(new Locale("es", "ES"), "title.restablecimiento_contrasena")%></h1>
      <form action="resetPassword" method="post">
      	<input type="hidden" name="token" value="<%= token %>"/>
        <label for="password"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.nueva_contrasena")%>:</label>
        <input type="password" name="password" required>
        <label for="confirm-password"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.confirm_contrasena")%>:</label>
        <input type="password" name="confirm-password" required>
        <input type="submit" value="<%= MessageUtil.getMessage(new Locale("es", "ES"), "label.restablecer_contrasena")%>">
        <span style="color:#B00C0C">
			<%
				if(request.getAttribute("error") != null){
					out.print(request.getAttribute("error"));
				}
			%>
		</span>
      </form>
      <%}else { %>
      <h1><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.enlace_caducado")%></h1>
      <%}%>
      <p><a href="login"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.volver_formulario_login")%></a></p>
      <% if (request.getAttribute("confirmation") != null) { %>
      <p id="confirmation"><%= MessageUtil.getMessage(new Locale("es", "ES"), "confirm.contrasena_restablecida")%></p>
      <%} %>
    </div>
  </body>
</html>