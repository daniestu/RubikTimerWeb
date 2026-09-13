<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="utils.MessageUtil" %>
<%@ page import="java.util.Locale" %>
<%@ page import="models.Conf" %>
<%@ page import="models.TemaConfig" %>

<%
    Conf conf = (Conf) request.getAttribute("conf");
    TemaConfig temaConfig = (TemaConfig) request.getAttribute("temaConfig");
    Locale locale = (Locale) request.getAttribute("locale");

    String codigoIdioma = (String) request.getAttribute("codigoIdioma");
    request.setAttribute("tituloClave", "seo.titulo_inicio");
    request.setAttribute("descripcionClave", "seo.descripcion_inicio");
    request.setAttribute("urlCanonica", "https://www.dtimerapp.com/" + codigoIdioma);
%>

<!DOCTYPE html>
<html data-tema="<%= conf.getTema() %>" data-bs-theme="<%= (conf.getTema() == 1 || conf.getTema() == 3) ? "dark" : "light" %>">
<head>
    <jsp:include page="/WEB-INF/views/common/head.jsp"/>
    <link rel="alternate" hreflang="en" href="https://www.dtimerapp.com/en">
    <link rel="alternate" hreflang="es" href="https://www.dtimerapp.com/es">
    <link rel="alternate" hreflang="fr" href="https://www.dtimerapp.com/fr">
    <link rel="alternate" hreflang="de" href="https://www.dtimerapp.com/de">
    <link rel="alternate" hreflang="it" href="https://www.dtimerapp.com/it">
    <link rel="alternate" hreflang="pt" href="https://www.dtimerapp.com/pt">
    <link rel="alternate" hreflang="zh" href="https://www.dtimerapp.com/zh">
    <link rel="alternate" hreflang="ar" href="https://www.dtimerapp.com/ar">
    <link rel="alternate" hreflang="ru" href="https://www.dtimerapp.com/ru">
    <link rel="alternate" hreflang="ja" href="https://www.dtimerapp.com/ja">
    <link rel="alternate" hreflang="ko" href="https://www.dtimerapp.com/ko">
    <link rel="alternate" hreflang="x-default" href="https://www.dtimerapp.com/en">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono:wght@400;500&family=Space+Grotesk:wght@500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/mainStyles.css">
    <link rel="stylesheet" type="text/css" href="css/landingStyles.css">
    <script type="application/ld+json">
        {
          "@context": "https://schema.org",
          "@type": "WebApplication",
          "name": "DTimer",
          "url": "https://www.dtimerapp.com/timer",
          "description": "<%= MessageUtil.getMessage(locale, "seo.descripcion_inicio")%>",
          "applicationCategory": "UtilitiesApplication",
          "operatingSystem": "Any (web-based)",
          "offers": { "@type": "Offer", "price": "0", "priceCurrency": "USD" }
        }
    </script>
    <script type="application/ld+json">
        {
          "@context": "https://schema.org",
          "@type": "FAQPage",
          "mainEntity": [
            { "@type": "Question", "name": "<%= MessageUtil.getMessage(locale, "landing.faq1_pregunta")%>", "acceptedAnswer": { "@type": "Answer", "text": "<%= MessageUtil.getMessage(locale, "landing.faq1_respuesta")%>" } },
            { "@type": "Question", "name": "<%= MessageUtil.getMessage(locale, "landing.faq2_pregunta")%>", "acceptedAnswer": { "@type": "Answer", "text": "<%= MessageUtil.getMessage(locale, "landing.faq2_respuesta")%>" } },
            { "@type": "Question", "name": "<%= MessageUtil.getMessage(locale, "landing.faq3_pregunta")%>", "acceptedAnswer": { "@type": "Answer", "text": "<%= MessageUtil.getMessage(locale, "landing.faq3_respuesta")%>" } },
            { "@type": "Question", "name": "<%= MessageUtil.getMessage(locale, "landing.faq4_pregunta")%>", "acceptedAnswer": { "@type": "Answer", "text": "<%= MessageUtil.getMessage(locale, "landing.faq4_respuesta")%>" } },
            { "@type": "Question", "name": "<%= MessageUtil.getMessage(locale, "landing.faq5_pregunta")%>", "acceptedAnswer": { "@type": "Answer", "text": "<%= MessageUtil.getMessage(locale, "landing.faq5_respuesta")%>" } }
          ]
        }
    </script>
</head>
<body class="landing-body">
<jsp:include page="/WEB-INF/views/common/icons.jsp" />

<nav class="landing-nav">
    <div class="landing-nav-brand">
        <img src="images/logo.png" alt="" width="32" height="32">
        <span>DER Timer</span>
    </div>
    <div class="landing-nav-links">
        <a href="user/login" class="landing-nav-link"><%= MessageUtil.getMessage(locale, "label.iniciar_sesion")%></a>
        <a href="timer" class="btn landing-btn landing-btn-sm"><%= MessageUtil.getMessage(locale, "landing.cta_principal")%></a>
    </div>
</nav>

