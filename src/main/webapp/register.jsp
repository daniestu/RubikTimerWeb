<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="utils.MessageUtil" %>
<%@ page import="java.util.Locale" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    Locale locale = (Locale) request.getAttribute("locale");
%>

<!DOCTYPE html>
<html data-bs-theme="dark">
    <head>
        <jsp:include page="head.jsp" />
        <script src="../js/login.js"></script>
        <link rel="stylesheet" type="text/css" href="../css/loginStyles.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono:wght@400;500&family=Space+Grotesk:wght@500;600;700&display=swap" rel="stylesheet">
    </head>
    <body class="auth-body">
        <div class="d-flex flex-column flex-lg-row min-vh-100">
            <div class="auth-form-side d-flex align-items-center justify-content-center p-4 p-lg-5">
                <div class="auth-form-card">
                    <div class="auth-brand">
                        <img src="../images/logo.png" alt="">
                        <span>DTimer</span>
                    </div>

                    <h1 class="auth-title"><%= MessageUtil.getMessage(locale, "title.registro_usuario")%></h1>

                    <form action="register" method="post">
                        <div class="form-floating mb-3">
                            <input type="email" class="form-control" name="correo" id="correo" placeholder="<%= MessageUtil.getMessage(locale, "label.correo")%>" required>
                            <label for="correo"><%= MessageUtil.getMessage(locale, "label.correo")%></label>
                        </div>
                        <div class="form-floating mb-3">
                            <input type="text" class="form-control" name="username" id="username" placeholder="<%= MessageUtil.getMessage(locale, "label.username_title")%>" required>
                            <label for="username"><%= MessageUtil.getMessage(locale, "label.usuario")%></label>
                        </div>
                        <div class="form-floating mb-3">
                            <input type="password" class="form-control" name="password" id="password" placeholder="<%= MessageUtil.getMessage(locale, "label.contrasena")%>" required>
                            <label for="password"><%= MessageUtil.getMessage(locale, "label.contrasena")%></label>
                        </div>
                        <div class="form-floating mb-3">
                            <input type="password" class="form-control" name="confirm-password" id="confirm-password" placeholder="<%= MessageUtil.getMessage(locale, "label.confirm_contrasena")%>" required>
                            <label for="confirm-password"><%= MessageUtil.getMessage(locale, "label.confirm_contrasena")%></label>
                        </div>

                        <div class="d-grid mb-2">
                            <button class="btn auth-btn btn-lg" type="submit"><%= MessageUtil.getMessage(locale, "label.registrarse") %></button>
                        </div>
                        <div class="d-grid mb-3">
                            <a href="/" class="btn auth-btn-ghost btn-lg"><%= MessageUtil.getMessage(locale, "label.continuar_invitado")%></a>
                        </div>

                        <p class="auth-foot mb-0"><%= MessageUtil.getMessage(locale, "label.ya_tienes_cuenta")%> <a href="login" class="auth-link"><%= MessageUtil.getMessage(locale, "label.inicia_sesion_aqui")%></a></p>
                        <c:if test="${not empty error}">
                            <div class="error">${error}</div>
                        </c:if>
                    </form>
                </div>
            </div>

            <div class="auth-brand-side d-flex align-items-center p-4 p-lg-5">
                <div class="auth-scramble" id="auth-scramble">R U R' U' R' F R2 U' R' U' R U R' F'</div>
            </div>
        </div>

        <script>
            fetch('../cube/generateScramble')
                .then(function (response) { return response.text(); })
                .then(function (scramble) {
                    document.getElementById('auth-scramble').textContent = scramble;
                })
                .catch(function () { });
        </script>
    </body>
</html>