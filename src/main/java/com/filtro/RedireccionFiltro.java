package com.filtro;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*") // Define que este filtro se aplicará a todas las URLs del proyecto
public class RedireccionFiltro implements Filter {

    // Método que se ejecuta cuando el filtro está en uso
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request; // Convertir ServletRequest a HttpServletRequest
        HttpServletResponse httpResponse = (HttpServletResponse) response; // Convertir ServletResponse a HttpServletResponse

        String contextPath = httpRequest.getContextPath(); // Obtener el contexto de la aplicación
        String requestURI = httpRequest.getRequestURI(); // Obtener la URI de la solicitud

        // Redirigir a /login si se accede a la raíz del proyecto
        if (requestURI.equals(contextPath + "/")) {
            httpResponse.sendRedirect(contextPath + "/login"); // Redirigir a la página de login
            return; // Detener la ejecución del filtro
        }

        chain.doFilter(request, response); // Pasar la solicitud y respuesta al siguiente filtro o recurso
    }
}
