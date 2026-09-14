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
        <jsp:include page="/WEB-INF/views/common/head.jsp" />
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
                        <img src="/images/logo.png" alt="">
                        <span>DTimer</span>
                    </div>

                    <h1 class="auth-title"><%= MessageUtil.getMessage(locale, "title.restablecimiento_contrasena") %></h1>

                    <c:choose>
                        <c:when test="${not empty confirmation}">
                            <div class="status-message success"><%= MessageUtil.getMessage(locale, "confirm.contrasena_restablecida")%></div>
                        </c:when>
                        <c:when test="${caducado}">
                            <div class="status-message warning"><%= MessageUtil.getMessage(locale, "label.enlace_caducado")%></div>
                        </c:when>
                        <c:otherwise>
                            <form action="resetPassword" method="post">
                                <input type="hidden" name="token" value="${token}">
                                <div class="form-floating mb-3">
                                    <input type="password" class="form-control" name="password" id="password" placeholder="<%= MessageUtil.getMessage(locale, "label.nueva_contrasena")%>" required>
                                    <label for="password"><%= MessageUtil.getMessage(locale, "label.nueva_contrasena")%></label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="password" class="form-control" name="confirm-password" id="confirm-password" placeholder="<%= MessageUtil.getMessage(locale, "label.confirm_contrasena")%>" required>
                                    <label for="confirm-password"><%= MessageUtil.getMessage(locale, "label.confirm_contrasena")%></label>
                                </div>
                                <div class="d-grid mb-3">
                                    <button class="btn auth-btn btn-lg" type="submit"><%= MessageUtil.getMessage(locale, "label.restablecer_contrasena")%></button>
                                </div>
                                <c:if test="${not empty error}">
                                    <div class="error">${error}</div>
                                </c:if>
                            </form>
                        </c:otherwise>
                    </c:choose>

                    <p class="auth-foot mt-3 mb-0 text-center">
                        <a href="login" class="auth-link"><%= MessageUtil.getMessage(locale, "label.volver_formulario_login")%></a>
                    </p>
                </div>
            </div>

            <div class="auth-brand-side d-flex align-items-center p-4 p-lg-5">
                <div class="auth-scramble" id="auth-scramble">R U R' U' R' F R2 U' R' U' R U R' F'</div>
            </div>
        </div>

        <script>
            fetch('/cube/generateScramble')
                .then(function (response) { return response.text(); })
                .then(function (scramble) {
                    document.getElementById('auth-scramble').textContent = scramble;
                })
                .catch(function () { });
        </script>
    </body>
</html>