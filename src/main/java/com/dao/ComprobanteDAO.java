package com.dao;

import com.modelo.Comprobante;
import com.utils.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComprobanteDAO {

    // método para agregar un comprobante a la base de datos
    public void agregarComprobante(Comprobante comprobante) {
        String sql = "INSERT INTO comprobantes (ruc_emisor, razon_social_emisor, tipo_comprobante, serie_comprobante, clave_acceso, fecha_autorizacion, fecha_emision, identificacion_receptor, valor_sin_impuestos, iva, importe_total, numero_documento_modificado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConnection(); // obtener una conexión a la base de datos
             PreparedStatement ps = con.prepareStatement(sql)) { // preparar la declaración SQL

            // establecer los parámetros de la declaración SQL
            ps.setString(1, comprobante.getRucEmisor());
            ps.setString(2, comprobante.getRazonSocialEmisor());
            ps.setString(3, comprobante.getTipoComprobante());
            ps.setString(4, comprobante.getSerieComprobante());
            ps.setString(5, comprobante.getClaveAcceso());
            ps.setTimestamp(6, comprobante.getFechaAutorizacion() != null ? new java.sql.Timestamp(comprobante.getFechaAutorizacion().getTime()) : null);
            ps.setDate(7, comprobante.getFechaEmision() != null ? new java.sql.Date(comprobante.getFechaEmision().getTime()) : null);
            ps.setString(8, comprobante.getIdentificacionReceptor());
            ps.setDouble(9, comprobante.getValorSinImpuestos());
            ps.setDouble(10, comprobante.getIva());
            ps.setDouble(11, comprobante.getImporteTotal());
            ps.setString(12, comprobante.getNumeroDocumentoModificado());

            ps.executeUpdate(); // ejecutar la declaración SQL
        } catch (SQLException e) {
            e.printStackTrace(); // manejar excepciones
        }
    }

    // método para obtener una lista de comprobantes según los criterios de búsqueda
    public List<Comprobante> obtenerComprobantesPorCriterios(String identificacionReceptor, String tipoComprobante, String dia, String mes, String año) {
        List<Comprobante> comprobantes = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM comprobantes WHERE identificacion_receptor = ?");

        // construir la declaración SQL según los criterios de búsqueda
        if (tipoComprobante != null && !tipoComprobante.equals("Todos")) {
            sql.append(" AND tipo_comprobante = ?");
        }
        if (dia != null && !dia.equals("Todos")) {
            sql.append(" AND DAY(fecha_emision) = ?");
        }
        if (mes != null && !mes.equals("Todos")) {
            sql.append(" AND MONTH(fecha_emision) = ?");
        }
        if (año != null && !año.equals("Todos")) {
            sql.append(" AND YEAR(fecha_emision) = ?");
        }

        try (Connection con = ConexionDB.getConnection(); // obtener una conexión a la base de datos
             PreparedStatement ps = con.prepareStatement(sql.toString())) { // preparar la declaración SQL
            ps.setString(1, identificacionReceptor); // establecer el primer parámetro
            int paramIndex = 2; // índice para los parámetros adicionales
            if (tipoComprobante != null && !tipoComprobante.equals("Todos")) {
                ps.setString(paramIndex++, tipoComprobante);
            }
            if (dia != null && !dia.equals("Todos")) {
                ps.setInt(paramIndex++, Integer.parseInt(dia));
            }
            if (mes != null && !mes.equals("Todos")) {
                ps.setInt(paramIndex++, Integer.parseInt(mes));
            }
            if (año != null && !año.equals("Todos")) {
                ps.setInt(paramIndex++, Integer.parseInt(año));
            }
            try (ResultSet rs = ps.executeQuery()) { // ejecutar la consulta SQL
                while (rs.next()) {
                    Comprobante comprobante = new Comprobante();
                    comprobante.setId(rs.getInt("id"));
                    comprobante.setRucEmisor(rs.getString("ruc_emisor"));
                    comprobante.setRazonSocialEmisor(rs.getString("razon_social_emisor"));
                    comprobante.setTipoComprobante(rs.getString("tipo_comprobante"));
                    comprobante.setSerieComprobante(rs.getString("serie_comprobante"));
                    comprobante.setClaveAcceso(rs.getString("clave_acceso"));
                    comprobante.setFechaAutorizacion(rs.getTimestamp("fecha_autorizacion"));
                    comprobante.setFechaEmision(rs.getDate("fecha_emision"));
                    comprobante.setIdentificacionReceptor(rs.getString("identificacion_receptor"));
                    comprobante.setValorSinImpuestos(rs.getDouble("valor_sin_impuestos"));
                    comprobante.setIva(rs.getDouble("iva"));
                    comprobante.setImporteTotal(rs.getDouble("importe_total"));
                    comprobante.setNumeroDocumentoModificado(rs.getString("numero_documento_modificado"));

                    comprobantes.add(comprobante); // agregar el comprobante a la lista
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // manejar excepciones
        }

        return comprobantes; // retornar la lista de comprobantes
    }
}
