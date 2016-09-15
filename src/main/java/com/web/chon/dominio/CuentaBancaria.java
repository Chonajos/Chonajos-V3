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

    @Override
    public String toString() {
        return "CuentaBancaria{" + "idCuentaBancariaPk=" + idCuentaBancariaPk + ", nombreBanco=" + nombreBanco + ", cuenta=" + cuenta + '}';
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
