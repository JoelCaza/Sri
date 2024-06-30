package com.dao;

import com.modelo.Usuario;
import com.utils.ConexionDB;

import java.sql.*;

public class UsuarioDAO {
    private Connection connection;

    // Constructor que obtiene una conexión a la base de datos
    public UsuarioDAO() {
        this.connection = ConexionDB.getConnection();
    }

    // Método para validar las credenciales de un usuario
    public Usuario validarUsuario(String ruc, String password) {
        Usuario usuario = null;
        try {
            // Preparar la declaración SQL para seleccionar el usuario con el RUC y contraseña proporcionados
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Usuarios WHERE ruc = ? AND password = ?");
            ps.setString(1, ruc); // Establecer el parámetro RUC
            ps.setString(2, password); // Establecer el parámetro contraseña
            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta
            if (rs.next()) {
                // Si se encuentra un usuario, crear una instancia de Usuario y establecer sus atributos
                usuario = new Usuario();
                usuario.setRuc(rs.getString("ruc"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setEmail(rs.getString("email"));
                usuario.setFechaNacimiento(rs.getString("fecha_nacimiento"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar excepciones SQL
        }
        return usuario; // Retornar el usuario encontrado o null si no se encontró
    }

    // Método para verificar si un RUC ya está registrado en la base de datos
    public boolean validarRuc(String ruc) {
        try {
            // Preparar la declaración SQL para seleccionar un usuario con el RUC proporcionado
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Usuarios WHERE ruc = ?");
            ps.setString(1, ruc); // Establecer el parámetro RUC
            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta
            return rs.next(); // Retornar true si se encuentra un registro, false si no
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar excepciones SQL
            return false; // Retornar false si ocurre una excepción
        }
    }

    // Método para registrar un nuevo usuario en la base de datos
    public void registrarUsuario(Usuario usuario) {
        try {
            // Preparar la declaración SQL para insertar un nuevo usuario
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Usuarios (ruc, password, nombre, apellido, email, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, usuario.getRuc()); // Establecer el parámetro RUC
            ps.setString(2, usuario.getPassword()); // Establecer el parámetro contraseña
            ps.setString(3, usuario.getNombre()); // Establecer el parámetro nombre
            ps.setString(4, usuario.getApellido()); // Establecer el parámetro apellido
            ps.setString(5, usuario.getEmail()); // Establecer el parámetro email
            ps.setString(6, usuario.getFechaNacimiento()); // Establecer el parámetro fecha de nacimiento
            ps.executeUpdate(); // Ejecutar la inserción
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar excepciones SQL
        }
    }
}
