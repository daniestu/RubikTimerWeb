<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="utils.MessageUtil" %>
<%@ page import="java.util.Locale" %>

<title><%= MessageUtil.getMessage(new Locale("es", "ES"), "title")%></title>
<meta charset="UTF-8">
<link rel="shortcut icon" type="image/x-icon" href="../images/favicon.ico" />
<link rel="shortcut icon" type="image/x-icon" href="images/favicon.ico" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<c:set var="projectName" value='<%= MessageUtil.getMessage(new Locale("es", "ES"), "project.name")%>'/>
<link href="/${projectName}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="/${projectName}/bootstrap/js/bootstrap.bundle.js" charset="UTF-8"></script>