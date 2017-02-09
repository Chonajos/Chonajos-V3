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
 * @author jramirez
 */
public class HistorialCrediticio implements Serializable{
    private static final long serialVersionUID = 1L;
    private BigDecimal idRegistroPk;
    private Date fechaCargo;
    private Date fechaAbono;
    private BigDecimal folioCargo;
    private BigDecimal folioAbono;
    private BigDecimal importeCargo;
    private BigDecimal importeAbono;
    private BigDecimal saldos;
    private BigDecimal tipo;

    public BigDecimal getTipo() {
        return tipo;
    }

    public void setTipo(BigDecimal tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getIdRegistroPk() {
        return idRegistroPk;
    }

    public void setIdRegistroPk(BigDecimal idRegistroPk) {
        this.idRegistroPk = idRegistroPk;
    }

    public Date getFechaCargo() {
        return fechaCargo;
    }

    public void setFechaCargo(Date fechaCargo) {
        this.fechaCargo = fechaCargo;
    }

    public Date getFechaAbono() {
        return fechaAbono;
    }

    public void setFechaAbono(Date fechaAbono) {
        this.fechaAbono = fechaAbono;
    }

    public BigDecimal getFolioCargo() {
        return folioCargo;
    }

    public void setFolioCargo(BigDecimal folioCargo) {
        this.folioCargo = folioCargo;
    }

    public BigDecimal getFolioAbono() {
        return folioAbono;
    }

    public void setFolioAbono(BigDecimal folioAbono) {
        this.folioAbono = folioAbono;
    }

    public BigDecimal getImporteCargo() {
        return importeCargo;
    }

    public void setImporteCargo(BigDecimal importeCargo) {
        this.importeCargo = importeCargo;
    }

    public BigDecimal getImporteAbono() {
        return importeAbono;
    }

    public void setImporteAbono(BigDecimal importeAbono) {
        this.importeAbono = importeAbono;
    }

    public BigDecimal getSaldos() {
        return saldos;
    }

    public void setSaldos(BigDecimal saldos) {
        this.saldos = saldos;
    }
    
    
    
    
    
    
}
