<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="utils.MessageUtil" %>
<%@ page import="java.util.Locale" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="head.jsp" />
        <script src="../js/login.js"></script>
        <link rel="stylesheet" type="text/css" href="../css/loginStyles.css">
    </head>
    <body>
        <section class="py-3 py-md-5">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-5 col-xxl-4">
                        <div class="card border border-light-subtle rounded-3 shadow">
                            <div class="card-body p-3 p-md-4 p-xl-5">
                                <div class="text-center mb-3">
                                    <img src="../images/logo-2.png" alt="DER Timer logo" width="99.75" height="57">
                                </div>
                                <h2 class="fs-6 fw-normal text-center text-secondary mb-4"><%= MessageUtil.getMessage(new Locale("es", "ES"), "title.inicio_sesion")%></h2>
                                <form action="login" method="post">
                                    <div class="row gy-2 overflow-hidden">
                                        <div class="col-12">
                                            <div class="form-floating mb-3">
                                                <input type="text" class="form-control" name="username" id="username" placeholder="<%= MessageUtil.getMessage(new Locale("es", "ES"), "label.username_title")%>" autocomplete="username" required>
                                                <label for="username" class="form-label"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.usuario")%></label>
                                            </div>
                                        </div>
                                        <div class="col-12">
                                            <div class="form-floating mb-3">
                                                <input type="password" class="form-control" name="password" id="password" placeholder="<%= MessageUtil.getMessage(new Locale("es", "ES"), "label.contrasena")%>" autocomplete="current-password" required>
                                                <label for="password" class="form-label"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.contrasena")%></label>
                                            </div>
                                        </div>
                                        <div class="col-12">
                                            <div class="d-flex gap-2 justify-content-between">
                                                <div class="form-check">
                                                    <input class="form-check-input" type="checkbox" name="rememberMe" id="rememberMe">
                                                    <label class="form-check-label text-secondary" for="rememberMe"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.rememberme") %></label>
                                                </div>
                                                <a href="forgotPassword" class="link-success text-decoration-none"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.olvidaste_contrasena")%></a>
                                            </div>
                                        </div>
                                        <div class="col-12">
                                            <div class="d-grid my-3">
                                                <button class="btn btn-success btn-lg" type="submit"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.iniciar_sesion") %></button>
                                            </div>
                                            <p><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.no_tienes_cuenta")%> <a href="register" class="link-success"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.registrate_aqui")%></a></p>
                                            <c:if test="${not empty error}">
                                                <div class="error m-auto">${error}</div>
                                            </c:if>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <script>
            autocompletarLogin();
        </script>
    </body>
</html>