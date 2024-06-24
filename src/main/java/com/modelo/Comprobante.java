package com.modelo;

import java.util.Date;

public class Comprobante {
    private int id;
    private String rucEmisor;
    private String razonSocialEmisor;
    private String tipoComprobante;
    private String serieComprobante;
    private String claveAcceso;
    private Date fechaAutorizacion;
    private Date fechaEmision;
    private String identificacionReceptor;
    private double valorSinImpuestos;
    private double iva;
    private double importeTotal;
    private String numeroDocumentoModificado;

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRucEmisor() {
        return rucEmisor;
    }

    public void setRucEmisor(String rucEmisor) {
        this.rucEmisor = rucEmisor;
    }

    public String getRazonSocialEmisor() {
        return razonSocialEmisor;
    }

    public void setRazonSocialEmisor(String razonSocialEmisor) {
        this.razonSocialEmisor = razonSocialEmisor;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getSerieComprobante() {
        return serieComprobante;
    }

    public void setSerieComprobante(String serieComprobante) {
        this.serieComprobante = serieComprobante;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public Date getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(Date fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getIdentificacionReceptor() {
        return identificacionReceptor;
    }

    public void setIdentificacionReceptor(String identificacionReceptor) {
        this.identificacionReceptor = identificacionReceptor;
    }

    public double getValorSinImpuestos() {
        return valorSinImpuestos;
    }

    public void setValorSinImpuestos(double valorSinImpuestos) {
        this.valorSinImpuestos = valorSinImpuestos;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public String getNumeroDocumentoModificado() {
        return numeroDocumentoModificado;
    }

    public void setNumeroDocumentoModificado(String numeroDocumentoModificado) {
        this.numeroDocumentoModificado = numeroDocumentoModificado;
    }
}