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
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class ControladorLogin extends HttpServlet {

    // metodo que maneja las solicitudes POST
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username"); // obtener el nombre de usuario del formulario
        String password = request.getParameter("password"); // obtener la contrasena del formulario

        UsuarioDAO usuarioDAO = new UsuarioDAO(); // crear una instancia del dao de usuarios
        Usuario usuario = usuarioDAO.validarUsuario(username, password); // validar el usuario con el dao

        if (usuario != null) { // si el usuario es valido
            HttpSession session = request.getSession(); // obtener la sesion actual
            session.setAttribute("ruc", usuario.getRuc()); // establecer el ruc del usuario en la sesion
            session.setAttribute("nombre", usuario.getNombre()); // establecer el nombre del usuario en la sesion
            session.setAttribute("apellido", usuario.getApellido()); // establecer el apellido del usuario en la sesion
            response.sendRedirect(request.getContextPath() + "/comprobantes"); // redirigir a la pagina de comprobantes
        } else {
            request.setAttribute("errorMessage", "Credenciales invalidas"); // establecer mensaje de error
            RequestDispatcher dispatcher = request.getRequestDispatcher("views/login.jsp"); // despachador para redirigir a la vista de login
            dispatcher.forward(request, response); // redirigir a la vista de login
        }
    }

    // metodo que maneja las solicitudes GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/login.jsp"); // despachador para redirigir a la vista de login
        dispatcher.forward(request, response); // redirigir a la vista de login
    }
}
