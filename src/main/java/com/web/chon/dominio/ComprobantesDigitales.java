/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author jramirez
 */
public class ComprobantesDigitales implements Serializable{
    private static final long serialVersionUID = 1L;
    private BigDecimal idComprobantesDigitalesPk;
    private BigDecimal idTipoFk;
    private BigDecimal idLlaveFk;
    private Date fecha; 
    private String nombre;
    private StreamedContent productImage;

    @Override
    public String toString() {
        return "ComprobantesDigitales{" + "idComprobantesDigitalesPk=" + idComprobantesDigitalesPk + ", idTipoFk=" + idTipoFk + ", idLlaveFk=" + idLlaveFk + ", fecha=" + fecha + ", nombre=" + nombre + ", productImage=" + productImage + '}';
    }
    
    

    public BigDecimal getIdComprobantesDigitalesPk() {
        return idComprobantesDigitalesPk;
    }

    public void setIdComprobantesDigitalesPk(BigDecimal idComprobantesDigitalesPk) {
        this.idComprobantesDigitalesPk = idComprobantesDigitalesPk;
    }

    public BigDecimal getIdTipoFk() {
        return idTipoFk;
    }

    public void setIdTipoFk(BigDecimal idTipoFk) {
        this.idTipoFk = idTipoFk;
    }

    public BigDecimal getIdLlaveFk() {
        return idLlaveFk;
    }

    public void setIdLlaveFk(BigDecimal idLlaveFk) {
        this.idLlaveFk = idLlaveFk;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public StreamedContent getProductImage() {
        return productImage;
    }

    public void setProductImage(StreamedContent productImage) {
        this.productImage = productImage;
    }
    
    
    
}
