package com.controlador;

import com.dao.ComprobanteDAO;
import com.modelo.Comprobante;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/comprobantes")
@MultipartConfig
public class ControladorComprobantes extends HttpServlet {

    // metodo que maneja las solicitudes GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(); // obtener la sesion actual
        String identificacionReceptor = (String) session.getAttribute("ruc"); // obtener el ruc del usuario desde la sesion

        // obtener los parametros de la solicitud
        String tipoComprobante = request.getParameter("tipo_comprobante");
        String dia = request.getParameter("dia");
        String mes = request.getParameter("mes");
        String año = request.getParameter("año");

        // crear instancia del dao de comprobantes y obtener comprobantes segun los criterios
        ComprobanteDAO comprobanteDAO = new ComprobanteDAO();
        List<Comprobante> comprobantes = comprobanteDAO.obtenerComprobantesPorCriterios(identificacionReceptor, tipoComprobante, dia, mes, año);
        request.setAttribute("comprobantes", comprobantes); // establecer los comprobantes como atributo de la solicitud
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/dashboard.jsp"); // despachador para redirigir a la vista del dashboard
        dispatcher.forward(request, response); // redirigir a la vista del dashboard
    }

    // metodo que maneja las solicitudes POST
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(); // obtener la sesion actual
        String identificacionReceptor = (String) session.getAttribute("ruc"); // obtener el ruc del usuario desde la sesion

        request.setCharacterEncoding("UTF-8"); // establecer la codificacion de caracteres
        Part filePart = request.getPart("archivo"); // obtener el archivo subido
        if (filePart == null || filePart.getSize() == 0) { // verificar si el archivo es nulo o vacio
            request.setAttribute("errorMessage", "El archivo no se pudo cargar o esta vacio."); // establecer mensaje de error
            RequestDispatcher dispatcher = request.getRequestDispatcher("views/dashboard.jsp"); // despachador para redirigir a la vista del dashboard
            dispatcher.forward(request, response); // redirigir a la vista del dashboard
            return; // terminar la ejecucion del metodo
        }

        InputStream fileContent = filePart.getInputStream(); // obtener el contenido del archivo como InputStream
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent, "UTF-8")); // leer el contenido del archivo
        String line; // variable para almacenar cada linea del archivo
        boolean isFirstLine = true; // indicador para verificar si es la primera linea
        int successfulLines = 0; // contador de lineas procesadas exitosamente
        int errorLines = 0; // contador de lineas con error

        while ((line = reader.readLine()) != null) { // leer cada linea del archivo
            if (isFirstLine) { // si es la primera linea
                isFirstLine = false; // cambiar el indicador
                if (line.startsWith("RUC_EMISOR")) { // si la linea contiene encabezados
                    continue; // saltar la linea
                }
            }
            Comprobante comprobante = parseComprobanteFromLine(line, identificacionReceptor); // parsear la linea a un objeto Comprobante
            if (comprobante != null) { // si el comprobante es valido
                ComprobanteDAO comprobanteDAO = new ComprobanteDAO(); // crear una instancia del dao de comprobantes
                comprobanteDAO.agregarComprobante(comprobante); // agregar el comprobante a la base de datos
                successfulLines++; // incrementar el contador de lineas exitosas
            } else {
                errorLines++; // incrementar el contador de lineas con error
            }
        }

        request.setAttribute("successMessage", "Se procesaron " + successfulLines + " lineas correctamente."); // establecer mensaje de exito
        request.setAttribute("errorMessage", "Hubo errores en " + errorLines + " lineas."); // establecer mensaje de error

        doGet(request, response); // redirigir a la vista del dashboard
    }

    // metodo para parsear una linea del archivo a un objeto Comprobante
    private Comprobante parseComprobanteFromLine(String line, String identificacionReceptor) {
        String[] data = line.split("\t"); // dividir la linea por tabulaciones
        if (data.length < 11) { // verificar si la linea tiene menos de 11 elementos
            System.out.println("Linea invalida: " + line); // imprimir mensaje de error en consola
            return null; // retornar nulo
        }

        try {
            Comprobante comprobante = new Comprobante(); // crear una nueva instancia de Comprobante
            comprobante.setRucEmisor(data[0]); // establecer el ruc del emisor
            comprobante.setRazonSocialEmisor(replaceSpecialCharacters(data[1], true)); // establecer la razon social del emisor
            comprobante.setTipoComprobante(replaceSpecialCharacters(data[2], false)); // establecer el tipo de comprobante
            comprobante.setSerieComprobante(data[3]); // establecer la serie del comprobante
            comprobante.setClaveAcceso(data[4]); // establecer la clave de acceso
            comprobante.setFechaAutorizacion(convertToDateTime(data[5])); // establecer la fecha de autorizacion
            comprobante.setFechaEmision(convertToDate(data[6])); // establecer la fecha de emision
            comprobante.setIdentificacionReceptor(identificacionReceptor); // establecer la identificacion del receptor desde la sesion
            comprobante.setValorSinImpuestos(parseDouble(data[8])); // establecer el valor sin impuestos
            comprobante.setIva(parseDouble(data[9])); // establecer el iva
            comprobante.setImporteTotal(parseDouble(data[10])); // establecer el importe total
            comprobante.setNumeroDocumentoModificado(data.length > 11 ? data[11] : null); // establecer el numero de documento modificado si existe
            return comprobante; // retornar el comprobante
        } catch (Exception e) {
            System.out.println("Error al parsear linea: " + line); // imprimir mensaje de error en consola
            e.printStackTrace(); // imprimir el stack trace del error
            return null; // retornar nulo
        }
    }

    // metodo para reemplazar caracteres especiales
    private String replaceSpecialCharacters(String text, boolean isRazonSocial) {
        if (text == null) { // verificar si el texto es nulo
            return null; // retornar nulo
        }
        if (isRazonSocial) { // verificar si es la razon social
            return text.replace("�", "Ñ"); // reemplazar caracter especial por Ñ
        } else {
            return text.replace("�", "ó"); // reemplazar caracter especial por ó
        }
    }

    // metodo para convertir una cadena de texto a java.sql.Date
    private java.sql.Date convertToDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) { // verificar si la cadena es nula o vacia
            return null; // retornar nulo
        }
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); // crear un formato de fecha
        try {
            Date parsed = format.parse(dateStr); // parsear la cadena a Date
            return new java.sql.Date(parsed.getTime()); // retornar la fecha como java.sql.Date
        } catch (ParseException e) {
            System.out.println("Error al parsear la fecha: " + dateStr); // imprimir mensaje de error en consola
            e.printStackTrace(); // imprimir el stack trace del error
            return null; // retornar nulo
        }
    }

    // metodo para convertir una cadena de texto a java.sql.Timestamp
    private java.sql.Timestamp convertToDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) { // verificar si la cadena es nula o vacia
            return null; // retornar nulo
        }
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // crear un formato de fecha y hora
        try {
            Date parsed = format.parse(dateTimeStr); // parsear la cadena a Date
            return new java.sql.Timestamp(parsed.getTime()); // retornar la fecha y hora como java.sql.Timestamp
        } catch (ParseException e) {
            System.out.println("Error al parsear la fecha y hora: " + dateTimeStr); // imprimir mensaje de error en consola
            e.printStackTrace(); // imprimir el stack trace del error
            return null; // retornar nulo
        }
    }

    // metodo para parsear una cadena de texto a Double
    private Double parseDouble(String numberStr) {
        if (numberStr == null || numberStr.trim().isEmpty()) { // verificar si la cadena es nula o vacia
            return 0.0; // retornar 0.0
        }
        try {
            return Double.parseDouble(numberStr); // retornar el numero parseado como Double
        } catch (NumberFormatException e) {
            System.out.println("Error al parsear el numero: " + numberStr); // imprimir mensaje de error en consola
            e.printStackTrace(); // imprimir el stack trace del error
            return 0.0; // retornar 0.0
        }
    }
}
