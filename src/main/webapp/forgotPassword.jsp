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
							<c:choose>
                                <c:when test="${not empty confirmation}">
                                    <div class="text-success d-flex text-center m-auto"><p><%= MessageUtil.getMessage(new Locale("es", "ES"), "confirmation.olvido_contrasena")%></p></div>
                                </c:when>
                                <c:otherwise>
                                    <form action="forgotPassword" method="post">
                                        <div class="row gy-2 overflow-hidden">
                                            <div class="col-12">
                                                <div class="form-floating mb-3">
                                                    <input type="email" class="form-control" name="correo" id="correo" placeholder="<%= MessageUtil.getMessage(new Locale("es", "ES"), "label.correo")%>" autocomplete="username" required>
                                                    <label for="correo" class="form-label"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.correo")%></label>
                                                </div>
                                            </div>
                                            <div class="col-12">
                                                <div class="d-grid my-3">
                                                    <button class="btn btn-success btn-lg" type="submit"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.enviar_solicitud")%></button>
                                                </div>
                                                <c:if test="${not empty error}">
                                                    <div class="error w-100 text-center m-auto">${error}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                            <div class="mt-2 m-auto back-link">
                                <a href="login" class="link-success text-underline"><%= MessageUtil.getMessage(new Locale("es", "ES"), "label.volver_login")%></a>
                            </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>