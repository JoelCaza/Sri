package com.dao;

import com.modelo.Usuario;
import com.utils.ConexionDB;

import java.sql.*;


public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO() {
        this.connection = ConexionDB.getConnection();
    }

    public boolean validarUsuario(String username, String password) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Usuarios WHERE username = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void registrarUsuario(Usuario usuario) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Usuarios (username, password) VALUES (?, ?)");
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}