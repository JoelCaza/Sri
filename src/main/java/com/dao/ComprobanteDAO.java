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

    public void agregarComprobante(Comprobante comprobante) {
        String sql = "INSERT INTO comprobantes (ruc_emisor, razon_social_emisor, tipo_comprobante, serie_comprobante, clave_acceso, fecha_autorizacion, fecha_emision, identificacion_receptor, valor_sin_impuestos, iva, importe_total, numero_documento_modificado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, comprobante.getRucEmisor());
            ps.setString(2, comprobante.getRazonSocialEmisor());
            ps.setString(3, comprobante.getTipoComprobante());
            ps.setString(4, comprobante.getSerieComprobante());
            ps.setString(5, comprobante.getClaveAcceso());
            ps.setDate(6, comprobante.getFechaAutorizacion() != null ? new java.sql.Date(comprobante.getFechaAutorizacion().getTime()) : null);
            ps.setDate(7, comprobante.getFechaEmision() != null ? new java.sql.Date(comprobante.getFechaEmision().getTime()) : null);
            ps.setString(8, comprobante.getIdentificacionReceptor());
            ps.setDouble(9, comprobante.getValorSinImpuestos());
            ps.setDouble(10, comprobante.getIva());
            ps.setDouble(11, comprobante.getImporteTotal());
            ps.setString(12, comprobante.getNumeroDocumentoModificado());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Comprobante> obtenerComprobantes() {
        List<Comprobante> comprobantes = new ArrayList<>();
        String sql = "SELECT * FROM comprobantes";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Comprobante comprobante = new Comprobante();
                comprobante.setId(rs.getInt("id"));
                comprobante.setRucEmisor(rs.getString("ruc_emisor"));
                comprobante.setRazonSocialEmisor(rs.getString("razon_social_emisor"));
                comprobante.setTipoComprobante(rs.getString("tipo_comprobante"));
                comprobante.setSerieComprobante(rs.getString("serie_comprobante"));
                comprobante.setClaveAcceso(rs.getString("clave_acceso"));
                comprobante.setFechaAutorizacion(rs.getDate("fecha_autorizacion"));
                comprobante.setFechaEmision(rs.getDate("fecha_emision"));
                comprobante.setIdentificacionReceptor(rs.getString("identificacion_receptor"));
                comprobante.setValorSinImpuestos(rs.getDouble("valor_sin_impuestos"));
                comprobante.setIva(rs.getDouble("iva"));
                comprobante.setImporteTotal(rs.getDouble("importe_total"));
                comprobante.setNumeroDocumentoModificado(rs.getString("numero_documento_modificado"));

                comprobantes.add(comprobante);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comprobantes;
    }
}
