package com.controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout") // Define la URL a la que este servlet responderá
public class ControladorLogout extends HttpServlet {

    // Método que maneja las solicitudes GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(); // Obtiene la sesión actual
        session.invalidate(); // Invalida la sesión, cerrando la sesión del usuario
        response.sendRedirect(request.getContextPath() + "/login"); // Redirige al usuario a la página de login
    }
}
