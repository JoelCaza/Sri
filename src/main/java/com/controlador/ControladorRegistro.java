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

@WebServlet("/registro")
public class ControladorRegistro extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ruc = request.getParameter("ruc");
        String password = request.getParameter("password");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String fechaNacimiento = request.getParameter("fecha_nacimiento");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if (usuarioDAO.validarRuc(ruc)) {
            request.setAttribute("errorMessage", "El RUC ya está registrado.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("views/registro.jsp");
            dispatcher.forward(request, response);
        } else {
            Usuario usuario = new Usuario(ruc, password, nombre, apellido, email, fechaNacimiento);
            usuarioDAO.registrarUsuario(usuario);
            request.setAttribute("successMessage", "Usuario registrado exitosamente. Por favor, inicie sesión.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("views/login.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/registro.jsp");
        dispatcher.forward(request, response);
    }
}
