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

    @Override
    public String toString() {
        return "EntrasaSalida{" + "idEntradaSalidaPk=" + idEntradaSalidaPk + ", idCajaFk=" + idCajaFk + ", tipoES=" + tipoES + ", fecha=" + fecha + ", idConceptoFk=" + idConceptoFk + ", comentarios=" + comentarios + ", monto=" + monto + '}';
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
