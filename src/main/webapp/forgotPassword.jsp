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
      <h1><%= MessageUtil.getMessage(new Locale("es", "ES"), "title.olvido_contrasena")%></h1>
      <form action="forgotPassword" method="post">
        <label for="correo"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.correo")%>:</label>
        <input type="email" name="correo" required>
        <input type="submit" value="<%= MessageUtil.getMessage(new Locale("es", "ES"), "label.enviar_solicitud")%>">
        <span style="color:#B00C0C">
			<%
				if(request.getAttribute("error") != null){
					out.print(request.getAttribute("error"));
				}
			%>
		</span>
      </form>
      <p><a href="login"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.volver_login")%></a></p>
	  <% if (request.getAttribute("confirmation") != null) { %>
      <p id="confirmation"><%= MessageUtil.getMessage(new Locale("es", "ES"), "confirmation.olvido_contrasena")%></p>
      <%} %>
    </div>
  </body>
</html>