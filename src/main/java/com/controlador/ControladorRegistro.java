package com.controlador;

import java.io.IOException;
import com.dao.UsuarioDAO;
import com.modelo.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/registro") // Define la URL a la que este servlet responderá
public class ControladorRegistro extends HttpServlet {

    // Método que maneja las solicitudes POST
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ruc = request.getParameter("ruc"); // Obtiene el RUC del formulario
        String password = request.getParameter("password"); // Obtiene la contraseña del formulario
        String nombre = request.getParameter("nombre"); // Obtiene el nombre del formulario
        String apellido = request.getParameter("apellido"); // Obtiene el apellido del formulario
        String email = request.getParameter("email"); // Obtiene el email del formulario
        String fechaNacimiento = request.getParameter("fecha_nacimiento"); // Obtiene la fecha de nacimiento del formulario

        UsuarioDAO usuarioDAO = new UsuarioDAO(); // Crea una instancia del DAO de usuarios
        if (usuarioDAO.validarRuc(ruc)) { // Verifica si el RUC ya está registrado
            request.setAttribute("errorMessage", "El RUC ya está registrado."); // Establece el mensaje de error
            RequestDispatcher dispatcher = request.getRequestDispatcher("views/registro.jsp"); // Despachador para redirigir a la vista de registro
            dispatcher.forward(request, response); // Redirige a la vista de registro
        } else {
            // Crea una instancia de Usuario con los datos del formulario
            Usuario usuario = new Usuario(ruc, password, nombre, apellido, email, fechaNacimiento);
            usuarioDAO.registrarUsuario(usuario); // Registra el usuario en la base de datos
            request.setAttribute("successMessage", "Usuario registrado exitosamente. Por favor, inicie sesión."); // Establece el mensaje de éxito
            RequestDispatcher dispatcher = request.getRequestDispatcher("views/login.jsp"); // Despachador para redirigir a la vista de login
            dispatcher.forward(request, response); // Redirige a la vista de login
        }
    }

    // Método que maneja las solicitudes GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/registro.jsp"); // Despachador para redirigir a la vista de registro
        dispatcher.forward(request, response); // Redirige a la vista de registro
    }
}
