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
public class OperacionesCuentas implements Serializable {
    private BigDecimal idOperacionCuenta;
    private BigDecimal idCuentaFk;
    private BigDecimal idCajaOrigenFk;
    private BigDecimal idConceptoFk;
    private Date fecha;
    private BigDecimal idStatusFk;
    private BigDecimal idUserFk;
    private String comentarios;
    private BigDecimal monto;
    private BigDecimal entradaSalida;

    @Override
    public String toString() {
        return "OperacionesCuentas{" + "idOperacionCuenta=" + idOperacionCuenta + ", idCuentaFk=" + idCuentaFk + ", idCajaOrigenFk=" + idCajaOrigenFk + ", idConceptoFk=" + idConceptoFk + ", fecha=" + fecha + ", idStatusFk=" + idStatusFk + ", idUserFk=" + idUserFk + ", comentarios=" + comentarios + ", monto=" + monto + ", entradaSalida=" + entradaSalida + '}';
    }

    
    
    public BigDecimal getIdOperacionCuenta() {
        return idOperacionCuenta;
    }

    public void setIdOperacionCuenta(BigDecimal idOperacionCuenta) {
        this.idOperacionCuenta = idOperacionCuenta;
    }

    public BigDecimal getIdCuentaFk() {
        return idCuentaFk;
    }

    public void setIdCuentaFk(BigDecimal idCuentaFk) {
        this.idCuentaFk = idCuentaFk;
    }

    public BigDecimal getIdCajaOrigenFk() {
        return idCajaOrigenFk;
    }

    public void setIdCajaOrigenFk(BigDecimal idCajaOrigenFk) {
        this.idCajaOrigenFk = idCajaOrigenFk;
    }

    public BigDecimal getIdConceptoFk() {
        return idConceptoFk;
    }

    public void setIdConceptoFk(BigDecimal idConceptoFk) {
        this.idConceptoFk = idConceptoFk;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getIdStatusFk() {
        return idStatusFk;
    }

    public void setIdStatusFk(BigDecimal idStatusFk) {
        this.idStatusFk = idStatusFk;
    }

    public BigDecimal getIdUserFk() {
        return idUserFk;
    }

    public void setIdUserFk(BigDecimal idUserFk) {
        this.idUserFk = idUserFk;
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

    public BigDecimal getEntradaSalida() {
        return entradaSalida;
    }

    public void setEntradaSalida(BigDecimal entradaSalida) {
        this.entradaSalida = entradaSalida;
    }
    
    
    
    
}
