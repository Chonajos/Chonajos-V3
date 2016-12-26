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
public class Apartado implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigDecimal idApartadoPk;
    private BigDecimal idVentaMayoreoFk;
    private BigDecimal idVentaMenudeoFk;
    private BigDecimal monto;
    private Date fecha;
    private BigDecimal idCajeroFk;
    private BigDecimal idStatus;

    @Override
    public String toString() {
        return "Apartado{" + "idApartadoPk=" + idApartadoPk + ", idVentaMayoreoFk=" + idVentaMayoreoFk + ", idVentaMenudeoFk=" + idVentaMenudeoFk + ", monto=" + monto + ", fecha=" + fecha + ", idCajeroFk=" + idCajeroFk + ", idStatus=" + idStatus + '}';
    }
    

    public BigDecimal getIdApartadoPk() {
        return idApartadoPk;
    }

    public void setIdApartadoPk(BigDecimal idApartadoPk) {
        this.idApartadoPk = idApartadoPk;
    }

    public BigDecimal getIdVentaMayoreoFk() {
        return idVentaMayoreoFk;
    }

    public void setIdVentaMayoreoFk(BigDecimal idVentaMayoreoFk) {
        this.idVentaMayoreoFk = idVentaMayoreoFk;
    }

    public BigDecimal getIdVentaMenudeoFk() {
        return idVentaMenudeoFk;
    }

    public void setIdVentaMenudeoFk(BigDecimal idVentaMenudeoFk) {
        this.idVentaMenudeoFk = idVentaMenudeoFk;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getIdCajeroFk() {
        return idCajeroFk;
    }

    public void setIdCajeroFk(BigDecimal idCajeroFk) {
        this.idCajeroFk = idCajeroFk;
    }

    public BigDecimal getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(BigDecimal idStatus) {
        this.idStatus = idStatus;
    }
    
    
    
}
