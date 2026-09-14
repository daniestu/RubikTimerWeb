<%@ page import="utils.MessageUtil" %>
<%@ page import="java.util.Locale" %>
<%
    Locale localeNav = (Locale) request.getAttribute("locale");
%>
<nav class="landing-nav">
    <div class="landing-nav-brand">
        <a href="/" style="display:flex;align-items:center;gap:8px;text-decoration:none;color:inherit;">
            <img src="/images/logo.png" alt="" width="32" height="32">
            <span>DTimer</span>
        </a>
    </div>
    <div class="landing-nav-links">
        <a href="/blog" class="landing-nav-link">Blog</a>
        <a href="/timer" class="btn landing-btn landing-btn-sm"><%= MessageUtil.getMessage(localeNav, "landing.cta_principal")%></a>
    </div>
</nav>