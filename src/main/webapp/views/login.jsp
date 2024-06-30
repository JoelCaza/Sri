<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #1E88E5, #1565C0);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .card {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            padding: 30px;
            width: 100%;
            max-width: 400px;
            text-align: center;
        }
        .card h2 {
            margin-bottom: 20px;
            color: #1E88E5;
        }
        .card form input[type="text"],
        .card form input[type="password"],
        .card form input[type="email"],
        .card form input[type="date"] {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .card form input[type="submit"] {
            background-color: #1E88E5;
            color: white;
            padding: 14px 20px;
            border: none;
            cursor: pointer;
            border-radius: 4px;
            transition: background-color 0.3s;
            width: 100%;
        }
        .card form input[type="submit"]:hover {
            background-color: #1565C0;
        }
        .alert {
            color: red;
            margin-top: 10px;
        }
        .success {
            color: green;
            margin-top: 10px;
        }
        .card p {
            margin-top: 20px;
        }
        .card a {
            color: #1E88E5;
            text-decoration: none;
        }
        .card a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="card">
    <h2><i class="fas fa-user-lock"></i> Login</h2>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <input type="text" name="username" placeholder="RUC (Username)" required>
        <input type="password" name="password" placeholder="Password" required>
        <input type="submit" value="Login">
    </form>
    <% if (request.getAttribute("errorMessage") != null) { %>
    <p class="alert"><i class="fas fa-exclamation-triangle"></i> <%= request.getAttribute("errorMessage") %></p>
    <% } %>
    <% if (request.getAttribute("successMessage") != null) { %>
    <p class="success"><i class="fas fa-check-circle"></i> <%= request.getAttribute("successMessage") %></p>
    <% } %>
    <p>¿No tienes una cuenta? <a href="${pageContext.request.contextPath}/registro">Regístrate aquí</a></p>
</div>
</body>
</html>
