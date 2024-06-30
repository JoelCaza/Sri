package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {
    private static String url = "jdbc:mysql://localhost:3306/SistemaComprobantes?useUnicode=true&characterEncoding=UTF-8";
    private static String driverName = "com.mysql.cj.jdbc.Driver";
    private static String username = "root";
    private static String password = "";
    private static Connection con;

    // método estático para obtener una conexión a la base de datos
    public static Connection getConnection() {
        try {
            // cargar el controlador JDBC
            Class.forName(driverName);
            try {
                // intentar establecer una conexión a la base de datos
                con = DriverManager.getConnection(url, username, password);
            } catch (Exception ex) {
                System.out.println("Failed to create the database connection."); // mensaje de error si falla la conexión
                ex.printStackTrace(); // imprimir el stack trace del error
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found."); // mensaje de error si no se encuentra el driver JDBC
            ex.printStackTrace(); // imprimir el stack trace del error
        }
        return con; // retornar la conexión a la base de datos (puede ser null si falló)
    }
}
