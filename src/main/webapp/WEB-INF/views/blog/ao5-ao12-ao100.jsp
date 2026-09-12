<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Conf" %>
<%
    Conf conf = (Conf) request.getAttribute("conf");
    request.setAttribute("tituloClave", "seo.titulo_blog_ao5");
    request.setAttribute("descripcionClave", "seo.descripcion_blog_ao5");
    request.setAttribute("urlCanonica", "https://www.dtimerapp.com/blog/que-es-ao5-ao12-ao100");
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
    <a href="/blog" class="blog-back">← Volver al blog</a>
    <article class="blog-article">
        <h1>Qué es el Ao5, el Ao12 y el Ao100 (y cómo se calculan)</h1>
        <p class="blog-intro">Si empiezas a cronometrar tus resoluciones del cubo de Rubik, pronto vas a ver estas siglas por todas partes. No son medias normales — se calculan de una forma concreta, y entenderla te ayuda a leer tu propio progreso mejor que mirando solo tu mejor tiempo.</p>

        <h2>No es una media simple</h2>
        <p>Si sumaras tus últimos 5 tiempos y dividieras entre 5, un solo resultado muy malo (te distraes, se te cae el cubo, un DNF) te destrozaría la media entera, aunque el resto de resoluciones hayan sido buenas. Por eso la comunidad usa un cálculo distinto, el mismo que se usa en las competiciones oficiales: <strong>se descartan el mejor y el peor tiempo del grupo</strong>, y se hace la media solo con los que quedan en medio.</p>

        <h2>Cómo se calcula un Ao5, paso a paso</h2>
        <p>Imagina estos 5 tiempos, en el orden en que los resolviste:</p>
        <div class="blog-example">
            12.34 &nbsp;·&nbsp; 11.98 &nbsp;·&nbsp; 13.50 &nbsp;·&nbsp; 10.87 &nbsp;·&nbsp; 12.10
        </div>
        <p>Ordenados de mejor a peor: 10.87 (mejor) · 11.98 · 12.10 · 12.34 · 13.50 (peor).</p>
        <p>Se descartan el mejor (10.87) y el peor (13.50), y se hace la media de los tres que quedan:</p>
        <div class="blog-example">
            (11.98 + 12.10 + 12.34) / 3 = 12.14
        </div>
        <p>Ese <strong>12.14</strong> es tu Ao5 — no el promedio de los 5 tiempos, sino de los 3 centrales.</p>

        <h2>Ao12 y Ao100: la misma idea, a mayor escala</h2>
        <p>El Ao12 hace exactamente lo mismo con tus últimos 12 tiempos (se descartan el mejor y el peor, media de los 10 restantes), y el Ao100 con los últimos 100. Cuantos más tiempos entran en el cálculo, menos afecta un resultado suelto — por eso el Ao100 es el que mejor refleja tu nivel real, mientras que el Ao5 varía mucho de una tanda a otra.</p>

        <h2>Tu media actual vs. tu mejor media histórica</h2>
        <p>Una cosa que suele confundir al principio: tu <strong>Ao5 actual</strong> (el de tus 5 solves más recientes) no es lo mismo que tu <strong>mejor Ao5 de toda la sesión</strong>. El segundo es el mejor grupo de 5 consecutivos que has conseguido en cualquier momento de la sesión, aunque ahora mismo no estés en racha. En DTimer puedes ver los dos por separado, precisamente para distinguir "cómo voy ahora mismo" de "cuál es mi techo real".</p>

        <h2>¿Y si hay un DNF de por medio?</h2>
        <p>Un DNF (Did Not Finish, una resolución no válida) cuenta como el peor resultado posible del grupo — automáticamente ocupa el lugar del "peor" y se descarta igual que un tiempo malo. Pero si tienes <strong>dos o más DNF</strong> en el mismo grupo de 5 (o 12, o 100), la media entera se considera DNF — no hay forma de calcular una media fiable con tantos intentos fallidos.</p>

        <div class="blog-cta">
            <p>La forma más rápida de entender esto de verdad es viéndolo con tus propios tiempos.</p>
            <a href="/timer" class="btn landing-btn landing-btn-lg">Probar el cronómetro gratis</a>
        </div>
    </article>
</div>
</body>
</html>