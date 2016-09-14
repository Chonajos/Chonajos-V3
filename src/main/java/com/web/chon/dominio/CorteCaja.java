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
public class CorteCaja implements Serializable {

    private BigDecimal idCorteCajaPk;
    private BigDecimal idCajaFk;
    private Date fecha;
    private BigDecimal cantChequesAnt;
    private BigDecimal montoChequesAnt;
    private BigDecimal saldoAnterior;
    private BigDecimal cantChequesNuevos;
    private BigDecimal montoChequesNuevos;
    private BigDecimal saldoNuevo;
    private String comentarios;
    private BigDecimal idUserFk;
    private BigDecimal idStatusFk;

    private String nombreCaja;
    private String nombreUsuario;
    private String nombreStatus;

    @Override
    public String toString() {
        return "CorteCaja{" + "idCorteCajaPk=" + idCorteCajaPk + ", idCajaFk=" + idCajaFk + ", fecha=" + fecha + ", cantChequesAnt=" + cantChequesAnt + ", montoChequesAnt=" + montoChequesAnt + ", saldoAnterior=" + saldoAnterior + ", cantChequesNuevos=" + cantChequesNuevos + ", montoChequesNuevos=" + montoChequesNuevos + ", saldoNuevo=" + saldoNuevo + ", comentarios=" + comentarios + ", idUserFk=" + idUserFk + ", idStatusFk=" + idStatusFk + ", nombreCaja=" + nombreCaja + ", nombreUsuario=" + nombreUsuario + ", nombreStatus=" + nombreStatus + '}';
    }

    
    

    public void reset() {
        idCorteCajaPk = null;
        idCajaFk = null;
        fecha = null;
        cantChequesAnt = null;
        montoChequesAnt = null;
        saldoAnterior = null;
        cantChequesNuevos = null;
        montoChequesNuevos = null;
        saldoNuevo = null;
        comentarios = null;
        idUserFk = null;
        idStatusFk = null;
        nombreCaja = null;
        nombreUsuario = null;
        nombreStatus = null;

    }

    public String getNombreCaja() {
        return nombreCaja;
    }

    public void setNombreCaja(String nombreCaja) {
        this.nombreCaja = nombreCaja;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreStatus() {
        return nombreStatus;
    }

    public void setNombreStatus(String nombreStatus) {
        this.nombreStatus = nombreStatus;
    }
    
    

    public BigDecimal getIdCorteCajaPk() {
        return idCorteCajaPk;
    }

    public void setIdCorteCajaPk(BigDecimal idCorteCajaPk) {
        this.idCorteCajaPk = idCorteCajaPk;
    }

    public BigDecimal getIdCajaFk() {
        return idCajaFk;
    }

    public void setIdCajaFk(BigDecimal idCajaFk) {
        this.idCajaFk = idCajaFk;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getCantChequesAnt() {
        return cantChequesAnt;
    }

    public void setCantChequesAnt(BigDecimal cantChequesAnt) {
        this.cantChequesAnt = cantChequesAnt;
    }

    public BigDecimal getMontoChequesAnt() {
        return montoChequesAnt;
    }

    public void setMontoChequesAnt(BigDecimal montoChequesAnt) {
        this.montoChequesAnt = montoChequesAnt;
    }

    public BigDecimal getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public BigDecimal getCantChequesNuevos() {
        return cantChequesNuevos;
    }

    public void setCantChequesNuevos(BigDecimal cantChequesNuevos) {
        this.cantChequesNuevos = cantChequesNuevos;
    }

    public BigDecimal getMontoChequesNuevos() {
        return montoChequesNuevos;
    }

    public void setMontoChequesNuevos(BigDecimal montoChequesNuevos) {
        this.montoChequesNuevos = montoChequesNuevos;
    }

    public BigDecimal getSaldoNuevo() {
        return saldoNuevo;
    }

    public void setSaldoNuevo(BigDecimal saldoNuevo) {
        this.saldoNuevo = saldoNuevo;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public BigDecimal getIdUserFk() {
        return idUserFk;
    }

    public void setIdUserFk(BigDecimal idUserFk) {
        this.idUserFk = idUserFk;
    }

    public BigDecimal getIdStatusFk() {
        return idStatusFk;
    }

    public void setIdStatusFk(BigDecimal idStatusFk) {
        this.idStatusFk = idStatusFk;
    }
}
