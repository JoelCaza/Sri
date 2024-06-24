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
        ComprobanteDAO comprobanteDAO = new ComprobanteDAO();
        List<Comprobante> comprobantes = comprobanteDAO.obtenerComprobantes();
        request.setAttribute("comprobantes", comprobantes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/dashboard.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("archivo");
        InputStream fileContent = filePart.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent));
        String line;
        boolean isFirstLine = true;

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
            }
        }

        doGet(request, response);
    }

    private Comprobante parseComprobanteFromLine(String line) {
        String[] data = line.split("\t");
        if (data.length < 12) {
            return null; // Línea no válida, ignorar
        }

        Comprobante comprobante = new Comprobante();
        comprobante.setRucEmisor(data[0]);
        comprobante.setRazonSocialEmisor(data[1]);
        comprobante.setTipoComprobante(data[2]);
        comprobante.setSerieComprobante(data[3]);
        comprobante.setClaveAcceso(data[4]);
        comprobante.setFechaAutorizacion(convertToDate(data[5]));
        comprobante.setFechaEmision(convertToDate(data[6]));
        comprobante.setIdentificacionReceptor(data[7]);
        comprobante.setValorSinImpuestos(parseDouble(data[8]));
        comprobante.setIva(parseDouble(data[9]));
        comprobante.setImporteTotal(parseDouble(data[10]));
        comprobante.setNumeroDocumentoModificado(data[11]);
        return comprobante;
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
            e.printStackTrace();
            return null; // Manejar el error adecuadamente
        }
    }

    private Double parseDouble(String numberStr) {
        if (numberStr == null || numberStr.trim().isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(numberStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0; // Manejar el error adecuadamente
        }
    }
}
