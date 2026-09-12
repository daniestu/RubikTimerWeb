<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Conf" %>
<%
    Conf conf = (Conf) request.getAttribute("conf");
    request.setAttribute("tituloClave", "seo.titulo_blog_index");
    request.setAttribute("descripcionClave", "seo.descripcion_blog_index");
    request.setAttribute("urlCanonica", "https://www.dtimerapp.com/blog");
%>
<!DOCTYPE html>
<html data-tema="<%= conf.getTema() %>" data-bs-theme="<%= (conf.getTema() == 1 || conf.getTema() == 3) ? "dark" : "light" %>">
<head>
    <jsp:include page="/WEB-INF/views/common/head.jsp"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono:wght@400;500&family=Space+Grotesk:wght@500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/css/mainStyles.css">
    <link rel="stylesheet" type="text/css" href="/css/landingStyles.css">
    <link rel="stylesheet" type="text/css" href="/css/blogStyles.css">
</head>
<body class="blog-body">
<jsp:include page="blogNav.jsp"/>

<div class="blog-wrapper">
    <h1 class="blog-index-title">Blog de DTimer</h1>
    <div class="blog-index-list">
        <a href="/blog/que-es-ao5-ao12-ao100" class="blog-index-card">
            <h2>Qué es el Ao5, el Ao12 y el Ao100 (y cómo se calculan)</h2>
            <p>La diferencia entre una media simple y una media WCA, con un ejemplo numérico paso a paso.</p>
        </a>
        <a href="/blog/como-mejorar-tiempos-cubo-rubik" class="blog-index-card">
            <h2>Cómo mejorar tus tiempos resolviendo el cubo de Rubik</h2>
            <p>Por qué mirar solo el tiempo final no basta, y en qué fijarte fase a fase para bajar tiempos de verdad.</p>
        </a>
        <a href="/blog/mejores-cronometros-online-cubo-rubik" class="blog-index-card">
            <h2>Mejores cronómetros online para cubo de Rubik</h2>
            <p>Qué mirar antes de elegir uno, y cómo se comparan las opciones más usadas por la comunidad.</p>
        </a>
    </div>
</div>
</body>
</html>