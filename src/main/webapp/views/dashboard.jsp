<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.modelo.Comprobante" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <!-- Enlace a Font Awesome para los íconos -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            color: #333;
            margin: 0;
            padding: 20px;
        }
        h1 {
            background-color: #1E88E5;
            color: white;
            padding: 10px 20px;
            margin: -20px -20px 20px;
        }
        .card {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin-bottom: 20px;
        }
        .card h2 {
            margin-top: 0;
        }
        .card form {
            display: flex;
            flex-direction: column;
        }
        .card form select,
        .card form input[type="file"] {
            margin-bottom: 10px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .card form button {
            background-color: #1E88E5;
            color: white;
            padding: 10px 20px;
            border: none;
            cursor: pointer;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        .card form button:hover {
            background-color: #1565C0;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            overflow: hidden;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #1E88E5;
            color: white;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .no-data {
            text-align: center;
            color: #999;
        }
        .icon {
            margin-right: 5px;
        }
        .alert {
            color: red;
            margin-top: 10px;
        }
        .success {
            color: green;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Bienvenido al Sistema de Comprobantes</h1>
    <p><i class="fas fa-user"></i> ${sessionScope.nombre} (${sessionScope.ruc}) <a href="${pageContext.request.contextPath}/logout">Salir</a></p>

    <div class="card">
        <h2><i class="fas fa-search icon"></i>Buscar Comprobantes</h2>
        <form action="${pageContext.request.contextPath}/comprobantes" method="get">
            <label>Tipo de Comprobante:</label>
            <select name="tipo_comprobante" required>
                <option value="Todos">Todos</option>
                <option value="Factura">Factura</option>
                <option value="Comprobante de Retención">Comprobante de Retención</option>
            </select>
            <label>Día:</label>
            <select name="dia" required>
                <option value="Todos">Todos</option>
                <% for (int i = 1; i <= 31; i++) { %>
                <option value="<%= i %>"><%= i %></option>
                <% } %>
            </select>
            <label>Mes:</label>
            <select name="mes" required>
                <option value="Todos">Todos</option>
                <% String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
                    for (int i = 0; i < meses.length; i++) { %>
                <option value="<%= i + 1 %>"><%= meses[i] %></option>
                <% } %>
            </select>
            <label>Año:</label>
            <select name="año" required>
                <option value="Todos">Todos</option>
                <% for (int i = 2020; i <= 2024; i++) { %>
                <option value="<%= i %>"><%= i %></option>
                <% } %>
            </select>
            <button type="submit"><i class="fas fa-search icon"></i>Buscar</button>
        </form>
    </div>

    <div class="card">
        <h2><i class="fas fa-upload icon"></i>Subir Comprobantes</h2>
        <form action="${pageContext.request.contextPath}/comprobantes" method="post" enctype="multipart/form-data">
            <input type="file" name="archivo" required>
            <button type="submit"><i class="fas fa-file-upload icon"></i>Subir Comprobantes</button>
        </form>
    </div>

    <% if (request.getAttribute("successMessage") != null) { %>
    <p class="success"><i class="fas fa-check-circle"></i> <%= request.getAttribute("successMessage") %></p>
    <% } %>
    <% if (request.getAttribute("errorMessage") != null) { %>
    <p class="alert"><i class="fas fa-exclamation-triangle"></i> <%= request.getAttribute("errorMessage") %></p>
    <% } %>

    <h2><i class="fas fa-list icon"></i>Comprobantes Subidos</h2>
    <table>
        <tr>
            <th>RUC Emisor</th>
            <th>Razón Social Emisor</th>
            <th>Tipo de Comprobante</th>
            <th>Serie del Comprobante</th>
            <th>Clave de Acceso</th>
            <th>Fecha de Autorización</th>
            <th>Fecha de Emisión</th>
            <th>Identificación del Receptor</th>
            <th>Valor sin Impuestos</th>
            <th>IVA</th>
            <th>Importe Total</th>
            <th>Número de Documento Modificado</th>
        </tr>
        <%
            List<Comprobante> comprobantes = (List<Comprobante>) request.getAttribute("comprobantes");
            if (comprobantes != null && !comprobantes.isEmpty()) {
                for (Comprobante comprobante : comprobantes) {
        %>
        <tr>
            <td><%= comprobante.getRucEmisor() %></td>
            <td><%= comprobante.getRazonSocialEmisor() %></td>
            <td><%= comprobante.getTipoComprobante() %></td>
            <td><%= comprobante.getSerieComprobante() %></td>
            <td><%= comprobante.getClaveAcceso() %></td>
            <td><%= comprobante.getFechaAutorizacion() %></td>
            <td><%= comprobante.getFechaEmision() %></td>
            <td><%= comprobante.getIdentificacionReceptor() %></td>
            <td><%= comprobante.getValorSinImpuestos() %></td>
            <td><%= comprobante.getIva() %></td>
            <td><%= comprobante.getImporteTotal() %></td>
            <td><%= comprobante.getNumeroDocumentoModificado() %></td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="12" class="no-data">No se encontraron comprobantes.</td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>
