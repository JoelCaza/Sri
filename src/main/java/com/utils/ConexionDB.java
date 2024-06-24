package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {
    private static String url = "jdbc:mysql://localhost:3306/SistemaComprobantes";
    private static String driverName = "com.mysql.cj.jdbc.Driver";
    private static String username = "root";
    private static String password = "";
    private static Connection con;

    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (Exception ex) {
                System.out.println("Failed to create the database connection.");
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found.");
            ex.printStackTrace();
        }
        return con;
    }

    // Clase de prueba para verificar la conexión a la base de datos
    public static void main(String[] args) {
        Connection con = ConexionDB.getConnection();
        if (con != null) {
            System.out.println("Conexión a la base de datos exitosa.");
        } else {
            System.out.println("Error al conectar a la base de datos.");
        }
    }
}
