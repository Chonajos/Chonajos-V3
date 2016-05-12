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
public class EntradaMercancia2 extends ValueObject implements Serializable {

    private BigDecimal idEmPK;
    private BigDecimal idProvedorFK;
    private BigDecimal movimiento;
    private Date fecha;
    private String remision;
    private BigDecimal idSucursalFK;
    private Date fechaFiltroInicio;
    private Date fechaFiltroFin;
    private Date fechaRemision;
    private String comentariosGenerales;
    private String abreviacion;
    private String folio; //identificacion
    private BigDecimal idStatusFk;
    private BigDecimal kilosTotales;
    private BigDecimal kilosTotalesProvedor;
    

    private String nombreSucursal;
    private String nombreProvedor;

    @Override
    public String toString() {
        return "EntradaMercancia2{" + "idEmPK=" + idEmPK + ", idProvedorFK=" + idProvedorFK + ", movimiento=" + movimiento + ", fecha=" + fecha + ", remision=" + remision + ", idSucursalFK=" + idSucursalFK + ", fechaFiltroInicio=" + fechaFiltroInicio + ", fechaFiltroFin=" + fechaFiltroFin + ", fechaRemision=" + fechaRemision + ", comentariosGenerales=" + comentariosGenerales + ", abreviacion=" + abreviacion + ", folio=" + folio + ", idStatusFk=" + idStatusFk + ", kilosTotales=" + kilosTotales + ", kilosTotalesProvedor=" + kilosTotalesProvedor + ", nombreSucursal=" + nombreSucursal + ", nombreProvedor=" + nombreProvedor + '}';
    }

    @Override
    public void reset() {
        idEmPK = null;
        idProvedorFK = null;
        movimiento = null;
        fecha = null;
        remision = null;
        idSucursalFK = new BigDecimal(-1);
        fechaFiltroFin = null;
        fechaFiltroInicio = null;
        abreviacion = null;
        folio = null;
        idStatusFk = null;
        kilosTotales = null;
        kilosTotalesProvedor = null;
        nombreSucursal = null;
        nombreProvedor = null;
        fechaRemision=null;
    comentariosGenerales=null;

    }

    
    
    
    public Date getFechaRemision() {
        return fechaRemision;
    }

    public void setFechaRemision(Date fechaRemision) {
        this.fechaRemision = fechaRemision;
    }

    public String getComentariosGenerales() {
        return comentariosGenerales;
    }

    public void setComentariosGenerales(String comentariosGenerales) {
        this.comentariosGenerales = comentariosGenerales;
    }

    public BigDecimal getIdStatusFk() {
        return idStatusFk;
    }

    public void setIdStatusFk(BigDecimal idStatusFk) {
        this.idStatusFk = idStatusFk;
    }

    

    public BigDecimal getKilosTotalesProvedor() {
        return kilosTotalesProvedor;
    }

    public void setKilosTotalesProvedor(BigDecimal kilosTotalesProvedor) {
        this.kilosTotalesProvedor = kilosTotalesProvedor;
    }

    public BigDecimal getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(BigDecimal movimiento) {
        this.movimiento = movimiento;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public BigDecimal getKilosTotales() {
        return kilosTotales;
    }

    public void setKilosTotales(BigDecimal kilosTotales) {
        this.kilosTotales = kilosTotales;
    }

    public String getAbreviacion() {
        return abreviacion;
    }

    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }

    public Date getFechaFiltroInicio() {
        return fechaFiltroInicio;
    }

    public void setFechaFiltroInicio(Date fechaFiltroInicio) {
        this.fechaFiltroInicio = fechaFiltroInicio;
    }

    public Date getFechaFiltroFin() {
        return fechaFiltroFin;
    }

    public void setFechaFiltroFin(Date fechaFiltroFin) {
        this.fechaFiltroFin = fechaFiltroFin;
    }

    public BigDecimal getIdEmPK() {
        return idEmPK;
    }

    public void setIdEmPK(BigDecimal idEmPK) {
        this.idEmPK = idEmPK;
    }

    public BigDecimal getIdProvedorFK() {
        return idProvedorFK;
    }

    public void setIdProvedorFK(BigDecimal idProvedorFK) {
        this.idProvedorFK = idProvedorFK;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getRemision() {
        return remision;
    }

    public void setRemision(String remision) {
        this.remision = remision;
    }

    public BigDecimal getIdSucursalFK() {
        return idSucursalFK;
    }

    public void setIdSucursalFK(BigDecimal idSucursalFK) {
        this.idSucursalFK = idSucursalFK;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public String getNombreProvedor() {
        return nombreProvedor;
    }

    public void setNombreProvedor(String nombreProvedor) {
        this.nombreProvedor = nombreProvedor;
    }
    
    

}
