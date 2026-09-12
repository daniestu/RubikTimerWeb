<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="utils.MessageUtil" %>
<%@ page import="java.util.Locale" %>

<%
    Locale locale = (Locale) request.getAttribute("locale");

    String tituloClave = (String) request.getAttribute("tituloClave");
    if (tituloClave == null) { tituloClave = "title"; }

    String descripcionClave = (String) request.getAttribute("descripcionClave");

    String urlCanonica = (String) request.getAttribute("urlCanonica");
    if (urlCanonica == null) { urlCanonica = "https://www.dtimerapp.com/"; }
%>

<title><%= MessageUtil.getMessage(locale, tituloClave)%></title>
<% if (descripcionClave != null) { %>
<meta name="description" content="<%= MessageUtil.getMessage(locale, descripcionClave)%>">
<% } %>
<link rel="canonical" href="<%= urlCanonica %>">
<meta charset="UTF-8">
<link rel="shortcut icon" type="image/x-icon" href="../images/favicon.ico" />
<link rel="shortcut icon" type="image/x-icon" href="images/favicon.ico" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="/bootstrap/js/bootstrap.bundle.min.js" charset="UTF-8"></script>