<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.modelo.Comprobante" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <!-- Enlace a Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <!-- Enlace a Font Awesome para los íconos -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            background-color: #f5f5f5;
            color: #333;
        }
        .navbar {
            background-color: #1E88E5;
        }
        .navbar-brand, .navbar-nav .nav-link {
            color: white !important;
        }
        .container {
            margin-top: 30px;
        }
        .card {
            border: none;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .card-header {
            background-color: #1E88E5;
            color: white;
            border-bottom: none;
            border-top-left-radius: 10px;
            border-top-right-radius: 10px;
        }
        table {
            border-collapse: separate;
            border-spacing: 0 10px;
        }
        th, td {
            padding: 15px;
            vertical-align: middle;
        }
        th {
            background-color: #1E88E5;
            color: white;
        }
        tr {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .no-data {
            text-align: center;
            color: #999;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Sistema de Comprobantes</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <span class="nav-link"><i class="fas fa-user"></i> ${sessionScope.nombre} (${sessionScope.ruc})</span>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/logout">Salir</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="card">
        <div class="card-header">
            <h2><i class="fas fa-search icon"></i> Buscar Comprobantes</h2>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/comprobantes" method="get" class="row g-3">
                <div class="col-md-3">
                    <label for="tipo_comprobante" class="form-label">Tipo de Comprobante:</label>
                    <select name="tipo_comprobante" id="tipo_comprobante" class="form-select" required>
                        <option value="Todos">Todos</option>
                        <option value="Factura">Factura</option>
                        <option value="Comprobante de Retención">Comprobante de Retención</option>
                    </select>
                </div>
                <div class="col-md-3">
                    <label for="dia" class="form-label">Día:</label>
                    <select name="dia" id="dia" class="form-select" required>
                        <option value="Todos">Todos</option>
                        <% for (int i = 1; i <= 31; i++) { %>
                        <option value="<%= i %>"><%= i %></option>
                        <% } %>
                    </select>
                </div>
                <div class="col-md-3">
                    <label for="mes" class="form-label">Mes:</label>
                    <select name="mes" id="mes" class="form-select" required>
                        <option value="Todos">Todos</option>
                        <% String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
                            for (int i = 0; i < meses.length; i++) { %>
                        <option value="<%= i + 1 %>"><%= meses[i] %></option>
                        <% } %>
                    </select>
                </div>
                <div class="col-md-3">
                    <label for="año" class="form-label">Año:</label>
                    <select name="año" id="año" class="form-select" required>
                        <option value="Todos">Todos</option>
                        <% for (int i = 2020; i <= 2024; i++) { %>
                        <option value="<%= i %>"><%= i %></option>
                        <% } %>
                    </select>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary"><i class="fas fa-search icon"></i> Buscar</button>
                </div>
            </form>
        </div>
    </div>

    <div class="card mt-4">
        <div class="card-header">
            <h2><i class="fas fa-upload icon"></i> Subir Comprobantes</h2>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/comprobantes" method="post" enctype="multipart/form-data" class="row g-3">
                <div class="col-md-9">
                    <input type="file" name="archivo" class="form-control" required>
                </div>
                <div class="col-md-3">
                    <button type="submit" class="btn btn-primary w-100"><i class="fas fa-file-upload icon"></i> Subir Comprobantes</button>
                </div>
            </form>
        </div>
    </div>

    <% if (request.getAttribute("successMessage") != null) { %>
    <div class="alert alert-success mt-4">
        <i class="fas fa-check-circle"></i> <%= request.getAttribute("successMessage") %>
    </div>
    <% } %>
    <% if (request.getAttribute("errorMessage") != null) { %>
    <div class="alert alert-danger mt-4">
        <i class="fas fa-exclamation-triangle"></i> <%= request.getAttribute("errorMessage") %>
    </div>
    <% } %>

    <div class="card mt-4">
        <div class="card-header">
            <h2><i class="fas fa-list icon"></i> Comprobantes Subidos</h2>
        </div>
        <div class="card-body p-0">
            <table class="table">
                <thead>
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
                </thead>
                <tbody>
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
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Enlace a Bootstrap JS y dependencias -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>

