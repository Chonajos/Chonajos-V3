/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author freddy
 */
public class EntradaMercancia extends ValueObject implements Serializable {

    private BigDecimal idEmPK;
    private BigDecimal idProvedorFK;
    private BigDecimal movimiento;
    private Date fecha;
    private String remision;
    private BigDecimal idSucursalFK;
    private Date fechaRemision;
    private String comentariosGenerales;
    private String abreviacion;
    private String folio; //identificacion
    private BigDecimal idStatusFk;
    private BigDecimal kilosTotales;
    private BigDecimal kilosTotalesProvedor;
    private BigDecimal idCarroSucursal;
    private String nombreSucursal;
    private String nombreProvedor;
    private BigDecimal idUsuario;
    private Date fechaFiltroInicio;
    private Date fechaFiltroFin;
    private ArrayList<EntradaMercanciaProducto> listaProductos;
    private BigDecimal cantidadEmpaquesProvedor;
    private BigDecimal cantidadEmpaquesReales;

    @Override
    public String toString() {
        return "EntradaMercancia{" + "idEmPK=" + idEmPK + ", idProvedorFK=" + idProvedorFK + ", movimiento=" + movimiento + ", fecha=" + fecha + ", remision=" + remision + ", idSucursalFK=" + idSucursalFK + ", fechaRemision=" + fechaRemision + ", comentariosGenerales=" + comentariosGenerales + ", abreviacion=" + abreviacion + ", folio=" + folio + ", idStatusFk=" + idStatusFk + ", kilosTotales=" + kilosTotales + ", kilosTotalesProvedor=" + kilosTotalesProvedor + ", idCarroSucursal=" + idCarroSucursal + ", nombreSucursal=" + nombreSucursal + ", nombreProvedor=" + nombreProvedor + ", idUsuario=" + idUsuario + ", fechaFiltroInicio=" + fechaFiltroInicio + ", fechaFiltroFin=" + fechaFiltroFin + ", listaProductos=" + listaProductos + ", cantidadEmpaquesProvedor=" + cantidadEmpaquesProvedor + ", cantidadEmpaquesReales=" + cantidadEmpaquesReales + '}';
    }

    
    @Override
    public void reset() {
        idEmPK = null;
        idProvedorFK = null;
        movimiento = null;
        fecha = null;
        remision = null;
        //idSucursalFK = new BigDecimal(-1);
        abreviacion = null;
        folio = null;
        idStatusFk = null;
        kilosTotales = null;
        kilosTotalesProvedor = null;
        nombreSucursal = null;
        nombreProvedor = null;
        fechaRemision = null;
        comentariosGenerales = null;
        idCarroSucursal = null;
        cantidadEmpaquesReales = null;
        cantidadEmpaquesProvedor = null;

    }

    public ArrayList<EntradaMercanciaProducto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(ArrayList<EntradaMercanciaProducto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public BigDecimal getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public BigDecimal getIdCarroSucursal() {
        return idCarroSucursal;
    }

    public void setIdCarroSucursal(BigDecimal idCarroSucursal) {
        this.idCarroSucursal = idCarroSucursal;
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

    public BigDecimal getCantidadEmpaquesProvedor() {
        return cantidadEmpaquesProvedor;
    }

    public void setCantidadEmpaquesProvedor(BigDecimal cantidadEmpaquesProvedor) {
        this.cantidadEmpaquesProvedor = cantidadEmpaquesProvedor;
    }

    public BigDecimal getCantidadEmpaquesReales() {
        return cantidadEmpaquesReales;
    }

    public void setCantidadEmpaquesReales(BigDecimal cantidadEmpaquesReales) {
        this.cantidadEmpaquesReales = cantidadEmpaquesReales;
    }

}
