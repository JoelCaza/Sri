package com.dao;

import com.modelo.Usuario;
import com.utils.ConexionDB;

import java.sql.*;

public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO() {
        this.connection = ConexionDB.getConnection();
    }

    public Usuario validarUsuario(String ruc, String password) {
        Usuario usuario = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Usuarios WHERE ruc = ? AND password = ?");
            ps.setString(1, ruc);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setRuc(rs.getString("ruc"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setEmail(rs.getString("email"));
                usuario.setFechaNacimiento(rs.getString("fecha_nacimiento"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public boolean validarRuc(String ruc) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Usuarios WHERE ruc = ?");
            ps.setString(1, ruc);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void registrarUsuario(Usuario usuario) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Usuarios (ruc, password, nombre, apellido, email, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, usuario.getRuc());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getNombre());
            ps.setString(4, usuario.getApellido());
            ps.setString(5, usuario.getEmail());
            ps.setString(6, usuario.getFechaNacimiento());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