<header class="landing-hero">
    <h1><%= MessageUtil.getMessage(locale, "landing.h1")%></h1>
    <p class="landing-subheadline"><%= MessageUtil.getMessage(locale, "landing.subheadline")%></p>
    <div class="landing-hero-actions">
        <a href="timer" class="btn landing-btn landing-btn-lg"><%= MessageUtil.getMessage(locale, "landing.cta_principal")%></a>
        <span class="landing-hero-note"><%= MessageUtil.getMessage(locale, "landing.cta_nota")%></span>
    </div>
    <div class="landing-scramble" id="landing-scramble">R U R' U' R' F R2 U' R' U' R U R' F'</div>
</header>

<section class="landing-features">
    <div class="landing-feature">
        <svg class="icon landing-feature-icon"><use href="#icon-chevron-right-double"/></svg>
        <h2><%= MessageUtil.getMessage(locale, "landing.feature1_titulo")%></h2>
        <p><%= MessageUtil.getMessage(locale, "landing.feature1_texto")%></p>
    </div>
    <div class="landing-feature">
        <svg class="icon landing-feature-icon"><use href="#icon-info"/></svg>
        <h2><%= MessageUtil.getMessage(locale, "landing.feature2_titulo")%></h2>
        <p><%= MessageUtil.getMessage(locale, "landing.feature2_texto")%></p>
    </div>
    <div class="landing-feature">
        <svg class="icon landing-feature-icon"><use href="#icon-plus"/></svg>
        <h2><%= MessageUtil.getMessage(locale, "landing.feature3_titulo")%></h2>
        <p><%= MessageUtil.getMessage(locale, "landing.feature3_texto")%></p>
    </div>
    <div class="landing-feature">
        <svg class="icon landing-feature-icon"><use href="#icon-sliders"/></svg>
        <h2><%= MessageUtil.getMessage(locale, "landing.feature4_titulo")%></h2>
        <p><%= MessageUtil.getMessage(locale, "landing.feature4_texto")%></p>
    </div>
</section>

<section class="landing-steps">
    <h2 class="landing-section-title"><%= MessageUtil.getMessage(locale, "landing.pasos_titulo")%></h2>
    <div class="landing-steps-grid">
        <div class="landing-step">
            <span class="landing-step-number">1</span>
            <h3><%= MessageUtil.getMessage(locale, "landing.paso1_titulo")%></h3>
            <p><%= MessageUtil.getMessage(locale, "landing.paso1_texto")%></p>
        </div>
        <div class="landing-step">
            <span class="landing-step-number">2</span>
            <h3><%= MessageUtil.getMessage(locale, "landing.paso2_titulo")%></h3>
            <p><%= MessageUtil.getMessage(locale, "landing.paso2_texto")%></p>
        </div>
        <div class="landing-step">
            <span class="landing-step-number">3</span>
            <h3><%= MessageUtil.getMessage(locale, "landing.paso3_titulo")%></h3>
            <p><%= MessageUtil.getMessage(locale, "landing.paso3_texto")%></p>
        </div>
    </div>
</section>

<section class="landing-faq">
    <h2 class="landing-section-title"><%= MessageUtil.getMessage(locale, "landing.faq_titulo")%></h2>
    <div class="landing-faq-list">
        <details class="landing-faq-item">
            <summary><%= MessageUtil.getMessage(locale, "landing.faq1_pregunta")%></summary>
            <p><%= MessageUtil.getMessage(locale, "landing.faq1_respuesta")%></p>
        </details>
        <details class="landing-faq-item">
            <summary><%= MessageUtil.getMessage(locale, "landing.faq2_pregunta")%></summary>
            <p><%= MessageUtil.getMessage(locale, "landing.faq2_respuesta")%></p>
        </details>
        <details class="landing-faq-item">
            <summary><%= MessageUtil.getMessage(locale, "landing.faq3_pregunta")%></summary>
            <p><%= MessageUtil.getMessage(locale, "landing.faq3_respuesta")%></p>
        </details>
        <details class="landing-faq-item">
            <summary><%= MessageUtil.getMessage(locale, "landing.faq4_pregunta")%></summary>
            <p><%= MessageUtil.getMessage(locale, "landing.faq4_respuesta")%></p>
        </details>
        <details class="landing-faq-item">
            <summary><%= MessageUtil.getMessage(locale, "landing.faq5_pregunta")%></summary>
            <p><%= MessageUtil.getMessage(locale, "landing.faq5_respuesta")%></p>
        </details>
    </div>
</section>

<footer class="landing-footer">
    <a href="timer" class="btn landing-btn landing-btn-lg"><%= MessageUtil.getMessage(locale, "landing.cta_final")%></a>
    <p class="landing-footer-note">DTimer — <%= MessageUtil.getMessage(locale, "landing.footer_texto")%></p>
</footer>

<script>
    fetch('cube/generateScramble')
        .then(function (response) { return response.text(); })
        .then(function (scramble) {
            document.getElementById('landing-scramble').textContent = scramble;
        })
        .catch(function () { });
</script>
</body>
</html>