/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author JesusAlfredo
 */
public class Caja implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idCajaPk;
    private BigDecimal idSucursalFk;
    private String nombre;
    private BigDecimal tipo;
    private BigDecimal cuenta;
    private BigDecimal monto;

    @Override
    public String toString() {
        return "Caja{" + "idCajaPk=" + idCajaPk + ", idSucursalFk=" + idSucursalFk + ", nombre=" + nombre + ", tipo=" + tipo + ", cuenta=" + cuenta + ", monto=" + monto + '}';
    }

    
    
    public void reset() {
        idCajaPk = null;
        idSucursalFk = null;
        nombre = null;
        tipo = null;
        cuenta = null;
        monto = null;
    }

    public BigDecimal getIdCajaPk() {
        return idCajaPk;
    }

    public void setIdCajaPk(BigDecimal idCajaPk) {
        this.idCajaPk = idCajaPk;
    }

    public BigDecimal getIdSucursalFk() {
        return idSucursalFk;
    }

    public void setIdSucursalFk(BigDecimal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getTipo() {
        return tipo;
    }

    public void setTipo(BigDecimal tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getCuenta() {
        return cuenta;
    }

    public void setCuenta(BigDecimal cuenta) {
        this.cuenta = cuenta;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

}
