/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author JesusAlfredo
 */
public class EntradaSalida implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idEntradaSalidaPk;
    private BigDecimal idCajaFk;
    private BigDecimal tipoES;
    private Date fecha;
    private BigDecimal idConceptoFk;
    private String comentarios;
    private BigDecimal monto;
    private String nombreCaja;
    private String nombreConcepto;
    private String nombreES;
    private BigDecimal numero;
    private BigDecimal idTipoOperacionFk;
    private String nombreOperacion;
    private BigDecimal idCajaOrigen;
    private BigDecimal idCajaDestino;

    public void reset() {
        idEntradaSalidaPk = null;
        idCajaFk = null;
        tipoES = null;
        fecha = null;
        idConceptoFk = null;
        comentarios = null;
        monto = null;
        nombreCaja = null;
        nombreConcepto = null;
        nombreES = null;
        numero = null;
        idTipoOperacionFk=null;
        nombreOperacion=null;
        idCajaOrigen=null;
        idCajaDestino=null;
    }

    @Override
    public String toString() {
        return "EntradaSalida{" + "idEntradaSalidaPk=" + idEntradaSalidaPk + ", idCajaFk=" + idCajaFk + ", tipoES=" + tipoES + ", fecha=" + fecha + ", idConceptoFk=" + idConceptoFk + ", comentarios=" + comentarios + ", monto=" + monto + ", nombreCaja=" + nombreCaja + ", nombreConcepto=" + nombreConcepto + ", nombreES=" + nombreES + ", numero=" + numero + ", idTipoOperacionFk=" + idTipoOperacionFk + ", nombreOperacion=" + nombreOperacion + ", idCajaOrigen=" + idCajaOrigen + ", idCajaDestino=" + idCajaDestino + '}';
    }

    
    public BigDecimal getIdCajaOrigen() {
        return idCajaOrigen;
    }

    public void setIdCajaOrigen(BigDecimal idCajaOrigen) {
        this.idCajaOrigen = idCajaOrigen;
    }

    public BigDecimal getIdCajaDestino() {
        return idCajaDestino;
    }

    public void setIdCajaDestino(BigDecimal idCajaDestino) {
        this.idCajaDestino = idCajaDestino;
    }

   

    public String getNombreOperacion() {
        return nombreOperacion;
    }

    public void setNombreOperacion(String nombreOperacion) {
        this.nombreOperacion = nombreOperacion;
    }

    
    
    public BigDecimal getIdTipoOperacionFk() {
        return idTipoOperacionFk;
    }

    public void setIdTipoOperacionFk(BigDecimal idTipoOperacionFk) {
        this.idTipoOperacionFk = idTipoOperacionFk;
    }

    
    public String getNombreCaja() {
        return nombreCaja;
    }

    public void setNombreCaja(String nombreCaja) {
        this.nombreCaja = nombreCaja;
    }

    public String getNombreConcepto() {
        return nombreConcepto;
    }

    public void setNombreConcepto(String nombreConcepto) {
        this.nombreConcepto = nombreConcepto;
    }

    public String getNombreES() {
        return nombreES;
    }

    public void setNombreES(String nombreES) {
        this.nombreES = nombreES;
    }

    public BigDecimal getNumero() {
        return numero;
    }

    public void setNumero(BigDecimal numero) {
        this.numero = numero;
    }

    public BigDecimal getIdEntradaSalidaPk() {
        return idEntradaSalidaPk;
    }

    public void setIdEntradaSalidaPk(BigDecimal idEntradaSalidaPk) {
        this.idEntradaSalidaPk = idEntradaSalidaPk;
    }

    public BigDecimal getIdCajaFk() {
        return idCajaFk;
    }

    public void setIdCajaFk(BigDecimal idCajaFk) {
        this.idCajaFk = idCajaFk;
    }

    public BigDecimal getTipoES() {
        return tipoES;
    }

    public void setTipoES(BigDecimal tipoES) {
        this.tipoES = tipoES;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getIdConceptoFk() {
        return idConceptoFk;
    }

    public void setIdConceptoFk(BigDecimal idConceptoFk) {
        this.idConceptoFk = idConceptoFk;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    

}
