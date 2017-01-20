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
 * @author marcogante
 */
public class CuentaBancaria implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idCuentaBancariaPk;
    private String nombreBanco;
    private BigDecimal cuenta;
    private BigDecimal idUsuarioFk;
    private BigDecimal  clabe;
    private BigDecimal sucursal;
    private String razon;
    

    @Override
    public String toString() {
        return "CuentaBancaria{" + "idCuentaBancariaPk=" + idCuentaBancariaPk + ", nombreBanco=" + nombreBanco + ", cuenta=" + cuenta + '}';
    }

    public BigDecimal getClabe() {
        return clabe;
    }

    public void setClabe(BigDecimal clabe) {
        this.clabe = clabe;
    }

    public BigDecimal getSucursal() {
        return sucursal;
    }

    public void setSucursal(BigDecimal sucursal) {
        this.sucursal = sucursal;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    
    

    public BigDecimal getIdUsuarioFk() {
        return idUsuarioFk;
    }

    public void setIdUsuarioFk(BigDecimal idUsuarioFk) {
        this.idUsuarioFk = idUsuarioFk;
    }
    
    
    public BigDecimal getIdCuentaBancariaPk() {
        return idCuentaBancariaPk;
    }

    public void setIdCuentaBancariaPk(BigDecimal idCuentaBancariaPk) {
        this.idCuentaBancariaPk = idCuentaBancariaPk;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public BigDecimal getCuenta() {
        return cuenta;
    }

    public void setCuenta(BigDecimal cuenta) {
        this.cuenta = cuenta;
    }
    
    
    


    
    
    

    
    
}
