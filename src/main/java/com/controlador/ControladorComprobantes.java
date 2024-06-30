package com.controlador;

import com.modelo.Comprobante;
import com.dao.ComprobanteDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/comprobantes")
@MultipartConfig
public class ControladorComprobantes extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        ComprobanteDAO comprobanteDAO = new ComprobanteDAO();
        List<Comprobante> comprobantes;

        if ("search".equals(action)) {
            String tipoComprobante = request.getParameter("tipo_comprobante");
            String dia = request.getParameter("dia");
            String mes = request.getParameter("mes");
            String año = request.getParameter("año");
            comprobantes = comprobanteDAO.buscarComprobantes(tipoComprobante, dia, mes, año);
        } else {
            comprobantes = comprobanteDAO.obtenerComprobantes();
        }

        request.setAttribute("comprobantes", comprobantes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/dashboard.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Part filePart = request.getPart("archivo");
        if (filePart == null || filePart.getSize() == 0) {
            request.setAttribute("errorMessage", "El archivo no se pudo cargar o está vacío.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("views/dashboard.jsp");
            dispatcher.forward(request, response);
            return;
        }

        InputStream fileContent = filePart.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent, "UTF-8"));
        String line;
        boolean isFirstLine = true;
        int successfulLines = 0;
        int errorLines = 0;

        while ((line = reader.readLine()) != null) {
            if (isFirstLine) {
                isFirstLine = false;
                if (line.startsWith("RUC_EMISOR")) {
                    continue; // Ignorar la primera línea si contiene encabezados
                }
            }
            Comprobante comprobante = parseComprobanteFromLine(line);
            if (comprobante != null) {
                ComprobanteDAO comprobanteDAO = new ComprobanteDAO();
                comprobanteDAO.agregarComprobante(comprobante);
                successfulLines++;
            } else {
                errorLines++;
            }
        }

        request.setAttribute("successMessage", "Se procesaron " + successfulLines + " líneas correctamente.");
        request.setAttribute("errorMessage", "Hubo errores en " + errorLines + " líneas.");

        doGet(request, response);
    }

    private Comprobante parseComprobanteFromLine(String line) {
        String[] data = line.split("\t");
        if (data.length < 12) {
            System.out.println("Línea inválida: " + line); // Logging
            return null; // Línea no válida, ignorar
        }

        try {
            Comprobante comprobante = new Comprobante();
            comprobante.setRucEmisor(data[0]);
            comprobante.setRazonSocialEmisor(replaceSpecialCharacters(data[1], true));
            comprobante.setTipoComprobante(replaceSpecialCharacters(data[2], false));
            comprobante.setSerieComprobante(data[3]);
            comprobante.setClaveAcceso(data[4]);
            comprobante.setFechaAutorizacion(convertToDateTime(data[5]));
            comprobante.setFechaEmision(convertToDate(data[6]));
            comprobante.setIdentificacionReceptor(data[7]);
            comprobante.setValorSinImpuestos(parseDouble(data[8]));
            comprobante.setIva(parseDouble(data[9]));
            comprobante.setImporteTotal(parseDouble(data[10]));
            comprobante.setNumeroDocumentoModificado(data[11]);
            return comprobante;
        } catch (Exception e) {
            System.out.println("Error al parsear línea: " + line);
            e.printStackTrace();
            return null;
        }
    }

    private String replaceSpecialCharacters(String text, boolean isRazonSocial) {
        if (text == null) {
            return null;
        }
        if (isRazonSocial) {
            return text.replace("�", "Ñ");
        } else {
            return text.replace("�", "ó");
        }
    }

    private java.sql.Date convertToDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date parsed = format.parse(dateStr);
            return new java.sql.Date(parsed.getTime());
        } catch (ParseException e) {
            System.out.println("Error al parsear la fecha: " + dateStr);
            e.printStackTrace();
            return null;
        }
    }

    private java.sql.Timestamp convertToDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date parsed = format.parse(dateTimeStr);
            return new java.sql.Timestamp(parsed.getTime());
        } catch (ParseException e) {
            System.out.println("Error al parsear la fecha y hora: " + dateTimeStr);
            e.printStackTrace();
            return null;
        }
    }

    private Double parseDouble(String numberStr) {
        if (numberStr == null || numberStr.trim().isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(numberStr);
        } catch (NumberFormatException e) {
            System.out.println("Error al parsear el número: " + numberStr);
            e.printStackTrace();
            return 0.0;
        }
    }
}
