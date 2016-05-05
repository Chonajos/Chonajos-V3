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
 * @author freddy
 */
public class VentaMayoreo implements Serializable{
    private static final long serialVersionUID = 1L;
    private BigDecimal idVentaMayoreoPk;
    private BigDecimal idClienteFk;
    private BigDecimal idVendedorFK;
    private Date fechaVenta;
    private Date fechaPromesaPago;
    private BigDecimal idStatusFk;
    private Date fechaPago;
    private BigDecimal idSucursalFk;
    private BigDecimal idtipoVentaFk;

    @Override
    public String toString() {
        return "VentaMayoreo{" + "idVentaMayoreoPk=" + idVentaMayoreoPk + ", idClienteFk=" + idClienteFk + ", idVendedorFK=" + idVendedorFK + ", fechaVenta=" + fechaVenta + ", fechaPromesaPago=" + fechaPromesaPago + ", idStatusFk=" + idStatusFk + ", fechaPago=" + fechaPago + ", idSucursalFk=" + idSucursalFk + ", idtipoVentaFk=" + idtipoVentaFk + '}';
    }

   
    public BigDecimal getIdtipoVentaFk() {
        return idtipoVentaFk;
    }

    public void setIdtipoVentaFk(BigDecimal idtipoVentaFk) {
        this.idtipoVentaFk = idtipoVentaFk;
    }

    
    
    public BigDecimal getIdVentaMayoreoPk() {
        return idVentaMayoreoPk;
    }

    public void setIdVentaMayoreoPk(BigDecimal idVentaMayoreoPk) {
        this.idVentaMayoreoPk = idVentaMayoreoPk;
    }

    public BigDecimal getIdClienteFk() {
        return idClienteFk;
    }

    public void setIdClienteFk(BigDecimal idClienteFk) {
        this.idClienteFk = idClienteFk;
    }

    public BigDecimal getIdVendedorFK() {
        return idVendedorFK;
    }

    public void setIdVendedorFK(BigDecimal idVendedorFK) {
        this.idVendedorFK = idVendedorFK;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Date getFechaPromesaPago() {
        return fechaPromesaPago;
    }

    public void setFechaPromesaPago(Date fechaPromesaPago) {
        this.fechaPromesaPago = fechaPromesaPago;
    }

    public BigDecimal getIdStatusFk() {
        return idStatusFk;
    }

    public void setIdStatusFk(BigDecimal idStatusFk) {
        this.idStatusFk = idStatusFk;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public BigDecimal getIdSucursalFk() {
        return idSucursalFk;
    }

    public void setIdSucursalFk(BigDecimal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
    }
    
    
    
    
}
