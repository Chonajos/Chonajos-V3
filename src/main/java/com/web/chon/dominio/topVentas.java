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
public class topVentas implements Serializable{
    private static final long serialVersionUID = 1L;
    private String nombreSucursal;
    private String nombreVendedor;
    private String apPaternoVendedor;
    private String apMaternoVendedor;
    private BigDecimal idVendedor;
    private BigDecimal numVentas;
    private Date fechaInicio;
    private Date fechaFin;

    @Override
    public String toString() {
        return "topVentas{" + "nombreSucursal=" + nombreSucursal + ", nombreVendedor=" + nombreVendedor + ", apPaternoVendedor=" + apPaternoVendedor + ", apMaternoVendedor=" + apMaternoVendedor + ", idVendedor=" + idVendedor + ", numVentas=" + numVentas + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + '}';
    }

    
    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public String getApPaternoVendedor() {
        return apPaternoVendedor;
    }

    public void setApPaternoVendedor(String apPaternoVendedor) {
        this.apPaternoVendedor = apPaternoVendedor;
    }

    public String getApMaternoVendedor() {
        return apMaternoVendedor;
    }

    public void setApMaternoVendedor(String apMaternoVendedor) {
        this.apMaternoVendedor = apMaternoVendedor;
    }

    public BigDecimal getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(BigDecimal idVendedor) {
        this.idVendedor = idVendedor;
    }

    public BigDecimal getNumVentas() {
        return numVentas;
    }

    public void setNumVentas(BigDecimal numVentas) {
        this.numVentas = numVentas;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
}
