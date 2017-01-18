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
    private BigDecimal idUsuarioFK;
    private String nombre;
    private BigDecimal montoInicial;
    private String nombreSucursal;

    @Override
    public String toString() {
        return "Caja{" + "idCajaPk=" + idCajaPk + ", idSucursalFk=" + idSucursalFk + ", idUsuarioFK=" + idUsuarioFK + ", nombre=" + nombre + ", montoInicial=" + montoInicial + '}';
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
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

    public BigDecimal getIdUsuarioFK() {
        return idUsuarioFK;
    }

    public void setIdUsuarioFK(BigDecimal idUsuarioFK) {
        this.idUsuarioFK = idUsuarioFK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(BigDecimal montoInicial) {
        this.montoInicial = montoInicial;
    }
    
    
    


  
     
}
