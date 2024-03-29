<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="head.jsp" />
    <script src="js/scrambleScript.js"></script>
    <link rel="stylesheet" type="text/css" href="css/cronometroStyles.css">
  </head>
  <body>
    <div class="scramble-container">
      <p id="scramble"></p>
    </div>
    <aside class="aside-container">
      <ul>
        <li>Item 1</li>
        <li>Item 2</li>
        <li>Item 3</li>
      </ul>
    </aside>
    <div class="cronometro-container">
      <p id="cronometro">00:00:00</p>
    </div>
    <script src="js/cronometroScript.js"></script>
    <script>generateScramble();</script>
  </body>
</html>