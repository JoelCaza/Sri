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
        .card form input[type="file"] {
            margin-bottom: 10px;
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
    </style>
</head>
<body>
<div class="container">
    <h1>Bienvenido al Sistema de Comprobantes</h1>
    <div class="card">
        <h2><i class="fas fa-upload icon"></i>Subir Comprobantes</h2>
        <form action="${pageContext.request.contextPath}/comprobantes" method="post" enctype="multipart/form-data">
            <input type="file" name="archivo" required>
            <button type="submit"><i class="fas fa-file-upload icon"></i>Subir Comprobantes</button>
        </form>
    </div>
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
            <td colspan="12" class="no-data">No hay comprobantes disponibles.</td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>
