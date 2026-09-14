<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="utils.MessageUtil" %>
<%@ page import="java.util.Locale" %>
<%
    Locale localeError = (Locale) request.getAttribute("locale");
    if (localeError == null) {
        localeError = request.getLocale();
    }
%>
<!DOCTYPE html>
<html data-tema="1" data-bs-theme="dark">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="robots" content="noindex">
    <title><%= MessageUtil.getMessage(localeError, "error.404_titulo")%> | DTimer</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono:wght@500&family=Space+Grotesk:wght@500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/css/mainStyles.css">
    <link rel="stylesheet" type="text/css" href="/css/landingStyles.css">
    <link rel="stylesheet" type="text/css" href="/css/errorStyles.css">
</head>
<body class="landing-body">
<div class="error-wrapper">
    <div class="error-card">
        <p class="error-code">404</p>
        <h1><%= MessageUtil.getMessage(localeError, "error.404_titulo")%></h1>
        <p class="error-text"><%= MessageUtil.getMessage(localeError, "error.404_texto")%></p>
        <a href="/" class="btn landing-btn landing-btn-lg"><%= MessageUtil.getMessage(localeError, "error.volver_inicio")%></a>
    </div>
</div>
</body>
</html>