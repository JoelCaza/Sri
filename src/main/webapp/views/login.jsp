<%--
  Created by IntelliJ IDEA.
  User: Joel
  Date: 23/6/2024
  Time: 18:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post">
    Username: <input type="text" name="username"><br>
    Password: <input type="password" name="password"><br>
    <input type="submit" value="Login">
</form>
<% if (request.getAttribute("errorMessage") != null) { %>
<p><%= request.getAttribute("errorMessage") %></p>
<% } %>
</body>
</html>


