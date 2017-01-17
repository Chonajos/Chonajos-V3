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
 * @author jramirez
 */
public class DominioCajas implements Serializable{
    private static final long serialVersionUID = 1L;
    private BigDecimal idCaja;
    private String nombreCaja;
    private BigDecimal nombreSucursal;
    private BigDecimal aperturaEfectivo;
    private BigDecimal aperturaCheques;
    private BigDecimal aperturaCuentas;
    private BigDecimal cheques;
    private BigDecimal efectivo;
    private BigDecimal cuentas;

    @Override
    public String toString() {
        return "DominioCajas{" + "idCaja=" + idCaja + ", nombreCaja=" + nombreCaja + ", nombreSucursal=" + nombreSucursal + ", aperturaEfectivo=" + aperturaEfectivo + ", aperturaCheques=" + aperturaCheques + ", aperturaCuentas=" + aperturaCuentas + ", cheques=" + cheques + ", efectivo=" + efectivo + ", cuentas=" + cuentas + '}';
    }
    

    public BigDecimal getAperturaEfectivo() {
        return aperturaEfectivo;
    }

    public void setAperturaEfectivo(BigDecimal aperturaEfectivo) {
        this.aperturaEfectivo = aperturaEfectivo;
    }

    public BigDecimal getAperturaCheques() {
        return aperturaCheques;
    }

    public void setAperturaCheques(BigDecimal aperturaCheques) {
        this.aperturaCheques = aperturaCheques;
    }

    public BigDecimal getAperturaCuentas() {
        return aperturaCuentas;
    }

    public void setAperturaCuentas(BigDecimal aperturaCuentas) {
        this.aperturaCuentas = aperturaCuentas;
    }

   
    

    public BigDecimal getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(BigDecimal idCaja) {
        this.idCaja = idCaja;
    }

    public String getNombreCaja() {
        return nombreCaja;
    }

    public void setNombreCaja(String nombreCaja) {
        this.nombreCaja = nombreCaja;
    }

    public BigDecimal getCheques() {
        return cheques;
    }

    public void setCheques(BigDecimal cheques) {
        this.cheques = cheques;
    }

    public BigDecimal getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(BigDecimal efectivo) {
        this.efectivo = efectivo;
    }

    public BigDecimal getCuentas() {
        return cuentas;
    }

    public void setCuentas(BigDecimal cuentas) {
        this.cuentas = cuentas;
    }

    public BigDecimal getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(BigDecimal nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }
    
    
    
}
