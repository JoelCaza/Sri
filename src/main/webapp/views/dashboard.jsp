<%--
  Created by IntelliJ IDEA.
  User: Joel
  Date: 23/6/2024
  Time: 18:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.modelo.Comprobante" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
<h1>Bienvenido al Sistema de Comprobantes</h1>
<form action="${pageContext.request.contextPath}/comprobantes" method="post" enctype="multipart/form-data">
    <input type="file" name="archivo" required>
    <button type="submit">Subir Comprobantes</button>
</form>
<h2>Comprobantes Subidos</h2>
<table border="1">
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
        if (comprobantes != null) {
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
        }
    %>
</table>
</body>
</html>

