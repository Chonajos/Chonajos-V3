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
 * @author marcogante
 */
public class RegistroEntradaSalida implements Serializable{
    private static final long serialVersionUID = 1L;
    private BigDecimal idRegEntSalPk;
    private Date fechaEntrada;
    private Date fechaSalida;
    private double latitudEntrada;
    private double latitudSalida;
    private double longitudEntrada;
    private double longitudSalida;
    private BigDecimal idUsuarioFk;
    private BigDecimal idSucursalFk;

    @Override
    public String toString() {
        return "RegistroEntradaSalida{" + "idRegEntSalPk=" + idRegEntSalPk + ", fechaEntrada=" + fechaEntrada + ", fechaSalida=" + fechaSalida + ", latitudEntrada=" + latitudEntrada + ", latitudSalida=" + latitudSalida + ", longitudEntrada=" + longitudEntrada + ", longitudSalida=" + longitudSalida + ", idUsuarioFk=" + idUsuarioFk + ", idSucursalFk=" + idSucursalFk + '}';
    }

    public BigDecimal getIdRegEntSalPk() {
        return idRegEntSalPk;
    }

    public void setIdRegEntSalPk(BigDecimal idRegEntSalPk) {
        this.idRegEntSalPk = idRegEntSalPk;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public double getLatitudEntrada() {
        return latitudEntrada;
    }

    public void setLatitudEntrada(double latitudEntrada) {
        this.latitudEntrada = latitudEntrada;
    }

    public double getLatitudSalida() {
        return latitudSalida;
    }

    public void setLatitudSalida(double latitudSalida) {
        this.latitudSalida = latitudSalida;
    }

    public double getLongitudEntrada() {
        return longitudEntrada;
    }

    public void setLongitudEntrada(double longitudEntrada) {
        this.longitudEntrada = longitudEntrada;
    }

    public double getLongitudSalida() {
        return longitudSalida;
    }

    public void setLongitudSalida(double longitudSalida) {
        this.longitudSalida = longitudSalida;
    }

 
    public BigDecimal getIdUsuarioFk() {
        return idUsuarioFk;
    }

    public void setIdUsuarioFk(BigDecimal idUsuarioFk) {
        this.idUsuarioFk = idUsuarioFk;
    }

    public BigDecimal getIdSucursalFk() {
        return idSucursalFk;
    }

    public void setIdSucursalFk(BigDecimal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
    }
    
    
}
