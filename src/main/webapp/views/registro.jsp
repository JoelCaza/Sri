<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Registro</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        @keyframes fadeIn {
            from {
                opacity: 0;
            }
            to {
                opacity: 1;
            }
        }

        body {
            font-family: 'Roboto', sans-serif;
            background: linear-gradient(135deg, #1E88E5, #1565C0);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            animation: fadeIn 1s ease-in-out;
        }

        .card {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            padding: 30px;
            width: 100%;
            max-width: 400px;
            text-align: center;
            animation: fadeIn 1.5s ease-in-out;
        }

        .card h2 {
            margin-bottom: 20px;
            color: #1E88E5;
            animation: fadeIn 2s ease-in-out;
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
            transition: box-shadow 0.3s;
        }

        .card form input[type="text"]:focus,
        .card form input[type="password"]:focus,
        .card form input[type="email"]:focus,
        .card form input[type="date"]:focus {
            box-shadow: 0 0 5px rgba(30, 136, 229, 0.5);
        }

        .card form input[type="submit"] {
            background-color: #1E88E5;
            color: white;
            padding: 14px 20px;
            border: none;
            cursor: pointer;
            border-radius: 4px;
            transition: background-color 0.3s, transform 0.3s;
            width: 100%;
        }

        .card form input[type="submit"]:hover {
            background-color: #1565C0;
            transform: scale(1.05);
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
            transition: color 0.3s;
        }

        .card a:hover {
            text-decoration: underline;
            color: #1565C0;
        }
    </style>
</head>
<body>
<div class="card">
    <h2><i class="fas fa-user-plus"></i> Registro</h2>
    <form action="${pageContext.request.contextPath}/registro" method="post">
        <input type="text" name="ruc" placeholder="RUC (Username)" required>
        <input type="password" name="password" placeholder="Password" required>
        <input type="text" name="nombre" placeholder="Nombre" required>
        <input type="text" name="apellido" placeholder="Apellido" required>
        <input type="email" name="email" placeholder="Email" required>
        <input type="date" name="fecha_nacimiento" required>
        <input type="submit" value="Registrar">
    </form>
    <% if (request.getAttribute("errorMessage") != null) { %>
    <p class="alert"><i class="fas fa-exclamation-triangle"></i> <%= request.getAttribute("errorMessage") %></p>
    <% } %>
    <% if (request.getAttribute("successMessage") != null) { %>
    <p class="success"><i class="fas fa-check-circle"></i> <%= request.getAttribute("successMessage") %></p>
    <% } %>
    <p>¿Ya tienes una cuenta? <a href="${pageContext.request.contextPath}/login">Inicia sesión aquí</a></p>
</div>
</body>
</html>
